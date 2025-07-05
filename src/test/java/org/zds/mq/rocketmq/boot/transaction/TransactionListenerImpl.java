package org.zds.mq.rocketmq.boot.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * 事务监听器实现
 */
@Component
@RocketMQTransactionListener
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    /**
     * 执行本地事务
     * @param message 半消息（prepare消息）
     * @param arg 自定义业务参数
     * @return 本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
        System.out.printf("执行本地事务 - 消息: %s, 参数: %s%n", message.getPayload(), arg);
        
        // 获取消息的标签
        String tags = (String) message.getHeaders().get("rocketmq_TAGS");
        
        /**
         * 根据标签决定事务结果：
         * TagA -> 提交事务
         * TagB -> 回滚事务
         * TagC -> 未知状态，需要回查
         */
        if (StringUtils.equals("TagA", tags)) {
            System.out.println("TagA 消息 - 提交事务");
            return RocketMQLocalTransactionState.COMMIT;
        } else if (StringUtils.equals("TagB", tags)) {
            System.out.println("TagB 消息 - 回滚事务");
            return RocketMQLocalTransactionState.ROLLBACK;
        } else {
            System.out.println("TagC 消息 - 未知状态，等待回查");
            return RocketMQLocalTransactionState.UNKNOWN;
        }
    }

    /**
     * MQ进行消息事务状态回查
     * @param message 需要回查的消息
     * @return 本地事务状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        String tags = (String) message.getHeaders().get("rocketmq_TAGS");
        System.out.printf("MQ检查消息Tag【%s】的本地事务执行结果%n", tags);
        
        // TagC 回查后提交
        if (StringUtils.equals("TagC", tags)) {
            System.out.println("TagC 消息回查 - 提交事务");
            return RocketMQLocalTransactionState.COMMIT;
        }
        
        // 其他情况回滚
        System.out.println("其他消息回查 - 回滚事务");
        return RocketMQLocalTransactionState.ROLLBACK;
    }
} 