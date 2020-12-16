package com.axilog.cov.web.rest;

import com.axilog.cov.domain.Outlet;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
import com.axilog.cov.service.dto.OutletCriteria;
import com.axilog.cov.service.OutletQueryService;

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
 * REST controller for managing {@link com.axilog.cov.domain.Outlet}.
 */
@RestController
@RequestMapping("/api")
public class OutletResource {

    private final Logger log = LoggerFactory.getLogger(OutletResource.class);

    private static final String ENTITY_NAME = "outlet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OutletService outletService;

    private final OutletQueryService outletQueryService;

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
        if (outlet.getId() != null) {
            throw new BadRequestAlertException("A new outlet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Outlet result = outletService.save(outlet);
        return ResponseEntity.created(new URI("/api/outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
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
        Page<Outlet> page = outletQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
}
