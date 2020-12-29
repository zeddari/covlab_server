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

import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.PurchaseOrderRepository;
import com.axilog.cov.service.dto.PurchaseOrderCriteria;

/**
 * Service for executing complex queries for {@link PurchaseOrder} entities in the database.
 * The main input is a {@link PurchaseOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrder} or a {@link Page} of {@link PurchaseOrder} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderQueryService extends QueryService<PurchaseOrder> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderQueryService.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderQueryService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrder} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrder> findByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrder} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrder> findByCriteria(PurchaseOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseOrder> createSpecification(PurchaseOrderCriteria criteria) {
        Specification<PurchaseOrder> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseOrder_.id));
            }
            if (criteria.getOrderNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderNo(), PurchaseOrder_.orderNo));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), PurchaseOrder_.quantity));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), PurchaseOrder_.createdBy));
            }
//            if (criteria.getCreatedOn() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), PurchaseOrder_.createdOn));
//            }
//            if (criteria.getDeliveredDate() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getDeliveredDate(), PurchaseOrder_.deliveredDate));
//            }
//            if (criteria.getUpdatedAt() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getUpdatedAt(), PurchaseOrder_.updatedAt));
//            }
//            if (criteria.getCreatedAt() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), PurchaseOrder_.createdAt));
//            }
            if (criteria.getPoStatusId() != null) {
                specification = specification.and(buildSpecification(criteria.getPoStatusId(),
                    root -> root.join(PurchaseOrder_.poStatuses, JoinType.LEFT).get(PoStatus_.id)));
            }
            if (criteria.getOutletId() != null) {
                specification = specification.and(buildSpecification(criteria.getOutletId(),
                    root -> root.join(PurchaseOrder_.outlet, JoinType.LEFT).get(Outlet_.id)));
            }
//            if (criteria.getProductId() != null) {
//                specification = specification.and(buildSpecification(criteria.getProductId(),
//                    root -> root.join(PurchaseOrder_.product, JoinType.LEFT).get(Product_.id)));
//            }
        }
        return specification;
    }
}
