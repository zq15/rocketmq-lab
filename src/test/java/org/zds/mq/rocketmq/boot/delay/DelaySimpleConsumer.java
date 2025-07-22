package org.zds.mq.rocketmq.boot.delay;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 延迟消息简单消费者
 */
@Component
@RocketMQMessageListener(
    topic = "DelayTopic",
    consumerGroup = "delay-simple-consumer-group",
    selectorExpression = "*"
)
public class DelaySimpleConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("DelaySimpleConsumer 收到延迟消息: %s，当前时间: %s %n", 
            message, new java.util.Date());
    }
} 