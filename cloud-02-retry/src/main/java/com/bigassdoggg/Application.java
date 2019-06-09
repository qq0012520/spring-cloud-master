package com.bigassdoggg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory requestFactory){
        return new RestTemplate(requestFactory);
    }

    @Bean
    @ConfigurationProperties(prefix = "client.request")
    HttpComponentsClientHttpRequestFactory requestFactory(){
        return new HttpComponentsClientHttpRequestFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
