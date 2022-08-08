package com.credit.analysis.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.analysis.config.exception.ApiException;
import com.credit.analysis.config.security.JwtTokenUtil;
import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.response.CreditProposalResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreditProposalCancelService {

	@Autowired
	private CreditProposalRepository creditProposalRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public CreditProposalResponse execute(Long idCreditProposal) {
		
		validateMandatoryFields(idCreditProposal);

		CreditProposal creditProposal = creditProposalRepository.getOne(idCreditProposal);
		creditProposal.setStatus(CreditProposalStatus.CANCELED);
		creditProposal.setStatusDate(LocalDate.now());
		creditProposal.setStatusUser(jwtTokenUtil.getLoggedUser());
		creditProposalRepository.save(creditProposal);
		
		log.info("Credit Proposal number "+creditProposal.getId()+" was canceled successfully.");
		
		return CreditProposalResponse.mapper(creditProposal);
	}
	
	private void validateMandatoryFields(Long idCreditProposal) {
		if(idCreditProposal == null)
			throw new ApiException("Credit Proposal number is mandatory");
	}
}
