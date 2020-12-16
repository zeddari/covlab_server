package com.axilog.cov.service;

import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.repository.OutletRepository;
import com.axilog.cov.service.dto.OutletCriteria;
import io.github.jhipster.service.QueryService;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for executing complex queries for {@link Outlet} entities in the database.
 * The main input is a {@link OutletCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Outlet} or a {@link Page} of {@link Outlet} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OutletQueryService extends QueryService<Outlet> {
    private final Logger log = LoggerFactory.getLogger(OutletQueryService.class);

    private final OutletRepository outletRepository;

    public OutletQueryService(OutletRepository outletRepository) {
        this.outletRepository = outletRepository;
    }

    /**
     * Return a {@link List} of {@link Outlet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Outlet> findByCriteria(OutletCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Outlet> specification = createSpecification(criteria);
        return outletRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Outlet} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Outlet> findByCriteria(OutletCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Outlet> specification = createSpecification(criteria);
        return outletRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OutletCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Outlet> specification = createSpecification(criteria);
        return outletRepository.count(specification);
    }

    /**
     * Function to convert {@link OutletCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Outlet> createSpecification(OutletCriteria criteria) {
        Specification<Outlet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Outlet_.id));
            }
            if (criteria.getOutletId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutletId(), Outlet_.outletId));
            }
            if (criteria.getOutletName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutletName(), Outlet_.outletName));
            }
            if (criteria.getOutletLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutletLocation(), Outlet_.outletLocation));
            }
            if (criteria.getOutletLat() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutletLat(), Outlet_.outletLat));
            }
            if (criteria.getOutletLong() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOutletLong(), Outlet_.outletLong));
            }
            if (criteria.getInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInventoryId(),
                            root -> root.join(Outlet_.inventories, JoinType.LEFT).get(Inventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
