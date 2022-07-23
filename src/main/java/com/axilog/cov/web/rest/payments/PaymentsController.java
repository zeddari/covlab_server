package com.axilog.cov.web.rest.payments;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.dto.representation.InvoiceRequest;
import com.axilog.cov.dto.representation.PaymentRepresentation;
import com.axilog.cov.security.SecurityUtils;
import com.axilog.cov.service.PaymentService;
import com.lowagie.text.DocumentException;

import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.RequestQuotation}.
 */
@RestController
@RequestMapping("/api/ui")
@Api(tags = "Payment management", value = "My Payments", description = "Controller for Request user Payments")
public class PaymentsController {

    private final Logger log = LoggerFactory.getLogger(PaymentsController.class);


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @Autowired
    PaymentService paymentService;




    /**
     * {@code GET  /RequestQuotations} : get all the RequestQuotations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of RequestQuotations in body.
     */
    @GetMapping("/myPayments")
    public ResponseEntity<List<PaymentRepresentation>> getAllRequestQuotations() {
        log.debug("REST request to get Payments for user");
        String currentUser = SecurityUtils.getCurrentUserLogin().get();
        return ResponseEntity.ok().body(paymentService.findAll(currentUser));
    }

    
    @PostMapping(value = "/addPayment", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest invoiceRequest ) throws URISyntaxException, IOException, DocumentException {
        log.debug("REST request to generate PDF : {}", invoiceRequest);
        paymentService.addPayment(invoiceRequest);
		return ResponseEntity.ok("Succes");
    }


   
}
