package org.zds.mq.rocketmq.boot.delay;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * 延迟消息发送测试
 * 延迟等级：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
 */
@SpringBootTest
public class DelayProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testDelayMessage() {
        for (int i = 0; i < 10; i++) {
            // 创建延迟消息，延迟等级为3（10秒）
            Message<String> message = MessageBuilder
                .withPayload("Hello scheduled message " + i)
                .setHeader("DELAY", 3)  // 延迟等级3 = 10秒
                .build();
            
            SendResult sendResult = rocketMQTemplate.syncSend("TestTopic", message);
            System.out.printf("延迟消息发送结果: %s%n", sendResult);
        }
    }

    @Test
    public void testDelayMessageWithDifferentLevels() {
        // 测试不同延迟等级
        int[] delayLevels = {1, 2, 3, 4, 5}; // 1s, 5s, 10s, 30s, 1m
        
        for (int i = 0; i < delayLevels.length; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Delay level " + delayLevels[i] + " message " + i)
                .setHeader("DELAY", delayLevels[i])
                .build();
            
            SendResult sendResult = rocketMQTemplate.syncSend("TestTopic", message);
            System.out.printf("延迟等级 %d 消息发送结果: %s%n", delayLevels[i], sendResult);
        }
    }

    @Test
    public void testDelayMessageWithTimeout() {
        Message<String> message = MessageBuilder
            .withPayload("Hello scheduled message with timeout")
            .setHeader("DELAY", 3)  // 延迟等级3 = 10秒
            .build();
        
        SendResult sendResult = rocketMQTemplate.syncSend("TestTopic", message, 5000);
        System.out.printf("延迟消息发送结果: %s%n", sendResult);
    }

    @Test
    public void testDelayMessageString() {
        // 简单的延迟消息发送
        for (int i = 0; i < 5; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Hello delayed message " + i)
                .setHeader("DELAY", 3)  // 延迟等级3 = 10秒
                .build();
            SendResult sendResult = rocketMQTemplate.syncSend("TestTopic", message);
            System.out.printf("延迟消息发送结果: %s%n", sendResult);
        }
    }
} 