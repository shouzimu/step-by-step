package com.dh;

import com.alibaba.fastjson.JSON;
import com.dh.serialize.model.AddressBookProtos;
import com.dh.serialize.model.Car;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.function.Function;

public class TestCar {



    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        Car c = new Car("丰田", "红色", 100);
        ObjectOutputStream out =
            new ObjectOutputStream(new FileOutputStream("/Users/lihong/Downloads/out"));
        out.writeObject(c);
        out.close();
        System.out.println(JSON.toJSONString(c));
        ObjectInputStream ins =
            new ObjectInputStream(new FileInputStream("/Users/lihong/Downloads/out"));

        Car c2 = (Car) ins.readObject();
        System.out.println(c2);
    }



    @Test
    public void testArraySerialization() throws IOException, ClassNotFoundException {
        //        String[] strings = {"aaa", "bbb", "ccc","ddd"};
        //        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/Users/lihong/Downloads/out_array"));
        //        out.writeObject(strings);
        //        out.close();

        ObjectInputStream ins =
            new ObjectInputStream(new FileInputStream("/Users/lihong/Downloads/out_array"));

        String[] array2 = (String[]) ins.readObject();
        for (String s : array2) {
            System.out.println(s);
        }

    }

    @Test
    public void testAnd() {
        System.out.println(1 & 2);
        System.out.println(3 & 2);
        System.out.println(4 & 3);
    }

    @Test
    public void testOr() {

        System.out.println(1 & 2);
        System.out.println(3 & 2);
        System.out.println(4 & 3);
    }

    @Test
    public void testGetModifier() {
        int pb = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;
        int pv = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;
        System.out.println(Integer.toBinaryString(Modifier.PUBLIC));
        System.out.println(Integer.toBinaryString(Modifier.PRIVATE));
        System.out.println(Integer.toBinaryString(Modifier.PROTECTED));
        System.out.println(Integer.toBinaryString(Modifier.STATIC));
        System.out.println(Integer.toBinaryString(Modifier.FINAL));
        System.out.println("---");
        System.out.println(Integer.toBinaryString(Modifier.FINAL | Modifier.STATIC));

        System.out.println("---");
        System.out.println(Integer.toBinaryString(pb));
        System.out.println(Integer.toBinaryString(pv));
    }


    @Test
    public void timeTest() {
        Car c = new Car("丰田", "红色", 100);
        methodExeTime(TestCar::jdkSerialization, c);
        methodExeTime(TestCar::fastJsonSerialization, c);
        methodExeTime(TestCar::protoSerialization, c);
        System.out.println("\n");

        methodExeTime(TestCar::jdkDeSerialization, c);
        methodExeTime(TestCar::fastJsonDeSerialization, c);
    }



    public void methodExeTime(Function<Car, String> r, Car car) {
        long start = System.currentTimeMillis();
        String m = r.apply(car);
        long end = System.currentTimeMillis();
        System.out.println(m + " exe time is " + (end - start) + " ms");
    }

    public static String jdkSerialization(Car car) {
        try {
            ObjectOutputStream out =
                new ObjectOutputStream(new FileOutputStream("/Users/lihong/Downloads/out"));
            out.writeObject(car);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "jdk serial";
    }

    public static String fastJsonSerialization(Car car) {
        JSON.toJSONString(car);
        return "fastJson serial";
    }

    public static String fastJsonDeSerialization(Car car) {
        String str = "{\"brand\":\"丰田\",\"color\":\"红色\",\"price\":100}";
        car = JSON.parseObject(str, Car.class);
        return "fastJson deserial";
    }

    public static String jdkDeSerialization(Car car) {
        try {
            ObjectInputStream ins =
                new ObjectInputStream(new FileInputStream("/Users/lihong/Downloads/out"));
            car = (Car) ins.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "jdk deserial";
    }


    public static String protoSerialization(Car car) {
        try {
            AddressBookProtos.Person person =AddressBookProtos.Person.newBuilder().setId(1234)
                .setName("John Doe")
                .setEmail("jdoe@example.com").addPhones(
                    AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("555-4321")
                        .setType(AddressBookProtos.Person.PhoneType.HOME))
                .build();
            System.out.println(person.toString());
            System.out.println(person.toByteString());
            FileOutputStream output = new FileOutputStream("/Users/lihong/Downloads/proto_out");
            person.writeTo(output);
        } catch (IOException e) {
            e.printStackTrace();
        }
         return "protobuf serial";

    }

    @Test
    public void testSout(){
        protoSerialization(null);
    }


}
