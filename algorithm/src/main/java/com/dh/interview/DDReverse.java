package com.dh.interview;

import org.junit.Test;

public class DDReverse {

    String reserve(String str) {
        if (null == str) {
            return null;
        }
        if (str.isEmpty()) {
            return "";
        }
        return reserve(str.substring(1)) + str.charAt(0);
    }

    @Test
    public void testReserve() {
        String str = "111222333";
        System.out.println(reserve(str));
    }
}
