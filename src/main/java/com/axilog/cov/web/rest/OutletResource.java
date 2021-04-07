package com.axilog.cov.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.axilog.cov.domain.DynamicApprovalConfig;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.dto.command.ConfigCommand;
import com.axilog.cov.dto.command.SelectCommand;
import com.axilog.cov.dto.mapper.InventoryMapper;
import com.axilog.cov.dto.representation.OutletRepresentation;
import com.axilog.cov.repository.ApprovalRepository;
import com.axilog.cov.service.OutletQueryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.dto.OutletCriteria;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.Outlet}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Inventory Management", value = "InventoryManagement", description = "Controller for Inventory Management")

public class OutletResource {

    private final Logger log = LoggerFactory.getLogger(OutletResource.class);

    private static final String ENTITY_NAME = "outlet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;
   
    @Autowired
    private  OutletService outletService;
   
    @Autowired
    private  OutletQueryService outletQueryService;
    
    @Autowired
    private InventoryMapper inventoryMapper;
    
    @Autowired
    private ApprovalRepository approvalRepository;

    public OutletResource(OutletService outletService, OutletQueryService outletQueryService) {
        this.outletService = outletService;
        this.outletQueryService = outletQueryService;
    }

    /**
     * {@code POST  /outlets} : Create a new outlet.
     *
     * @param outlet the outlet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new outlet, or with status {@code 400 (Bad Request)} if the outlet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/outlets")
    public ResponseEntity<Outlet> createOutlet(@RequestBody Outlet outlet) throws URISyntaxException {
        log.debug("REST request to save Outlet : {}", outlet);
        List <Outlet> listoutlet = outletService.findByOuletName(outlet.getOutletName());
        
		if (listoutlet != null && !listoutlet.isEmpty()) {
			throw new BadRequestAlertException("item exists", ENTITY_NAME, "outlet exist already ");
		} else {
			Outlet result = outletService.save(outlet);

			return ResponseEntity
					.created(new URI("/api/outlets/" + result.getId())).headers(HeaderUtil
							.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
					.body(result);

		}
    }
    
    @PostMapping("/outlets/config")
    public void createConfig(@RequestBody ConfigCommand configCommand) throws URISyntaxException {
        log.debug("REST request to save ConfigCommand : {}", configCommand);
        List<DynamicApprovalConfig> dynamicApprovalInValidations = approvalRepository.findByCurrentStepStatus("IN_VALIDATION");
        if (dynamicApprovalInValidations == null) {
        	approvalRepository.save(createDynamicConfig("IN_VALIDATION", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "IN_VALIDATION", true, "PENDING_AP1", "PENDING_AP1", configCommand.getOutlet(), true));
        }
        if (dynamicApprovalInValidations != null && !dynamicApprovalInValidations.isEmpty()) {
        	DynamicApprovalConfig dynamicApprovalInValidation = dynamicApprovalInValidations.get(0);
        	if (configCommand.getDestinationEmails() == null || configCommand.getDestinationEmails().isEmpty()) {
        		configCommand.setDestinationEmails(dynamicApprovalInValidation.getCurrentStepEmail());
        	}
        	if (configCommand.getCcEmails() == null || configCommand.getCcEmails().isEmpty()) {
        		configCommand.setCcEmails(dynamicApprovalInValidation.getCurrentStepEmailcc());
        	}
        	approvalRepository.save(createDynamicConfig("IN_VALIDATION", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "IN_VALIDATION", false, "PENDING_AP1", "PENDING_AP1", configCommand.getOutlet(), true));
        }
        // create others approval config
        approvalRepository.save(createDynamicConfig("PENDING_LEVEL1", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "PENDING_AP1", false, "PENDING_AP2", "PENDING_AP2", configCommand.getOutlet(), false));
        approvalRepository.save(createDynamicConfig("PENDING_LEVEL2", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "PENDING_AP2", false, "PENDING_AP3", "PENDING_AP3", configCommand.getOutlet(), false));
        approvalRepository.save(createDynamicConfig("PENDING_LEVEL3", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "PENDING_AP3", false, "PENDING_AP4", "PENDING_AP4", configCommand.getOutlet(), false));
        approvalRepository.save(createDynamicConfig("PENDING_LEVEL4", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "PENDING_AP4", false, "SENT_TO_NUPCO", "SENT_TO_NUPCO", configCommand.getOutlet(), false));
        approvalRepository.save(createDynamicConfig("SENT_TO_NUPCO", configCommand.getDestinationEmails(), configCommand.getCcEmails(), "SENT_TO_NUPCO", false, "NA", "NA", configCommand.getOutlet(), true));
    }

    /**
     * @param currentStep
     * @param currentStepEmail
     * @param currentStepEmailcc
     * @param currentStepStatus
     * @param finalStatus
     * @param nextStep
     * @param nextStepStatus
     * @param outlet
     * @param startStatus
     * @return
     */
    private DynamicApprovalConfig createDynamicConfig(String currentStep, String currentStepEmail, String currentStepEmailcc, String currentStepStatus, Boolean startStatus, String nextStep, String nextStepStatus, String outlet, Boolean finalStatus) {
    	return DynamicApprovalConfig.builder()
    			.currentStep(currentStep)
    			.currentStepEmail(currentStepEmail)
    			.currentStepEmailcc(currentStepEmailcc)
    			.currentStepStatus(currentStepStatus)
    			.finalStatus(finalStatus)
    			.nextStep(nextStep)
    			.nextStepStatus(nextStepStatus)
    			.outlet(outlet)
    			.startStatus(startStatus)
    			.build();
    }
    /**
     * {@code PUT  /outlets} : Updates an existing outlet.
     *
     * @param outlet the outlet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated outlet,
     * or with status {@code 400 (Bad Request)} if the outlet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the outlet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/outlets")
    public ResponseEntity<Outlet> updateOutlet(@RequestBody Outlet outlet) throws URISyntaxException {
        log.debug("REST request to update Outlet : {}", outlet);
        if (outlet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Outlet result = outletService.save(outlet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, outlet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /outlets} : get all the outlets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of outlets in body.
     */
    @GetMapping("/outlets")
    public ResponseEntity<List<Outlet>> getAllOutlets(OutletCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Outlets by criteria: {}", criteria);
        List<Outlet> oulets = outletService.findAll();
        return ResponseEntity.ok().body(oulets);
    }

