package com.sudhanshu.profileservice.service.impl;

import com.sudhanshu.profileservice.component.ProfileMapper;
import com.sudhanshu.profileservice.dto.request.CreateProfileRequest;
import com.sudhanshu.profileservice.dto.response.ProfileResponse;
import com.sudhanshu.profileservice.dto.response.ProfileSummaryResponse;
import com.sudhanshu.profileservice.entity.UserProfiles;
import com.sudhanshu.profileservice.repository.ProfileRepository;
import com.sudhanshu.profileservice.repository.projection.ProfileSummaryProjection;
import com.sudhanshu.profileservice.service.UserProfileService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Transactional
public class UserProfileServiceImpl implements UserProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    public UserProfileServiceImpl(ProfileRepository profileRepository,ProfileMapper profileMapper){
        this.profileRepository=profileRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public Page<ProfileSummaryResponse> getHighEngagementProfiles(BigDecimal minScore, Pageable pageable) {
        Page<ProfileSummaryProjection> page = profileRepository.findHighEngagementProfiles(minScore,pageable);
        return page.map(
                p->ProfileSummaryResponse.builder()
                        .userId(p.getUserId())
                        .engagementScore(p.getEngagementScore())
                        .churnRiskScore(p.getChurnRiskScore())
                        .lastSeen(p.getLastSeen())
                        .subscriptionTier(p.getSubscriptionTier())
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getUserByUserId(UUID id) {
        return profileMapper.toResponse(profileRepository.findById(id).orElseThrow());
    }

    @Override
    public ProfileResponse createUserProfile(CreateProfileRequest createProfileRequest) {
        UserProfiles profiles = profileMapper.fromCreateRequest(createProfileRequest);
        return profileMapper.toResponse(profileRepository.save(profiles));
    }
}
