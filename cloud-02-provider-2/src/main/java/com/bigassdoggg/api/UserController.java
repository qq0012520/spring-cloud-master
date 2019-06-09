package com.bigassdoggg.api;

import com.bigassdoggg.model.User;
import org.springframework.web.bind.annotation.*;

@RequestMapping("user")
@RestController
public class UserController {

    @GetMapping("findUser")
    public User findUser(String id){
        System.out.println("------ provider 2 ------ findUser Get invoke-------");
        return new User(id,"张三");
    }

    @PostMapping("findUserPost")
    public User findUserPost(String id){
        System.out.println("------ provider 2 ------ findUser Post invoke-------");
        return new User(id,"张三 post");
    }
}
