package com.dh.mq.consumer;

import com.dh.service.MessageService;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Consumer {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    @Value("${mq.consumer.group}")
    public String consumerGroup;

    @Value("${mq.name.addr}")
    public String namesrvAddr;

    @Autowired
    private MessageService messageService;


    @Bean
    public DefaultMQPushConsumer consumerInit() throws MQClientException {

        logger.info("Consumer is exe");

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setVipChannelEnabled(false);

        // 订阅指定MyTopic下tags等于MyTag

        defaultMQPushConsumer.subscribe("MyTopic", "MyTag");

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            MessageExt msg = msgs.get(0);
            if (msg.getTopic().equals("MyTopic")) {
                logger.info("MyTopic");
                messageService.pringMsg(msg.getBody());
                if (msg.getTags() != null && msg.getTags().equals("MyTag")) {
                    logger.info("MyTag");
                }
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();

        logger.info("DefaultMQPushConsumer start success!");
        return defaultMQPushConsumer;

    }



}
