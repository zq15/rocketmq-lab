package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * 同步消息发送测试
 */
@SpringBootTest
public class SyncProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSyncSend() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // 同步发送消息
            SendResult sendResult = rocketMQTemplate.syncSend("TopicTest:TagA", "Hello RocketMQ " + i);
            System.out.printf("同步发送结果: %s%n", sendResult);
        }
        TimeUnit.SECONDS.sleep(30);
    }
} 