package org.zds.mq.rocketmq.boot.delay;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

/**
 * 延迟消息消费测试
 */
@SpringBootTest
@ActiveProfiles("test-delay")
public class DelayConsumerTest {

    @Test
    public void testDelayConsumer() throws InterruptedException {
        System.out.println("启动延迟消息消费者测试...");
        System.out.println("DelayConsumer 和 DelaySimpleConsumer 已自动启动");
        // 让消费者运行一段时间
        TimeUnit.SECONDS.sleep(60);
    }
} 