package com.dh.netty.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class ClientDemo {

    public static void main(String[] args) throws IOException {
        try (Socket client = new Socket(InetAddress.getLocalHost(), 8888)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            reader.lines().forEach(s -> {
                System.out.println(s);
            });
        }
    }
}
