package com.credit.analysis.response;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.credit.analysis.model.CreditProposal;
import com.credit.analysis.model.CreditProposalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProposalResponse {

	private Long id;
	
	private Long personDocument;
	private String personName;
	
	private String captureUserName;
	private LocalDate captureDate;
	
	private String statusUserName;
	private LocalDate statusDate;
	private CreditProposalStatus status;
	
	private Double creditValue;
	
	private String advice;
	
	public static CreditProposalResponse mapper(CreditProposal creditProposal) {
		return CreditProposalResponse.builder()
				.id(creditProposal.getId())
				.personDocument(creditProposal.getPerson().getDocument())
				.personName(creditProposal.getPerson().getName())
				.captureUserName(creditProposal.getCaptureUser().getName())
				.captureDate(creditProposal.getCaptureDate())
				.statusUserName(creditProposal.getStatusUser() == null ? "" : creditProposal.getStatusUser().getName())
				.statusDate(creditProposal.getStatusDate())
				.status(creditProposal.getStatus())
				.creditValue(creditProposal.getCreditValue())
				.advice(creditProposal.getAdvice())
				.build();
	}
	
	public static List<CreditProposalResponse> mapperList(List<CreditProposal> creditProposals) {
		return creditProposals
				  .stream()
				  .map(creditProposal -> mapper(creditProposal))
				  .collect(Collectors.toList());
	}
}
