package com.example.eurekafeign8765.service.impl;

import com.example.eurekafeign8765.service.HiService;
import org.springframework.stereotype.Component;

@Component
public class HiServiceImpl implements HiService{
    @Override
    public String sayHi() {
        return "error";
    }
}
