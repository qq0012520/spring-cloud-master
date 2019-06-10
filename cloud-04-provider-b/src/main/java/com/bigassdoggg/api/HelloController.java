package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("hello method invoked from provider b");
        return "Hello there from provider b";
    }

    @RequestMapping("/findUser")
    public User findUser(String id){
        System.out.println("findUser id=" + id + " from provider b");
        return new User(id,"zhangsan" + id);
    }
}
