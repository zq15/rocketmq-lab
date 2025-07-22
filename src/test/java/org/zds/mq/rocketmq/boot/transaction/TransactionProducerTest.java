package org.zds.mq.rocketmq.boot.transaction;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 事务消息生产者测试
 */
@SpringBootTest
public class TransactionProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testTransactionProducer() throws InterruptedException {
        String[] tags = {"TagA", "TagB", "TagC"};
        
        for (int i = 0; i < 3; i++) {
            try {
                Message<String> message = MessageBuilder
                    .withPayload(tags[i])
                    .build();
                
                // 发送事务消息
                // 注意：需要配置事务监听器
                rocketMQTemplate.sendMessageInTransaction(
                    "TransactionTopic:" + tags[i % tags.length], 
                    message,
                    null
                );
                
                System.out.printf("事务消息发送 - Tag: %s, 消息: %s%n", 
                    tags[i % tags.length], "Hello RocketMQ " + i);
                
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 等待事务检查
        TimeUnit.SECONDS.sleep(120);
    }

} 