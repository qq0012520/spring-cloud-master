package com.bigassdoggg.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/order")
    public String order(){
        System.out.println("order service exec");
        return "order service exec";
    }
}
