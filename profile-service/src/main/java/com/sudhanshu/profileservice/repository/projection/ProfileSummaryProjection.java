package com.sudhanshu.profileservice.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ProfileSummaryProjection {
    String getUserId();

    BigDecimal getEngagementScore();

    BigDecimal getChurnRiskScore();

    LocalDateTime getLastSeen();

    String getSubscriptionTier();
}
