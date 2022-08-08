package com.credit.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.Person;
import com.credit.analysis.model.User;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.request.CreditProposalFindRequest;
import com.credit.analysis.response.CreditProposalResponse;

@Service
public class CreditProposalFindService {

	@Autowired
	private CreditProposalRepository creditProposalRepository;

	public List<CreditProposalResponse> execute(CreditProposalFindRequest creditProposalFindRequest) {

		ExampleMatcher matcher = ExampleMatcher
				.matching();

		Example<CreditProposal> example = Example.of(
				CreditProposal.builder()
				.id(creditProposalFindRequest.getId())
				.person(Person
						.builder()
						.document(creditProposalFindRequest.getPersonDocument())
						.name(creditProposalFindRequest.getPersonName())
						.build())
				.captureUser(User
						.builder()
						.login(creditProposalFindRequest.getCaptureUserLogin())
						.build())
				.captureDate(creditProposalFindRequest.getCaptureDate())
				.statusUser(creditProposalFindRequest
						.getStatusUserLogin() == null ? null :
							User.builder()
							.login(creditProposalFindRequest.getStatusUserLogin())
							.build())
				.statusDate(creditProposalFindRequest.getStatusDate())
				.status(creditProposalFindRequest.getStatus())
				.build(), matcher);

		return CreditProposalResponse.mapperList(creditProposalRepository.findAll(example));
	}
}