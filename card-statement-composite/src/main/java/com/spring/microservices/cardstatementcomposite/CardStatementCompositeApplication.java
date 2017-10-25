package com.spring.microservices.cardstatementcomposite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages={"com.spring.microservices.services"})
@ComponentScan({"com.spring.microservices.*"})
public class CardStatementCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardStatementCompositeApplication.class, args);
	}
}
