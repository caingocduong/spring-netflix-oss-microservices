package com.spring.microservices.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.microservices.models.CardVO;
import com.spring.microservices.models.StatementVO;
import com.spring.microservices.services.ICardClient;
import com.spring.microservices.services.IStatementClient;

@RestController
@RequestMapping("/api")
public class CardStatementController{
	@Autowired
	ICardClient cardClient;
	@Autowired
	IStatementClient statementClient;
	
	@HystrixCommand(fallbackMethod = "defaultCardStatementError")
	@GetMapping("/statement-by-card")
	public ResponseEntity<Map<CardVO, List<StatementVO>>> getStatementsByCardId(@RequestParam("cardId") Long cardId){
		Map<CardVO, List<StatementVO>> response = new HashMap<>();
		response.put(cardClient.getCard(cardId), statementClient.getStatements(cardId));
		
		return new ResponseEntity<Map<CardVO, List<StatementVO>>>(response, HttpStatus.OK);
	}
	
	public ResponseEntity<Map<CardVO, List<StatementVO>>> defaultCardStatementError(Long cardId){
		Map<CardVO, List<StatementVO>> response = new HashMap<>();
		
		return new ResponseEntity<Map<CardVO, List<StatementVO>>>(response, HttpStatus.OK);
	}
}
