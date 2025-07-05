package org.zds.mq.rocketmq.boot.filter.tag;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 消费所有 Tag 消息的消费者
 */
@Component
@Profile({"filter", "tag", "test-filter", "test-tag", "all"})
@RocketMQMessageListener(
    topic = "TagFilterTopic",
    consumerGroup = "tag-all-consumer-group",
    selectorExpression = "*"
)
public class TagAllConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("TagAllConsumer 收到所有 Tag 消息: %s %n", message);
    }
} 