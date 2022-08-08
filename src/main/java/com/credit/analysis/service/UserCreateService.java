package com.credit.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.model.User;
import com.credit.analysis.repository.UserRepository;
import com.credit.analysis.request.UserCreateRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserCreateService {

	@Autowired
	private UserRepository userRepository;

	public User execute(UserCreateRequest userCreateRequest) {

		User user = createUser(userCreateRequest);
		
		validateMandatoryFields(user);
		passwordEncryption(user);

		user = userRepository.save(user);
		log.info("User "+user.getName()+" was created successfully.");
		
		return user;
	}

	private User createUser(UserCreateRequest userCreateRequest) {
		return User.builder()
				.login(userCreateRequest.getLogin())
				.password(userCreateRequest.getPassword())
				.name(userCreateRequest.getName())
				.userPermission(userCreateRequest.getUserPermission())
				.status(true)
				.build();
	}

	private void passwordEncryption(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	}

	private void validateMandatoryFields(User user) {

		if(user.getName() == null)
			throw new ApiException("Name is mandatory");

		if(user.getLogin() == null)
			throw new ApiException("Login is mandatory");
		
		if(user.getPassword() == null)
			throw new ApiException("Password is mandatory");
		
		if(user.getUserPermission() == null)
			throw new ApiException("Permission is mandatory");
	}
}
