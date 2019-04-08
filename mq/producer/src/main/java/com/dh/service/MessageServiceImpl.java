package com.dh.service;

import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Override
    public void pringMsg(byte[] msg) {
        String m = null;
        try {
            m = new String(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.info(m);
    }
}
