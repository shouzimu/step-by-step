package com.dh.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer extends Thread {

    @Override
    public void run() {
        try {
            //创建select和channel
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(InetAddress.getLocalHost(), 8888));
            channel.configureBlocking(false);

            //注册到select，并说明关键点
            channel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                selector.select();//阻塞等待就绪的channel，这是关键点之一
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey k = selectionKeyIterator.next();
                    sayHello((ServerSocketChannel) k.channel());
                    selectionKeyIterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sayHello(ServerSocketChannel channel) throws IOException {
        try (SocketChannel client = channel.accept();) {
            String msg = "Hello world";
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
            client.write(buffer);

        }
    }

    public static void main(String[] args) {
        NIOServer server = new NIOServer();
        server.start();
    }
}
