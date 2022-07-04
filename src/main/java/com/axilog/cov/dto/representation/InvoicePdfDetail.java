package com.axilog.cov.dto.representation;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoicePdfDetail {
	
	private String customerFirstName;
	private String customerLastName;
	private String header;
	private List<InvoiceDetail> invoiceDetails ;
	


}
