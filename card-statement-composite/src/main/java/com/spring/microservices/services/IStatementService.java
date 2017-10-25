package com.spring.microservices.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.microservices.models.StatementVO;

import feign.RequestLine;

public interface IStatementService {
	final static String PREFIX = "api/";
	
	@RequestLine("GET")
	@RequestMapping(method = RequestMethod.GET ,value = PREFIX + "statements")
	List<StatementVO> getStatements();
	
	@RequestLine("GET")
	@RequestMapping(method = RequestMethod.GET, value = PREFIX + "statements/{statementId}")
	StatementVO getStatement(@PathVariable("statementId") Long statementId);
	
	@RequestLine("GET")
	@RequestMapping(method = RequestMethod.GET, value = PREFIX + "statement")
	List<StatementVO> getStatements(@RequestParam(name="cardId") Long cardId);
}
