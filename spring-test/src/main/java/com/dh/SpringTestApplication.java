package com.dh;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@MapperScan("com.dh.repository")
@SpringBootApplication
public class SpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestApplication.class);
    }
}

