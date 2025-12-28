package com.sudhanshu.eventservice.service;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import com.sudhanshu.eventservice.dto.EventCreateRequest;
import com.sudhanshu.eventservice.dto.EventResponse;
import com.sudhanshu.eventservice.dto.EventUpdateRequest;
import com.sudhanshu.eventservice.entity.Event;
import com.sudhanshu.eventservice.entity.EventType;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public interface EventService {

	EventResponse createEvent(EventCreateRequest createRequest);
	
	EventResponse getEvent(UUID id);
	
	Page<EventResponse> searchEvents(String userId, EventType type, Pageable pageable);
	
	Page<EventResponse> searchEvents(@Nullable Specification<Event> spec, Pageable pageable );
	
	EventResponse updateEvent(UUID id, EventUpdateRequest updateRequest);
	
	void softDelete(UUID id);
}
