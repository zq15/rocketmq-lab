package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * 单向消息发送测试
 */
@SpringBootTest
public class OnewayProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSendOneWay() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // 单向发送消息，没有返回结果
            rocketMQTemplate.sendOneWay("TopicTest:TagA", "Hello RocketMQ " + i);
            System.out.printf("单向发送消息: %d%n", i);
        }

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testSendOneWayWithMessage() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // 使用 Message 对象单向发送
            rocketMQTemplate.sendOneWay(
                "TopicTest:TagA", 
                MessageBuilder.withPayload("Hello RocketMQ " + i).build()
            );
            System.out.printf("单向发送消息: %d%n", i);
        }
        TimeUnit.SECONDS.sleep(30);
    }
} 