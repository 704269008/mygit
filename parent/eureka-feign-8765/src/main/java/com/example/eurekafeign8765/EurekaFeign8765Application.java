package com.example.eurekafeign8765;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class EurekaFeign8765Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaFeign8765Application.class, args);
	}
}
