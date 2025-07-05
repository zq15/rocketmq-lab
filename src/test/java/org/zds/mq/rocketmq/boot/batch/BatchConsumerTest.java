package org.zds.mq.rocketmq.boot.batch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 批量消息消费测试
 */
@SpringBootTest
@ActiveProfiles("test-batch")
public class BatchConsumerTest {

    @Test
    public void testBatchConsumer() throws InterruptedException {
        System.out.println("启动批量消息消费者测试...");
        System.out.println("BatchConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(30);
    }
} 