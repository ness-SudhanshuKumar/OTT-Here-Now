package com.sudhanshu.profileservice.dto.metrics;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class TopContent {
    private String contentId;
    private BigDecimal watchTimeMin;
    private BigDecimal completionRate;
    private String genre;
}