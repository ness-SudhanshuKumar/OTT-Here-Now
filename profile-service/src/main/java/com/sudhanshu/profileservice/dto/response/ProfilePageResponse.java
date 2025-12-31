package com.sudhanshu.profileservice.dto.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record ProfilePageResponse(
	    List<ProfileSummaryResponse> content,
	    int page,
	    int size,
	    long totalElements,
	    int totalPages
	) {
		public static  ProfilePageResponse from(Page<ProfileSummaryResponse> page){
			return new ProfilePageResponse(
					page.getContent(),
					page.getNumber(),
					page.getSize(),
					page.getTotalElements(),
					page.getTotalPages()
			);
		}

}
