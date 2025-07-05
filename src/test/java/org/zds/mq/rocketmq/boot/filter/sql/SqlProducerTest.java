package org.zds.mq.rocketmq.boot.filter.sql;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * SQL 过滤生产者测试
 */
@SpringBootTest
public class SqlProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSqlProducer() {
        // 发送 a=1 的消息
        Message<String> message1 = MessageBuilder
            .withPayload("a1 message")
            .setHeader("a", 1)
            .build();
        SendResult sendResult1 = rocketMQTemplate.syncSend("SqlTest:Tag", message1);
        System.out.printf("a=1 消息发送结果: %s%n", sendResult1);

        // 发送 a=6 的消息
        Message<String> message2 = MessageBuilder
            .withPayload("a6 message")
            .setHeader("a", 6)
            .build();
        SendResult sendResult2 = rocketMQTemplate.syncSend("SqlTest:Tag", message2);
        System.out.printf("a=6 消息发送结果: %s%n", sendResult2);
    }

    @Test
    public void testSqlProducerWithMultipleProperties() {
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Message " + i)
                .setHeader("a", i)
                .setHeader("b", i * 2)
                .setHeader("c", "value" + i)
                .build();
            
            SendResult sendResult = rocketMQTemplate.syncSend("SqlTest:Tag", message);
            System.out.printf("a=%d, b=%d, c=value%d 消息发送结果: %s%n", i, i*2, i, sendResult);
        }
    }

    @Test
    public void testSqlProducerWithStringProperty() {
        String[] colors = {"red", "green", "blue", "yellow"};
        
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Color message " + i)
                .setHeader("color", colors[i % colors.length])
                .setHeader("price", i * 10)
                .build();
            
            SendResult sendResult = rocketMQTemplate.syncSend("SqlTest:Tag", message);
            System.out.printf("color=%s, price=%d 消息发送结果: %s%n", 
                colors[i % colors.length], i * 10, sendResult);
        }
    }
} 