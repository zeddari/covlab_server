package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.Outlet;

/**
 * Service Interface for managing {@link DeviceOverviewStats}.
 */
public interface DeviceOverviewStatsService {

    /**
     * Save a deviceOverviewStats.
     *
     * @param deviceOverviewStats the entity to save.
     * @return the persisted entity.
     */
    DeviceOverviewStats save(DeviceOverviewStats deviceOverviewStats);

    /**
     * Get all the deviceOverviewStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DeviceOverviewStats> findAll(Pageable pageable);

    /**
     * @return
     */
    List<DeviceOverviewStats> findAll();
    
    
    List<DeviceOverviewStats>  findByOutletOutletId(Long outletId);
    
    List<DeviceOverviewStats>  findByOutlet(Outlet outlet);

    /**
     * Get the "id" deviceOverviewStats.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DeviceOverviewStats> findOne(Long id);

    /**
     * Delete the "id" deviceOverviewStats.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
