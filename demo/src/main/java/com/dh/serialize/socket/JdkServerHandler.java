package com.dh.serialize.socket;


import com.dh.serialize.model.AddressBook;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class JdkServerHandler implements Runnable {
    private Socket client = null;

    public JdkServerHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ins = new ObjectInputStream(client.getInputStream());
            AddressBook book = (AddressBook) ins.readObject();

            System.out.println(book);
            ObjectOutputStream output =
                new ObjectOutputStream(client.getOutputStream());
            output.writeObject(book);
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
