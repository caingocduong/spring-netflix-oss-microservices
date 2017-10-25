package com.spring.microservices.services;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name="card-service")
public interface ICardClient extends ICardService{

}
