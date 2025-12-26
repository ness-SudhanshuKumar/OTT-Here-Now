package com.sudhanshu.eventservice.controller;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.dto.EventUpdateRequest;
import com.sudhanshu.eventservice.entity.EventType;
import com.sudhanshu.eventservice.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {
	
	private final EventService eventService;
	
	public EventController(EventService eventService) {
		this.eventService=eventService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EventResponse create(@Valid @RequestBody EventCreateRequest createRequest) {
		return eventService.createEvent(createRequest);
	}
	
	@GetMapping("/{id}")
	public EventResponse getById(@PathVariable UUID id) {
		return eventService.getEvent(id);
	}
	
	@GetMapping
	public Page<EventResponse> search(
			@RequestParam(required = false) String userId,
			@RequestParam(required = false) EventType type,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size
			){
		Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
		return eventService.searchEvents(userId, type, pageable);
	}
	
	@PutMapping("/{id}")
	public EventResponse update(
			@PathVariable UUID id,
			@Valid @RequestBody EventUpdateRequest eventUpdateRequest
			) {
		return eventService.updateEvnt(id, eventUpdateRequest);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		eventService.softDelete(id);
	}
	

}
