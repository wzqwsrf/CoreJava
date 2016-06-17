package com.wzq.core.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author: zhenqing.wang <wangzhenqing1008@163.com>
 * Date: 2016-06-16 17:42:51
 * Description: Client
 */
public class SocketClient {

    private static int port = 8899;

    public void init() {
        try {
            Socket socket = new Socket("127.0.0.1", port);
            socket.setSoTimeout(60000);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String result = "";
            while (!result.contains("bye")){
                BufferedReader sysBuff = new BufferedReader(new InputStreamReader(System.in));
                printWriter.println(sysBuff.readLine());
                printWriter.flush();
                result = bufferedReader.readLine();
                System.out.println("Server say : " + result);
            }

            printWriter.close();
            bufferedReader.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient();
        socketClient.init();
    }
}
