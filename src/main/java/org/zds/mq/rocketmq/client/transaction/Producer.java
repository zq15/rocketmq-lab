package org.zds.mq.rocketmq.client.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class Producer {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("please_rename_unique_group_name");
        // 添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 执行本地事务
             * @param msg Half(prepare) message
             * @param arg Custom business parameter
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("执行本地事务");
                /**
                 * 发送 TagA -> 提交
                 * 发送 TagB -> 回滚
                 * 发送 TagC -> 未知 -> 多次重试（checkLocalTransaction）后失败
                 */
                if (StringUtils.equals("TagA", msg.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StringUtils.equals("TagB", msg.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else {
                    return LocalTransactionState.UNKNOW;
                }

            }

            /**
             * MQ进行消息事务状态回查
             * @param msg Check message
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                // tagC 回查，提交
                System.out.println("MQ检查消息Tag【"+msg.getTags()+"】的本地事务执行结果");
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        producer.setNamesrvAddr("192.168.139.10:9876");
        producer.start();
        String[] tags = new String[]{"TagA", "TagB", "TagC"};
        for (int i = 0; i < 3; i++) {
            try {
                Message msg = new Message("TransactionTopic", tags[i % tags.length], "KEY" + i,
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.sendMessageInTransaction(msg, null);
                System.out.printf("%s%n", sendResult);
                TimeUnit.SECONDS.sleep(1);
            } catch (MQClientException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // 不结束，需要调用检查的方法
//        producer.shutdown();
    }
}
