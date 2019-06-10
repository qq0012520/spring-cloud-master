package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello method invoked from provider a");
        return "Hello there from provider a";
    }

    @RequestMapping("/findUser")
    public User findUser(String id){
        System.out.println("findUser id=" + id + " from provider a");
        return new User(id,"zhangsan" + id);
    }
}
