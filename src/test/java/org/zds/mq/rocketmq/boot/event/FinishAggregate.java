package org.zds.mq.rocketmq.boot.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishAggregate {

    private String orderNumber;
    /** 调薪单号 */
    private String orderId;
}
