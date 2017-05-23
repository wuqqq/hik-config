package com.hikvision.ga.commons.server.net;

import com.hikvision.ga.commons.server.spring.ConfigServerInfo;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by wuqi5 on 2017/5/15.
 */
@Service
public class configServer {
    private final NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private final NioEventLoopGroup workGroup = new NioEventLoopGroup();

    @Autowired
    private ConfigServerInfo configServerInfo;

    @PostConstruct
    public void init() {
        ServerBootstrap bootstrap = new ServerBootstrap();
    }

}
