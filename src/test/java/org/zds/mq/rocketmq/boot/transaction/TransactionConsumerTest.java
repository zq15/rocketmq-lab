package org.zds.mq.rocketmq.boot.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 事务消息消费者测试
 */
@SpringBootTest
@ActiveProfiles("test-transaction")
public class TransactionConsumerTest {

    @Test
    public void testTransactionConsumer() throws InterruptedException {
        System.out.println("启动事务消息消费者测试...");
        System.out.println("TransactionConsumer, TransactionTagAConsumer, TransactionTagBConsumer, TransactionTagCConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(60);
    }
} 