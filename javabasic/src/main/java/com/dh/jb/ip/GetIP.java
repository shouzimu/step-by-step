package com.dh.jb.ip;

import java.io.IOException;
import java.util.Arrays;

public class GetIP {

    public static void main(String[] args) {

        try {
            City city = new City("/Users/lihong/Downloads/17monipdb/17monipdb.datx"); // 城市库

            long start = System.currentTimeMillis();
            System.out.println(Arrays.toString(city.find("140.207.54.75")));
            long end = System.currentTimeMillis();
            System.out.println(end-start);

            System.out.println(Arrays.toString(city.find("114.242.249.118")));
            System.out.println(Arrays.toString(city.find("182.254.86.156")));
            System.out.println(Arrays.toString(city.find("112.96.66.237")));

        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (IPv4FormatException ipex) {
            ipex.printStackTrace();
        }
    }
}
