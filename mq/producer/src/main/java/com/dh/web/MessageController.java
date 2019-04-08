package com.dh.web;

import java.lang.invoke.MethodHandles;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    private DefaultMQProducer defaultMQProducer;

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @RequestMapping("/test/{str}")
    public String sendMessage(@PathVariable String str) {
        Message msg = new Message("MyTopic", "MyTag", str.getBytes());
        SendResult sendResult = null;
        try {
            try {
                sendResult = defaultMQProducer.send(msg);
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        }
        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            return "error";
        }

        return "success";
    }
}
