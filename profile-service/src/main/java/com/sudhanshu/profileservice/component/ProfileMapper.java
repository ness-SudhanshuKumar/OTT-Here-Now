package com.sudhanshu.profileservice.component;


import org.springframework.stereotype.Component;

import com.sudhanshu.profileservice.dto.metrics.EngagementMetrics;
import com.sudhanshu.profileservice.dto.response.ProfileResponse;
import com.sudhanshu.profileservice.entity.UserProfiles;

@Component
public class ProfileMapper {

    public ProfileResponse toResponse(UserProfiles profile) {
        return ProfileResponse.builder()
            .userId(profile.getUserId())
            .engagement(EngagementMetrics.builder()
                .totalWatchTimeMin(profile.getTotalWatchTimeMin())
                .score(profile.getEngagementScore())
                .build())
            .build();
    }
}
