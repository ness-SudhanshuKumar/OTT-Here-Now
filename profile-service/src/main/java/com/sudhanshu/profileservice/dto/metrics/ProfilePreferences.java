package com.sudhanshu.profileservice.dto.metrics;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ProfilePreferences {
    private List<String> genres;
    private List<String> devices;
    private List<String> quality;
}
