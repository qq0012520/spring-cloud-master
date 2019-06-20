package com.bigassdoggg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    private final RestTemplate restTemplate;

    @Autowired
    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("hello")
    public String helloB(){
        System.out.println("Hello invoked from client B");
        return restTemplate.getForObject("http://work-client-c/hello",String.class);
    }
}

