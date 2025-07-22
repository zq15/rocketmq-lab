package org.zds.mq.rocketmq.boot.filter.tag;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * 标签过滤生产者测试
 */
@SpringBootTest
public class TagProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testTagProducer() throws InterruptedException {
        // 发送 TagA 消息
        SendResult sendResult1 = rocketMQTemplate.syncSend("TagTest:TagA", "TagA Message");
        System.out.printf("TagA 消息发送结果: %s%n", sendResult1);

        // 发送 TagB 消息
        SendResult sendResult2 = rocketMQTemplate.syncSend("TagTest:TagB", "TagB Message");
        System.out.printf("TagB 消息发送结果: %s%n", sendResult2);

        // 发送 TagC 消息
        SendResult sendResult3 = rocketMQTemplate.syncSend("TagTest:TagC", "TagC Message");
        System.out.printf("TagC 消息发送结果: %s%n", sendResult3);

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testMultipleTagMessages() throws InterruptedException {
        String[] tags = {"TagA", "TagB", "TagC"};
        
        for (int i = 0; i < 10; i++) {
            String tag = tags[i % tags.length];
            String message = tag + " Message " + i;
            
            SendResult sendResult = rocketMQTemplate.syncSend("TagTest:" + tag, message);
            System.out.printf("%s 消息发送结果: %s%n", tag, sendResult);
        }

        TimeUnit.SECONDS.sleep(30);
    }

} 