package com.hikvision.ga.commons.client.net;

import com.hikvision.ga.commons.config.net.*;
import com.hikvision.ga.commons.config.util.WhiteListUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wuqi5 on 2017/5/15.
 */
public class LoginAuthReqHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthReqHandler.class);

    private final ConfigClient client;

    public LoginAuthReqHandler(ConfigClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        client.channelActive();
        //登录请求
        Request loginReq = Request.builder().command(Commands.LOGIN_REQ).build();
        ctx.writeAndFlush(Message.request().jsonData(loginReq).build());
        super.channelActive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        client.channelUnregistered(ctx);
        super.channelUnregistered(ctx);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (!(evt instanceof IdleStateEvent)) {
            ctx.fireUserEventTriggered(evt);
            return;
        }
        if (((IdleStateEvent) evt).state() == IdleState.WRITER_IDLE) {
            Request heartbeatReq = Request.builder().command(Commands.HEARTBEAT).build();
            ctx.writeAndFlush(Message.request().jsonData(heartbeatReq).build());
        } else if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
            // 读超时，直接关闭连接，尝试重连
            LOGGER.error("wait for server response time out, please check the network");
            ctx.channel().close();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        if (msg.getType() == MessageType.RESPONSE) {
            Response resp = msg.dataToResponse();
            if (!resp.isSuccess()) {
                LOGGER.info("客户端：{}登录失败", resp.getResult().get(WhiteListUtil.CLIENT_IP));
                ctx.channel().close();
            } else {
                LOGGER.info("客户端：{}登录成功", resp.getResult().get(WhiteListUtil.CLIENT_IP));
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }
}
