package com.sudhanshu.eventservice.dto;

import java.time.Instant;

import com.sudhanshu.eventservice.entity.EventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EventCreateRequest {

	@NotBlank
	private String userId;
	
	@NotNull
	private EventType type;
	
	@NotNull
	private Instant timestamp;
	
	private String metadata;

	public EventCreateRequest(){}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	
}
