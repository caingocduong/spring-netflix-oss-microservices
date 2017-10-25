package com.spring.microservices.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatementVO {
	private Long id;
	private Long cardId;
	private String operationDate;
	private String value;

	public StatementVO() {}
	
	public StatementVO(Long id, Long cardId, String operationDate, String value) {
		this.id = id;
		this.cardId = cardId;
		this.operationDate = operationDate;
		this.value = value;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
