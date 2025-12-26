package com.sudhanshu.eventservice.dto;

import com.sudhanshu.eventservice.entity.EventStatus;

import jakarta.validation.constraints.NotNull;

public class EventUpdateRequest {

	@NotNull
	private EventStatus status;
	
	private String metadata;
	
	public EventUpdateRequest() {}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	
}
