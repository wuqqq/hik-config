package org.wuqqq.xconfig.server.net;

import org.wuqqq.xconfig.common.net.MessageDecoder;
import org.wuqqq.xconfig.common.net.MessageEncoder;
import org.wuqqq.xconfig.server.spring.ConfigServerInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by wuqi5 on 2017/5/15.
 */
@Service
public class ConfigServer {
    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private final NioEventLoopGroup workGroup = new NioEventLoopGroup();

    @Autowired
    private ConfigServerInfo configServerInfo;

    @PostConstruct
    public void init() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true).childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new MessageDecoder(1024 * 1024, 2, 4))
                                .addLast(new MessageEncoder());
                    }
                });
        bootstrap.bind(configServerInfo.getServerIP(), configServerInfo.getServerPort()).sync().channel().closeFuture().addListener(future -> {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        });
    }

    @PreDestroy
    public void destory() {
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

}
