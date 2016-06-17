package com.wzq.core.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-16 15:49:23
 * Description: NIO Buffer
 */
public class BufferDemo {
    public void buffer(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        for (int i = 0; i < byteBuffer.capacity(); i++) {
            byteBuffer.put((byte)i);
        }

        byteBuffer.flip();
        while(byteBuffer.hasRemaining()){
            int num = byteBuffer.get();
            System.out.println(num);
        }
    }

    public void buffer1(){
        try {
            RandomAccessFile accessFile = new RandomAccessFile("/home/zhenqingwang/solution.java","rw");
            FileChannel channel = accessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);
            int bytesRead = channel.read(byteBuffer);
            while(bytesRead != -1){
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    System.out.println((char)byteBuffer.get());
                }
                byteBuffer.clear();
                bytesRead = channel.read(byteBuffer);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BufferDemo bufferDemo = new BufferDemo();
//        bufferDemo.buffer();
        bufferDemo.buffer1();
    }
}
