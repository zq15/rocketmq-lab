package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;

/**
 * 同步消息发送测试
 */
@SpringBootTest
public class SyncProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSyncSend() {
        for (int i = 0; i < 10; i++) {
            // 同步发送消息
            SendResult sendResult = rocketMQTemplate.syncSend("TopicTest:TagA", "Hello RocketMQ " + i);
            System.out.printf("同步发送结果: %s%n", sendResult);
        }
    }

    @Test
    public void testSyncSendWithMessage() {
        for (int i = 0; i < 10; i++) {
            // 使用 Message 对象发送
            SendResult sendResult = rocketMQTemplate.syncSend(
                "TopicTest:TagA", 
                MessageBuilder.withPayload("Hello RocketMQ " + i).build()
            );
            System.out.printf("同步发送结果: %s%n", sendResult);
        }
    }

    @Test
    public void testSyncSendWithTimeout() {
        for (int i = 0; i < 10; i++) {
            // 带超时时间的同步发送
            SendResult sendResult = rocketMQTemplate.syncSend("TopicTest:TagA", "Hello RocketMQ " + i, 3000);
            System.out.printf("同步发送结果: %s%n", sendResult);
        }
    }
} 