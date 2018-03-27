package org.wuqqq.xconfig.client.net;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wuqi 2017/5/15.
 */
public class ClientThreadFactory implements ThreadFactory {
    private final AtomicLong count = new AtomicLong(1);
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(String.format("x-config-clientNioEventLoop-thread-%d", count.getAndIncrement()));
        return t;
    }
}
