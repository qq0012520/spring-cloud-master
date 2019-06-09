package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import com.bigassdoggg.service.HelloService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class HelloController {
    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("callHello")
    public String callHello(){
        return helloService.callHello();
    }

    @GetMapping("callHi")
    public String callHi(){
        return helloService.callHi();
    }

    @GetMapping("callTimeout")
    public String callTimeout(){
        return helloService.callHelloForCommandTimeout();
    }

    @GetMapping("findUser")
    public User findUser(String id) throws ExecutionException, InterruptedException {
        return helloService.findUser(id).get();
    }

    @GetMapping("collapseReq")
    public String collapseReq() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        Future<User> userFuture1 = helloService.findUser("1");
        Future<User> userFuture2 = helloService.findUser("2");
        Future<User> userFuture3 = helloService.findUser("3");
        Future<User> userFuture4 = helloService.findUser("4");
        Future<User> userFuture5 = helloService.findUser("5");

        System.out.println("user : " + userFuture1.get());
        System.out.println("user : " + userFuture2.get());
        System.out.println("user : " + userFuture3.get());
        System.out.println("user : " + userFuture4.get());
        System.out.println("user : " + userFuture5.get());

        context.shutdown();
        return "success";
    }

    @GetMapping("/costWork")
    public String costWork(){
        return helloService.invokeCost();
    }

    @GetMapping("/semaphoreLimit")
    public String semaphoreLimit(){
        return helloService.semaphoreLimit();
    }

    @GetMapping("circuitBreakerTest")
    public String circuitBreakerTest(){
        return helloService.circuitBreakerCon();
    }
}
