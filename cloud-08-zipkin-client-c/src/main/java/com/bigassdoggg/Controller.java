package com.bigassdoggg;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("hello")
    public String helloB(){
        System.out.println("Hello invoked from client B");
        return "Hello invoked from client C";
    }
}

