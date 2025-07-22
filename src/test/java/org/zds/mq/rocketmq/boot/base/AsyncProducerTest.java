package org.zds.mq.rocketmq.boot.base;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 异步消息发送测试
 */
@SpringBootTest
public class AsyncProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testAsyncSend() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 异步发送消息
            rocketMQTemplate.asyncSend("TopicTest:TagA", "Hello RocketMQ " + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d 异步发送成功 %s %n", index, sendResult.getMsgId());
                    latch.countDown();
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d 异步发送异常 %s %n", index, e.getMessage());
                    e.printStackTrace();
                    latch.countDown();
                }
            });
        }
        
        // 等待所有异步发送完成
        latch.await(30, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testAsyncSendWithMessage() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 使用 Message 对象异步发送
            rocketMQTemplate.asyncSend(
                "TopicTest:TagA", 
                MessageBuilder.withPayload("Hello RocketMQ " + i).build(),
                new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        System.out.printf("%-10d 异步发送成功 %s %n", index, sendResult.getMsgId());
                        latch.countDown();
                    }

                    @Override
                    public void onException(Throwable e) {
                        System.out.printf("%-10d 异步发送异常 %s %n", index, e.getMessage());
                        e.printStackTrace();
                        latch.countDown();
                    }
                }
            );
        }
        
        // 等待所有异步发送完成
        latch.await(30, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void testAsyncSendWithTimeout() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(10);
        
        for (int i = 0; i < 10; i++) {
            final int index = i;
            // 带超时时间的异步发送
            rocketMQTemplate.asyncSend("TopicTest:TagA", "Hello RocketMQ " + i, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d 异步发送成功 %s %n", index, sendResult.getMsgId());
                    latch.countDown();
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d 异步发送异常 %s %n", index, e.getMessage());
                    e.printStackTrace();
                    latch.countDown();
                }
            }, 3000);
        }
        
        // 等待所有异步发送完成
        latch.await(30, TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(30);
    }
} 