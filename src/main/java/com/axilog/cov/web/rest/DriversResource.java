package com.axilog.cov.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.axilog.cov.domain.Drivers;
import com.axilog.cov.service.DriversQueryService;
import com.axilog.cov.service.DriversService;
import com.axilog.cov.service.dto.DriversCriteria;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.Drivers}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Driver Management", value = "DriverManagement", description = "Controller for Driver Management")
public class DriversResource {

    private final Logger log = LoggerFactory.getLogger(DriversResource.class);

    private static final String ENTITY_NAME = "drivers";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DriversService driversService;
    private final DriversQueryService driversQueryService;


    public DriversResource(DriversService driversService, DriversQueryService driversQueryService) {
        this.driversService = driversService;
        this.driversQueryService = driversQueryService;
    }

    /**
     * {@code POST  /drivers} : Create a new drivers.
     *
     * @param drivers the drivers to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drivers, or with status {@code 400 (Bad Request)} if the drivers has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drivers")
    public ResponseEntity<Drivers> createdrivers(@RequestBody Drivers drivers) throws URISyntaxException {
        log.debug("REST request to save drivers : {}", drivers);
        if (drivers.getIdDrivers() != null) {
            throw new BadRequestAlertException("A new drivers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Drivers result = driversService.save(drivers);
        return ResponseEntity.created(new URI("/api/drivers/" + result.getIdDrivers()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getIdDrivers().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drivers} : Updates an existing drivers.
     *
     * @param drivers the drivers to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drivers,
     * or with status {@code 400 (Bad Request)} if the drivers is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drivers couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drivers")
    public ResponseEntity<Drivers> updateDrivers(@RequestBody Drivers drivers) throws URISyntaxException {
        log.debug("REST request to update drivers : {}", drivers);
        if (drivers.getIdDrivers() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Drivers result = driversService.save(drivers);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drivers.getIdDrivers().toString()))
            .body(result);
    }

    /**
     * {@code GET  /drivers} : get all the drivers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drivers in body.
     */

   

    /**
     * {@code GET  /drivers} : get all the drivers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drivers in body.
     */
    @GetMapping("/drivers")
    public ResponseEntity<List<Drivers>> getAllDrivers(DriversCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Drivers by criteria: {}", criteria);
        List<Drivers> drivers = driversService.findAll();
        return ResponseEntity.ok().body(drivers);
        
    }

    /**
     * {@code GET  /s/count} : count all the s.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/drivers/count")
    public ResponseEntity<Long> countDrivers(DriversCriteria criteria) {
        log.debug("REST request to count Drivers by criteria: {}", criteria);
        return ResponseEntity.ok().body(driversQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /drivers/:id} : get the "id" drivers.
     *
     * @param id the id of the s to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drivers, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drivers/{id}")
    public ResponseEntity<Drivers> getDrivers(@PathVariable Long id) {
        log.debug("REST request to get Drivers : {}", id);
        Optional<Drivers> drivers = driversService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drivers);
    }

    /**
     * {@code DELETE  /drivers/:id} : delete the "id" drivers.
     *
     * @param id the id of the drivers to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<Void> deleteDrivers(@PathVariable Long id) {
        log.debug("REST request to delete Drivers : {}", id);
        driversService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
