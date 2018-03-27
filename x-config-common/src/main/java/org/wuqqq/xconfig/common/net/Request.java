package org.wuqqq.xconfig.common.net;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求消息
 * Created by wuqi on 2017/4/24.
 */
public class Request {
    private int id;

    private int command;

    private Map<String, Object> data;

    public static RequestBuilder builder() {
        return new RequestBuilder();
    }

    public static class RequestBuilder {
        private Request request;

        public RequestBuilder() {
            request = new Request();
        }

        public RequestBuilder id(int id) {
            request.setId(id);
            return this;
        }

        public RequestBuilder command(int command) {
            request.setCommand(command);
            return this;
        }

        public RequestBuilder data(Map<String, Object> data) {
            request.setData(data);
            return this;
        }

        public RequestBuilder withData(String key, Object value) {
            request.putData(key, value);
            return this;
        }

        public Request build() {
            return request;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void putData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    public Object dataValue(String key){
        if (data != null)
            return data.get(key);
        return null;
    }

    public Object deleteData(String key){
        if (data != null)
            return data.remove(key);
        return null;
    }
}
