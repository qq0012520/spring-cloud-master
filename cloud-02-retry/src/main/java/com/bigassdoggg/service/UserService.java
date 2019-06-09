package com.bigassdoggg.service;

import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Retryable(
            value = {RemoteAccessException.class,NullPointerException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000,multiplier = 1)
    )
    public void call() throws Exception{
        System.out.println("do something .......");
        throw new NullPointerException("null调用异常.....");
    }

    @Recover
    public void recover(RemoteAccessException ex){
        System.out.println("重试失败。。。 最终处理结果1 " + ex.getMessage());
    }

    @Recover
    public void recover(NullPointerException ex){
        System.out.println("重试失败。。。 最终处理结果2 " + ex.getMessage());
    }


}
