package org.wuqqq.xconfig.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.Map;

/**
 * 消息编码
 * Created by wuqi5 on 2017/4/24.
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        out.writeShort(msg.getVersion());
        out.writeByte(msg.getType());
        out.writeInt(msg.getAttachment().size());
        if (!msg.getAttachment().isEmpty()) {
            for (Map.Entry<String, String> entry : msg.getAttachment().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (key != null && key.length() > 0 && value != null && value.length() > 0) {
                    out.writeInt(key.getBytes("UTF-8").length);
                    out.writeBytes(key.getBytes("UTF-8"));
                    out.writeInt(value.getBytes("UTF-8").length);
                    out.writeBytes(value.getBytes("UTF-8"));
                }
            }
        }
        if (msg.getData() != null && msg.getData().length > 0) {
            out.writeInt(msg.getData().length);
            out.writeBytes(msg.getData());
        } else {
            out.writeInt(0);
        }
        out.setInt(2, out.readableBytes());
        ctx.flush();
    }
}
