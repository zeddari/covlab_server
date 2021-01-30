package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.Substitute;

/**
 * Service Interface for managing {@link Category}.
 */
/**
 * @author lenovo
 *
 */
public interface SubstituteService {


    /**
     * @param category
     * @return
     */
    Substitute save(Substitute category);

    /**
     * Get all the Substitutes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Substitute> findAll(Pageable pageable);
    
    List<Substitute> findAll();


    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Substitute> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
