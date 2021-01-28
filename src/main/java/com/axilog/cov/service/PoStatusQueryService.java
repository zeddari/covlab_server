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

import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.PoStatusRepository;
import com.axilog.cov.service.dto.PoStatusCriteria;

/**
 * Service for executing complex queries for {@link PoStatus} entities in the database.
 * The main input is a {@link PoStatusCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PoStatus} or a {@link Page} of {@link PoStatus} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PoStatusQueryService extends QueryService<PoStatus> {

    private final Logger log = LoggerFactory.getLogger(PoStatusQueryService.class);

    private final PoStatusRepository poStatusRepository;

    public PoStatusQueryService(PoStatusRepository poStatusRepository) {
        this.poStatusRepository = poStatusRepository;
    }

    /**
     * Return a {@link List} of {@link PoStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PoStatus> findByCriteria(PoStatusCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PoStatus> specification = createSpecification(criteria);
        return poStatusRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PoStatus} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PoStatus> findByCriteria(PoStatusCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PoStatus> specification = createSpecification(criteria);
        return poStatusRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PoStatusCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PoStatus> specification = createSpecification(criteria);
        return poStatusRepository.count(specification);
    }

    /**
     * Function to convert {@link PoStatusCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PoStatus> createSpecification(PoStatusCriteria criteria) {
        Specification<PoStatus> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PoStatus_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), PoStatus_.status));
            }
            if (criteria.getUpdatedAt() != null) {
       //         specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PoStatus_.updatedAt));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(PoStatus_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
