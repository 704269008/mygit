package com.example.eurekaribbon8764.controller;

import com.example.eurekaribbon8764.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController{
    @Autowired
    HelloService helloService;

    @RequestMapping("/hi")
    public  String toHi(){
        return helloService.toHello();
    }
}
