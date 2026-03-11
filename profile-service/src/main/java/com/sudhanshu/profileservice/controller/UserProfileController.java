package com.sudhanshu.profileservice.controller;

import com.sudhanshu.profileservice.component.AppConfiguration;
import com.sudhanshu.profileservice.component.ProfileMapper;
import com.sudhanshu.profileservice.dto.request.CreateProfileRequest;
import com.sudhanshu.profileservice.dto.response.ProfilePageResponse;
import com.sudhanshu.profileservice.dto.response.ProfileResponse;
import com.sudhanshu.profileservice.service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final ProfileMapper profileMapper;
    private final AppConfiguration appConfiguration;

    public UserProfileController(UserProfileService userProfileService, ProfileMapper profileMapper, AppConfiguration appConfiguration){
        this.userProfileService = userProfileService;
        this.profileMapper = profileMapper;
        this.appConfiguration = appConfiguration;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(userProfileService.getUserByUserId(id) );
    }
    @GetMapping("/high-engagement")
    public ResponseEntity<ProfilePageResponse> getHighEngagementProfiles(
            @RequestParam(defaultValue = "0.50") BigDecimal minScore,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        System.out.println(page+"  "+ size);
        return ResponseEntity.ok(ProfilePageResponse.from(userProfileService.getHighEngagementProfiles(minScore,pageable)));
    }

    @PostMapping
    ResponseEntity<ProfileResponse> createUserProfile(
            @Valid @RequestBody CreateProfileRequest createProfileRequest
    ){
        ProfileResponse profileResponse=  userProfileService.createUserProfile(createProfileRequest);
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/config")
    public String getConfig() {
        return "Message: " + appConfiguration.getMessage() +
                ", Feature Enabled: " + appConfiguration.isFeatureEnabled();
    }
}
