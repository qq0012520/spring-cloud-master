package com.bigassdoggg.api;

import com.bigassdoggg.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
