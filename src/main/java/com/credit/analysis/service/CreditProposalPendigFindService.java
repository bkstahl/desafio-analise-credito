package com.credit.analysis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.repository.CreditProposalRepository;
import com.credit.analysis.response.CreditProposalResponse;

@Service
public class CreditProposalPendigFindService {

	@Autowired
	private CreditProposalRepository creditProposalRepository;

	public List<CreditProposalResponse> execute() {
		return CreditProposalResponse
				.mapperList(creditProposalRepository
						.findByStatus(CreditProposalStatus.ON_APPROVAL));
	}
}
