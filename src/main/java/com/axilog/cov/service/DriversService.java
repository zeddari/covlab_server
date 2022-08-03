package com.axilog.cov.service;

import com.axilog.cov.domain.Drivers;

import com.axilog.cov.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Drivers}.
 */
public interface DriversService {

    /**
     * Save a drivers.
     *
     * @param tickets the entity to save.
     * @return the persisted entity.
     */
    Drivers save(Drivers drivers);

    /**
     * Get all the tickets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Drivers> findAll(Pageable pageable);


    /**
     * @return
     */
    List<User> findAll();
    /**
     * Get the "id" tickets.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Drivers> findOne(Long id);

    /**
     * Delete the "id" tickets.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
