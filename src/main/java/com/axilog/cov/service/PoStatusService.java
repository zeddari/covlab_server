package com.axilog.cov.service;

import com.axilog.cov.domain.PoStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PoStatus}.
 */
public interface PoStatusService {

    /**
     * Save a poStatus.
     *
     * @param poStatus the entity to save.
     * @return the persisted entity.
     */
    PoStatus save(PoStatus poStatus);

    /**
     * Get all the poStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PoStatus> findAll(Pageable pageable);


    /**
     * Get the "id" poStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PoStatus> findOne(Long id);

    /**
     * Delete the "id" poStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
