package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.GrnHistory;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.dto.representation.PoUpdateRepresentation;

/**
 * Service Interface for managing {@link PurchaseOrder}.
 */
public interface PurchaseOrderService {

    /**
     * Save a purchaseOrder.
     *
     * @param purchaseOrder the entity to save.
     * @return the persisted entity.
     */
    PurchaseOrder save(PurchaseOrder purchaseOrder);

    /**
     * Get all the purchaseOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PurchaseOrder> findAll(Pageable pageable);

    /**
     * @return
     */
    List<PurchaseOrder> findAll();

    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrder> findOne(Long id);

    /**
     * Get the "id" purchaseOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PurchaseOrder> findOne(Example<PurchaseOrder> example);
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
    PurchaseOrder findByOrderNo(String orderNo);
    
    PoUpdateRepresentation savepo(PoUpdateRepresentation poUpdateRepresentation);
    
    
    /**
     * @return
     */
    List<GrnHistory> findAllGrn();
}
