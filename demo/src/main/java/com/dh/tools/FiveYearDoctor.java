package com.dh.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FiveYearDoctor {

    private final String path = "C:\\Users\\shouzimu\\Desktop\\fuck\\";

    public static void main(String[] args) throws Exception {
        FiveYearDoctor f = new FiveYearDoctor();
        f.paras();
    }


    private void paras() throws Exception {
        File[] flies = new File(path).listFiles();

        String id;
        String name;
        String[] ps;
        for (File file : flies) {
            ps = file.getName().split("\\.")[0].split("_");
            id = ps[1];
            name = ps[0];

            FileInputStream stream = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String jsonStr = reader.readLine();
            JSONObject object = JSON.parseObject(jsonStr);
            String data = object.getString("data");
            List<JSONObject> dataList = JSONArray.parseArray(data, JSONObject.class);

            for (int i = 2016; i <= 2019; i++) {
                checkDate(i, dataList, id, name);
            }

        }


    }

    private void getAndSaveFile() throws Exception {

        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new CookieJar() {
            @Override
            public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
            }

            @NotNull
            @Override
            public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
                List<Cookie> cookies = new ArrayList<>();
                return cookies;
            }
        }).sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier((hostname, session) -> true)
                .build();

        String inputFilePath = "C:\\Users\\shouzimu\\Desktop\\fuck\\all.log";
        BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
        List<String> psArray = reader.lines().collect(Collectors.toList());
        String[] ps;
        String id;
        String name;
        for (String s : psArray) {
            ps = s.split("\\t");
            id = ps[1];
            name = ps[0];
            Request request = new Request.Builder()
                    .get()
                    .url("" + id + "")
                    .build();
            Call call = client.newCall(request);
            Response response = call.execute();
            String res = response.body().string();
            String filePath = path + name + "_" + id + ".json";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(res);
            writer.flush();
        }
    }

    private Cookie cookie(String name, String value) {
        Cookie cookie = new Cookie.Builder().domain("zanchina.com").name(name).value(value).build();
        return cookie;
    }

    static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }
        return ssfFactory;
    }

    private String checkDate(int year, List<JSONObject> objects, String id, String name) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.MONTH, 0);

        Set<String> scheduleSet = objects.stream().map(t -> t.getString("date")).collect(Collectors.toSet());

        LocalDate start = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));

        LocalDate end = calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDate stopStart = null;

        int stopDay = 0;
        while (start.compareTo(end) <= 0) {
            String date = getDate(start);
            if (!scheduleSet.contains(date)) {
                if (stopDay == 0) {
                    stopStart = start;
                }
                stopDay++;
            } else {
                if (stopDay >= 30) {
                    System.out.println(name + "\t" + getDate(stopStart) + "\t" + getDate(stopStart.plusDays(stopDay)) + "\t" + stopDay);
                }
                stopDay = 0;
            }
            start = start.plusDays(1);
        }
        return null;
    }

    private String getV(int v) {
        return v < 10 ? "0" + v : String.valueOf(v);
    }

    private String getDate(LocalDate start) {
        String date = start.getYear() + "-" + getV(start.getMonthValue()) + "-" + getV(start.getDayOfMonth());
        return date;
    }
}
