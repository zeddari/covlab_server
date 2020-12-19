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

import com.axilog.cov.domain.Tickets;
import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.TicketsRepository;
import com.axilog.cov.service.dto.TicketsCriteria;

/**
 * Service for executing complex queries for {@link Tickets} entities in the database.
 * The main input is a {@link TicketsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tickets} or a {@link Page} of {@link Tickets} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TicketsQueryService extends QueryService<Tickets> {

    private final Logger log = LoggerFactory.getLogger(TicketsQueryService.class);

    private final TicketsRepository ticketsRepository;

    public TicketsQueryService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    /**
     * Return a {@link List} of {@link Tickets} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tickets> findByCriteria(TicketsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tickets> specification = createSpecification(criteria);
        return ticketsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tickets} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tickets> findByCriteria(TicketsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tickets> specification = createSpecification(criteria);
        return ticketsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TicketsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tickets> specification = createSpecification(criteria);
        return ticketsRepository.count(specification);
    }

    /**
     * Function to convert {@link TicketsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tickets> createSpecification(TicketsCriteria criteria) {
        Specification<Tickets> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tickets_.id));
            }
            if (criteria.getTicketNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicketNo(), Tickets_.ticketNo));
            }
            if (criteria.getTicketType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTicketType(), Tickets_.ticketType));
            }
            if (criteria.getTicketStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTicketStatus(), Tickets_.ticketStatus));
            }
            if (criteria.getTicketDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicketDueDate(), Tickets_.ticketDueDate));
            }
            if (criteria.getTicketPriority() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTicketPriority(), Tickets_.ticketPriority));
            }
            if (criteria.getTicketCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicketCreatedOn(), Tickets_.ticketCreatedOn));
            }
            if (criteria.getTicketUpdateAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTicketUpdateAt(), Tickets_.ticketUpdateAt));
            }
            if (criteria.getOutletId() != null) {
                specification = specification.and(buildSpecification(criteria.getOutletId(),
                    root -> root.join(Tickets_.outlet, JoinType.LEFT).get(Outlet_.id)));
            }
            if (criteria.getProductId() != null) {
                specification = specification.and(buildSpecification(criteria.getProductId(),
                    root -> root.join(Tickets_.product, JoinType.LEFT).get(Product_.id)));
            }
        }
        return specification;
    }
}
