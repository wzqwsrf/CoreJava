package com.wzq.core.nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

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

    public static void main(String[] args) {
        BufferDemo bufferDemo = new BufferDemo();
        bufferDemo.buffer();
    }
}
