package com.example.eurekaribbon8764;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


//@EnableCircuitBreaker 注解开启断路器功能
@EnableHystrixDashboard
@EnableCircuitBreaker
@SpringBootApplication
@EnableEurekaClient
public class EurekaRibbon8764Application {
	@Bean
	@LoadBalanced //封装了负载均衡的复杂操作,开启客户端负载均衡
	//RestTemplate是spring自己提供的对象，该对象会使用Ribbon的自动化配置。
	RestTemplate restTemplate(){
		return  new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaRibbon8764Application.class, args);
	}
}
