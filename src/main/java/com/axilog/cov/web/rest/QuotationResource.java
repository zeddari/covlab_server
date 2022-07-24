package com.axilog.cov.web.rest;

import com.axilog.cov.domain.CurrentCustomerLocation;
import com.axilog.cov.domain.Notification;
import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.dto.command.NewPoCommand;
import com.axilog.cov.dto.command.SmsCommand;
import com.axilog.cov.dto.command.workflow.RequestQuotationCommand;
import com.axilog.cov.dto.mapper.QuotationMapper;
import com.axilog.cov.service.RequestQuotationService;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;


import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

/**
 * REST controller for managing {@link com.axilog.cov.domain.RequestQuotation}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Request Quotation Management", value = "Request QuotationManagement", description = "Controller for Request Quotation Management")
public class QuotationResource {

    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);

    private static final String ENTITY_NAME = "RequestQuotation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestQuotationService requestQuotationService;

    public static final String ACCOUNT_SID = "AC31a85bac123ec3b2ef0ec4ca055d3eb5";
    public static final String AUTH_TOKEN = "2dd32219d0d51ce1660f128a4911781b";
    public QuotationResource(RequestQuotationService requestQuotationService) {
        this.requestQuotationService = requestQuotationService;
    }

    /**
     * {@code POST  /RequestQuotations} : Create a new RequestQuotation.
     *
     * @param requestQuotation the RequestQuotationCommand to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new RequestQuotation, or with status {@code 400 (Bad Request)} if the RequestQuotation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quotations")
    public ResponseEntity<RequestQuotation> createQuotation(@RequestBody RequestQuotationCommand requestQuotation) throws URISyntaxException, ParseException {
        log.debug("REST request to save Request Quotation : {}", requestQuotation);

        RequestQuotation result = requestQuotationService.save(QuotationMapper.toQuotation(requestQuotation));
        return ResponseEntity.created(new URI("/api/quotations/"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getRequestQuotationId()))
                .body(result);


    }

    /**
     * {@code PUT  /RequestQuotations} : Updates an existing RequestQuotation.
     *
     * @param RequestQuotation the RequestQuotation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated RequestQuotation,
     * or with status {@code 400 (Bad Request)} if the RequestQuotation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the RequestQuotation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requestQuotations")
    public ResponseEntity<RequestQuotation> updateRequestQuotation(@RequestBody RequestQuotation requestQuotation) throws URISyntaxException {
        log.debug("REST request to update RequestQuotation : {}", requestQuotation);
        if (requestQuotation.getRequestQuotationId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RequestQuotation result = requestQuotationService.save(requestQuotation);
        requestQuotationService.incrementQuotationSequence();
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, requestQuotation.getRequestQuotationId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /RequestQuotations} : get all the RequestQuotations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of RequestQuotations in body.
     */
    @GetMapping("/requestQuotations")
    public ResponseEntity<List<RequestQuotation>> getAllRequestQuotations() {
        log.debug("REST request to get RequestQuotations by findAll");
        List<RequestQuotation> requestQuotations = requestQuotationService.findAll();
        return ResponseEntity.ok().body(requestQuotations);
    }




    /**
     * {@code GET  /RequestQuotations/:id} : get the "id" RequestQuotation.
     *
     * @param id the id of the RequestQuotation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the RequestQuotation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requestQuotations/{id}")
    public ResponseEntity<RequestQuotation> getRequestQuotation(@PathVariable Long id) {
        log.debug("REST request to get RequestQuotation : {}", id);
        Optional<RequestQuotation> requestQuotation = requestQuotationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestQuotation);
    }

    /**
     * {@code DELETE  /requestQuotations/:id} : delete the "id" RequestQuotation.
     *
     * @param id the id of the RequestQuotation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requestQuotations/{id}")
    public ResponseEntity<Void> deleteRequestQuotation(@PathVariable Long id) {
        log.debug("REST request to delete RequestQuotation : {}", id);
        requestQuotationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/requestQuotations/currentVal")
    public Long getCurrentVal() {
        log.debug("REST request to get current sequence");
        return requestQuotationService.getNextRequestQuotationSequence();
    }

    @PostMapping("/locations/update/")
    public void updateLocations(@RequestParam String quotationId, @RequestParam String lat, @RequestParam String lng) {
        log.debug("REST update customer locations");
        CurrentCustomerLocation currentCustomerLocation = CurrentCustomerLocation.builder().quotationId(quotationId).lat(lat).lng(lng).build();
        requestQuotationService.saveLocation(currentCustomerLocation);
        requestQuotationService.createUpdateLocationNotif(lat, lng, quotationId);
    }

    @PostMapping("/locations/sendSms/")
    public void updateLocations(@RequestBody SmsCommand smsCommand) {
        log.debug("REST send sms request location");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(smsCommand.getPhone()),
                smsCommand.getMessagingServiceSid(),
                smsCommand.getMessage())
            .create();
    }

    @GetMapping("/notifications")
    public List<Notification> getTop5Notifs() {
        log.debug("REST get Top 5 notif");
        return requestQuotationService.getTop5Notif();
    }

    @PostMapping("/po/save/")
    public void saveNewPo(@RequestBody NewPoCommand newPoCommand) {
        requestQuotationService.saveNewPo(newPoCommand);
    }
}
