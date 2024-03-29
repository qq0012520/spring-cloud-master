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
## 6.cloud-06
集群配置中心:
* 在client端通过访问 http://localhost:8888/actuator/refresh 来刷新单个客户端配置
## 7.cloud-7
Bus消息总线和集群配置中心
* 重点：消息总线就是通过消息中间件来将分布式系统中各个节点连接起来，
这样就可以通过该中间件来更新集群中整体或部分节点的状态（比如配置，管理指令等）
####1. 重点： 修改和刷新节点的配置
##### (a).修改（临时）配置：
  通过POST访问任意某个配置客户端节点路径：/actuator/env（配置单节点） 或 /actuator/bus-env（配置所有节点），
并传入json参数，如：
{
	"name": "from",
	"value": "11.11"
} 
来设置环境配置的值。设置值后要刷新后才能生效 
##### (b).刷新配置：
  通过POST访问任意某个配置客户端节点路径：/actuator/refresh（刷新单节点） 或 /actuator/bus-refresh（刷新所有节点）
后，
会刷新节点的配置

*注意：通过actuator的方式修改配置值是指临时性的，他不会修改git中配置的值
，如果应用重启后，还是会去git上读取*

## 8.cloud-8
分布式系统链路追踪Zipkin使用
####1. 重点： 追踪客户端配置：
配置zipkin采集服务器地址：  
spring.zipkin.base-url=http://localhost:9411/   
配置采样率（一批请求中有多少比例会被采样）：   
spring.sleuth.sampler.probability=1 0.001 - 1 （从千分之一到百分之百）

####1. 重点： 追踪采样服务器：
从以下地址下载最新的服务器Jar包：  
https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec   
下载完成后运行：java -jar zipkin.jar   
&ensp;&ensp;*tip：从v2版本开始，官方不推荐自己搭建zipkin采样服务器，
而是推荐使用他们编译好的Jar包*  
&ensp;&ensp;可以通过springboot传递参数的方式来配置服务器，所有的配置参数可以
参考项目里的 zipkin-server-shared.yml 文件。   
&ensp;&ensp;如果用mysql数据库保存（默认使用内存保存）链路数据
那么要先导入zipkin要用到的数据库表 zipkin_mysql.sql，然后
在启动jar时传入响应参数，比如：   
`java -jar zipkin-server-2.12.9-exec.jar
 --STORAGE_TYPE=mysql --MYSQL_USER=mysql_accessor --MYSQL_PASS=Ppoo56i$ --MYSQL_HOST=192.168.56.1`
