//package com.axilog.cov.web.rest.pdf;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URISyntaxException;
//import java.util.List;
//
//import org.apache.commons.io.FileUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.axilog.cov.domain.Invoice;
//import com.axilog.cov.domain.Payment;
//import com.axilog.cov.dto.mapper.InvoiceMapper;
//import com.axilog.cov.dto.representation.InvoicePdfDetail;
//import com.axilog.cov.dto.representation.InvoicePdfRequest;
//import com.axilog.cov.repository.InvoiceRepository;
//import com.axilog.cov.repository.PaymentRepository;
//import com.axilog.cov.service.pdf.PdfServiceInvoice;
//import com.lowagie.text.DocumentException;
//
//import io.swagger.annotations.Api;
//
///**
// * REST controller for managing {@link com.axilog.cov.domain.PurchaseOrder}.
// */
//@SuppressWarnings("unused")
//@RestController
//@RequestMapping("/api")
//@Api(tags = "Generate PDF Files", value = "generatePdfs", description = "Controller for PDF generations")
//public class generatePdfs {
//
//    private final Logger log = LoggerFactory.getLogger(generatePdfs.class);
//
//    private static final String ENTITY_NAME = "purchaseOrder";
//
//    @Value("${jhipster.clientApp.name}")
//    private String applicationName;
////    @Autowired
////    private CustomerRepository customerRepository;
//    @Autowired
//    private PaymentRepository paymentRepository;
//    
//    @Autowired
//    private InvoiceRepository invoiceRepository;
//    
//    @Autowired
//    private PdfServiceInvoice pdfServiceInvoice;
//    
//    
//    @Autowired
//    private InvoiceMapper invoiceMapper;
//
//    
//    
// 
//    
////    
////    @PostMapping(value = "/invoices/generate/pdf/customer", consumes = MediaType.APPLICATION_JSON_VALUE )
////    public ResponseEntity<String> generatePdf(@RequestBody InvoicePdfRequest invoicePdfRequest) throws URISyntaxException, IOException, DocumentException {
////        log.debug("REST request to generate PDF : {}", invoicePdfRequest);
////
////        //Customer customer =   customerRepository.findById(invoicePdfRequest.getCustomerId()).get();        
////        List<Payment> paiementList =  paymentRepository.findByDriverNameOrSupervisorName(null, null);     
////        if (paiementList != null & paiementList.size()!=0) {
////            InvoicePdfDetail invoicePdfDetail = invoiceMapper.toInvoicePdfDetail(paiementList);
////            File poPdf = pdfServiceInvoice.generatePdf(invoicePdfDetail);
////    		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
////    		//save PDF
////    		Invoice invoice = new Invoice();
////    		invoice.setFile(fileContent);
////    		//invoice.setCustomer(customer);
////    		invoiceRepository.save(invoice);
////		}
////		return ResponseEntity.ok("Succes");
////
////
////    }
//    
//
//}
