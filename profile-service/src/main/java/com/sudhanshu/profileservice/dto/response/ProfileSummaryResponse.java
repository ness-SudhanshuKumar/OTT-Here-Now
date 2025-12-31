package com.sudhanshu.profileservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileSummaryResponse {
    private String userId;
    private String name;
    private LocalDateTime lastSeen;
    private BigDecimal engagementScore;
    private BigDecimal churnRiskScore;
    private List<String> preferredGenres;
    private String subscriptionTier;
}