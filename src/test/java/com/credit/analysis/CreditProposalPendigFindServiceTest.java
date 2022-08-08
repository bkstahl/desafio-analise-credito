package com.credit.analysis;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.model.Person;
import com.credit.analysis.model.User;
import com.credit.analysis.model.UserPermission;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalPendigFindService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditProposalPendigFindServiceTest {

	@InjectMocks
	private CreditProposalPendigFindService creditProposalPendigFindService;

	@Mock
	private CreditProposalRepository creditProposalRepository;

	@Test
	public void shoulFindPendingCreditProposal(){

		List<CreditProposal> creditProposalsMock = createCreditProposalsMock();
		
		when(creditProposalRepository.findByStatus(CreditProposalStatus.ON_APPROVAL))
			.thenReturn(creditProposalsMock);

		List<CreditProposalResponse> creditProposalsResponse = creditProposalPendigFindService.execute();
		
		verify(creditProposalRepository).findByStatus(CreditProposalStatus.ON_APPROVAL);
		
		assert(creditProposalsResponse.size() == creditProposalsMock.size());
	}

	@Test(expected = NullPointerException.class)
	public void shouldOccurErrorWhenExistsCreditProposalWithoutUserCapture(){

		List<CreditProposal> creditProposalsMock = createCreditProposalsPendingMock();
		
		when(creditProposalRepository.findByStatus(CreditProposalStatus.ON_APPROVAL))
			.thenReturn(creditProposalsMock);

		List<CreditProposalResponse> creditProposalsResponse = creditProposalPendigFindService.execute();
		
		verify(creditProposalRepository).findByStatus(CreditProposalStatus.ON_APPROVAL);
		
		assert(creditProposalsResponse.size() == 0);
	}
	
	private List<CreditProposal> createCreditProposalsMock(){
		List<CreditProposal> creditProposals = new ArrayList<CreditProposal>();
		creditProposals.add(createCreditProposalMock());
		creditProposals.add(createCreditProposalMock());
		return creditProposals;
	}
	
	private CreditProposal createCreditProposalMock() {
		return CreditProposal.builder()
				.id(new Random().nextLong())
				.person(createPersonMock())
				.captureUser(createCaptureUserMock())
				.captureDate(LocalDate.now())
				.statusUser(null)
				.statusDate(null)
				.status(CreditProposalStatus.ON_APPROVAL)
				.creditValue(new Random().nextDouble())
				.advice(null)
				.build();
	}
	
	private List<CreditProposal> createCreditProposalsPendingMock(){
		List<CreditProposal> creditProposals = new ArrayList<CreditProposal>();
		creditProposals.add(createCreditProposPendingMock());
		creditProposals.add(createCreditProposPendingMock());
		return creditProposals;
	}
	
	private CreditProposal createCreditProposPendingMock() {
		CreditProposal creditProposal = createCreditProposalMock();
		creditProposal.setCaptureUser(null);
		return creditProposal;
	}
	
	private User createCaptureUserMock() {
		return User.builder()
				.id(new Random().nextLong())
				.name(getRandonString())
				.userPermission(UserPermission.PROPOSAL_CAPTURE)
				.build();
	}
	
	private Person createPersonMock() {
		return Person.builder()
				.document(new Random().nextLong())
				.name(getRandonString())
				.build();
	}
	
	private static String getRandonString() {
		byte[] array = new byte[10];
		new Random().nextBytes(array);
		return new String(array, Charset.forName("UTF-8"));
	}
}
