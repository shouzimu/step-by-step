package com.dh.web.controller;

import com.dh.retry.PayObject;
import com.dh.retry.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("retry")
public class RetryController {

    @Autowired
    private PayService payService;

    @GetMapping("/test")
    public Object test() {
        PayObject p = new PayObject();
        try {
            return payService.pay(p);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
