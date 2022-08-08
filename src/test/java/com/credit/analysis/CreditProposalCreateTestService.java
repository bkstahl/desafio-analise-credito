package com.credit.analysis;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.config.security.JwtTokenUtil;
import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.model.Person;
import com.credit.analysis.model.User;
import com.credit.analysis.model.UserPermission;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.repository.PersonRepository;
import com.credit.analysis.request.CreditProposalCreateRequest;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalCreateService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditProposalCreateTestService {

	@InjectMocks
	private CreditProposalCreateService creditProposalCreateService;

	@Mock
	private CreditProposalRepository creditProposalRepository;

	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private JwtTokenUtil tokenUtil;

	@Test
	public void shoulCreateCreditProposal(){

		User userMock = createUserMock(UserPermission.PROPOSAL_CAPTURE);
		Person personMock = createPersonMock();
 		CreditProposal creditProposalMock = createCreditProposalMock();

		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);

		when(creditProposalRepository.save(any(CreditProposal.class)))
			.thenReturn(creditProposalMock);

		when(personRepository.existsById(any(Long.class)))
			.thenReturn(false);
		
		when(personRepository.getOne(any(Long.class)))
			.thenReturn(personMock);
		
		CreditProposalCreateRequest creditProposalCreateRequest = CreditProposalCreateRequest
				.builder()
				.personDocument(new Random().nextLong())
				.personName(getRandonString())
				.creditValue(new Random().nextDouble())
				.build();

		CreditProposalResponse creditProposalResponse = creditProposalCreateService.execute(creditProposalCreateRequest);

		verify(creditProposalRepository).save(any(CreditProposal.class));

		assert(creditProposalResponse != null);
	}

	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenCreateCreditProposalWithoutPersonInformation(){

		User userMock = createUserMock(UserPermission.PROPOSAL_CAPTURE);
		
		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);
		
		CreditProposalCreateRequest creditProposalCreateRequest = CreditProposalCreateRequest
				.builder()
				.personDocument(null)
				.personName(null)
				.creditValue(null)
				.build();

		creditProposalCreateService.execute(creditProposalCreateRequest);

		verify(creditProposalRepository, never()).save(any(CreditProposal.class));
	}
	
	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenCreateCreditProposalWithUserAnalyzer(){

		User userMock = createUserMock(UserPermission.PROPOSAL_ANALYZER);

		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);
		
		CreditProposalCreateRequest creditProposalCreateRequest = CreditProposalCreateRequest
				.builder()
				.personDocument(new Random().nextLong())
				.personName(getRandonString())
				.creditValue(new Random().nextDouble())
				.build();

		creditProposalCreateService.execute(creditProposalCreateRequest);

		verify(creditProposalRepository, never()).save(any(CreditProposal.class));
	}

	private CreditProposal createCreditProposalMock() {
		return CreditProposal.builder()
				.id(new Random().nextLong())
				.person(createPersonMock())
				.captureUser(createUserMock(UserPermission.PROPOSAL_CAPTURE))
				.captureDate(LocalDate.now())
				.statusUser(null)
				.statusDate(null)
				.status(CreditProposalStatus.ON_APPROVAL)
				.creditValue(new Random().nextDouble())
				.advice(null)
				.build();
	}

	private Person createPersonMock() {
		return Person.builder()
				.document(new Random().nextLong())
				.name(getRandonString())
				.build();
	}

	private User createUserMock(UserPermission userPermission) {
		return User.builder()
				.id(new Random().nextLong())
				.login(getRandonString())
				.name(getRandonString())
				.userPermission(userPermission)
				.status(true)
				.build();
	}

	private static String getRandonString() {
		byte[] array = new byte[10];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
}
