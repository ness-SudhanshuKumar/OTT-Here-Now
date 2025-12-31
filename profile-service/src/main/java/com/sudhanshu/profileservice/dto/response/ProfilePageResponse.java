package com.sudhanshu.profileservice.dto.response;

import java.util.List;

public record ProfilePageResponse(
	    List<ProfileSummaryResponse> content,
	    int page,
	    int size,
	    long totalElements,
	    int totalPages
	) {}
