package com.bigassdoggg.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api")
public class InvokerController {
    private final RestTemplate restTemplate;

    @Autowired
    public InvokerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/invoke")
    public String invoke(){
        return restTemplate.getForObject("http://eureka-client/index/main",String.class);
    }
}
