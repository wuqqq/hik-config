package org.wuqqq.xconfig.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息解码 Created by wuqi on 2017/4/24.
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

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
        Message msg = new Message();
        int len = in.readInt();
        msg.setVersion(in.readShort());
        msg.setType(in.readByte());
        int dataSize;
        if ((dataSize = in.readInt()) > 0) {
            byte[] dataArr = new byte[dataSize];
            in.readBytes(dataArr);
            msg.setData(dataArr);
        }
        int attachmentSize;
        if ((attachmentSize = in.readInt()) > 0) {
            Map<String, String> attachment = new HashMap<>(attachmentSize);
            for (int i = 0; i < attachmentSize; i++) {
                // 解析key
                int keySize = in.readInt();
                byte[] keyArr = new byte[keySize];
                in.readBytes(keyArr);
                String key = new String(keyArr, "UTF-8");
                // 解析value
                int valSize = in.readInt();
                byte[] valArr = new byte[valSize];
                in.readBytes(valArr);
                String val = new String(valArr, "UTF-8");
                attachment.put(key, val);
            }
            msg.setAttachment(attachment);
        }
        return msg;
    }
}
