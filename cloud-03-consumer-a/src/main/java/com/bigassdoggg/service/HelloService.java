package com.bigassdoggg.service;

import com.bigassdoggg.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class HelloService {

    private final RestTemplate restTemplate;

    @Autowired
    public HelloService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "callHelloFall")
    public String callHello(){
        return restTemplate.getForObject("http://eureka-provider/hello",String.class);
    }

    private String callHelloFall(){
        return "callHello fall back execute";
    }

    @HystrixCommand(fallbackMethod = "callHiFall")
    public String callHi(){
        return restTemplate.getForObject("http://eureka-provider/hi",String.class);
    }

    private String callHiFall(){
        return "callHi fall back execute";
    }

    @HystrixCommand(fallbackMethod = "callTimeout",
        commandKey = "callTimeoutKey",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
        })
    public String callHelloForCommandTimeout(){
        return restTemplate.getForObject("http://eureka-provider/hello",String.class);
    }

    private String callTimeout(){
        return "call command timeout -------";
    }

    @HystrixCollapser(
            batchMethod = "findUsers",
            collapserKey = "findUserCollapserKey",
            collapserProperties = {
                @HystrixProperty(name = "maxRequestsInBatch",value = "100"), //最大合并请求数，当达到这个数量后立即发送请求
                @HystrixProperty(name = "timerDelayInMilliseconds",value = "100"), //最大合并请求延迟时间，当达到时间后立即发送请求
                @HystrixProperty(name = "requestCache.enabled",value = "false") //是否缓存请求，对于一些静态请求数据可以开启
            }
    )
    public Future<User> findUser(String id){
        return null;
    }

    @HystrixCommand
    public List<User> findUsers(List<String> ids){
        System.out.println("请求合并线程操作；--------------> " + Thread.currentThread().getName());
        User[] users = restTemplate.getForObject("http://eureka-provider/users?ids={ids}"
              ,User[].class, StringUtils.join(ids,","));
        return Arrays.asList(users);
    }


    @HystrixCommand(
            commandKey = "invokeCostKey",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value = "20"), //允许最大的并发线程数
                    @HystrixProperty(name = "maxQueueSize",value = "1000"),//请求队列最大数
                    @HystrixProperty(name = "queueSizeRejectionThreshold",value = "50")//队列达到该阈值后执行降级策略
            },
            fallbackMethod = "invokeCostFall"
    )
    public String invokeCost(){
        return restTemplate.getForObject("http://eureka-provider/costWork",String.class);
    }

    private String invokeCostFall(){
        System.out.println("invokeCostFall 执行限流降级策略-----");
        return "执行限流降级策略";
    }

    @HystrixCommand(
            commandKey = "semaphoreLimitKey",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy",value = "SEMAPHORE"),
                    @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests",value = "20")
            },
            fallbackMethod = "semaphoreLimitFall"
    )
    public String semaphoreLimit(){
        return restTemplate.getForObject("http://eureka-provider/costWork",String.class);
    }

    private String semaphoreLimitFall(){
        System.out.println("semaphore 限流降级执行 ");
        return "semaphore 限流降级执行";
    }

    /**
     * 测试断路器打开和关闭条件
     * @return
     */
    @HystrixCommand(
            commandKey = "circuitBreakerConKey",
            commandProperties = {
                    //下面3个参数同时起作用：表示在10秒内，10次请求中失败率达到50% 则打开断路器
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                    //下面的参数表示：在打开断路器后经过多少秒后开始尝试关闭断路器
                    //如果开始尝试后第一个请求是成功的，则关闭断路器
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000")
            },
            fallbackMethod = "circuitBreakerConFall"
    )
    public String circuitBreakerCon(){
        System.out.println("circuit normal route exec");
        // It will fail from provider a
        return restTemplate.getForObject("http://eureka-provider/hi",String.class);
    }

    private String circuitBreakerConFall(){
        System.out.println("circuit fallback route exec");
        return "circuit breaker opened, so fall back";
    }

}
