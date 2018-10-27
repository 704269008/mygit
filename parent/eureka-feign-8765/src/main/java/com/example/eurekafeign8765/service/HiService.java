package com.example.eurekafeign8765.service;

import com.example.eurekafeign8765.service.impl.HiServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//使用@FeignClient注解定义了该接口是一个Feign客户端，name指定了注册到Eureka上的服务名，
//fallback是服务降级后的接口实现类
@FeignClient(value = "service-hi",fallback = HiServiceImpl.class)
public interface HiService {
    //通过spring mvc的注解来配置特定的服务下的具体实现。与服务端一一对应
    @RequestMapping(value = "/client",method = RequestMethod.GET)
    String sayHi();
}
