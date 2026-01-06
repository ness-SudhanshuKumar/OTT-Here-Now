package com.sudhanshu.eventservice.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.dto.EventUpdateRequest;
import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventStatus;
import com.sudhanshu.eventservice.entity.EventType;
import com.sudhanshu.eventservice.exception.NotFoundException;
import com.sudhanshu.eventservice.repository.EventRepository;
import com.sudhanshu.eventservice.service.EventService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;


@Service
@Transactional
public class EventServiceImpl implements EventService{

	private final EventRepository eventRepository;
	
	public EventServiceImpl(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	@Override
	public EventResponse createEvent(EventCreateRequest createRequest) {
		// TODO Auto-generated method stub
		Event event = new Event();
		event.setUserId(createRequest.getUserId());
		event.setType(createRequest.getType());
		event.setTimestamp(createRequest.getTimestamp());
		event.setMetadata(createRequest.getMetadata());
		event.setStatus(EventStatus.RECEIVED);
		return toResponse(eventRepository.save(event));
	}

	@Override
	@Transactional(readOnly = true)
    @CircuitBreaker(name = "dbService", fallbackMethod = "getEventFallBack")
    @TimeLimiter(name = "dbService")
    @Retry(name = "dbService")
	public EventResponse getEvent(UUID id) {
		// TODO Auto-generated method stub
		Event event = eventRepository.findById(id)
				.orElseThrow(()-> new NotFoundException("Event not Found: "+ id));
		return toResponse(event);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<EventResponse> searchEvents(String userId, EventType type, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Event> page;
		if(userId != null && type != null)
			page = eventRepository.searchByUserIdAndType(userId, type, pageable);
		else if (userId != null)
			page = eventRepository.findByUserId(userId, pageable);
		else
			page = eventRepository.findAll(pageable);
		System.out.println("ResultSet:  \t"+page);
		return page.map(this::toResponse);
	}

	@Override
	public EventResponse updateEvent(UUID id, EventUpdateRequest updateRequest) {
		// TODO Auto-generated method stub
		
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Event not Found: "+ id));
		event.setStatus(updateRequest.getStatus());
		if(updateRequest.getMetadata() != null)
			event.setMetadata(updateRequest.getMetadata());
		return toResponse(event);
		
	}

	@Override
	public void softDelete(UUID id) {
		// TODO Auto-generated method stub
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Event not Found: "+ id));
		event.setStatus(EventStatus.DELETED);
	}

	@Override
	public Page<EventResponse> searchEvents(Specification<Event> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		Page<Event> page = eventRepository.findAll(spec, pageable);
		return page.map(this::toResponse);
	}
	private EventResponse toResponse(Event event) {
		EventResponse response = new EventResponse();
		response.setId(event.getId());
		response.setUserId(event.getUserId());
		response.setType(event.getType());
		response.setTimestamp(event.getTimestamp());
		response.setMetadata(event.getMetadata());
		response.setStatus(event.getStatus());
		response.setCreatedAt(event.getCreatedAt());
		response.setUpdatedAt(event.getUpdatedAt());
		return response;
		
	}

	public EventResponse getEventFallBack(UUID id, Throwable ex) {
		System.out.println("USERID "+ id + " failing with exception "+ ex.getMessage());
		EventResponse response = new EventResponse();
		response.setId(id);
		response.setUserId("Dummy UserId");
		response.setType(EventType.VIEW_START);
		response.setTimestamp(Instant.now());

		return response;
	}
}
