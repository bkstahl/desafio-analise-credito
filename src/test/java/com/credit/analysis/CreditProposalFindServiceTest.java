package com.credit.analysis;

import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.data.domain.Example;

import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.model.Person;
import com.credit.analysis.model.User;
import com.credit.analysis.model.UserPermission;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.request.CreditProposalFindRequest;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalFindService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CreditProposalFindServiceTest {

	@InjectMocks
	private CreditProposalFindService creditProposalFindService;

	@Mock
	private CreditProposalRepository creditProposalRepository;

	@Test
	public void shoulFindCreditProposals(){

		List<CreditProposal> creditProposalsMock = createCreditProposalsMock();

		when(creditProposalRepository.findAll(any(Example.class)))
			.thenReturn(creditProposalsMock);

		CreditProposalFindRequest creditProposalFindRequest = 
				CreditProposalFindRequest.builder().build();

		List<CreditProposalResponse> response = creditProposalFindService.execute(creditProposalFindRequest);
		
		verify(creditProposalRepository).findAll(any(Example.class));
		
		assert(creditProposalsMock.size() == response.size());
	}

	private List<CreditProposal> createCreditProposalsMock(){
		List<CreditProposal> creditProposals = new ArrayList<CreditProposal>();
		for (int i = 0; i < new Random().nextInt(); i++)
			creditProposals.add(createCreditProposalMock());
		return creditProposals;
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
