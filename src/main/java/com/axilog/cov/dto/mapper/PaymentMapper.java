package com.axilog.cov.dto.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.axilog.cov.domain.Payment;
import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.representation.InvoiceDetail;
import com.axilog.cov.dto.representation.InvoicePdfDetail;
import com.axilog.cov.dto.representation.InvoiceRequest;

@Component
public class PaymentMapper {
	
	

	public Payment toPayment(InvoiceRequest invoiceRequest, RequestQuotation requestQuotation, byte[] invoice) {
		Payment payment = new Payment();
		payment.setCreatedDate(new Date());
		payment.setDriverName(invoiceRequest.getDriverName());
		payment.setInvoiceId("Invoice" + new Timestamp(System.currentTimeMillis()));
		payment.setMotif(invoiceRequest.getMotif());
		payment.setPaymentAmount(invoiceRequest.getPaymentAmount());
		payment.setReason(invoiceRequest.getReason());
		payment.setRequestQuotation(requestQuotation);
		payment.setSupervisorName(invoiceRequest.getSupervisorName());
		payment.setTaskId(invoiceRequest.getTaskId());
		payment.setInvoiceFile(invoice);
		return payment;
	}
	
	
	
	public InvoicePdfDetail toInvoicePdfDetail(InvoiceRequest invoiceRequest, RequestQuotation requestQuotation) {
		
			
		String header =String.valueOf(invoiceRequest.getPaymentAmount()) ;
		
		return InvoicePdfDetail.builder().customerName(requestQuotation.getCustomerName()).customerEmail(requestQuotation.getCustomerEmail())
				.mobileNumber(requestQuotation.getMobileNumber())
				.serviceType(requestQuotation.getServiceType())
				.product(requestQuotation.getProduct())
				.header(header)
				.invoiceDetail(InvoiceDetail.builder().driverName(invoiceRequest.getDriverName())
						.invoiceId(invoiceRequest.getInvoiceId())
						.motif(invoiceRequest.getMotif())
						.paymentAmount(invoiceRequest.getPaymentAmount())
						.reason(invoiceRequest.getReason())
						.supervisorName(invoiceRequest.getSupervisorName())
						.paymentDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()))
						.taskId(invoiceRequest.getTaskId()).build())
						
				.build();
	}
	

	
	
}
