package com.sudhanshu.reportservice.controller;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/reports")
public class ReportController {

	private final DiscoveryClient discoveryClient;
	private final RestTemplate restTemplate;
	
	public ReportController(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}
	
	@GetMapping("/event/stats")
	@CircuitBreaker(name = "event-stats-cb", fallbackMethod = "eventStatsFallback")
	public ResponseEntity<?> getEventStatus(){
		//find event service instances
		System.out.println("start /event/stats");
		
		List<ServiceInstance> instances = discoveryClient.getInstances("event-service");
		if(instances.isEmpty()) {
			return ResponseEntity.status(503).body("Event service unavailable");
		}
		
		ServiceInstance eventServiceInstance = instances.get(0);
		System.out.println(eventServiceInstance.toString() + "  URI: " + eventServiceInstance.getUri());

        String eventUrl = "http://event-service/events";


		//String eventUrl = eventServiceInstance.getUri() + "/events/aggregates";
		
		System.out.println(eventUrl);
		
		ResponseEntity<?> stats = restTemplate.getForEntity(eventUrl, Object.class);
		return stats;
	}
	
	public ResponseEntity<?> eventStatsFallback(Exception ex){
		
		System.out.println("Event stats API is down: " + ex.getMessage());
		
		return new ResponseEntity<String>("Servie is down", HttpStatusCode.valueOf(503));
	}
}
