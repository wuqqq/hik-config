package com.hikvision.ga.commons.config.net;

import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by wuqi5 on 2017/4/21.
 */
public class Message {
    /**
     * 协议版本
     */
    private short version = 1;
    /**
     * 消息类型
     */
    private short type;

    /**
     * 消息头长度
     */
    private int length;

    /**
     * 消息体
     */
    private byte[] data;

    static Object obj = null;
    private static class MessageBuilder {

    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            String str = "abc";
            while (obj == null) {
                System.out.println(str += "asdsfvasfdgvagfgvafgfasafae");
                System.out.println("-------------------------------------------");
            }
        });
        Thread t2 = new Thread(()->{
            obj = new Object();
        });
        t1.setPriority(1);
        t2.setPriority(9);
        t1.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        t2.start();
    }


}
