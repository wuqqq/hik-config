package org.wuqqq.xconfig.common.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息解码
 * Created by wuqi on 2017/4/24.
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
        msg.setVersion(in.readShort());
        int len = in.readInt();
        msg.setType(in.readByte());
        int size = in.readInt();
        if (size > 0) {
            Map<String, String> attachment = new HashMap<>(size);
            for (int i = 0; i < size; i++) {
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
        int dataSize = in.readInt();
        if (dataSize > 0) {
            byte[] dataArr = new byte[dataSize];
            in.readBytes(dataArr);
            msg.setData(dataArr);
        }
        return msg;
    }
}
