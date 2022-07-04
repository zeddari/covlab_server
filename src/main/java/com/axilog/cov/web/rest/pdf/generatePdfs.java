package com.axilog.cov.web.rest.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import com.axilog.cov.domain.Customer;
import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.domain.GrnHistSequence;
import com.axilog.cov.domain.GrnHistory;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Invoice;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Paiement;
import com.axilog.cov.domain.PoReport;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.Sequence;
import com.axilog.cov.domain.Substitute;
import com.axilog.cov.dto.command.POMailDetail;
import com.axilog.cov.dto.command.PurchaseOrderCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.mapper.InvoiceMapper;
import com.axilog.cov.dto.mapper.PurchaseOrderMapper;
import com.axilog.cov.dto.representation.GrnHistoryRepresentation;
import com.axilog.cov.dto.representation.InventoryPdfDetail;
import com.axilog.cov.dto.representation.InvoicePdfDetail;
import com.axilog.cov.dto.representation.InvoicePdfRequest;
import com.axilog.cov.dto.representation.PoApprovalRepresentation;
import com.axilog.cov.dto.representation.PoPdfDetail;
import com.axilog.cov.dto.representation.PoReportRepresentation;
import com.axilog.cov.dto.representation.PurchaseOrderRepresentation;
import com.axilog.cov.enums.PurchaseStatusEnum;
import com.axilog.cov.repository.CustomerRepository;
import com.axilog.cov.repository.GrnHistSequenceRepository;
import com.axilog.cov.repository.InvoiceRepository;
import com.axilog.cov.repository.PaiementRepository;
import com.axilog.cov.repository.PoStatusRepository;
import com.axilog.cov.repository.SequenceRepository;
import com.axilog.cov.repository.SubstituteRepository;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.ApprovalService;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.PoMailService;
import com.axilog.cov.service.PoReportService;
import com.axilog.cov.service.ProductService;
import com.axilog.cov.service.PurchaseOrderHistoryService;
import com.axilog.cov.service.PurchaseOrderQueryService;
import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.service.dto.PurchaseOrderCriteria;
import com.axilog.cov.service.pdf.PdfService;
import com.axilog.cov.service.pdf.PdfServiceInvoice;
import com.axilog.cov.service.xlsx.XlsService;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.util.JsonUtils;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
import com.axilog.cov.web.rest.errors.NotFoundAlertException;
import com.lowagie.text.DocumentException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import liquibase.pro.packaged.in;

/**
 * REST controller for managing {@link com.axilog.cov.domain.PurchaseOrder}.
 */
@SuppressWarnings("unused")
@RestController
@RequestMapping("/api")
@Api(tags = "Generate PDF Files", value = "generatePdfs", description = "Controller for PDF generations")
public class generatePdfs {

    private final Logger log = LoggerFactory.getLogger(generatePdfs.class);

    private static final String ENTITY_NAME = "purchaseOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PaiementRepository paiementRepository;
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private PdfServiceInvoice pdfServiceInvoice;
    
    
    @Autowired
    private InvoiceMapper invoiceMapper;

    
    
 
    
    
    @PostMapping(value = "/invoices/generate/pdf/customer", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> generatePdf(@RequestBody InvoicePdfRequest invoicePdfRequest) throws URISyntaxException, IOException, DocumentException {
        log.debug("REST request to generate PDF : {}", invoicePdfRequest);

        Customer customer =   customerRepository.findById(invoicePdfRequest.getCustomerId()).get();        
        List<Paiement> paiementList =  paiementRepository.findByCustomer(customer);     
        if (paiementList != null & paiementList.size()!=0) {
            InvoicePdfDetail invoicePdfDetail = invoiceMapper.toInvoicePdfDetail(paiementList, customer);
            File poPdf = pdfServiceInvoice.generatePdf(invoicePdfDetail);
    		byte[] fileContent = FileUtils.readFileToByteArray(poPdf);
    		//save PDF
    		Invoice invoice = new Invoice();
    		invoice.setFile(fileContent);
    		invoice.setCustomer(customer);
    		invoiceRepository.save(invoice);
		}
		return ResponseEntity.ok("Succes");


    }
    

}
