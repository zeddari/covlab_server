package com.axilog.cov.service.impl;

import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.repository.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link PurchaseOrder}.
 */
@Service
@Transactional
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    @Override
    public PurchaseOrder save(PurchaseOrder purchaseOrder) {
        log.debug("Request to save PurchaseOrder : {}", purchaseOrder);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrder> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrderRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrder> findOne(Long id) {
        log.debug("Request to get PurchaseOrder : {}", id);
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.deleteById(id);
    }

	@Override
	public List<PurchaseOrder> findAll() {
		return purchaseOrderRepository.findAll();
	}

	@Override
	public PurchaseOrder findByOrderNo(String orderNo) {
		List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findByOrderNo(new Long(orderNo));
		if (Optional.ofNullable(purchaseOrders).isPresent() && purchaseOrders.size() > 0) {
			return purchaseOrders.get(0);
		}
		return null;
	}
}
