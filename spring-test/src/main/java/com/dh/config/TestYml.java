package com.dh.config;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "")
@Data
public class TestYml {

    private Map<String, String> test = new HashMap<>();

    public  Map<String, String> getMap(){
        return test;
    }
}
