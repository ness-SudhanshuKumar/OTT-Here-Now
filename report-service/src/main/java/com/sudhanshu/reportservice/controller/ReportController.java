package com.sudhanshu.reportservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sudhanshu.reportservice.util.RestTemplateHelper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@RestController
@RequestMapping("/reports")
public class ReportController {

    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

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
		log.debug("start /event/stats");
		
		List<ServiceInstance> instances = discoveryClient.getInstances("event-service");
		if(instances.isEmpty()) {
			return ResponseEntity.status(503).body("Event service unavailable");
		}
		
		ServiceInstance eventServiceInstance = instances.get(0);
		log.info(eventServiceInstance.toString() + "  URI: " + eventServiceInstance.getUri());

        String eventUrl = "http://event-service/events";


		//String eventUrl = eventServiceInstance.getUri() + "/events/aggregates";
		
		log.info(eventUrl);
		
		ResponseEntity<?> stats = restTemplate.getForEntity(eventUrl, Object.class);
		return stats;
	}
	
	@GetMapping("/high-engagement")
	public ResponseEntity<?> getHighEngagementProfiles(
            @RequestParam(defaultValue = "0.50") BigDecimal minScore){
		log.info("/high-engagement with miScore", minScore );
		List<ServiceInstance> instances = discoveryClient.getInstances("PROFILE-SERVICE");
		if(instances.isEmpty()) {
			return ResponseEntity.status(503).body("Event service unavailable");
		}
		ServiceInstance profieServiceInstance = instances.get(0);
		log.info(profieServiceInstance.toString() + "  URI: " + profieServiceInstance.getUri());
        String profileUrl = "http://profile-service/user-profiles/high-engagement";
		log.info(profileUrl);
		String urlWithParams = RestTemplateHelper.addStandardQueryParams(profileUrl);
		ResponseEntity<?> profiles = restTemplate.getForEntity(urlWithParams, Object.class);
		return profiles;

	}
	
	public ResponseEntity<?> eventStatsFallback(Exception ex){
		
		log.debug("Event stats API is down: " ,ex.getMessage());
		
		return new ResponseEntity<String>("Servie is down", HttpStatusCode.valueOf(503));
	}
}
