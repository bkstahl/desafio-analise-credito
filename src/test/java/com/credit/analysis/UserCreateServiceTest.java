package com.credit.analysis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.model.User;
import com.credit.analysis.model.UserPermission;
import com.credit.analysis.repository.UserRepository;
import com.credit.analysis.request.UserCreateRequest;
import com.credit.analysis.service.UserCreateService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserCreateServiceTest {

	@InjectMocks
	private UserCreateService userCreateService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void shoulCreateUser(){

		User userMock = createUserMock();

		when(userRepository.save(any(User.class)))
			.thenReturn(userMock);
		
		UserCreateRequest userCreateRequest = 
				UserCreateRequest.builder()
				.login(getRandonString())
				.password(getRandonString())
				.userPermission(UserPermission.PROPOSAL_CAPTURE)
				.name(getRandonString())
				.build();
				
		userCreateService.execute(userCreateRequest);
		
		verify(userRepository).save(any(User.class));
	}
	
	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenCreateUserWithoutMandatoryFields(){
	
		UserCreateRequest userCreateRequest = 
				UserCreateRequest.builder()
				.login(null)
				.password(null)
				.build();
				
		userCreateService.execute(userCreateRequest);
		
		verify(userRepository, never()).save(any(User.class));
	}

	private User createUserMock() {
		return User.builder()
				.id(new Random().nextLong())
				.login(getRandonString())
				.name(getRandonString())
				.userPermission(UserPermission.PROPOSAL_CAPTURE)
				.status(true)
				.build();
	}

	private static String getRandonString() {
		byte[] array = new byte[10];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
}
