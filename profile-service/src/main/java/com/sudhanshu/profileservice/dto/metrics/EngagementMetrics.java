package com.sudhanshu.profileservice.dto.metrics;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngagementMetrics {
    private BigDecimal totalWatchTimeMin;
    private Integer totalSessions;
    private BigDecimal avgCompletionRate;
    private BigDecimal avgSessionDurationMin;
    private BigDecimal score;  // 0.0-1.0
}