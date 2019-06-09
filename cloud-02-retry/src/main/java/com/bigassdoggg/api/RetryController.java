package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RetryController {

    private final RestTemplate restTemplate;

    @Autowired
    public RetryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("retry")
    private User retry(){
        long before = System.currentTimeMillis();
        User user = restTemplate.getForObject("http://eureka-provider/user/findUser?id={id}"
                ,User.class,"111");
        System.out.println("retry cost time: " + (System.currentTimeMillis() - before));
        return user;
    }
}
