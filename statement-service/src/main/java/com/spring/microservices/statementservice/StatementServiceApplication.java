package com.spring.microservices.statementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan({"com.spring.microservices.*"})
public class StatementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StatementServiceApplication.class, args);
	}
}
