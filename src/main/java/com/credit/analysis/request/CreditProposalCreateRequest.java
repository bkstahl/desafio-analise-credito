package com.credit.analysis.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProposalCreateRequest {

	private String personName;

	private Long personDocument;
	
	private Double creditValue;
}
