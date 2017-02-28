package com.dh.socket;


import com.dh.model.AddressBookProtos;

import java.io.InputStream;
import java.net.Socket;

public class ProtoServerHandler implements Runnable {
    private Socket client = null;

    public ProtoServerHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //获取Socket的输入流，用来接收从客户端发送过来的数据
            InputStream inputStream = client.getInputStream();

            AddressBookProtos.AddressBook book =
                AddressBookProtos.AddressBook.parseFrom(inputStream);
            book.writeDelimitedTo(client.getOutputStream());
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
