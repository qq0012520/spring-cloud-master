package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@RestController
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

    @GetMapping("findUser")
    public User findUser(String id){
        return restTemplate.getForObject("http://eureka-provider/user/findUser?id={id}"
                , User.class,id);
    }

    @GetMapping("findUserPost")
    public User findUserPost(String id){
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.put("id", Collections.singletonList(id));
        return restTemplate.postForObject("http://eureka-provider/user/findUserPost"
                ,params,User.class);
    }
}
