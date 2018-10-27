package com.example.eurekaclient8762;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class EurekaClient8762Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient8762Application.class, args);
	}
	@RequestMapping("/client")
	public String client(){
		return "hi,i am from 8762";
	}
}
