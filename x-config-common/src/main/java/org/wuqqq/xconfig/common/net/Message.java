package org.wuqqq.xconfig.common.net;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息
 * Created by wuqi on 2017/4/21.
 */
public class Message {

    /**
     * 协议版本
     */
    private short version = 1;
    /**
     * 消息类型
     */
    private byte type;

    /**
     * 消息体
     */
    private byte[] data;

    private Map<String, String> attachment;

    public static MessageBuilder oneway() {
        return builder().type(MessageType.ONEWAY);
    }

    public static MessageBuilder request() {
        return builder().type(MessageType.REQUEST);
    }

    public static MessageBuilder response() {
        return builder().type(MessageType.RESPONSE);
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        Message message;

        public MessageBuilder() {
            this.message = new Message();
        }

        public MessageBuilder version(short version) {
            message.setVersion(version);
            return this;
        }

        public MessageBuilder type(byte type) {
            message.setType(type);
            return this;
        }

        public MessageBuilder data(byte[] data) {
            message.setData(data);
            return this;
        }

        public MessageBuilder withAttachment(String key, String value) {
            message.putAttachment(key, value);
            return this;
        }

        public MessageBuilder jsonData(Object object) {
            message.setData(JSON.toJSONBytes(object, SerializerFeature.DisableCircularReferenceDetect));
            return this;
        }

        public Message build() {
            return message;
        }
    }

    public Request dataToRequest(){
        return JSON.parseObject(data, Request.class);
    }

    public Response dataToResponse(){
        return JSON.parseObject(data, Response.class);
    }

    public Request dataToOneway(){
        return JSON.parseObject(data, Oneway.class);
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Map<String, String> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, String> attachment) {
        this.attachment = attachment;
    }

    public void putAttachment(String key, String value) {
        if (attachment == null)
            attachment = new HashMap<>();
        attachment.put(key, value);
    }
}
