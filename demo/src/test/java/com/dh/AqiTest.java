package com.dh;

import com.alibaba.fastjson.JSONArray;
import com.dh.model.Aqi;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public class AqiTest {

    @Test
    public void aqiRank() {
        String filePath = "/Users/lihong/Downloads/aqi.json";

        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
            List<Aqi> aqiList = JSONArray.parseArray(sb.toString(), Aqi.class);
            aqiList.stream().filter(a-> !"-".equalsIgnoreCase(a.getAqi())).sorted((a1,a2)-> Integer.parseInt(a2.getAqi())-Integer.parseInt(a1.getAqi())).limit(10).forEach(r -> {
                System.out.println(r.getCity() + "\t" + r.getAqi());
            });

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
