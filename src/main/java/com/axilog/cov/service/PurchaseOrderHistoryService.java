package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.PurchaseOrderHistory;

/**
 * Service Interface for managing {@link PurchaseOrder}.
 */
public interface PurchaseOrderHistoryService {

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrder the entity to save.
     * @return the persisted entity.
     */
	PurchaseOrderHistory save(PurchaseOrderHistory purchaseOrderHistory);

    /**
     * Get all the purchaseOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseOrderHistory> findAll(Pageable pageable);

    /**
     * @return
     */
    List<PurchaseOrderHistory> findAll();

    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrderHistory> findOne(Long id);

    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrderHistory> findOne(Example<PurchaseOrderHistory> example);
    /**
     * Delete the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * @param orderNo
     * @return
     */
    PurchaseOrderHistory findByOrderNo(String orderNo);
    
}