package com.hikvision.ga.commons.server.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by wuqi5 on 2017/5/20.
 */
@ConfigurationProperties(prefix = "netty")
public class ConfigServerInfo {
    private String serverPort;
    private String serverIP;
}
