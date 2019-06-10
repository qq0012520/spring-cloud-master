package com.bigassdoggg.api;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UserController {
    @GetMapping("user")
    public String user(){
        System.out.println("user service execute!!");
        return "user service execute";
    }

    @GetMapping("coupon")
    public String coupon(@RequestHeader("token") String token,@RequestHeader("level") String level){
        System.out.println("token : " + token + ",userLevel : " + level);
        return "---------- get coupon ------------";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file){
        System.out.println("文件名：" + file.getOriginalFilename() + ", 文件大小：" + file.getSize());
        try {
            file.transferTo(new File("e:/" + file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }
}
