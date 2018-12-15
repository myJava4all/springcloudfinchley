package com.java4all;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.java4all.dao")
@SpringBootApplication
@Configuration
public class ZklockApplication {

	public static void main(String[] args) {
		 SpringApplication.run(ZklockApplication.class, args);
	}
//	@Bean
//	public RedissonClient redissonClient(){
//		Config config = new Config();
//		SingleServerConfig serverConfig = config.useSingleServer().setAddress("116.62.62.26:6379")
//				.setPassword("wang917917");
//		RedissonClient redissonClient = Redisson.create(config);
//		return redissonClient;
//	}
	//配置文档：https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
}
