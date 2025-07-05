package org.zds.mq.rocketmq.boot.event;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
public class EventTest {
    @Resource
    EventPublisher eventPublisher;

    @Test
    public void test() throws InterruptedException {
        eventPublisher.publish(DemoEvent.TOPIC,
                DemoEvent.create(FinishAggregate.builder()
                                .orderId(RandomStringUtils.randomNumeric(6))
                                .orderNumber(RandomStringUtils.randomNumeric(6))
                .build()));

        Thread.sleep(100000L);
    }

}
