package com.example.eurekaribbon8764.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    //hystrix熔断后服务降级为hellServiceFallback来提供服务。
    @HystrixCommand(fallbackMethod = "helloServiceFallback")
    public  String toHello(){
        //Restful URL访问，也就是访问注册中心的服务名。
        return restTemplate.getForObject("http://SERVICE-HI/client/",String.class);
    }

    public String helloServiceFallback() {
        return "error";
    }
}
