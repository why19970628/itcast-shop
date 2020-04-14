package com.itheima.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);

        while (true){
            Socket socket = serverSocket.accept();
            //InputStream inputStream = socket.getInputStream();
            //OutputStream outputStream = socket.getOutputStream();
            //socketWrapper wrapper new socketWrapper(socket);
            new Thread(()->{
                //拿到wrapper
                //在这里开始 取出输入流 输出流
                //封装 request  response
                //找到servlet  调用servlet的service 吧request和response穿进去
            }).start();
        }


    }
}
