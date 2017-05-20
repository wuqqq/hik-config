package com.hikvision.ga.commons.client.net;

import com.hikvision.ga.commons.config.net.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by wuqi5 on 2017/5/15.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    private final ConfigClient client;

    public ClientHandler(ConfigClient client) {
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    }
}
