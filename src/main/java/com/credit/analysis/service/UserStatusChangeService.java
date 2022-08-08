package com.credit.analysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.model.User;
import com.credit.analysis.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserStatusChangeService {

	@Autowired
	private UserRepository userRepository;
	
	public void execute(Long userId, Boolean status) {

		validateMandatoryFields(userId, status);
		
		User user = userRepository.getOne(userId);
		user.setStatus(status);
		userRepository.save(user);
		
		log.info("User status "+user.getName()+" was  change to "+status+" successfully.");
	}
	
	private void validateMandatoryFields(Long userId, Boolean status) {

		if(userId == null)
			throw new ApiException("User id is mandatory");

		if(status == null)
			throw new ApiException("Stauts is mandatory");
	}
}
