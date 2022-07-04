package com.axilog.cov.dto.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDetail {
	private Long id;
	private Double paiementAmount;
	private String paiementDate;

	private String supervisorName;
	private String driverName;
	private String TaskId;
	private String motif;
	private String reason;
}