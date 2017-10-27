# spring-netflix-oss-microservices
## Microservices communication
### **What is a Feign Client?**

```
Netflix provides Feign as an abstraction over Rest-based calls, by which Microservice can communicate 
with each other, but developers don’t have to bother about Rest internal details.
```

### **Apply to code**
**Note:** Here, **card-statement-composite** will communicate with two services: **card-service** and **statement-service**. I only explain how **card-statement-composite** communicate with **card-service** (with **statement-service** is the same). 
* We will add feign dependency into **card-statement-composite** Service:

```
   <dependency>
          <groupId>org.springframework.cloud</groupId>
          <artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
```
* Next, we have to create an Interface where we declare the services which we wanted to call. Please note that the Service Request mapping is same as  **card-service** Service Rest URL. Feign will call this URL when we call **card-statement-composite** service.

Feign dynamically generate the Implementation of the interface which  we created , so Feign has to know which service to call before hand that's why we need to give a name of the interface which is the {Service-Id} of **card-service** Service, now Feign contact to Eureka server with this Service Id and resolve the actual Ip/Host name of the **card-service** and call the URL provided in Request Mapping.

```javascript
@FeignClient(name="card-service" )//Service Id of card-service
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
```

**Note:** All Request Mapping in **card-statement-composite** must be the same as in **card-service**.
* Now we will create a **CardStatementController** where we autowired our Interface so in runtime Spring can Inject actual implementation, then we call that implementation to call **card-service** Rest API.

```javascript
@RestController
@RequestMapping("/api")
public class CardStatementController{
	@Autowired
	ICardService cardService;
  @Autowired
  IStatementService statementService;
  
	@GetMapping("/statement-by-card")
	public ResponseEntity<Map<CardVO, List<StatementVO>>> getStatementsByCardId(@RequestParam("cardId") Long cardId){
		Map<CardVO, List<StatementVO>> response = new HashMap<>();
		response.put(cardService.getCard(cardId), statementService.getStatements(cardId));
		
		return new ResponseEntity<Map<CardVO, List<StatementVO>>>(response, HttpStatus.OK);
	}
}
```
* At las, we need add **@EnableFeignClients** on top of **CardStatementCompositeApplication**

```javascript
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CardStatementCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardStatementCompositeApplication.class, args);
	}
}
```
* Start the following Microservices in order.
  1. **config-server**
  2. **dicovery**
  3. **gateway**,**card-service**,**statement-service**,**card-statement-composite**
  
  Then type the URL: http://localhost:8765/card-statement-composite/api/statement-by-card?cardId=6 
  
  And this is result:
  
  ```
  [
    {
      id:	9
      cardId:	6
      operationDate:	"01/11/09 13:02"
      value:	"€1,888.93"
     },
    {
      id:	10
      cardId:	6
      operationDate:	"01/11/20 08:41"
      value:	"€293.30"
    },
    {
      id:	11
      cardId:	6
      operationDate:	"01/11/20 08:41"
      value:	"€11.68"
    }
  ]
  ```
  ## Discovery Service
 There are two main service discovery patterns: *client‑side discovery* and *server‑side discovery*. This project uses server‑side discovery.
 
 The structure of this pattern:
 
 ![alt text](https://github.com/caingocduong/spring-netflix-oss-microservices/blob/master/server-side-pattern.png)
 
 The client makes a request to a service via a load balancer. The load balancer queries the service registry and routes each request to an available service instance. Service instances are registered and deregistered with the service registry.
 
 The service registry is a key part of service discovery. It is a database containing the network locations of service instances. 
 
 Here, **Netflix Eureka** is a service registry. It provides a REST API for registering service instances
 
 By default, Zuul uses Ribbon(Load Balancer) to look up available services instance (registered with service registry) and routes the request to te service instance.
 
 **Netflix Zuul**-Edge Server Zuul is our gatekeeper to the outside world, not allowing any unauthorized external requests pass through. It uses dynamically allocated ports to avoid port conflicts.
