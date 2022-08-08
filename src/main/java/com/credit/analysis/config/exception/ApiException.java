package com.credit.analysis.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiException extends RuntimeException{

	private static final long serialVersionUID = -7379576201522872456L;
	private String message;
}