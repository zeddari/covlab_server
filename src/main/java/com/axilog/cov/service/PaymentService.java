package com.axilog.cov.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.axilog.cov.dto.representation.InvoiceRequest;
import com.axilog.cov.dto.representation.PaymentRepresentation;
import com.lowagie.text.DocumentException;

public interface PaymentService {
	

    /**
     * @return
     */
    List<PaymentRepresentation> findAll(String user);
    /**
     * 
     * @param invoiceRequest
     * @throws DocumentException 
     * @throws IOException 
     */
    void addPayment(InvoiceRequest invoiceRequest ) throws IOException, DocumentException;

    /**
     * getPdfByInvoicePdf
     */
    byte[] getPdfByInvoicePdf(String invoiceId );


}
