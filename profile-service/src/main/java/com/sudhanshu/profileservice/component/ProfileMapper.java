package com.sudhanshu.profileservice.component;


import com.sudhanshu.profileservice.dto.request.CreateProfileRequest;
import org.springframework.stereotype.Component;

import com.sudhanshu.profileservice.dto.metrics.EngagementMetrics;
import com.sudhanshu.profileservice.dto.response.ProfileResponse;
import com.sudhanshu.profileservice.entity.UserProfiles;

import java.time.LocalDateTime;

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
    public UserProfiles fromCreateRequest(CreateProfileRequest req) {
        UserProfiles profile = new UserProfiles();
        profile.setUserId(req.getUserId());
        profile.setName(req.getName());
        profile.setEmail(req.getEmail());
        profile.setCreatedAt(LocalDateTime.now());
        profile.setLastSeen(LocalDateTime.now());
        profile.setSubscriptionTier(req.getSubscriptionTier());
        profile.setIsActive(true);
        profile.setVersion(0);
        return profile;
    }

}
