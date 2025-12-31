package com.sudhanshu.profileservice.service;

import com.sudhanshu.profileservice.dto.request.CreateProfileRequest;
import com.sudhanshu.profileservice.dto.response.ProfileResponse;
import com.sudhanshu.profileservice.dto.response.ProfileSummaryResponse;
import com.sudhanshu.profileservice.entity.UserProfiles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserProfileService {
    Page<ProfileSummaryResponse> getHighEngagementProfiles(BigDecimal minScore, Pageable pageable);
    ProfileResponse getUserByUserId(UUID userId);
    ProfileResponse createUserProfile(CreateProfileRequest createProfileRequest);
}
