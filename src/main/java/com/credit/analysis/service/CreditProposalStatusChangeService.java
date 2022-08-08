package com.credit.analysis.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.config.security.JwtTokenUtil;
import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.model.User;
import com.credit.analysis.model.UserPermission;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.request.CreditProposalStatusChangeRequest;
import com.credit.analysis.response.CreditProposalResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditProposalStatusChangeService {

	@Autowired
	private CreditProposalRepository creditProposalRepository;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public CreditProposalResponse execute(CreditProposalStatusChangeRequest creditProposalStatusChangeRequest) {

		validateMandatoryFields(creditProposalStatusChangeRequest);

		User user = jwtTokenUtil.getLoggedUser();
		
		CreditProposal creditProposal = 
				creditProposalRepository.getOne(creditProposalStatusChangeRequest.getCreditProposalId());
		
		validateConditionsForStatusChange(user, creditProposal, creditProposalStatusChangeRequest);
		
		creditProposal.setAdvice(creditProposalStatusChangeRequest.getAdvice());
		creditProposal.setStatus(creditProposalStatusChangeRequest.getCreditProposalStatus());
		creditProposal.setStatusDate(LocalDate.now());
		creditProposal.setStatusUser(user);
		
		creditProposal = creditProposalRepository.save(creditProposal);

		log.info("Credit Proposal number "+creditProposal.getId()+" was disapproved successfully.");
		
		return CreditProposalResponse.mapper(creditProposal);
	}

	private void validateMandatoryFields(CreditProposalStatusChangeRequest CreditProposalStatusChangeRequest) {
		if(CreditProposalStatusChangeRequest.getCreditProposalId() == null)
			throw new ApiException("Credit Proposal number is mandatory");

		if(CreditProposalStatusChangeRequest.getCreditProposalStatus() == null)
			throw new ApiException("Credit Proposal Status is mandatory");
	}
	
	private void validateConditionsForStatusChange(
			User user, CreditProposal creditProposal,
			CreditProposalStatusChangeRequest creditProposalStatusChangeRequest) {
		
		if(UserPermission.PROPOSAL_CAPTURE.equals(user.getUserPermission()))
			throw new ApiException("Only analyst users can change credit proposal status.");

		if(!CreditProposalStatus.ON_APPROVAL.equals(creditProposal.getStatus()))
			throw new ApiException("Only credit proposals 'On Approval' can be changed.");
		
		if(!CreditProposalStatus.APPROVED.equals(creditProposalStatusChangeRequest.getCreditProposalStatus())
				&& !CreditProposalStatus.DISAPPROVED.equals(creditProposalStatusChangeRequest.getCreditProposalStatus()))
			throw new ApiException("It is only possible to change the status to approved and disapproved.");
	}
}
