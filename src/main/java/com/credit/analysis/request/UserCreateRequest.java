package com.credit.analysis.request;

import com.credit.analysis.model.UserPermission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateRequest {

	private String login;

	private String password;

	private String name;
	
	private UserPermission userPermission;
}
