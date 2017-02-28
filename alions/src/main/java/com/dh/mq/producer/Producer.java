package com.dh.mq.producer;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.invoke.MethodHandles;
import java.util.Properties;


@Configuration
public class Producer {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean
    public ProducerBean getProducer(){
        ProducerBean bean = new ProducerBean();
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId,"PID_modprobe");
        properties.setProperty(PropertyKeyConst.AccessKey, "4tO0hmpXz0BE4o79");
        properties.setProperty(PropertyKeyConst.SecretKey, "6pzQRXae7nVI1moPaOsXKZprgVAS2N");
        bean.setProperties(properties);
        bean.start();
        return bean;
    }
}
