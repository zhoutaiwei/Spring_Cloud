package com.spring.cloud.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class Application_Customer  {

	   public static void main(String[] args) {
	        SpringApplication.run(Application_Customer.class, args);
	    }

	    @Bean
	    @LoadBalanced//表明这个restRemplate开启负载均衡的功能
	    RestTemplate restTemplate() {
	        return new RestTemplate();
	    }

}