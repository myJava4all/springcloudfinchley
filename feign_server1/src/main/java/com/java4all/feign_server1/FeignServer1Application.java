package com.java4all.feign_server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//开启Feign的负载均衡功能
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class FeignServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(FeignServer1Application.class, args);
	}
}
