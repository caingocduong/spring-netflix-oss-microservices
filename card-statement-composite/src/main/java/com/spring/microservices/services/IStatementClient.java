package com.spring.microservices.services;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name="statement-service")
public interface IStatementClient extends IStatementService{

}
