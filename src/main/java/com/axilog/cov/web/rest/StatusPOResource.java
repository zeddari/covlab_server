package com.axilog.cov.web.rest;

import com.axilog.cov.domain.StatusPO;
import com.axilog.cov.service.StatusPOService;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
import com.axilog.cov.service.dto.StatusPOCriteria;
import com.axilog.cov.service.StatusPOQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.axilog.cov.domain.StatusPO}.
 */
@RestController
@RequestMapping("/api")
public class StatusPOResource {

    private final Logger log = LoggerFactory.getLogger(StatusPOResource.class);

    private static final String ENTITY_NAME = "statusPO";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusPOService statusPOService;

    private final StatusPOQueryService statusPOQueryService;

    public StatusPOResource(StatusPOService statusPOService, StatusPOQueryService statusPOQueryService) {
        this.statusPOService = statusPOService;
        this.statusPOQueryService = statusPOQueryService;
    }

    /**
     * {@code POST  /status-pos} : Create a new statusPO.
     *
     * @param statusPO the statusPO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statusPO, or with status {@code 400 (Bad Request)} if the statusPO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-pos")
    public ResponseEntity<StatusPO> createStatusPO(@RequestBody StatusPO statusPO) throws URISyntaxException {
        log.debug("REST request to save StatusPO : {}", statusPO);
        if (statusPO.getId() != null) {
            throw new BadRequestAlertException("A new statusPO cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusPO result = statusPOService.save(statusPO);
        return ResponseEntity.created(new URI("/api/status-pos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /status-pos} : Updates an existing statusPO.
     *
     * @param statusPO the statusPO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statusPO,
     * or with status {@code 400 (Bad Request)} if the statusPO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statusPO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-pos")
    public ResponseEntity<StatusPO> updateStatusPO(@RequestBody StatusPO statusPO) throws URISyntaxException {
        log.debug("REST request to update StatusPO : {}", statusPO);
        if (statusPO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusPO result = statusPOService.save(statusPO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusPO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /status-pos} : get all the statusPOS.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statusPOS in body.
     */
    @GetMapping("/status-pos")
    public ResponseEntity<List<StatusPO>> getAllStatusPOS(StatusPOCriteria criteria, Pageable pageable) {
        log.debug("REST request to get StatusPOS by criteria: {}", criteria);
        Page<StatusPO> page = statusPOQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /status-pos/count} : count all the statusPOS.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/status-pos/count")
    public ResponseEntity<Long> countStatusPOS(StatusPOCriteria criteria) {
        log.debug("REST request to count StatusPOS by criteria: {}", criteria);
        return ResponseEntity.ok().body(statusPOQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /status-pos/:id} : get the "id" statusPO.
     *
     * @param id the id of the statusPO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statusPO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-pos/{id}")
    public ResponseEntity<StatusPO> getStatusPO(@PathVariable Long id) {
        log.debug("REST request to get StatusPO : {}", id);
        Optional<StatusPO> statusPO = statusPOService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusPO);
    }

    /**
     * {@code DELETE  /status-pos/:id} : delete the "id" statusPO.
     *
     * @param id the id of the statusPO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-pos/{id}")
    public ResponseEntity<Void> deleteStatusPO(@PathVariable Long id) {
        log.debug("REST request to delete StatusPO : {}", id);
        statusPOService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
