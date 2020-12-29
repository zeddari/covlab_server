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

import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.ProductRepository;
import com.axilog.cov.service.dto.ProductCriteria;

/**
 * Service for executing complex queries for {@link Product} entities in the database.
 * The main input is a {@link ProductCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Product} or a {@link Page} of {@link Product} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Return a {@link List} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Product> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Product} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Product> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Product> specification = createSpecification(criteria);
        return productRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProductId(), Product_.productId));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getProductCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProductCode(), Product_.productCode));
            }
            if (criteria.getInventoriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getInventoriesId(),
                    root -> root.join(Product_.inventories, JoinType.LEFT).get(Inventory_.id)));
            }
//            if (criteria.getPurchaseOrdersId() != null) {
//                specification = specification.and(buildSpecification(criteria.getPurchaseOrdersId(),
//                    root -> root.join(Product_.purchaseOrders, JoinType.LEFT).get(PurchaseOrder_.id)));
//            }
            if (criteria.getTicketsId() != null) {
                specification = specification.and(buildSpecification(criteria.getTicketsId(),
                    root -> root.join(Product_.tickets, JoinType.LEFT).get(Tickets_.id)));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Product_.category, JoinType.LEFT).get(Category_.id)));
            }
            if (criteria.getDeviceOverviewStatsId() != null) {
                specification = specification.and(buildSpecification(criteria.getDeviceOverviewStatsId(),
                    root -> root.join(Product_.deviceOverviewStats, JoinType.LEFT).get(DeviceOverviewStats_.id)));
            }
        }
        return specification;
    }
}
