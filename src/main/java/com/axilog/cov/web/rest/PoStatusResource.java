package com.axilog.cov.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.service.PoStatusQueryService;
import com.axilog.cov.service.PoStatusService;
import com.axilog.cov.service.dto.PoStatusCriteria;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.PoStatus}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Purchase Management", value = "PurchaseManagement", description = "Controller for Purchase Management")

public class PoStatusResource {

    private final Logger log = LoggerFactory.getLogger(PoStatusResource.class);

    private static final String ENTITY_NAME = "poStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoStatusService poStatusService;

    private final PoStatusQueryService poStatusQueryService;

    public PoStatusResource(PoStatusService poStatusService, PoStatusQueryService poStatusQueryService) {
        this.poStatusService = poStatusService;
        this.poStatusQueryService = poStatusQueryService;
    }

    /**
     * {@code POST  /po-statuses} : Create a new poStatus.
     *
     * @param poStatus the poStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new poStatus, or with status {@code 400 (Bad Request)} if the poStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/po-statuses")
    public ResponseEntity<PoStatus> createPoStatus(@RequestBody PoStatus poStatus) throws URISyntaxException {
        log.debug("REST request to save PoStatus : {}", poStatus);
        if (poStatus.getId() != null) {
            throw new BadRequestAlertException("A new poStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PoStatus result = poStatusService.save(poStatus);
        return ResponseEntity.created(new URI("/api/po-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /po-statuses} : Updates an existing poStatus.
     *
     * @param poStatus the poStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated poStatus,
     * or with status {@code 400 (Bad Request)} if the poStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the poStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/po-statuses")
    public ResponseEntity<PoStatus> updatePoStatus(@RequestBody PoStatus poStatus) throws URISyntaxException {
        log.debug("REST request to update PoStatus : {}", poStatus);
        if (poStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PoStatus result = poStatusService.save(poStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, poStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /po-statuses} : get all the poStatuses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of poStatuses in body.
     */
    @GetMapping("/po-statuses")
    public ResponseEntity<List<PoStatus>> getAllPoStatuses(PoStatusCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PoStatuses by criteria: {}", criteria);
        Page<PoStatus> page = poStatusQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /po-statuses/count} : count all the poStatuses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/po-statuses/count")
    public ResponseEntity<Long> countPoStatuses(PoStatusCriteria criteria) {
        log.debug("REST request to count PoStatuses by criteria: {}", criteria);
        return ResponseEntity.ok().body(poStatusQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /po-statuses/:id} : get the "id" poStatus.
     *
     * @param id the id of the poStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the poStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/po-statuses/{id}")
    public ResponseEntity<PoStatus> getPoStatus(@PathVariable Long id) {
        log.debug("REST request to get PoStatus : {}", id);
        Optional<PoStatus> poStatus = poStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(poStatus);
    }

    /**
     * {@code DELETE  /po-statuses/:id} : delete the "id" poStatus.
     *
     * @param id the id of the poStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/po-statuses/{id}")
    public ResponseEntity<Void> deletePoStatus(@PathVariable Long id) {
        log.debug("REST request to delete PoStatus : {}", id);
        poStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
