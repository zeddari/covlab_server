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

import com.axilog.cov.domain.StatusPO;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.StatusPORepository;
import com.axilog.cov.service.dto.StatusPOCriteria;

/**
 * Service for executing complex queries for {@link StatusPO} entities in the database.
 * The main input is a {@link StatusPOCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StatusPO} or a {@link Page} of {@link StatusPO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StatusPOQueryService extends QueryService<StatusPO> {

    private final Logger log = LoggerFactory.getLogger(StatusPOQueryService.class);

    private final StatusPORepository statusPORepository;

    public StatusPOQueryService(StatusPORepository statusPORepository) {
        this.statusPORepository = statusPORepository;
    }

    /**
     * Return a {@link List} of {@link StatusPO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StatusPO> findByCriteria(StatusPOCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<StatusPO> specification = createSpecification(criteria);
        return statusPORepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link StatusPO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StatusPO> findByCriteria(StatusPOCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<StatusPO> specification = createSpecification(criteria);
        return statusPORepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(StatusPOCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<StatusPO> specification = createSpecification(criteria);
        return statusPORepository.count(specification);
    }

    /**
     * Function to convert {@link StatusPOCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<StatusPO> createSpecification(StatusPOCriteria criteria) {
        Specification<StatusPO> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), StatusPO_.id));
            }
            if (criteria.getStatusPoId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStatusPoId(), StatusPO_.statusPoId));
            }
            if (criteria.getStatusPoName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatusPoName(), StatusPO_.statusPoName));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification = specification.and(buildSpecification(criteria.getPurchaseOrderId(),
                    root -> root.join(StatusPO_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)));
            }
        }
        return specification;
    }
}
