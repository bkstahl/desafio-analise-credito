package com.credit.analysis.request;

import com.credit.analysis.model.CreditProposalStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProposalStatusChangeRequest {

	private Long creditProposalId;
	
	private String advice;
	
	@ApiModelProperty(hidden=true)
	private CreditProposalStatus creditProposalStatus;
}
