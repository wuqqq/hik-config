package com.hikvision.ga.commons.client.net;

import com.hikvision.ga.commons.config.net.MessageDecoder;
import com.hikvision.ga.commons.config.net.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuqi5 on 2017/5/11.
 */
public class ConfigClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigClient.class);

    /**
     * 当前重连次数
     */
    private int currentRetryTimes = 0;

    /**
     * 最大重连次数
     */
    private int maxRetryTimes = Integer.MAX_VALUE;

    /**
     * 最长重连时间间隔
     */
    private int maxRetryInterval = 2 * 60 * 1000;

    /**
     * 读超时时间
     */
    private int readIdleTime = 15;

    /**
     * 写超时时间
     */
    private int writeIdleTime = 5;

    private final EventLoopGroup group = new NioEventLoopGroup(1, new ClientThreadFactory());

    private final ScheduledExecutorService reconnectExecutor = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "hik-config-clientReconnect-thread");
        }
    });

    public ChannelFuture connect() {
        return buildBootstrap(group).connect();
    }

    private Bootstrap buildBootstrap(EventLoopGroup group) {
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5 * 1000)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new IdleStateHandler(readIdleTime, writeIdleTime, Math.max(readIdleTime, writeIdleTime)))
                                .addLast(new MessageDecoder(1024 * 1024, 2, 4))
                                .addLast(new MessageEncoder())
                                .addLast(new LoginAuthReqHandler(ConfigClient.this))
                                .addLast(new ClientHandler(ConfigClient.this));
                    }
                });
        return b;
    }

    public void channelActive() {
        currentRetryTimes = 0;
    }

    public void channelUnregistered(ChannelHandlerContext ctx) {
        if (currentRetryTimes > maxRetryTimes) {
            return;
        }
        int currentRetryInterval = 5000 * (currentRetryTimes >= 30 ? 30 : 1 << currentRetryTimes++);
        if (currentRetryInterval <= 0 || currentRetryInterval > maxRetryInterval) {
            currentRetryInterval = maxRetryInterval;
        }
        reconnectExecutor.schedule(() ->
                        buildBootstrap(ctx.channel().eventLoop()).connect().addListener(future -> {
                            if (!future.isSuccess()) {
                                LOGGER.error("connect to {}:{} failed, {}", "", "", future.cause());
                            } else {
                                LOGGER.info("connect to {}:{} success", "", "");
                            }
                        })
                , currentRetryInterval, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 31; ) {
            System.out.println(1 << i++);
        }
    }
}
