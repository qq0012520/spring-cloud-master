package com.bigassdoggg.client;

import com.bigassdoggg.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//这里name为所调用的服务名称
@FeignClient(name = "eureka-provider",fallback = HelloClientFall.class)
public interface HelloClient {
    @GetMapping("/hello")
    String hello();

    @GetMapping("/findUser?id={id}")
    User findUser(@PathVariable String id);
}
