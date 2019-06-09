package com.bigassdoggg.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("index")
public class IndexController {

    @GetMapping("main")
    public String mainIndex(){
        System.out.println("client 1 executed");
        return "hello spring cloud";
    }
}
