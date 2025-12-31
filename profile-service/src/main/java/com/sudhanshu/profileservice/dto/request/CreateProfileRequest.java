package com.sudhanshu.profileservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProfileRequest {

	@NotBlank
	@Size(max = 50)
	private String userId;

	@Email
	@Size(max = 100)
	private String email;

	@Size(max = 100)
	private String name;

	@NotBlank
	private String subscriptionTier;
}
