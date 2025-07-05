package org.zds.mq.rocketmq.boot.filter.sql;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * SQL 过滤消费者测试
 */
@SpringBootTest
@ActiveProfiles("test-sql")
public class SqlConsumerTest {

    @Test
    public void testSqlConsumer() throws InterruptedException {
        System.out.println("启动 SQL 过滤消费者测试...");
        System.out.println("SqlAConsumer, SqlAGtConsumer, SqlColorConsumer, SqlAllConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(30);
    }
} 