package com.dh.retry;

import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {


    @Retryable
    public boolean pay(PayObject pay) throws RuntimeException {
        System.out.println("exe");
        throw new RuntimeException("error");
    }


    @Recover
    public void recover(Exception e) {
        System.out.println("recovery");
    }
}
