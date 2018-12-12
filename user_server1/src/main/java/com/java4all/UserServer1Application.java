package com.java4all;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class UserServer1Application {

	public static void main(String[] args) {
		SpringApplication.run(UserServer1Application.class, args);
	}
}
