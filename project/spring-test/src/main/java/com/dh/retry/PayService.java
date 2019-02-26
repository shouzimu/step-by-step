package com.dh.retry;

public interface PayService {

    boolean pay(PayObject pay) throws RuntimeException;
}
