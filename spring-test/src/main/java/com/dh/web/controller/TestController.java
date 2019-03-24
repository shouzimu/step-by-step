package com.dh.web.controller;

import com.dh.config.TestYml;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestYml testYml;

    @GetMapping("/t1")
    public Map<String,String> getData(){
        return testYml.getMap();
    }
}
