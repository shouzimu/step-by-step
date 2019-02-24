package com.dh.web.controller;

import com.dh.bean.Greeting;
import com.dh.bean.HelloMessage;
import java.util.stream.IntStream;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @GetMapping("test")
    public void test() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            HelloMessage m = new HelloMessage();
            m.setName("name " + i);
            try {
                greeting(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


}