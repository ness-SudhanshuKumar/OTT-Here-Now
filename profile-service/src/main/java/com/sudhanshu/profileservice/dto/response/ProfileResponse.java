package com.sudhanshu.profileservice.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sudhanshu.profileservice.dto.metrics.ActivityMetrics;
import com.sudhanshu.profileservice.dto.metrics.EngagementMetrics;
import com.sudhanshu.profileservice.dto.metrics.ProfilePreferences;
import com.sudhanshu.profileservice.dto.metrics.RiskMetrics;
import com.sudhanshu.profileservice.dto.metrics.TopContent;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ProfileResponse {

	 private String userId;
	    private String name;
	    private String email;
	    private LocalDateTime createdAt;
	    private LocalDateTime lastSeen;
	    
	    @JsonProperty("engagement")
	    private EngagementMetrics engagement;
	    
	    @JsonProperty("risk")
	    private RiskMetrics risk;
	    
	    @JsonProperty("preferences")
	    private ProfilePreferences preferences;
	    
	    @JsonProperty("activity")
	    private ActivityMetrics activity;
	    
	    private List<TopContent> topContent;
	    private Boolean isActive;
	    
	
}
