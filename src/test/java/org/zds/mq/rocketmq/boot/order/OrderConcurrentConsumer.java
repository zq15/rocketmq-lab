package org.zds.mq.rocketmq.boot.order;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 并发消费者（用于对比）
 */
@Component
@Profile({"order", "test-order", "all"})
@RocketMQMessageListener(
    topic = "TopicTest",
    consumerGroup = "boot_order_concurrent_consumer_group",
    selectorExpression = "TagA || TagC || TagD",
    consumeMode = ConsumeMode.CONCURRENTLY
)
public class OrderConcurrentConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("并发消费 - 线程: %s, 消息: %s%n",
            Thread.currentThread().getName(), message);
    }
} 