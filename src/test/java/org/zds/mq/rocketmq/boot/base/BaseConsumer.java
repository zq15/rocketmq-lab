package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 基础消息消费者 - 负载均衡模式
 */
@Component
@RocketMQMessageListener(
    topic = "TopicTest",
    consumerGroup = "base-consumer-group",
    selectorExpression = "*"
)
public class BaseConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("BaseConsumer 收到消息: %s %n", message);
    }
} 