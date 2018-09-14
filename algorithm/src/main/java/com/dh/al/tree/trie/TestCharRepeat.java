package com.dh.al.tree.trie;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestCharRepeat {


    @Test
    public void testTime() {
        List<String> strings = Arrays.asList(("a,b,cc,b,b,cc,bb" +
                ",dd,aa,c,a,b,cc,b,b,cc,bb,dd,aa,c,a,b,cc,b,b,cc,bb,dd" +
                "a,b,cc,b,b,cc,bb,dd,aa,c,a,b,cc,b,b,cc,bb,dd,aa,c,a,b,cc,b,b,cc," +
                "bb,dd,aa,c,a,b,cc,b,b,cc,bb,dd," +
                "aa,c,a,b,cc,b,b,cc,bb,dd,aa,c,a,b,cc,b,b,cc,bb,dd,aa,c").split(","));

        int time = 100000;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            testStringRepeatSort(strings);
        }
        long t2 = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            sort(strings);
        }
        long t3 = System.currentTimeMillis();
        for (int i = 0; i < time; i++) {
            testStringRepeatSortNormal(strings);
        }
        long t4 = System.currentTimeMillis();

        System.out.println(t2 - t1);
        System.out.println(t3 - t2);
        System.out.println(t4 - t3);
    }


    public void testStringRepeatSort(List<String> strList) {
        Map<String, Long> sortMap = strList.stream().collect(Collectors.groupingBy(t -> t, Collectors.counting()));
        List<Map.Entry<String, Long>> entryList = new ArrayList<>(sortMap.entrySet());
        entryList.sort((t1, t2) -> (int) (t2.getValue() - t1.getValue()));
    }

    public void testStringRepeatSortNormal(List<String> strList) {
        Map<String, Integer> sortMap = new HashMap<>();
        for (String s : strList) {
            Integer count = sortMap.get(s);
            if (null != count) {
                count++;
            } else {
                count = 1;
            }
            sortMap.put(s, count);
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(sortMap.entrySet());
        entryList.sort((t1, t2) -> t2.getValue() - t1.getValue());
    }

    // 排序
    public List<String> sort(List<String> list) {
        List<String> listResult = new ArrayList<>();
        if (list.isEmpty()) {
            return listResult;
        }
        //
        List<OrderAssist> listOrder = new ArrayList<>();
        for (String s : list) {
            OrderAssist tmp = new OrderAssist(s, 1);
            if (listOrder.contains(tmp)) {
                listOrder.get(listOrder.indexOf(tmp)).count++;
            } else {
                listOrder.add(tmp);
            }
        }
        Collections.sort(listOrder);
        //
        for (OrderAssist object : listOrder) {
            listResult.add(object.name);
        }
        //
        return listResult;
    }

    //排序辅助类
    static class OrderAssist implements Comparable<OrderAssist> {
        public String name;
        public int count;

        public OrderAssist(String name, int count) {
            this.name = name;
            this.count = count;
        }

        @Override
        public boolean equals(Object object) {
            return this.name.equals(((OrderAssist) object).name);
        }

        @Override
        public int compareTo(OrderAssist entity) {
            return entity.count - count;
        }
    }
}
