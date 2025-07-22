package org.zds.mq.rocketmq.boot.delay;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 延迟消息消费者
 */
@Component
@RocketMQMessageListener(
    topic = "TestTopic",
    consumerGroup = "boot_delay_consumer_group"
)
public class DelayConsumer implements RocketMQListener<MessageExt> {
    
    @Override
    public void onMessage(MessageExt messageExt) {
        // 计算延迟时间
        long delayTime = System.currentTimeMillis() - messageExt.getStoreTimestamp();
        System.out.printf("接收到延迟消息 [msgId=%s] 延迟了 %d ms, 内容: %s %n", 
            messageExt.getMsgId(), delayTime, new String(messageExt.getBody()));
    }
} 