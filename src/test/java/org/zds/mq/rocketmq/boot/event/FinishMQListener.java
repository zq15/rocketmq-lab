package org.zds.mq.rocketmq.boot.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketMQMessageListener(topic = "demo-mq", consumerGroup = "zds-group")
public class FinishMQListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String s) {
        log.info("接收到MQ消息 {}", s);
    }
}
