package com.sudhanshu.eventservice.service;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.dto.EventUpdateRequest;
import com.sudhanshu.eventservice.entity.EventType;

public interface EventService {

	EventResponse createEvent(EventCreateRequest createRequest);
	
	EventResponse getEvent(UUID id);
	
	Page<EventResponse> searchEvents(String userId, EventType type, Pageable pageable);
	
	EventResponse updateEvent(UUID id, EventUpdateRequest updateRequest);
	
	void softDelete(UUID id);
}
