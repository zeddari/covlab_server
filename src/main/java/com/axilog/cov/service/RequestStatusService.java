package com.axilog.cov.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.RequestStatus;

/**
 * Service Interface for managing {@link RequestStatus}.
 */
public interface RequestStatusService {

    /**
     * Save a requestStatus.
     *
     * @param requestStatus the entity to save.
     * @return the persisted entity.
     */
    RequestStatus save(RequestStatus requestStatus);

    /**
     * Get all the requestStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestStatus> findAll(Pageable pageable);


    /**
     * Get the "id" requestStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RequestStatus> findOne(Long id);

    /**
     * Delete the "id" requestStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the requestStatus corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RequestStatus> search(String query, Pageable pageable);
}
