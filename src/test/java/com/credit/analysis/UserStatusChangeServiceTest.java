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
import com.credit.analysis.service.UserStatusChangeService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserStatusChangeServiceTest {
	
	@InjectMocks
	private UserStatusChangeService userStatusChangeService;

	@Mock
	private UserRepository userRepository;

	@Test
	public void shoulCreateUser(){

		User userMock = createUserMock();

		when(userRepository.getOne(any(Long.class)))
			.thenReturn(userMock);
		
		when(userRepository.save(any(User.class)))
			.thenReturn(userMock);
		
		userStatusChangeService.execute(new Random().nextLong(), new Random().nextBoolean());
		
		verify(userRepository).save(any(User.class));
	}
	
	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenCreateUserWithoutMandatoryFields(){
	
		userStatusChangeService.execute(null, null);
		
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
