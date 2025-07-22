package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 广播消费者
 */
@Component
@RocketMQMessageListener(
    topic = "TopicTest",
    consumerGroup = "boot_broadcast_consumer_group",
    selectorExpression = "TagA",
    messageModel = MessageModel.BROADCASTING
)
public class BroadcastConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("%s 广播接收到新消息: %s %n", 
            Thread.currentThread().getName(), message);
    }
} 