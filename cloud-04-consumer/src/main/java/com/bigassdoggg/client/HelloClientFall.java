package com.bigassdoggg.client;

import com.bigassdoggg.model.User;
import org.springframework.stereotype.Component;

@Component
public class HelloClientFall implements HelloClient{

    @Override
    public String hello() {
        System.out.println("hello 降级方法执行");
        return "hello 降级方法执行!!!";
    }

    @Override
    public User findUser(String id) {
        System.out.println("findUser 降级方法执行");
        return null;
    }
}
