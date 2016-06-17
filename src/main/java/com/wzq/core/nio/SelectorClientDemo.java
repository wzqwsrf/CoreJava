package com.wzq.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-17 17:25:39
 * Description: client
 */
public class SelectorClientDemo {

    public void selector() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.socket().connect(new InetSocketAddress("127.0.0.1", 8888));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear();
            String message = "这是一条信息" + System.currentTimeMillis();
            byteBuffer.put(message.getBytes());
            byteBuffer.flip();
            channel.write(byteBuffer);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SelectorClientDemo selectorClientDemo = new SelectorClientDemo();
        for (int i = 0; i < 5; i++) {
            selectorClientDemo.selector();
        }
    }
}
