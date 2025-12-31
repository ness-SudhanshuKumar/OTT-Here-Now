package com.sudhanshu.profileservice.dto.request;

import java.math.BigDecimal;
import java.util.Map;

import com.sudhanshu.profileservice.dto.EventType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest (
	    @NotBlank String userId,
	    @NotNull @Positive BigDecimal watchTimeIncrement,
	    @NotNull EventType eventType,
	    @Size(max = 100) String contentId,
	    @NotNull String deviceType,
	    Map<String, Object> metadata
	) {

}
