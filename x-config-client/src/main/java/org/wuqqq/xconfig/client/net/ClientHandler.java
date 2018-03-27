package org.wuqqq.xconfig.client.net;

import org.wuqqq.xconfig.common.net.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuqi 2017/5/15.
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
