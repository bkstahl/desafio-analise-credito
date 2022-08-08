package com.credit.analysis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.credit.analysis.model.User;
import com.credit.analysis.request.UserCreateRequest;
import com.credit.analysis.service.UserCreateService;
import com.credit.analysis.service.UserStatusChangeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/user")
@Api(tags = "Credit Proposal Users")
public class UserController {

	@Autowired
	private UserCreateService userCreateService;

	@Autowired
	private UserStatusChangeService userStatusChangeService;

	@PostMapping(value = "create")
	@ApiOperation(value = "Create a user")
	public ResponseEntity<User> create(
			@RequestBody UserCreateRequest userCreateRequest) {

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(userCreateService.execute(userCreateRequest));
	}

	@PutMapping(value = "enable")
	@ApiOperation(value = "Change user status to allow system access")
	public ResponseEntity<?> enable(
			@RequestParam Long idUser) {

		userStatusChangeService.execute(idUser, true);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping(value = "disable")
	@ApiOperation(value = "Change user status to deny system access")
	public ResponseEntity<?> disable(
			@RequestParam Long idUser) {

		userStatusChangeService.execute(idUser, false);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
