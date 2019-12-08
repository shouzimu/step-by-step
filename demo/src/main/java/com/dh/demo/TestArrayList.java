package com.dh.demo;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TestArrayList {

    @Test
    public void testAarratList() {
        String[] arr = {"a", "b", "c"};
        List<String> list = Arrays.asList(arr);

        ArrayList<String> arrayList = new ArrayList<>(3);
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        Object[] obs = arrayList.toArray();

        System.out.println(obs);
    }


}
