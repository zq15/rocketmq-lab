package org.zds.mq.rocketmq.boot.event;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class DemoEvent extends BaseEvent<FinishAggregate>{

    public static String TOPIC = "demo-mq";

    public static DemoEvent create(FinishAggregate finishAggregate) {
        DemoEvent event = new DemoEvent();
        event.setId(RandomStringUtils.randomNumeric(11));
        event.setTimestamp(new Date());
        event.setData(finishAggregate);
        return event;
    }
}
