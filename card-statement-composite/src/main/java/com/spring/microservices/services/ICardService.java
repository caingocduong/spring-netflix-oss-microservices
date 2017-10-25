package com.spring.microservices.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.microservices.models.CardVO;

import feign.RequestLine;

public interface ICardService {
	final static String PREFIX = "api/";
	
	@RequestLine("GET")
	@RequestMapping(method = RequestMethod.GET, value = PREFIX + "cards")
	List<CardVO> getCards();
	
	@RequestLine("GET")
	@RequestMapping(method = RequestMethod.GET, value = PREFIX + "cards/{cardId}")
	CardVO getCard(@PathVariable("cardId") Long cardId);
	
	@RequestLine("POST")
	@RequestMapping(method = RequestMethod.POST, value = PREFIX + "/create-card")
	CardVO createCard(@RequestBody CardVO card);
}
