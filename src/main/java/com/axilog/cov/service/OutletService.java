package com.axilog.cov.service;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

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
    
    /**
     * @param outletRegion
     * @return
     */
    List<Outlet> findByOutletRegion(String outletRegion);
    
    /**
     * @param outletRegion
     * @return
     */
    List<Outlet> findAll();
    
    
    Optional<Outlet> findByExample(Example<Outlet> outletExample);
    
    /**
     * @param outlet bby ParentRegion
     * @return
     */
    List<Outlet> findOutletParentRegion(String outletParentRegion);
    
}
