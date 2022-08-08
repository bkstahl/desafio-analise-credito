package com.credit.analysis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;

public interface CreditProposalRepository extends JpaRepository<CreditProposal, Long> {

	List<CreditProposal> findByStatus(CreditProposalStatus status);
}
