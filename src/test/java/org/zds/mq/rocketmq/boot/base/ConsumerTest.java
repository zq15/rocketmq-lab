package org.zds.mq.rocketmq.boot.base;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 基础消息消费测试
 */
@SpringBootTest
@ActiveProfiles("test-base")
public class ConsumerTest {

    @Test
    public void testConsumer() throws InterruptedException {
        System.out.println("启动基础消息消费者测试...");
        System.out.println("BaseConsumer 和 BroadcastConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(30);
    }
} 