package com.hikvision.ga.commons.server.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Created by wuqi5 on 2017/5/20.
 */
@ConfigurationProperties(prefix = "netty")
@Validated
public class ConfigServerInfo {
    @NotNull
    private String serverIP;

    @NotNull
    private String serverPort;

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
