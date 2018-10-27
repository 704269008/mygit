package com.example.eurekaribbon8764.configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HystrixConfiguration {
    @Bean(name = "hystrixRegistrationBean")
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet(),"/hystrix.stream");
        registration.setLoadOnStartup(1);
        registration.setName("hystrixServlet");
        return registration;
    }
}
