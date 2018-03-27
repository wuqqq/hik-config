package org.wuqqq.xconfig.common.net;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应消息
 * Created by wuqi5 on 2017/4/24.
 */
public class Response {
    private int id;

    private int command;

    private boolean success;

    private Map<String, Object> result;

    private Map<String, Object> error;

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public static ResponseBuilder success() {
        return new ResponseBuilder().success();
    }

    public static ResponseBuilder fail() {
        return new ResponseBuilder().fail();
    }

    public static class ResponseBuilder {
        Response response = new Response();

        public ResponseBuilder success() {
            response.setSuccess(true);
            return this;
        }

        public ResponseBuilder fail() {
            response.setSuccess(false);
            return this;
        }

        public ResponseBuilder id(int id) {
            response.setId(id);
            return this;
        }

        public ResponseBuilder command(int command) {
            response.setCommand(command);
            return this;
        }

        public ResponseBuilder withErrorCode(int code) {
            response.putError("code", code);
            return this;
        }

        public ResponseBuilder withErrorMessage(String message) {
            response.putError("message", message);
            return this;
        }

        public ResponseBuilder withError(String key, Object value) {
            response.putError(key, value);
            return this;
        }

        public ResponseBuilder withResult(String key, Object value) {
            response.putResult(key, value);
            return this;
        }

        public Response build() {
            return response;
        }
    }

    public void putResult(String key, Object value) {
        if (result == null) {
            result = new HashMap<>();
        }
        result.put(key, value);
    }

    public void deleteResult(String key) {
        if (result != null) {
            result.remove(key);
        }
    }

    public String errorMessage() {
        if (error == null) {
            return null;
        }
        return (String) error.get("message");
    }

    public Integer errorCode() {
        if (error == null) {
            return null;
        }
        return (Integer) error.get("code");
    }

    public void putError(String key, Object value) {
        if (error == null) {
            error = new HashMap<>();
        }
        error.put(key, value);
    }

    public void deleteError(String key) {
        if (error != null) {
            error.remove(key);
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getError() {
        return error;
    }

    public void setError(Map<String, Object> error) {
        this.error = error;
    }
}
