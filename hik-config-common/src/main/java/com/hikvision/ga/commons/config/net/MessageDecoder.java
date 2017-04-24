package com.hikvision.ga.commons.config.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 消息解码
 * Created by wuqi5 on 2017/4/24.
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder{

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        // 如果frame为null，表示读半包
        if (frame == null) {
            return null;
        }
        return null;
    }
}
