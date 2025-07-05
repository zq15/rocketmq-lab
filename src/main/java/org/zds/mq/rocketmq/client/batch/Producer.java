package org.zds.mq.rocketmq.client.batch;

import java.util.ArrayList;
import java.util.List;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * 批量，但注意大小不可超过 1MiB
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr("192.168.139.10:9876");
        // 启动Producer实例
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("BatchTopic", "Tag", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message("BatchTopic", "Tag", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message("BatchTopic", "Tag", "OrderID003", "Hello world 2".getBytes()));

        producer.send(messages);
        producer.shutdown();
    }
}
