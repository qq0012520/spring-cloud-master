package com.bigassdoggg.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

}
