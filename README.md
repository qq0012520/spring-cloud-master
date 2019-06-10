# 各章节内容说明
## 1.cloud-01 
Eureka 服务注册与发现使用：
* 重点 多server replica 使用，发现
eureka.instance.hostname 和
eureka.client.service-url 不能相同否则无法建立replica
## 2.cloud-02
Ribbon的使用：
#### 1.负债均衡使用
#### 2.重试策略使用
## 3.cloud-03
Hystrix 的使用：
#### 1. 服务调用异常或者超时后的降级
* 重点：服务熔断的超时时间即使小于重试时间，
但重试策略依然会被执行
#### 2. 使用线程数和队列长度来限流
* 重点：使用 queueSizeRejectionThreshold 来动态控制队列长度
#### 3. 使用semaphore来限流
这种方式比使用线程来更轻量级，通常作为线程的补充
#### 4. 断路器的打开和关闭
* 重点：断路器打开：requestVolumeThreshold、timeInMilliseconds、errorThresholdPercentage
这3个参数同时控制断路器的打开条件，表示：在 timeInMilliseconds 毫秒内，
如果 requestVolumeThreshold 次请求中失败率达到 errorThresholdPercentage 则开启断路器
* 重点：断路器关闭：sleepWindowInMilliseconds 这个参数控制短路器的关闭。
表示在断路器打开后经过 sleepWindowInMilliseconds 毫秒后开始尝试关闭断路器。
如果开始尝试后执行的第一次指令成功，则关闭断路器。
####5. Hystrix Dashboard 监控台
* 重点：被监控的节点要引入 actuator-starter 依赖，然后设置暴露监控节点：
management.endpoints.web.exposure.include=hystrix.stream
* 单节点监控：访问Dashboard控制台 http://localhost:2001/hystrix/
输入节点路径，比如：http://localhost:8080/actuator/hystrix.stream，点击开始监控
* 集群监控：首先创建turbine服务，将服务注册到Eureka中并将要监控的服务名配置上。
然后在Dashboard控制台中输入如turbine监控url，
比如：http://localhost:3001/turbine.stream
## 4.cloud-04
Feign 微服务内部调用
* 重点：Feign 默认使用Ribbon进行负载均衡。
设置 feign.hystrix.enabled=true 让Feign开启Hystrix的服务熔断功能
## 5.cloud-05
Zuul微服务集群网关
####1. 网关配置
* 重点：引入zuul和eureka依赖，Application加上@EnableZuulProxy注解，配置eureka服务器地址，配置路由映射
####2. 实现ZuulFilter过滤器
####3. 通过Zuul进行文件上传
####4. 实现FallbackProvider来进行服务降级
