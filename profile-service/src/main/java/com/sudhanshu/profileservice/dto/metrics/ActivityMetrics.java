package com.sudhanshu.profileservice.dto.metrics;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ActivityMetrics {
    private Integer peakHour;
    private Integer avgDailySessions;
    private BigDecimal weeklyGrowthRate;
}