package org.zds.mq.rocketmq.boot.filter.sql;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * SQL 过滤消费者 - 只消费 a=1 的消息
 */
@Component
@Profile({"filter", "sql", "test-filter", "test-sql", "all"})
@RocketMQMessageListener(
    topic = "SqlFilterTopic",
    consumerGroup = "sql-a-consumer-group",
    selectorType = SelectorType.SQL92,
    selectorExpression = "a=1"
)
public class SqlAConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.printf("SqlAConsumer 收到 a=1 的消息: %s %n", message);
    }
} 