package com.axilog.cov.service;

import com.axilog.cov.domain.Tickets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Tickets}.
 */
public interface TicketsService {

    /**
     * Save a tickets.
     *
     * @param tickets the entity to save.
     * @return the persisted entity.
     */
    Tickets save(Tickets tickets);

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Tickets> findAll(Pageable pageable);


    /**
     * Get the "id" tickets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Tickets> findOne(Long id);

    /**
     * Delete the "id" tickets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
