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

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.service.dto.InventoryCriteria;

/**
 * Service for executing complex queries for {@link Inventory} entities in the database.
 * The main input is a {@link InventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Inventory} or a {@link Page} of {@link Inventory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryQueryService extends QueryService<Inventory> {

    private final Logger log = LoggerFactory.getLogger(InventoryQueryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryQueryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Return a {@link List} of {@link Inventory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Inventory> findByCriteria(InventoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Inventory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inventory> findByCriteria(InventoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventory> createSpecification(InventoryCriteria criteria) {
        Specification<Inventory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inventory_.id));
            }
            if (criteria.getInventoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInventoryId(), Inventory_.inventoryId));
            }
            if (criteria.getQuantitiesInHand() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantitiesInHand(), Inventory_.quantitiesInHand));
            }
            if (criteria.getQuantitiesInTransit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantitiesInTransit(), Inventory_.quantitiesInTransit));
            }
            if (criteria.getUom() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUom(), Inventory_.uom));
            }
            if (criteria.getActualDailyConsumption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualDailyConsumption(), Inventory_.actualDailyConsumption));
            }
            if (criteria.getActualAvgConsumption() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getActualAvgConsumption(), Inventory_.actualAvgConsumption));
            }
            if (criteria.getReOrderLevel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReOrderLevel(), Inventory_.reOrderLevel));
            }
            if (criteria.getSuggestedQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSuggestedQuantity(), Inventory_.suggestedQuantity));
            }
            if (criteria.getExpectedCoveringDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedCoveringDay(), Inventory_.expectedCoveringDay));
            }
            if (criteria.getLastUpdatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastUpdatedAt(), Inventory_.lastUpdatedAt));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), Inventory_.status));
            }
            if (criteria.getOutletId() != null) {
                specification = specification.and(buildSpecification(criteria.getOutletId(),
                    root -> root.join(Inventory_.outlet, JoinType.LEFT).get(Outlet_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Inventory_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
