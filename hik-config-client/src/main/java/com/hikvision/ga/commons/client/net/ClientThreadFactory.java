package com.hikvision.ga.commons.client.net;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuqi5 on 2017/5/15.
 */
public class ClientThreadFactory implements ThreadFactory {
    private final AtomicLong count = new AtomicLong(1);
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(String.format("hik-config-clientNioEventLoop-thread-%d", count.getAndIncrement()));
        return t;
    }
}
