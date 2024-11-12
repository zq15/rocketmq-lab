package org.zds.mq.rocketmq.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

// 发送 a=1 a=6
public class SqlProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message msg1 = new Message("TagTest" /* Topic */,
                    "Tag" /* Tag */,
                    ("a1").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
        // 设置一些属性
        msg1.putUserProperty("a", String.valueOf(1));
        SendResult sendResult1 = producer.send(msg1);
        System.out.printf("%s%n", sendResult1);

        Message msg2 = new Message("TagTest" /* Topic */,
                "Tag" /* Tag */,
                ("a6").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        // 设置一些属性
        msg2.putUserProperty("a", String.valueOf(6));
        SendResult sendResult2 = producer.send(msg2);
        System.out.printf("%s%n", sendResult2);


        producer.shutdown();
    }
}
