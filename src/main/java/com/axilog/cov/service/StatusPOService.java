package com.axilog.cov.service;

import com.axilog.cov.domain.StatusPO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link StatusPO}.
 */
public interface StatusPOService {

    /**
     * Save a statusPO.
     *
     * @param statusPO the entity to save.
     * @return the persisted entity.
     */
    StatusPO save(StatusPO statusPO);

    /**
     * Get all the statusPOS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<StatusPO> findAll(Pageable pageable);


    /**
     * Get the "id" statusPO.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatusPO> findOne(Long id);

    /**
     * Delete the "id" statusPO.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
