package com.dh.demo.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import javax.ws.rs.Path;

@Service
@Path("demo")
public class DefaultDemoService implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name + " (from Spring Boot)";
    }
}
