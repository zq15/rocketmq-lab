package org.zds.mq.rocketmq.boot.filter.sql;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * SQL 过滤消费者 - 只消费 color='red' 的消息
 */
@Component
@RocketMQMessageListener(
    topic = "SqlFilterTopic",
    consumerGroup = "sql-color-consumer-group",
    selectorType = SelectorType.SQL92,
    selectorExpression = "color='red'"
)
public class SqlColorConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("SqlColorConsumer 收到 color='red' 的消息: %s %n", message);
    }
} 