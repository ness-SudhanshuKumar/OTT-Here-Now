package com.sudhanshu.profileservice.dto.metrics;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class RiskMetrics {
    private BigDecimal churnScore;  // 0.0-1.0
    private Integer bingeStreakDays;
    private LocalDate lastBingeDate;
    private Integer peakActivityHour;
}
