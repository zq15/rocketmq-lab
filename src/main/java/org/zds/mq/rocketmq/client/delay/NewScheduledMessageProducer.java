//package org.zds.mq.rocketmq.client.delay;
//
//import org.apache.rocketmq.client.java.message.MessageBuilderImpl;
//import org.apache.rocketmq.client.producer.DefaultMQProducer;
//import org.apache.rocketmq.common.message.Message;
//import org.springframework.messaging.support.MessageBuilder;
//
//public class NewScheduledMessageProducer {
//    public static void main(String[] args) throws Exception {
//        // 实例化一个生产者来产生延时消息
//        DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
//        producer.setNamesrvAddr("127.0.0.1:9876");
//        // 启动生产者
//        producer.start();
//        int totalMessagesToSend = 100;
//        MessageBuilder messageBuilder = new MessageBuilderImpl();;
//        for (int i = 0; i < totalMessagesToSend; i++) {
//            Message message = new MessageBuilderImpl().
//                    new Message("delay-topic", ("Hello scheduled message " + i).getBytes());
//
//            // 发送消息
//            producer.send(message);
//        }
//        // 关闭生产者
//        producer.shutdown();
//    }
//}
