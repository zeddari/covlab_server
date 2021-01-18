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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.service.ImportHistoryQueryService;
import com.axilog.cov.service.ImportHistoryService;
import com.axilog.cov.service.dto.ImportHistoryCriteria;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.importHistory}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Inventory Management", value = "InventoryManagement", description = "Controller for Inventory Management")

public class ImportHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ImportHistoryResource.class);

    private static final String ENTITY_NAME = "importhistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImportHistoryService importHistoryService;

    private final ImportHistoryQueryService importHistoryQueryService;

    public ImportHistoryResource(ImportHistoryService importHistoryService, ImportHistoryQueryService importHistoryQueryService) {
        this.importHistoryService = importHistoryService;
        this.importHistoryQueryService = importHistoryQueryService;
    }

    /**
     * {@code POST  /importHistory} : Create a new importHistory.
     *
     * @param importHistory the importHistory to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new importHistory, or with status {@code 400 (Bad Request)} if the importHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/importsHistory")
    public ResponseEntity<ImportHistory> createImportHistory(@RequestBody ImportHistory importHistory) throws URISyntaxException {
        log.debug("REST request to save importHistory : {}", importHistory);
        if (importHistory.getId() != null) {
            throw new BadRequestAlertException("A new importHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ImportHistory result = importHistoryService.save(importHistory);
        return ResponseEntity.created(new URI("/api/importHistory/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /importHistory} : Updates an existing importHistory.
     *
     * @param importHistory the importHistory to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated importHistory,
     * or with status {@code 400 (Bad Request)} if the importHistory is not valid,
     * or with status {@code 500 (Internal Server Error)} if the importHistory couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/importsHistory")
    public ResponseEntity<ImportHistory> updateImportHistory(@RequestBody ImportHistory importHistory) throws URISyntaxException {
        log.debug("REST request to update importHistory : {}", importHistory);
        if (importHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImportHistory result = importHistoryService.save(importHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, importHistory.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /importHistory} : get all the importHistory.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of importHistory in body.
     */
    @GetMapping("/importsHistory")
    public ResponseEntity<List<ImportHistory>> getAllImportHistory() {
        log.debug("REST request to get importHistory by findAll");
        List<ImportHistory> page = importHistoryService.findAll();
        return ResponseEntity.ok().body(page);
    }

    /**
     * {@code GET  /importHistory/count} : count all the importHistory.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/importHistory/count")
    public ResponseEntity<Long> countimportHistory(ImportHistoryCriteria criteria) {
        log.debug("REST request to count importHistory by criteria: {}", criteria);
        return ResponseEntity.ok().body(importHistoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /importHistory/:id} : get the "id" importHistory.
     *
     * @param id the id of the importHistory to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the importHistory, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/importHistory/{id}")
    public ResponseEntity<ImportHistory> getimportHistory(@PathVariable Integer id) {
        log.debug("REST request to get importHistory : {}", id);
        Optional<ImportHistory> importHistory = importHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(importHistory);
    }

  
   
}
