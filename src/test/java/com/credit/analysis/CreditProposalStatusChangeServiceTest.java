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
import com.credit.analysis.request.CreditProposalStatusChangeRequest;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalStatusChangeService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditProposalStatusChangeServiceTest {

	@InjectMocks
	private CreditProposalStatusChangeService creditProposalStatusChangeService;

	@Mock
	private CreditProposalRepository creditProposalRepository;

	@Mock
	private JwtTokenUtil tokenUtil;

	@Test
	public void shoulApproveCreditProposal(){

		CreditProposalStatusChangeRequest creditProposalStatusChangeRequest =
				CreditProposalStatusChangeRequest.builder()
				.creditProposalId(new Random().nextLong())
				.creditProposalStatus(CreditProposalStatus.APPROVED)
				.advice(getRandonString())
				.build();

		User userMock = createUserMock(UserPermission.PROPOSAL_ANALYZER);
		CreditProposal creditProposalMock = createCreditProposalMock();

		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);

		when(creditProposalRepository.getOne(any(Long.class)))
			.thenReturn(creditProposalMock);

		when(creditProposalRepository.save(any(CreditProposal.class)))
			.thenReturn(creditProposalMock);

		CreditProposalResponse response = creditProposalStatusChangeService
				.execute(creditProposalStatusChangeRequest);

		verify(creditProposalRepository).save(any(CreditProposal.class));

		assert(response.getStatusDate().equals(LocalDate.now()));
		assert(response.getStatusUserName().equals(userMock.getName()));
		assert(response.getStatus().equals(CreditProposalStatus.APPROVED));
	}

	@Test
	public void shoulDisapprovedCreditProposal(){
		
		CreditProposalStatusChangeRequest creditProposalStatusChangeRequest =
				CreditProposalStatusChangeRequest.builder()
				.creditProposalId(new Random().nextLong())
				.creditProposalStatus(CreditProposalStatus.DISAPPROVED)
				.advice(getRandonString())
				.build();

		User userMock = createUserMock(UserPermission.PROPOSAL_ANALYZER);
		CreditProposal creditProposalMock = createCreditProposalMock();

		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);

		when(creditProposalRepository.getOne(any(Long.class)))
			.thenReturn(creditProposalMock);

		when(creditProposalRepository.save(any(CreditProposal.class)))
			.thenReturn(creditProposalMock);

		CreditProposalResponse response = creditProposalStatusChangeService
				.execute(creditProposalStatusChangeRequest);

		verify(creditProposalRepository).save(any(CreditProposal.class));

		assert(response.getStatusDate().equals(LocalDate.now()));
		assert(response.getStatusUserName().equals(userMock.getName()));
		assert(response.getStatus().equals(CreditProposalStatus.DISAPPROVED));
	}

	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenChangeStatusWithCaptureUser(){

		CreditProposalStatusChangeRequest creditProposalStatusChangeRequest =
				CreditProposalStatusChangeRequest.builder()
				.creditProposalId(new Random().nextLong())
				.creditProposalStatus(CreditProposalStatus.APPROVED)
				.advice(getRandonString())
				.build();

		User userMock = createUserMock(UserPermission.PROPOSAL_CAPTURE);
		CreditProposal creditProposalMock = createCreditProposalMock();

		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);

		when(creditProposalRepository.getOne(any(Long.class)))
			.thenReturn(creditProposalMock);

		creditProposalStatusChangeService
				.execute(creditProposalStatusChangeRequest);

		verify(creditProposalRepository, never()).save(any(CreditProposal.class));
	}
	
	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenCreditProposalStatusIsNotOnApproval(){

		CreditProposalStatusChangeRequest creditProposalStatusChangeRequest =
				CreditProposalStatusChangeRequest.builder()
				.creditProposalId(new Random().nextLong())
				.creditProposalStatus(CreditProposalStatus.APPROVED)
				.advice(getRandonString())
				.build();

		User userMock = createUserMock(UserPermission.PROPOSAL_ANALYZER);
		CreditProposal creditProposalMock = createCreditProposalMock();
		creditProposalMock.setStatus(CreditProposalStatus.CANCELED);
		
		when(tokenUtil.getLoggedUser())
			.thenReturn(userMock);

		when(creditProposalRepository.getOne(any(Long.class)))
			.thenReturn(creditProposalMock);

		creditProposalStatusChangeService
				.execute(creditProposalStatusChangeRequest);

		verify(creditProposalRepository, never()).save(any(CreditProposal.class));
	}
	
	@Test(expected = ApiException.class)
	public void shouldOccurErrorWhenChangeStatusWithoutMandatoryFields(){

		CreditProposalStatusChangeRequest creditProposalStatusChangeRequest =
				CreditProposalStatusChangeRequest.builder()
				.creditProposalId(null)
				.creditProposalStatus(null)
				.advice(null)
				.build();
		
		creditProposalStatusChangeService.execute(creditProposalStatusChangeRequest);

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
