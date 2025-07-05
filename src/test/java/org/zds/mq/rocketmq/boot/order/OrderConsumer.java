package org.zds.mq.rocketmq.boot.order;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 顺序消息消费者
 */
@Component
@Profile({"order", "test-order", "all"})
@RocketMQMessageListener(
    topic = "OrderTopic",
    consumerGroup = "order-consumer-group",
    selectorExpression = "*",
    consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("OrderConsumer 顺序消费消息: %s，线程: %s %n", 
            message, Thread.currentThread().getName());
    }
} 