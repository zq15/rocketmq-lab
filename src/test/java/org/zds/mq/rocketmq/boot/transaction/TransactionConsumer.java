package org.zds.mq.rocketmq.boot.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 事务消息消费者
 */
@Component
@Profile({"transaction", "test-transaction", "all"})
@RocketMQMessageListener(
    topic = "TransactionTopic",
    consumerGroup = "transaction-consumer-group",
    selectorExpression = "*"
)
public class TransactionConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("TransactionConsumer 收到事务消息: %s %n", message);
    }
} 