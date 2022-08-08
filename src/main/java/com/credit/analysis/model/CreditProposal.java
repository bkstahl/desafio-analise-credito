package com.credit.analysis.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditProposal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="personDocument", nullable=false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="captureUserId", nullable=false)
	private User captureUser;
	private LocalDate captureDate;
	
	@ManyToOne
	@JoinColumn(name="statusUserId", nullable=true)
	private User statusUser;
	private LocalDate statusDate;
	
	@Enumerated(EnumType.ORDINAL)
	private CreditProposalStatus status;
	
	private Double creditValue;
	
	private String advice;
}