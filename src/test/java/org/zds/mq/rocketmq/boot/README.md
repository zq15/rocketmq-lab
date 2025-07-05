# RocketMQ Spring Boot 测试案例

本目录包含了基于 Spring Boot 的 RocketMQ 各种功能测试案例，对应 client 模块中的原生 RocketMQ 实现。

## 项目结构

```
src/test/java/org/zds/mq/rocketmq/boot/
├── base/                    # 基础消息测试
│   ├── SyncProducerTest.java     # 同步生产者测试
│   ├── AsyncProducerTest.java    # 异步生产者测试
│   ├── OnewayProducerTest.java   # 单向生产者测试
│   ├── ConsumerTest.java         # 基础消费者测试
│   ├── BaseConsumer.java         # 负载均衡消费者
│   └── BroadcastConsumer.java    # 广播消费者
├── batch/                   # 批量消息测试
│   ├── BatchProducerTest.java    # 批量生产者测试
│   ├── BatchConsumerTest.java    # 批量消费者测试
│   └── BatchConsumer.java        # 批量消费者
├── delay/                   # 延迟消息测试
│   ├── DelayProducerTest.java    # 延迟生产者测试
│   ├── DelayConsumerTest.java    # 延迟消费者测试
│   ├── DelayConsumer.java        # 延迟消费者（详细版）
│   └── DelaySimpleConsumer.java  # 延迟消费者（简单版）
├── filter/                  # 过滤消息测试
│   ├── tag/                      # 标签过滤
│   │   ├── TagProducerTest.java  # 标签生产者测试
│   │   ├── TagConsumerTest.java  # 标签消费者测试
│   │   ├── TagAConsumer.java     # TagA 消费者
│   │   ├── TagABConsumer.java    # TagA/TagB 消费者
│   │   └── TagAllConsumer.java   # 所有标签消费者
│   └── sql/                      # SQL过滤
│       ├── SqlProducerTest.java  # SQL生产者测试
│       ├── SqlConsumerTest.java  # SQL消费者测试
│       ├── SqlAConsumer.java     # SQL A 范围消费者
│       ├── SqlAGtConsumer.java   # SQL A 大于消费者
│       ├── SqlColorConsumer.java # SQL 颜色过滤消费者
│       └── SqlAllConsumer.java   # SQL 所有消费者
├── order/                   # 顺序消息测试
│   ├── OrderProducerTest.java    # 顺序生产者测试
│   ├── OrderConsumerTest.java    # 顺序消费者测试
│   ├── OrderConsumer.java        # 顺序消费者（详细版）
│   ├── OrderSimpleConsumer.java  # 顺序消费者（简单版）
│   └── OrderConcurrentConsumer.java # 并发消费者（对比）
├── transaction/             # 事务消息测试
│   ├── TransactionProducerTest.java     # 事务生产者测试
│   ├── TransactionConsumerTest.java     # 事务消费者测试
│   ├── TransactionListenerImpl.java    # 事务监听器实现
│   ├── TransactionConsumer.java         # 事务消费者
│   ├── TransactionTagAConsumer.java     # 事务 TagA 消费者
│   ├── TransactionTagBConsumer.java     # 事务 TagB 消费者
│   └── TransactionTagCConsumer.java     # 事务 TagC 消费者
└── ProducerTest.java        # 原有的基础测试
```

## 重要变更说明

### 消费者监听器架构调整

为了解决 Spring Boot 测试中消费者监听器无法正确加载的问题，我们将所有消费者监听器从测试类的内部静态类提取为独立的类文件。

**变更前**：
```java
@SpringBootTest
public class ConsumerTest {
    @Component
    @RocketMQMessageListener(...)
    public static class BaseConsumer implements RocketMQListener<String> {
        // 消费者实现
    }
}
```

**变更后**：
```java
// 独立的消费者类文件
@Component
@RocketMQMessageListener(...)
public class BaseConsumer implements RocketMQListener<String> {
    // 消费者实现
}

// 测试类只负责测试逻辑
@SpringBootTest
public class ConsumerTest {
    @Test
    public void testConsumer() {
        // 测试逻辑
    }
}
```

### 优势

1. **正确的 Bean 注册**: 独立的消费者类能够被 Spring Boot 正确扫描和注册
2. **自动启动**: 消费者在应用启动时自动注册和启动
3. **更好的分离**: 测试逻辑与消费者实现分离，职责更清晰
4. **易于管理**: 每个消费者都有独立的类文件，便于维护和扩展

## 功能对比

