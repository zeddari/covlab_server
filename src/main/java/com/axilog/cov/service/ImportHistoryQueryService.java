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

import com.axilog.cov.domain.*; // for static metamodels
import com.axilog.cov.repository.ImportHistoryRepository;
import com.axilog.cov.service.dto.ImportHistoryCriteria;

/**
 * Service for executing complex queries for {@link ImportHistory} entities in the database.
 * The main input is a {@link ImportHistoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ImportHistory} or a {@link Page} of {@link ImportHistory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImportHistoryQueryService extends QueryService<ImportHistory> {

    private final Logger log = LoggerFactory.getLogger(ImportHistoryQueryService.class);

    private final ImportHistoryRepository ImportHistoryRepository;

    public ImportHistoryQueryService(ImportHistoryRepository ImportHistoryRepository) {
        this.ImportHistoryRepository = ImportHistoryRepository;
    }

    /**
     * Return a {@link List} of {@link ImportHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ImportHistory> findByCriteria(ImportHistoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ImportHistory> specification = createSpecification(criteria);
        return ImportHistoryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ImportHistory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ImportHistory> findByCriteria(ImportHistoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ImportHistory> specification = createSpecification(criteria);
        return ImportHistoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImportHistoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ImportHistory> specification = createSpecification(criteria);
        return ImportHistoryRepository.count(specification);
    }

    /**
     * Function to convert {@link ImportHistoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ImportHistory> createSpecification(ImportHistoryCriteria criteria) {
        Specification<ImportHistory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ImportHistory_.id));
            }
           
            if (criteria.getfileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getfileName(), ImportHistory_.fileName));
            }
            if (criteria.getjobId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getjobId(), ImportHistory_.jobId));
            }
//            if (criteria.getActualDailyConsumption() != null) {
//                specification = specification.and(buildRangeSpecification(criteria.getActualDailyConsumption(), ImportHistory_.actualDailyConsumption));
//            }
            if (criteria.getoutlet()!= null) {
                specification = specification.and(buildStringSpecification(criteria.getoutlet(), ImportHistory_.outlet));
            }
       //     if (criteria.getimportedAt() != null) {
        //        specification = specification.and(likeUpperSpecification(criteria.getimportedAt(), ImportHistory_.importedAt));
          //  }
            if (criteria.getimportedBy()!= null) {
                specification = specification.and(buildStringSpecification(criteria.getimportedBy(), ImportHistory_.importedby));
            }
            if (criteria.getnupcoCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnupcoCode(), ImportHistory_.nupcoCode));
            }
            if (criteria.getStatus()!= null) {
                specification = specification.and(buildStringSpecification(criteria.getStatus(), ImportHistory_.status));
            }
            if (criteria.getresult() != null) {
                specification = specification.and(buildStringSpecification(criteria.getresult(), ImportHistory_.result));
            }
            if (criteria.getmessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getmessage(), ImportHistory_.message));
            }
        
        }
        return specification;
    }
}
