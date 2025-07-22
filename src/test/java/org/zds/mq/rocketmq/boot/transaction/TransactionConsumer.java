package org.zds.mq.rocketmq.boot.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 事务消息 TagA 消费者
 */
@Component
@RocketMQMessageListener(
    topic = "TransactionTopic",
    consumerGroup = "transaction-consumer-a"
)
public class TransactionConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("TransactionConsumer 收到  事务消息: %s %n", message);
    }
} 