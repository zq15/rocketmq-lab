package org.zds.mq.rocketmq.boot.order;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 顺序消息生产者测试
 */
@SpringBootTest
public class OrderProducerTest {
    
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testOrderProducer() {
        String[] tags = {"TagA", "TagC", "TagD"};
        
        // 订单列表
        List<OrderStep> orderList = buildOrders();
        
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        
        for (int i = 0; i < orderList.size(); i++) {
            OrderStep orderStep = orderList.get(i);
            String body = dateStr + " Hello RocketMQ " + orderStep;
            
            // 使用订单ID作为 hashKey 确保同一订单的消息发送到同一个队列
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(
                "TopicTest:" + tags[i % tags.length], 
                body, 
                String.valueOf(orderStep.getOrderId())
            );
            
            System.out.printf("顺序消息发送结果 - 订单ID: %d, 队列ID: %d, 状态: %s, 内容: %s%n",
                orderStep.getOrderId(),
                sendResult.getMessageQueue().getQueueId(),
                sendResult.getSendStatus(),
                body);
        }
    }

    @Test
    public void testOrderProducerWithMessage() {
        String[] tags = {"TagA", "TagC", "TagD"};
        List<OrderStep> orderList = buildOrders();
        
        for (int i = 0; i < orderList.size(); i++) {
            OrderStep orderStep = orderList.get(i);
            
            Message<String> message = MessageBuilder
                .withPayload("Order message: " + orderStep)
                .setHeader("KEYS", "KEY" + i)
                .build();
            
            // 使用订单ID作为 hashKey
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(
                "TopicTest:" + tags[i % tags.length], 
                message, 
                String.valueOf(orderStep.getOrderId())
            );
            
            System.out.printf("顺序消息发送结果 - 订单ID: %d, 队列ID: %d, 状态: %s%n",
                orderStep.getOrderId(),
                sendResult.getMessageQueue().getQueueId(),
                sendResult.getSendStatus());
        }
    }

    @Test
    public void testOrderProducerWithTimeout() {
        List<OrderStep> orderList = buildOrders();
        
        for (OrderStep orderStep : orderList) {
            SendResult sendResult = rocketMQTemplate.syncSendOrderly(
                "TopicTest:TagA", 
                "Order message with timeout: " + orderStep, 
                String.valueOf(orderStep.getOrderId()),
                3000
            );
            
            System.out.printf("顺序消息发送结果 - 订单ID: %d, 队列ID: %d%n",
                orderStep.getOrderId(),
                sendResult.getMessageQueue().getQueueId());
        }
    }

    /**
     * 订单的步骤
     */
    private static class OrderStep {
        private long orderId;
        private String desc;

        public OrderStep(long orderId, String desc) {
            this.orderId = orderId;
            this.desc = desc;
        }

        public long getOrderId() {
            return orderId;
        }

        public void setOrderId(long orderId) {
            this.orderId = orderId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "OrderStep{" +
                    "orderId=" + orderId +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    /**
     * 生成模拟订单数据
     */
    private List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<>();

        orderList.add(new OrderStep(15103111039L, "创建"));
        orderList.add(new OrderStep(15103111065L, "创建"));
        orderList.add(new OrderStep(15103111039L, "付款"));
        orderList.add(new OrderStep(15103117235L, "创建"));
        orderList.add(new OrderStep(15103111065L, "付款"));
        orderList.add(new OrderStep(15103117235L, "付款"));
        orderList.add(new OrderStep(15103111065L, "完成"));
        orderList.add(new OrderStep(15103111039L, "推送"));
        orderList.add(new OrderStep(15103117235L, "完成"));
        orderList.add(new OrderStep(15103111039L, "完成"));

        return orderList;
    }
} 