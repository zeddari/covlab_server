package com.axilog.cov.service.impl;

import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.repository.DeviceOverviewStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DeviceOverviewStats}.
 */
@Service
@Transactional
public class DeviceOverviewStatsServiceImpl implements DeviceOverviewStatsService {

    private final Logger log = LoggerFactory.getLogger(DeviceOverviewStatsServiceImpl.class);

    private final DeviceOverviewStatsRepository deviceOverviewStatsRepository;

    public DeviceOverviewStatsServiceImpl(DeviceOverviewStatsRepository deviceOverviewStatsRepository) {
        this.deviceOverviewStatsRepository = deviceOverviewStatsRepository;
    }

    @Override
    public DeviceOverviewStats save(DeviceOverviewStats deviceOverviewStats) {
        log.debug("Request to save DeviceOverviewStats : {}", deviceOverviewStats);
        return deviceOverviewStatsRepository.save(deviceOverviewStats);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DeviceOverviewStats> findAll(Pageable pageable) {
        log.debug("Request to get all DeviceOverviewStats");
        return deviceOverviewStatsRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DeviceOverviewStats> findOne(Long id) {
        log.debug("Request to get DeviceOverviewStats : {}", id);
        return deviceOverviewStatsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeviceOverviewStats : {}", id);
        deviceOverviewStatsRepository.deleteById(id);
    }
}
