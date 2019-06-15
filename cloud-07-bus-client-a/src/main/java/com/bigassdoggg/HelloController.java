package com.bigassdoggg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class HelloController {

    @Value("${from:defaultFrom default}")
    private String from;

    @GetMapping("/hello")
    public String hello(){
        return "client a : Hello there! from " + from;
    }
}
