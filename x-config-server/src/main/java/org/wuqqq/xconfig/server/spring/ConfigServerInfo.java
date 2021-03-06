package org.wuqqq.xconfig.server.spring;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author wuqi 2017/5/20.
 */
@Component
@ConfigurationProperties(prefix = "netty")
@Validated
public class ConfigServerInfo {

    @NotBlank
    private String serverIP;

    @NotNull
    private int serverPort;

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public int getServerPort() {
        return serverPort;
    }
}
