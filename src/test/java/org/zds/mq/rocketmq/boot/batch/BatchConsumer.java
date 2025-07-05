package org.zds.mq.rocketmq.boot.batch;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 批量消息消费者
 */
@Component
@Profile({"batch", "test-batch", "all"})
@RocketMQMessageListener(
    topic = "BatchTest",
    consumerGroup = "batch-consumer-group",
    selectorExpression = "*"
)
public class BatchConsumer implements RocketMQListener<String> {
    
    @Override
    public void onMessage(String message) {
        System.out.printf("BatchConsumer 收到消息: %s %n", message);
    }
} 