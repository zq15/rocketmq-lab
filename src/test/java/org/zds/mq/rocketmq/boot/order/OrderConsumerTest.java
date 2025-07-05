package org.zds.mq.rocketmq.boot.order;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 顺序消息消费者测试
 */
@SpringBootTest
@ActiveProfiles("test-order")
public class OrderConsumerTest {

    @Test
    public void testOrderConsumer() throws InterruptedException {
        System.out.println("启动顺序消息消费者测试...");
        System.out.println("OrderConsumer, OrderSimpleConsumer, OrderConcurrentConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(60);
    }
} 