    /**
     * {@code GET  /outlets/count} : count all the outlets.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/outlets/count")
    public ResponseEntity<Long> countOutlets(OutletCriteria criteria) {
        log.debug("REST request to count Outlets by criteria: {}", criteria);
        return ResponseEntity.ok().body(outletQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /outlets/:id} : get the "id" outlet.
     *
     * @param id the id of the outlet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the outlet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/outlets/{id}")
    public ResponseEntity<Outlet> getOutlet(@PathVariable Long id) {
        log.debug("REST request to get Outlet : {}", id);
        Optional<Outlet> outlet = outletService.findOne(id);
        return ResponseUtil.wrapOrNotFound(outlet);
    }

    /**
     * {@code DELETE  /outlets/:id} : delete the "id" outlet.
     *
     * @param id the id of the outlet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/outlets/{id}")
    public ResponseEntity<Void> deleteOutlet(@PathVariable Long id) {
        log.debug("REST request to delete Outlet : {}", id);
        outletService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
    
    @PostMapping("/outlets/region")
    public ResponseEntity<OutletRepresentation> getOutletRegion(@RequestBody @Valid SelectCommand selectCommand) {
        log.debug("REST request to get Inventories by FindOutletByRegion: {}");
        
        List<Outlet> outlets = outletService.findOutletParentRegion(selectCommand.getParentRegion());
        OutletRepresentation outletRepresentation = inventoryMapper.toOutletRepresentation(outlets);
        return ResponseEntity.ok().body(outletRepresentation);
    }
}
