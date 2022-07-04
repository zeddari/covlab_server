package com.axilog.cov.dto.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Customer;
import com.axilog.cov.domain.Paiement;
import com.axilog.cov.dto.representation.InvoiceDetail;
import com.axilog.cov.dto.representation.InvoicePdfDetail;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InvoiceMapper {
	
	
	
	
	public InvoicePdfDetail toInvoicePdfDetail(List<Paiement> paiementList , Customer customer) {
		List<InvoiceDetail> invoiceDetails = new ArrayList<InvoiceDetail>();
		
		for (Paiement paiement : paiementList) {
			invoiceDetails.add(InvoiceDetail.builder().driverName(paiement.getDriverName())
			.id(paiement.getId())
			.motif(paiement.getMotif())
			.paiementAmount(paiement.getPaiementAmount())
			.reason(paiement.getReason())
			.supervisorName(paiement.getSupervisorName())
			.TaskId(paiement.getTaskId()).build());
		}

		
		return InvoicePdfDetail.builder().customerFirstName(customer.getFirstName())
				.header(null)
				.customerLastName(customer.getLastName())
				.customerFirstName(customer.getFirstName())
				.header("header")
				.invoiceDetails(invoiceDetails)
				.build();
	}
	

	
	
}
