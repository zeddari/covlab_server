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

import com.axilog.cov.domain.Drivers;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.DriversRepository;
import com.axilog.cov.service.dto.DriversCriteria;
import com.axilog.cov.service.dto.TicketsCriteria;

/**
 * Service for executing complex queries for {@link Drivers} entities in the database.
 * The main input is a {@link TicketsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Drivers} or a {@link Page} of {@link Drivers} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DriversQueryService extends QueryService<Drivers> {

    private final Logger log = LoggerFactory.getLogger(DriversQueryService.class);

    private final DriversRepository driversRepository;

    public DriversQueryService(DriversRepository driversRepository) {
        this.driversRepository = driversRepository;
    }

    /**
     * Return a {@link List} of {@link Tickets} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Drivers> findByCriteria(DriversCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Drivers> specification = createSpecification(criteria);
        return driversRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tickets} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Drivers> findByCriteria(DriversCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Drivers> specification = createSpecification(criteria);
        return driversRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DriversCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Drivers> specification = createSpecification(criteria);
        return driversRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Drivers> createSpecification(DriversCriteria criteria) {
        Specification<Drivers> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getIdDrivers() != null) {
         //       specification = specification.and(buildRangeSpecification(criteria.getIdDrivers()), Drivers_.idDrivers));
            }
           
            if (criteria.getNameDrivers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameDrivers(), Drivers_.nameDrivers));
            }
                   
        }
        return specification;
    }
}
