package com.credit.analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.credit.analysis.model.CreditProposalStatus;
import com.credit.analysis.request.CreditProposalStatusChangeRequest;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalStatusChangeService;
import com.credit.analysis.service.CreditProposalPendigFindService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/creditProposalAnalysis")
@Api(tags = "Credit Proposal Analysis" )
public class CreditProposalAnalysisController {

	@Autowired
	private CreditProposalStatusChangeService creditProposalStatusChangeService;
	
	@Autowired
	private CreditProposalPendigFindService creditProposalPendigFindService;
	
	@PutMapping(value = "approval")
	@ApiOperation(value = "Credit Proposal Approval")
	public ResponseEntity<CreditProposalResponse> approval(
			@RequestBody CreditProposalStatusChangeRequest creditProposalStatusChangeRequest) {
		
		creditProposalStatusChangeRequest.setCreditProposalStatus(CreditProposalStatus.APPROVED);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(creditProposalStatusChangeService
						.execute(creditProposalStatusChangeRequest));
	}
	
	@PutMapping(value = "disapproval")
	@ApiOperation(value = "Credit Proposal Disapproval")
	public ResponseEntity<CreditProposalResponse> disapproval(
			@RequestBody CreditProposalStatusChangeRequest creditProposalStatusChangeRequest) {
		
		creditProposalStatusChangeRequest.setCreditProposalStatus(CreditProposalStatus.DISAPPROVED);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(creditProposalStatusChangeService
						.execute(creditProposalStatusChangeRequest));
	}
	
	@GetMapping(value="/pending")
	@ApiOperation(value = "Credit Proposals Without Analysis Report")
	public ResponseEntity<List<CreditProposalResponse>> pending() {
		
		return ResponseEntity.ok(creditProposalPendigFindService.execute());
	}
}
