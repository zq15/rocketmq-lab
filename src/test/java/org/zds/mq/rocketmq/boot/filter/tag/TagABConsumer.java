package org.zds.mq.rocketmq.boot.filter.tag;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 消费 TagA 和 TagB 消息的消费者
 */
@Component
@Profile({"filter", "tag", "test-filter", "test-tag", "all"})
@RocketMQMessageListener(
    topic = "TagFilterTopic",
    consumerGroup = "tag-ab-consumer-group",
    selectorExpression = "TagA || TagB"
)
public class TagABConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("TagABConsumer 收到 TagA/TagB 消息: %s %n", message);
    }
} 