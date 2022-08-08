package com.credit.analysis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.credit.analysis.request.CreditProposalCreateRequest;
import com.credit.analysis.request.CreditProposalFindRequest;
import com.credit.analysis.response.CreditProposalResponse;
import com.credit.analysis.service.CreditProposalCancelService;
import com.credit.analysis.service.CreditProposalCreateService;
import com.credit.analysis.service.CreditProposalFindService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/v1/creditProposal")
@Api(tags = "Credit Proposal Capture")
public class CreditProposalCaptureController {

	@Autowired
	private CreditProposalCreateService creditProposalCreateService;
	
	@Autowired
	private CreditProposalCancelService creditProposalCancelService;
	
	@Autowired
	private CreditProposalFindService creditProposalFindService;
	
	@PostMapping(value = "create")
	@ApiOperation(value = "Create a Credit Proposal")
	public ResponseEntity<CreditProposalResponse> create(
			@RequestBody CreditProposalCreateRequest creditProposalCreateRequest) {
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(creditProposalCreateService.execute(creditProposalCreateRequest));
	}
	
	@PutMapping(value = "cancel")
	@ApiOperation(value = "Cancel a Credit Proposal")
	public ResponseEntity<CreditProposalResponse> cancel(
			@RequestParam Long idCreditProposal) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(creditProposalCancelService.execute(idCreditProposal));
	}
	
	@GetMapping(value = "find")
	@ApiOperation(value = "Credit Analysis Search")
	public ResponseEntity<List<CreditProposalResponse>> find(
			CreditProposalFindRequest creditProposalFindRequest) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(creditProposalFindService.execute(creditProposalFindRequest));
	}
}
