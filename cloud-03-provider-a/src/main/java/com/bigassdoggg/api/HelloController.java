package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        System.out.println("say hello from provider 1 ------");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "say hello ------";
    }

    @GetMapping("/hi")
    public String hi(){
        System.out.println("say hi from provider 1 --------");
        int i = 1/0;
        return "say hi -------";
    }

    @GetMapping("users/{id}")
    public User findUser(@PathVariable String id){
        System.out.println("findUser invoke from provider 1");
        return new User(id,"张三" + id);
    }

    @GetMapping("users")
    public List<User> findUsers(@RequestParam String ids){
        System.out.println("findUsers invoke from provider 1");
        if(StringUtils.isBlank(ids)){
            return null;
        }
        List<User> users = new ArrayList<>();
        List<String> idList = new ArrayList<>();
        if(ids.contains(",")){
            idList.addAll(Arrays.asList(ids.split(",")));
        }else{
            idList.add(ids);
        }
        for(String id : idList){
            users.add(new User(id,"张三" + id));
        }
        return users;
    }

    @GetMapping("costWork")
    public String costWork(){
        System.out.println("provider 1 cost work execute");
        try {
            Thread.sleep(RandomUtils.nextInt(500));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "cost work done";
    }
}
