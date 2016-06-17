package com.wzq.core.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-17 16:36:18
 * Description: Channel
 */
public class ChannelDemo {

    public void channel1() {
        try {
            RandomAccessFile fromFile = new RandomAccessFile("/home/zhenqingwang/solution.java", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("/home/zhenqingwang/toSolution.java", "rw");
            FileChannel toChannel = toFile.getChannel();
            long count = fromChannel.size();
            fromChannel.transferTo(0, count, toChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void channel2() {
        try {
            RandomAccessFile fromFile = new RandomAccessFile("/home/zhenqingwang/solution.java", "rw");
            FileChannel fromChannel = fromFile.getChannel();
            RandomAccessFile toFile = new RandomAccessFile("/home/zhenqingwang/toSolution.java", "rw");
            FileChannel toChannel = toFile.getChannel();
            long count = fromChannel.size();
            toChannel.transferFrom(fromChannel, 0, count);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChannelDemo channelDemo = new ChannelDemo();
//        channelDemo.channel1();
        channelDemo.channel2();
    }
}
