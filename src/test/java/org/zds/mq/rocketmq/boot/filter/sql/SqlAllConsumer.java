package org.zds.mq.rocketmq.boot.filter.sql;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * SQL 过滤消费者 - 消费所有消息
 */
@Component
@Profile({"filter", "sql", "test-filter", "test-sql", "all"})
@RocketMQMessageListener(
    topic = "SqlFilterTopic",
    consumerGroup = "sql-all-consumer-group",
    selectorExpression = "*"
)
public class SqlAllConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("SqlAllConsumer 收到所有消息: %s %n", message);
    }
} 