package com.spring.microservices.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.microservices.models.Statement;

@RestController
@RequestMapping("/api")
public class StatementController {
	private List<Statement> fakeRepo;
	
	@PostConstruct
    public void init(){
        this.fakeRepo = new ArrayList<>();
        fakeRepo.add(new Statement(1l, 2l,"01/11/15 08:41", "US$411.05"));
        fakeRepo.add(new Statement(2l, 1l,"13/04/13 20:16", "US$1,914.00"));
        fakeRepo.add(new Statement(3l, 3l,"31/12/15 18:00", "€12.10"));
        fakeRepo.add(new Statement(4l, 4l,"21/11/10 19:55", "US$1.50"));
        fakeRepo.add(new Statement(5l, 4l,"10/06/14 09:37", "US$116.00"));
        fakeRepo.add(new Statement(6l, 5l,"14/01/12 14:49", "R$11.02"));
        fakeRepo.add(new Statement(7l, 7l,"15/12/20 12:00", "US$14.60"));
        fakeRepo.add(new Statement(9l, 6l,"01/11/09 13:02", "€1,888.93"));
        fakeRepo.add(new Statement(10l, 6l,"01/11/20 08:41", "€293.30"));
        fakeRepo.add(new Statement(11l, 6l,"01/11/20 08:41", "€11.68"));
    }
	
	@GetMapping("/statements")
	public List<Statement> getStatements(){
		
		return fakeRepo;
	}
	
	@GetMapping("/statements/{id}")
	public Statement getStatementById(@PathVariable Long id){
		
		return Optional.ofNullable(
				fakeRepo
				.stream()
				.filter((statement)-> statement.getId() == id)
				.reduce(null,(u,v)->{
					if(u!=null && v!=null)
						throw new IllegalStateException("More than one Statement Id found!");
					
					return u == null ? v : u;
				})).get();
	}
	
	@GetMapping("/statement")
	public List<Statement> createStatement(@RequestParam Long cardId){
		if(cardId != null){
			
			return fakeRepo.stream().filter(statement -> statement.getCardId() == cardId).collect(Collectors.toList());
		}
		
		return null;
	}
}
