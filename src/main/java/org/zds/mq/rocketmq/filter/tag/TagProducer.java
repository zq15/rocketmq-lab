package org.zds.mq.rocketmq.filter.tag;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

// 发送 TagA TagB TagC
public class TagProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message msg1 = new Message("TagTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("TagA").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
        SendResult sendResult1 = producer.send(msg1);
        System.out.printf("%s%n", sendResult1);

        Message msg2 = new Message("TagTest" /* Topic */,
                "TagB" /* Tag */,
                ("TagB").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        SendResult sendResult2 = producer.send(msg2);
        System.out.printf("%s%n", sendResult2);

        Message msg3 = new Message("TagTest" /* Topic */,
                "TagC" /* Tag */,
                ("TagC").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );
        SendResult sendResult3 = producer.send(msg3);
        System.out.printf("%s%n", sendResult3);

        producer.shutdown();
    }
}
