package org.zds.mq.rocketmq.boot.filter.tag;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 只消费 TagA 消息的消费者
 */
@Component
@RocketMQMessageListener(
    topic = "TagFilterTopic",
    consumerGroup = "tag-a-consumer-group",
    selectorExpression = "TagA"
)
public class TagAConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("TagAConsumer 收到 TagA 消息: %s %n", message);
    }
} 