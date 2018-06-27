package com.dh;

import com.alibaba.fastjson.JSON;
import com.dh.model.AddressBook;
import com.dh.model.AddressBookProtos;
import com.dh.util.DemoObjectFactory;
import org.testng.annotations.ObjectFactory;
import org.testng.annotations.Test;

import java.io.*;
import java.util.function.Function;

public class SerializableTest {

    @Test
    public void testTime() {
        SerializableTest test = new SerializableTest();
        int n = 50;
        methodExeTime(test::protoSerial, n);
        methodExeTime(test::fastJsonSerial, n);
        methodExeTime(test::jdkSerial, n);

        System.out.println("\n");

        methodExeTime(test::protoDeSerial, n);
        methodExeTime(test::fastJsonDeSerial, n);
        methodExeTime(test::jdkDeSerial, n);

    }

    public void methodExeTime(Function<Integer, String> f, int n) {
        long start = System.currentTimeMillis();
        String r = f.apply(n);
        long end = System.currentTimeMillis();
        System.out.println(r + " " + n + " time " + (end - start) + " ms");
    }

    public String protoSerial(int n) {
        try {
            for (int i = 0; i < n; i++) {
                FileOutputStream output =
                    new FileOutputStream("/Users/lihong/Downloads/test/proto_out");
                AddressBookProtos.AddressBook book = DemoObjectFactory.newProtoAddressBook();
                book.writeTo(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "protoSerial";

    }

    public String protoDeSerial(int n) {
        try {
            for (int i = 0; i < n; i++) {
                FileInputStream input =
                    new FileInputStream("/Users/lihong/Downloads/test/proto_out");
                AddressBookProtos.AddressBook book = AddressBookProtos.AddressBook.parseFrom(input);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "protoDeSerial";

    }

    public String fastJsonSerial(int n) {
        for (int i = 0; i < n; i++) {
            AddressBook book = DemoObjectFactory.newNormalAddressBook();
            JSON.toJSONString(book);
        }
        return "fastJsonSerial";
    }

    public String fastJsonDeSerial(int n) {
        for (int i = 0; i < n; i++) {
            String json =
                "{\"persons\":[{\"email\":\"jdoe@example.com\",\"id\":13958235,\"name\":\"John Doe\",\"phones\":[{\"number\":\"555-4321\",\"type\":\"HOME\"}]}]}\n";
            JSON.parseObject(json, AddressBook.class);

        }
        return "fastJsonDeSerial";
    }

    public String jdkSerial(int n) {
        try {
            for (int i = 0; i < n; i++) {
                AddressBook book = DemoObjectFactory.newNormalAddressBook();

                ObjectOutputStream output =
                    new ObjectOutputStream(new FileOutputStream("/Users/lihong/Downloads/test/out"));
                output.writeObject(book);
                output.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "jdkSerial";
    }


    public String jdkDeSerial(int n) {
        try {
            for (int i = 0; i < n; i++) {
                ObjectInputStream ins =
                    new ObjectInputStream(new FileInputStream("/Users/lihong/Downloads/test/out"));
                ins.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "jdkDeSerial";
    }

}
