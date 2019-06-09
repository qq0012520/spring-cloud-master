package com.bigassgoddd;

import com.bigassdoggg.Application;
import com.bigassdoggg.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class RetryTest {
    @Autowired
    private UserService userService;

    @Test
    public void callTest(){
        try {
            userService.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
