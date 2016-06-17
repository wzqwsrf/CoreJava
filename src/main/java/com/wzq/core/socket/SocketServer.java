package com.wzq.core.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-16 17:27:27
 * Description: Socket
 */
public class SocketServer {

    private static int port = 8899;

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new WangzqRunnable(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class WangzqRunnable implements Runnable {

        private Socket socket;
        private BufferedReader bufferedReader;
        private PrintWriter printWriter;

        public WangzqRunnable(Socket socket) throws IOException {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream());
        }

        @Override
        public void run() {
            try {
                String name = Thread.currentThread().getName();
                String result = bufferedReader.readLine();
                System.out.println(result);
                while (!result.equals("bye")) {
                    System.out.println("Client(" + name + ")say:" + result);
                    printWriter.println("Hello Client(" + name + "), I am Server!");
                    printWriter.flush();
                    result = bufferedReader.readLine();
                }
                printWriter.println("Client(" + name + ") exit!");
                printWriter.flush();
                printWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer();
        socketServer.init();
    }
}
