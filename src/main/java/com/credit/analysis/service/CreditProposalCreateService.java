package com.credit.analysis.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditProposalCreateService {

	@Autowired
	private CreditProposalRepository creditProposalRepository;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public CreditProposalResponse execute(CreditProposalCreateRequest creditProposalCreateRequest) {

		User captureUser = jwtTokenUtil.getLoggedUser();
		
		validateConditionsForCreateCreditProposal(captureUser);
		validateMandatoryFields(creditProposalCreateRequest);
		
		Person person = createPerson(creditProposalCreateRequest);
		CreditProposal creditProposal = createCreditProposal(captureUser, creditProposalCreateRequest);
		creditProposal.setPerson(person);

		creditProposal = creditProposalRepository.save(creditProposal);

		log.info("Credit Proposal number "+creditProposal.getId()+" was created successfully.");

		return CreditProposalResponse.mapper(creditProposal);
	}

	private Person createPerson(CreditProposalCreateRequest creditProposalCreateRequest) {
		if(personRepository.existsById(creditProposalCreateRequest.getPersonDocument()))
			return personRepository.getOne(creditProposalCreateRequest.getPersonDocument());

		return personRepository.save(Person.builder()
				.name(creditProposalCreateRequest.getPersonName())
				.document(creditProposalCreateRequest.getPersonDocument())
				.build());
	}

	private CreditProposal createCreditProposal(User captureUser,
			CreditProposalCreateRequest creditProposalCreateRequest) {
		
		return CreditProposal.builder()
				.captureUser(captureUser)
				.captureDate(LocalDate.now())
				.statusUser(null)
				.statusDate(LocalDate.now())
				.status(CreditProposalStatus.ON_APPROVAL)
				.creditValue(creditProposalCreateRequest.getCreditValue())
				.advice(null)
				.build();
	}

	private void validateMandatoryFields(CreditProposalCreateRequest creditProposalCreateRequest) {

		if(creditProposalCreateRequest.getPersonName() == null)
			throw new ApiException("Person name is mandatory");

		if(creditProposalCreateRequest.getPersonDocument() == null)
			throw new ApiException("Person document is mandatory");

		if(creditProposalCreateRequest.getCreditValue() == null)
			throw new ApiException("Credit value is mandatory");
	}
	
	private void validateConditionsForCreateCreditProposal(User user) {
		
		if(!UserPermission.PROPOSAL_CAPTURE.equals(user.getUserPermission()))
			throw new ApiException("Only capture users can create credit proposal.");
	}
}
