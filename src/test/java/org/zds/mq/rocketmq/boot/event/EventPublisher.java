package org.zds.mq.rocketmq.boot.event;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class EventPublisher {

    @Resource
    private RocketMQTemplate rocketmqTemplate;

    /**
     * 普通消息
     *
     * @param topic   主题
     * @param message 消息
     */
    public void publish(String topic, BaseEvent<?> message) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ消息 topic:{} message:{}", topic, mqMessage);
            rocketmqTemplate.convertAndSend(topic, mqMessage);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

    /**
     * 延迟消息
     *
     * @param topic          主题
     * @param message        消息
     * @param delayTimeLevel 延迟时长
     */
    public void publishDelivery(String topic, BaseEvent<?> message, int delayTimeLevel) {
        try {
            String mqMessage = JSON.toJSONString(message);
            log.info("发送MQ延迟消息 topic:{} message:{}", topic, mqMessage);
            rocketmqTemplate.syncSend(topic, MessageBuilder.withPayload(message).build(), 1000, delayTimeLevel);
        } catch (Exception e) {
            log.error("发送MQ延迟消息失败 topic:{} message:{}", topic, JSON.toJSONString(message), e);
            // 大部分MQ发送失败后，会需要任务补偿
        }
    }

}
