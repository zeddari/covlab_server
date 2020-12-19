package com.axilog.cov.web.rest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.service.DeviceOverviewStatsQueryService;
import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.service.dto.DeviceOverviewStatsCriteria;

import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;

/**
 * REST controller for managing {@link com.axilog.cov.domain.DeviceOverviewStats}.
 */
@RestController
@RequestMapping("/api")
@Api(tags = "Temperature Management", value = "TemperatureManagement", description = "Controller for Temperature Management")

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

 
}
