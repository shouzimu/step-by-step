package com.dh.netty.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Proxy {
    private final int port;

    public Proxy(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        Proxy server = new Proxy(port);
        server.start();
    }

    public void start() {
        final ProxyHandler handler = new ProxyHandler();
        EventLoopGroup loopGroup = new NioEventLoopGroup();//创建eventloopgroup
        try {
            //创建Server-BootStrap
            ServerBootstrap bootstrap = new ServerBootstrap();
            //使用指定端口设置套接字
            //添加一个EchoServerHandler到Chanel的ChanelPipeline
            bootstrap.group(loopGroup).channel(NioServerSocketChannel.class).localAddress(port).childHandler(new ChannelInitializer() {
                protected void initChannel(Channel ch) {
                    ch.pipeline().addLast(handler);
                }
            });
            //异步绑定服务器，调用sync()方法阻塞等待直到所有绑定完成
            ChannelFuture f = bootstrap.bind().sync();
            //获取Channel的CloseFuture,并且租塞当前线程直到它完成
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭EventLoopGroup，并释放所有资源
            loopGroup.shutdownGracefully();

        }
    }
}
