package com.wzq.core.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-17 11:39:00
 * Description: Selector
 */
public class SelectorDemo {

    public void selector() throws IOException {

        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int readyChannel = selector.select();
            if (readyChannel == 0) {
                continue;
            }
            Set selectionKeys = selector.selectedKeys();
            Iterator iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("isAcceptable");
                    channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("Connected =====" + socketChannel.socket().getInetAddress());
                } else if (selectionKey.isReadable()) {
                    System.out.println("isReadable");
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int byteReader = socketChannel.read(byteBuffer);
                    while(byteReader != -1){
                        byteBuffer.flip();
                        byte[] dst = new byte[byteBuffer.limit()];
                        byteBuffer.get(dst);
                        System.out.println(new String(dst));
                    }
                }
                iterator.remove();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        SelectorDemo selectorDemo = new SelectorDemo();
        selectorDemo.selector();
    }

}
