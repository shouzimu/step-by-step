package com.dh.mq.consumer;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.dh.mq.listener.DemoMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

@Configuration
public class ModprobeConsumer {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean(name = "messageListener")
    public DemoMessageListener messageListener() {
        DemoMessageListener listener = new DemoMessageListener();
        return listener;
    }


    @Bean
    @Autowired
    public Consumer getConsumer(DemoMessageListener messageListener) {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.ConsumerId, "CID_modprobe");
        properties.setProperty(PropertyKeyConst.AccessKey, "4tO0hmpXz0BE4o79");
        properties.setProperty(PropertyKeyConst.SecretKey, "6pzQRXae7nVI1moPaOsXKZprgVAS2N");

        Consumer consumer = ONSFactory.createConsumer(properties);
        consumer.subscribe("modprobe", "*", messageListener);
        consumer.start();
        return consumer;
    }

}
