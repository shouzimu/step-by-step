package com.dh;

import com.dh.model.AddressBook;
import com.dh.model.AddressBookProtos;
import com.dh.util.DemoObjectFactory;

import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Function;

public class TestSocket {


    @Test
    public void testTime() throws Exception {
        TestSocket ts = new TestSocket();
        int times = 1;
        //  ts.executeTime(ts::testProtoSocket, times);
        ts.executeTime(ts::testJdkSocket, times);

    }

    public void executeTime(Function<Integer, String> f, int times) {
        f.apply(times);
    }


    public String testProtoSocket(int times) {

        try {
            long start = System.currentTimeMillis();

            for (int i = 0; i < times; i++) {


                Socket client = new Socket("127.0.0.1", 9090);


                AddressBookProtos.AddressBook book = DemoObjectFactory.newProtoAddressBook();
                //        out.writeUInt32NoTag(book.getSerializedSize());
                book.writeTo(client.getOutputStream());

                //获取Socket的输入流，用来接收从服务端发送过来的数据
                InputStream inputStream = client.getInputStream();

                try {
                    AddressBookProtos.AddressBook book2 =
                            AddressBookProtos.AddressBook.parseFrom(inputStream);

                    if (client != null) {
                        //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
                        // client.close(); //只关闭socket，其关联的输入输出流也会被关闭
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            long end = System.currentTimeMillis();

            System.out.println("proto\t" + times + " time is\t" + (end - start));
        } catch (Exception e) {

        }
        return "proto";
    }


    public String testJdkSocket(int times) {


        try {
            long start = System.currentTimeMillis();

            for (int i = 0; i < times; i++) {


                Socket client = new Socket("127.0.0.1", 20006);
                AddressBook book = DemoObjectFactory.newNormalAddressBook();

                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                out.writeObject(book);

                //获取Socket的输入流，用来接收从服务端发送过来的数据
                InputStream inputStream = client.getInputStream();

                try {
                    ObjectInputStream in = new ObjectInputStream(inputStream);
                    AddressBook book2 = (AddressBook) in.readObject();

                    System.out.println(book2.getPersons().size());
                    if (client != null) {
                        //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
                        // client.close(); //只关闭socket，其关联的输入输出流也会被关闭
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            long end = System.currentTimeMillis();

            System.out.println("jdk\t" + times + " time is\t" + (end - start));
        } catch (Exception e) {

        }
        return "jdk";

    }

}
