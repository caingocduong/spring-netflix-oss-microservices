package com.spring.microservices.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.models.Card;

@RestController
@RequestMapping("/api")
public class CardController {
	private List<Card> fakeRepo;
	
	@PostConstruct
	public void init(){
		this.fakeRepo = new ArrayList<>();
        fakeRepo.add(new Card(1l, "John Warner", String.valueOf(Math.random()).substring(0, 16),"11/20"));
        fakeRepo.add(new Card(2l, "Paul Crarte", String.valueOf(Math.random()).substring(0, 16),"09/25"));
        fakeRepo.add(new Card(3l, "Ana Hassent", String.valueOf(Math.random()).substring(0, 16),"01/19"));
        fakeRepo.add(new Card(4l, "Elena Tarin", String.valueOf(Math.random()).substring(0, 16),"06/22"));
        fakeRepo.add(new Card(5l, "Wending Qua", String.valueOf(Math.random()).substring(0, 16),"03/25"));
        fakeRepo.add(new Card(6l, "Julio Sanch", String.valueOf(Math.random()).substring(0, 16),"09/18"));
        fakeRepo.add(new Card(7l, "Adolf Bianc", String.valueOf(Math.random()).substring(0, 16),"07/22"));
	}
	
	@GetMapping("/cards")
	public List<Card> getCards(){
		
		return fakeRepo;
	}
	
	@GetMapping("/cards/{id}")
	public Card getCardById(@PathVariable Long id){
		
		return Optional.ofNullable(
				fakeRepo
					.stream()
					.filter((card) -> card.getId().equals(id))
					.reduce(null,(u,v)->{
						if(u!=null && v!=null){
							throw new IllegalStateException("More than one Id Card found!");
						} else {
							
							return u == null ? v : u;
						}
					})).get();
	}
	
	@PostMapping("/new-card")
	public Card createCard(@RequestBody Card newCard){
		if(newCard.getId() != null){
			fakeRepo.add(newCard);
		}
		
		return newCard;
	}
}
