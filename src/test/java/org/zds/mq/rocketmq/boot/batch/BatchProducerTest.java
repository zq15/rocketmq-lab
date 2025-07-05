package org.zds.mq.rocketmq.boot.batch;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 批量消息发送测试
 * 注意：批量消息大小不可超过 1MiB
 */
@SpringBootTest
public class BatchProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testBatchSend() {
        List<Message<String>> messages = new ArrayList<>();
        
        // 创建批量消息
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Hello world " + i)
                .setHeader("KEYS", "OrderID00" + i)
                .build();
            messages.add(message);
        }
        
        // 批量发送消息
        SendResult sendResult = rocketMQTemplate.syncSend("BatchTopic:Tag", messages);
        System.out.printf("批量发送结果: %s%n", sendResult);
    }

    @Test
    public void testBatchSendWithTimeout() {
        List<Message<String>> messages = new ArrayList<>();
        
        // 创建批量消息
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder
                .withPayload("Hello world " + i)
                .setHeader("KEYS", "OrderID00" + i)
                .build();
            messages.add(message);
        }
        
        // 批量发送消息，带超时时间
        SendResult sendResult = rocketMQTemplate.syncSend("BatchTopic:Tag", messages, 3000);
        System.out.printf("批量发送结果: %s%n", sendResult);
    }

    @Test
    public void testBatchSendString() {
        List<String> messages = new ArrayList<>();
        
        // 创建批量字符串消息
        for (int i = 0; i < 10; i++) {
            messages.add("Hello world " + i);
        }
        
        // 批量发送字符串消息
        SendResult sendResult = rocketMQTemplate.syncSend("BatchTopic:Tag", messages);
        System.out.printf("批量发送结果: %s%n", sendResult);
    }
} 