### 1. 基础消息 (base)
- **同步发送**: 等待发送结果，确保消息送达
- **异步发送**: 非阻塞发送，通过回调处理结果
- **单向发送**: 只发送不等待结果，性能最高
- **负载均衡消费**: 多个消费者共同消费队列消息
- **广播消费**: 每个消费者都接收所有消息

### 2. 批量消息 (batch)
- **批量发送**: 一次发送多条消息，提高吞吐量
- **注意事项**: 批量消息大小不可超过 1MiB

### 3. 延迟消息 (delay)
- **延迟等级**: 支持 18 个固定延迟等级
- **延迟时间**: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
- **使用场景**: 定时任务、超时处理等

### 4. 过滤消息 (filter)
- **标签过滤**: 基于 Tag 进行简单过滤
- **SQL过滤**: 基于消息属性的复杂过滤条件

### 5. 顺序消息 (order)
- **全局顺序**: 整个 Topic 内消息有序
- **分区顺序**: 同一分区内消息有序
- **使用场景**: 订单处理、状态机等

### 6. 事务消息 (transaction)
- **事务保证**: 确保本地事务与消息发送的一致性
- **事务状态**: 提交、回滚、未知
- **回查机制**: 对未知状态消息进行回查

## 配置说明

### application.yml 配置
```yaml
rocketmq:
  name-server: 127.0.0.1:9876
  producer:
    group: demo_group
```

### 依赖要求
- Spring Boot 2.x+
- RocketMQ Spring Boot Starter
- Apache Commons Lang3 (用于事务消息)

## 使用方法

### 1. 启动 RocketMQ 服务
```bash
# 启动 Name Server
sh mqnamesrv

# 启动 Broker
sh mqbroker -n 127.0.0.1:9876
```

### 2. 运行测试
```bash
# 运行特定测试
mvn test -Dtest=SyncProducerTest

# 运行消费者测试（消费者会自动启动）
mvn test -Dtest=ConsumerTest

# 运行所有测试
mvn test
```

### 3. 创建 Topic
```bash
# 创建普通 Topic
sh mqadmin updateTopic -n 127.0.0.1:9876 -t TopicTest -c DefaultCluster

# 创建批量消息 Topic
sh mqadmin updateTopic -n 127.0.0.1:9876 -t BatchTopic -c DefaultCluster

# 创建延迟消息 Topic
sh mqadmin updateTopic -n 127.0.0.1:9876 -t TestTopic -c DefaultCluster

# 创建过滤消息 Topic
sh mqadmin updateTopic -n 127.0.0.1:9876 -t TagTest -c DefaultCluster
sh mqadmin updateTopic -n 127.0.0.1:9876 -t SqlTest -c DefaultCluster

# 创建事务消息 Topic
sh mqadmin updateTopic -n 127.0.0.1:9876 -t TransactionTopic -c DefaultCluster
```

## 注意事项

1. **消费者组**: 每个消费者需要使用不同的消费者组名
2. **SQL过滤**: 需要 Broker 开启 `enablePropertyFilter=true`
3. **事务消息**: 需要实现 `RocketMQLocalTransactionListener` 接口
4. **顺序消息**: 消费者需要设置 `consumeMode = ConsumeMode.ORDERLY`
5. **延迟消息**: 只支持预设的 18 个延迟等级
6. **消费者自动启动**: 所有消费者在 Spring Boot 应用启动时自动注册和启动

## 与原生 Client 的差异

| 功能 | 原生 Client | Spring Boot |
|------|-------------|-------------|
| 配置方式 | 代码配置 | 配置文件 + 注解 |
| 生产者创建 | 手动创建 DefaultMQProducer | 自动注入 RocketMQTemplate |
| 消费者创建 | 手动创建 DefaultMQPushConsumer | 使用 @RocketMQMessageListener |
| 消费者管理 | 内部静态类 | 独立的 @Component 类 |
| 事务监听器 | 直接实现 TransactionListener | 实现 RocketMQLocalTransactionListener |
| 启动关闭 | 手动调用 start()/shutdown() | 自动管理生命周期 |
| 异常处理 | 手动处理 | Spring 统一异常处理 |

## 测试建议

1. **先运行生产者测试**，确保消息发送成功
2. **再运行消费者测试**，观察消息消费情况（消费者会自动启动）
3. **查看控制台日志**，了解消息处理流程
4. **使用 RocketMQ 控制台**，监控消息状态
5. **调整参数配置**，测试不同场景

## 扩展功能

可以基于这些测试案例进一步扩展：
- 消息重试机制
- 死信队列处理
- 消息轨迹追踪
- 性能压测
- 集群部署测试 