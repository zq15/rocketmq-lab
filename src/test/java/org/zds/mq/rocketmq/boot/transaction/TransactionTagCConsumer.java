package org.zds.mq.rocketmq.boot.transaction;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 事务消息 TagC 消费者
 */
@Component
@Profile({"transaction", "test-transaction", "all"})
@RocketMQMessageListener(
    topic = "TransactionTopic",
    consumerGroup = "transaction-tag-c-consumer-group",
    selectorExpression = "TagC"
)
public class TransactionTagCConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("TransactionTagCConsumer 收到 TagC 事务消息: %s %n", message);
    }
} 