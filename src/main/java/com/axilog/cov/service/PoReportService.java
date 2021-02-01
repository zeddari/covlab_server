package com.axilog.cov.service;

import com.axilog.cov.domain.PoReport;
import com.axilog.cov.domain.PoStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PoStatus}.
 */
public interface PoReportService {

    /**
     * Save a PoReport.
     *
     * @param PoReport the entity to save.
     * @return the persisted entity.
     */
    PoReport save(PoReport poReport);

    /**
     * Get all the PoReport.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PoReport> findAll(Pageable pageable);


    /**
     * Get the "id" poStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PoReport> findOne(Long id);

    /**
     * Delete the "id" PoReport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
