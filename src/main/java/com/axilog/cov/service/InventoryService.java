package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.Inventory;

/**
 * Service Interface for managing {@link Inventory}.
 */
public interface InventoryService {

    /**
     * Save a inventory.
     *
     * @param inventory the entity to save.
     * @return the persisted entity.
     */
    Inventory save(Inventory inventory);

    /**
     * Get all the inventories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Inventory> findAll(Pageable pageable);

    /**
     * @return
     */
    List<Inventory> findAll();
    
    /**
     * find by a list of status
     * @param status
     * @return
     */
    List<Inventory> findByStatusIn(List<String> status);

    /**
     * Get the "id" inventory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Inventory> findOne(Long id);

    /**
     * Delete the "id" inventory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @return
     */
    public List<String> getOutletWithTemperatureIssue();
}
