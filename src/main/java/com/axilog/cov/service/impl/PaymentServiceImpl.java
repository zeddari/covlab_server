package com.axilog.cov.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Payment;
import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.mapper.PaymentMapper;
import com.axilog.cov.dto.representation.InvoicePdfDetail;
import com.axilog.cov.dto.representation.InvoiceRequest;
import com.axilog.cov.dto.representation.PaymentRepresentation;
import com.axilog.cov.repository.PaymentRepository;
import com.axilog.cov.repository.RequestQuotationRepository;
import com.axilog.cov.service.OtpMailService;
import com.axilog.cov.service.PaymentService;
import com.axilog.cov.service.pdf.PdfServiceInvoice;
import com.axilog.cov.util.DateUtil;
import com.lowagie.text.DocumentException;

/**
 * Service Implementation for managing {@link RequestQuotation}.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	private final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	PaymentRepository paymentRepository;
	@Autowired
	RequestQuotationRepository requestQuotationRepository;

	@Autowired
	PaymentMapper paymentMapper;
	@Autowired
	PdfServiceInvoice pdfServiceInvoice;


	@Autowired
	OtpMailService otpMailService;

	@Override
	public List<PaymentRepresentation> findAll(String user) {
		log.info("get all payment info for user");
		List<Payment> paymentList = paymentRepository.findByDriverNameOrSupervisorName(user, user);
		List<PaymentRepresentation> payments = new ArrayList<PaymentRepresentation>();
		for (Payment payment : paymentList) {
			PaymentRepresentation PaymentRepresentation = new PaymentRepresentation();
			if (payment.getRequestQuotation() != null) {
				PaymentRepresentation.setCustomerEmail(payment.getRequestQuotation().getCustomerEmail());
				PaymentRepresentation.setCustomerMobileNumber(payment.getRequestQuotation().getMobileNumber());

				PaymentRepresentation.setCustomerName(payment.getRequestQuotation().getCustomerName());
				PaymentRepresentation.setServiceType(payment.getRequestQuotation().getServiceType());
			}
			PaymentRepresentation.setPaymentAmount(payment.getPaymentAmount());
			PaymentRepresentation.setDriverName(payment.getDriverName());
			PaymentRepresentation.setSupervisorName(payment.getSupervisorName());
			PaymentRepresentation.setInvoiceId(payment.getInvoiceId());
			PaymentRepresentation.setPaymentId(payment.getId());
			payments.add(PaymentRepresentation);
		}

		return payments;
	}

	@Override
	public void addPayment(InvoiceRequest invoiceRequest) throws IOException, DocumentException {

		RequestQuotation requestQuotation = requestQuotationRepository
				.findByRequestQuotationId(invoiceRequest.getRequestQuotationId());

		InvoicePdfDetail invoicePdfDetail = paymentMapper.toInvoicePdfDetail(invoiceRequest, requestQuotation);
		Object[] pdfService = pdfServiceInvoice.generatePdf(invoicePdfDetail);
		File poPdf = (File)pdfService[1] ;
		byte[] invoice = FileUtils.readFileToByteArray(poPdf);

		Payment payment = paymentMapper.toPayment(invoiceRequest, requestQuotation, invoice);

		paymentRepository.save(payment);
		
//		try {
//			otpMailService.sendHtmlMail(requestQuotation.getCustomerEmail(), "Quotation Payment : " + DateUtil.dateTimeNow(DateUtil.MOI_DATE_ENCODING)
//			, (String)pdfService[0]);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		otpMailService.sendEmailWithAttachment(requestQuotation.getCustomerEmail(), "Quotation Payment : " + DateUtil.dateTimeNow(DateUtil.MOI_DATE_ENCODING)
		, (String)pdfService[0], true, true, poPdf);
		}

	@Override
	public byte[] getPdfByInvoicePdf(String invoiceId) {
		Payment payment = paymentRepository.findByInvoiceId(invoiceId);
		if (payment !=null) {
			return payment.getInvoiceFile();
		}
		return null;
	}

	@Override
	public String getMyPaymentAmountTotal(String user) {
		double paymentsAmount = 0;
		List<Payment> paymentList = paymentRepository.findByDriverNameOrSupervisorName(user, user);
		if (paymentList !=null) {
			for (Payment payment : paymentList) {
				paymentsAmount = paymentsAmount + payment.getPaymentAmount();			
			}
		}
		return new DecimalFormat("##.##").format(paymentsAmount);
	}

}
