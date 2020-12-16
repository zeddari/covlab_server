package com.axilog.cov.service;

import com.axilog.cov.domain.Outlet;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Outlet}.
 */
public interface OutletService {
    /**
     * Save a outlet.
     *
     * @param outlet the entity to save.
     * @return the persisted entity.
     */
    Outlet save(Outlet outlet);

    /**
     * Get all the outlets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Outlet> findAll(Pageable pageable);

    /**
     * Get the "id" outlet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Outlet> findOne(Long id);

    /**
     * Delete the "id" outlet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
