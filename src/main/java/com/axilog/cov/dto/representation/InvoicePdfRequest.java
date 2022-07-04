package com.axilog.cov.dto.representation;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePdfRequest {

	private Long customerId;
	private Date starteDate;
	private String endDate;
}
