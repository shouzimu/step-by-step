package com.dh;

import com.alibaba.fastjson.JSON;
import com.dh.serialize.model.CarExt;
import org.testng.annotations.Test;

import java.io.*;

public class TestCarExt {



    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        CarExt c = new CarExt("丰田", "红色", 100);
        ObjectOutputStream out =
            new ObjectOutputStream(new FileOutputStream("/Users/lihong/Downloads/test/out3"));
        out.writeObject(c);
        out.close();
        ObjectInputStream ins =
            new ObjectInputStream(new FileInputStream("/Users/lihong/Downloads/test/out3"));

        CarExt c2 = (CarExt) ins.readObject();
        System.out.println(c2);
    }





}
