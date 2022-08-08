package com.credit.analysis.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.credit.analysis.model.CreditProposalStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProposalFindRequest {

	private Long id;

	private Long personDocument;
	private String personName;

	private String captureUserLogin;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate captureDate;

	private String statusUserLogin;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate statusDate;
	private CreditProposalStatus status;
}
