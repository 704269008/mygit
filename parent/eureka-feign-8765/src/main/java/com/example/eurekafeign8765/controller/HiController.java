package com.example.eurekafeign8765.controller;

import com.example.eurekafeign8765.service.HiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    HiService hiService;

    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(){
        return hiService.sayHi();
    }
}
