package com.axilog.cov.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.DeviceOverviewStatsRepository;
import com.axilog.cov.service.dto.DeviceOverviewStatsCriteria;

/**
 * Service for executing complex queries for {@link DeviceOverviewStats} entities in the database.
 * The main input is a {@link DeviceOverviewStatsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DeviceOverviewStats} or a {@link Page} of {@link DeviceOverviewStats} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DeviceOverviewStatsQueryService extends QueryService<DeviceOverviewStats> {

    private final Logger log = LoggerFactory.getLogger(DeviceOverviewStatsQueryService.class);

    private final DeviceOverviewStatsRepository deviceOverviewStatsRepository;

    public DeviceOverviewStatsQueryService(DeviceOverviewStatsRepository deviceOverviewStatsRepository) {
        this.deviceOverviewStatsRepository = deviceOverviewStatsRepository;
    }

    /**
     * Return a {@link List} of {@link DeviceOverviewStats} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DeviceOverviewStats> findByCriteria(DeviceOverviewStatsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DeviceOverviewStats> specification = createSpecification(criteria);
        return deviceOverviewStatsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DeviceOverviewStats} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DeviceOverviewStats> findByCriteria(DeviceOverviewStatsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DeviceOverviewStats> specification = createSpecification(criteria);
        return deviceOverviewStatsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DeviceOverviewStatsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DeviceOverviewStats> specification = createSpecification(criteria);
        return deviceOverviewStatsRepository.count(specification);
    }

    /**
     * Function to convert {@link DeviceOverviewStatsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DeviceOverviewStats> createSpecification(DeviceOverviewStatsCriteria criteria) {
        Specification<DeviceOverviewStats> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DeviceOverviewStats_.id));
            }
            if (criteria.getDeviceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeviceId(), DeviceOverviewStats_.deviceId));
            }
            if (criteria.getTimestamp() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTimestamp(), DeviceOverviewStats_.timestamp));
            }
            if (criteria.getSerialNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSerialNumber(), DeviceOverviewStats_.serialNumber));
            }
            if (criteria.getHumidity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHumidity(), DeviceOverviewStats_.humidity));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), DeviceOverviewStats_.temperature));
            }
            if (criteria.getCo2() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCo2(), DeviceOverviewStats_.co2));
            }
            if (criteria.getPressure() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPressure(), DeviceOverviewStats_.pressure));
            }
            if (criteria.getDifferentialPressure() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDifferentialPressure(), DeviceOverviewStats_.differentialPressure));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(DeviceOverviewStats_.products, JoinType.LEFT).get(Product_.id)));
            }
            if (criteria.getOutletId() != null) {
                specification = specification.and(buildSpecification(criteria.getOutletId(),
                    root -> root.join(DeviceOverviewStats_.outlet, JoinType.LEFT).get(Outlet_.id)));
            }
        }
        return specification;
    }
}
