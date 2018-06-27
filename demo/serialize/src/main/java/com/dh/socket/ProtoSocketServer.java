package com.dh.socket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProtoSocketServer {

    private static final ExecutorService pool = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws Exception {
        //服务端在20005端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(9090);
        Socket client = null;
        boolean f = true;
        while (f) {
            //等待客户端的连接，如果没有获取连接
            client = server.accept();
            System.out.println("与客户端连接成功！");
            //将新来的连接加入线程池
            pool.execute(new ProtoServerHandler(client));
        }
        server.close();
    }
}
