package org.zds.mq.rocketmq.boot.event;

import lombok.Data;

import java.util.Date;

@Data
public class BaseEvent<T> {

    private String id;
    private Date timestamp;
    private T data;

}
