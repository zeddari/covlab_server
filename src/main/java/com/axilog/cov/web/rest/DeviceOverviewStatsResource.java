package com.axilog.cov.web.rest;

import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.web.rest.errors.BadRequestAlertException;
import com.axilog.cov.service.dto.DeviceOverviewStatsCriteria;
import com.axilog.cov.service.DeviceOverviewStatsQueryService;

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
 * REST controller for managing {@link com.axilog.cov.domain.DeviceOverviewStats}.
 */
@RestController
@RequestMapping("/api")
public class DeviceOverviewStatsResource {

    private final Logger log = LoggerFactory.getLogger(DeviceOverviewStatsResource.class);

    private static final String ENTITY_NAME = "deviceOverviewStats";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeviceOverviewStatsService deviceOverviewStatsService;

    private final DeviceOverviewStatsQueryService deviceOverviewStatsQueryService;

    public DeviceOverviewStatsResource(DeviceOverviewStatsService deviceOverviewStatsService, DeviceOverviewStatsQueryService deviceOverviewStatsQueryService) {
        this.deviceOverviewStatsService = deviceOverviewStatsService;
        this.deviceOverviewStatsQueryService = deviceOverviewStatsQueryService;
    }

    /**
     * {@code POST  /device-overview-stats} : Create a new deviceOverviewStats.
     *
     * @param deviceOverviewStats the deviceOverviewStats to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deviceOverviewStats, or with status {@code 400 (Bad Request)} if the deviceOverviewStats has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/device-overview-stats")
    public ResponseEntity<DeviceOverviewStats> createDeviceOverviewStats(@RequestBody DeviceOverviewStats deviceOverviewStats) throws URISyntaxException {
        log.debug("REST request to save DeviceOverviewStats : {}", deviceOverviewStats);
        if (deviceOverviewStats.getId() != null) {
            throw new BadRequestAlertException("A new deviceOverviewStats cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeviceOverviewStats result = deviceOverviewStatsService.save(deviceOverviewStats);
        return ResponseEntity.created(new URI("/api/device-overview-stats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /device-overview-stats} : Updates an existing deviceOverviewStats.
     *
     * @param deviceOverviewStats the deviceOverviewStats to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deviceOverviewStats,
     * or with status {@code 400 (Bad Request)} if the deviceOverviewStats is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deviceOverviewStats couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/device-overview-stats")
    public ResponseEntity<DeviceOverviewStats> updateDeviceOverviewStats(@RequestBody DeviceOverviewStats deviceOverviewStats) throws URISyntaxException {
        log.debug("REST request to update DeviceOverviewStats : {}", deviceOverviewStats);
        if (deviceOverviewStats.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeviceOverviewStats result = deviceOverviewStatsService.save(deviceOverviewStats);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, deviceOverviewStats.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /device-overview-stats} : get all the deviceOverviewStats.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deviceOverviewStats in body.
     */
    @GetMapping("/device-overview-stats")
    public ResponseEntity<List<DeviceOverviewStats>> getAllDeviceOverviewStats(DeviceOverviewStatsCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DeviceOverviewStats by criteria: {}", criteria);
        Page<DeviceOverviewStats> page = deviceOverviewStatsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /device-overview-stats/count} : count all the deviceOverviewStats.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/device-overview-stats/count")
    public ResponseEntity<Long> countDeviceOverviewStats(DeviceOverviewStatsCriteria criteria) {
        log.debug("REST request to count DeviceOverviewStats by criteria: {}", criteria);
        return ResponseEntity.ok().body(deviceOverviewStatsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /device-overview-stats/:id} : get the "id" deviceOverviewStats.
     *
     * @param id the id of the deviceOverviewStats to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deviceOverviewStats, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/device-overview-stats/{id}")
    public ResponseEntity<DeviceOverviewStats> getDeviceOverviewStats(@PathVariable Long id) {
        log.debug("REST request to get DeviceOverviewStats : {}", id);
        Optional<DeviceOverviewStats> deviceOverviewStats = deviceOverviewStatsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deviceOverviewStats);
    }

    /**
     * {@code DELETE  /device-overview-stats/:id} : delete the "id" deviceOverviewStats.
     *
     * @param id the id of the deviceOverviewStats to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/device-overview-stats/{id}")
    public ResponseEntity<Void> deleteDeviceOverviewStats(@PathVariable Long id) {
        log.debug("REST request to delete DeviceOverviewStats : {}", id);
        deviceOverviewStatsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
