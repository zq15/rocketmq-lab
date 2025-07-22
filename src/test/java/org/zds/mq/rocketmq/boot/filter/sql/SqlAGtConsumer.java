package org.zds.mq.rocketmq.boot.filter.sql;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * SQL 过滤消费者 - 只消费 a>5 的消息
 */
@Component
@RocketMQMessageListener(
    topic = "SqlFilterTopic",
    consumerGroup = "sql-a-gt-consumer-group",
    selectorType = SelectorType.SQL92,
    selectorExpression = "a>5"
)
public class SqlAGtConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("SqlAGtConsumer 收到 a>5 的消息: %s %n", message);
    }
} 