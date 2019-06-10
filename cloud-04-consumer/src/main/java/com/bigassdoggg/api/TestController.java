package com.bigassdoggg.api;

import com.bigassdoggg.client.HelloClient;
import com.bigassdoggg.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final HelloClient helloClient;

    @Autowired
    public TestController(HelloClient helloClient) {
        this.helloClient = helloClient;
    }

    @GetMapping("/hello")
    public String hello(){
        long before = System.currentTimeMillis();
        String msg = helloClient.hello();
        System.out.println("hello cost time : " + (System.currentTimeMillis() - before));
        return msg;
    }

    @GetMapping("/findUser")
    public User findUser(String id){
        return helloClient.findUser(id);
    }
}
