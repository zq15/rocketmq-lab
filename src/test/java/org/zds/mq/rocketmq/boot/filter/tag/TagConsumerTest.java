package org.zds.mq.rocketmq.boot.filter.tag;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 标签过滤消费者测试
 */
@SpringBootTest
@ActiveProfiles("test-tag")
public class TagConsumerTest {

    @Test
    public void testTagConsumer() throws InterruptedException {
        System.out.println("启动标签过滤消费者测试...");
        System.out.println("TagAConsumer, TagABConsumer, TagAllConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(30);
    }
} 