# ncservice

## 使用技术

* springcloud eureka,  发现服务，实现服务注册,类似zookeeper.支持高可用
* springcloud feign,   eureka服务注解配置化，优雅调用
* springcloud ribbon,  eureka服务程序,实现多种负载均衡算法
* springcloud hystrix, 断路器，防止服务无线等待，雪崩效应
* springcloud turbine, 监控多个微服务,收集运行情况，生成图表. 各个注册服务需要引用hystrix
* springcloud zuul,    开源微服务网卡/代理,非常强大，可以隐藏服务地址等
* springcloud sleuth,  服务跟踪,打印各个子服务的请求流向
* springcloud zipkin,  可视化日志数据分析，可以直接集成进去(未使用)
* springboot actuator, 打印断点信息，实现监控，服务状态等...
* azkaban,             开源任务调度器


### 1. ncservice-az-provider 

* (1) 封装azkaban api调用信息
* (2) 使用mybatis对数据库信息进行查询

### 2. ncservice-az-consumer

* (1) 使用feign进行优雅的服务调用

### 3. ncservice-web

* (1) 使用shiro实现权限校验
* (2) 使用hystrix进行服务调用处理,防止雪崩