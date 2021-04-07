package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.PurchaseOrderHistory;
import com.axilog.cov.repository.PurchaseOrderHistoryRepository;
import com.axilog.cov.service.PurchaseOrderHistoryService;

/**
 * Service Implementation for managing {@link PurchaseOrder}.
 */
@Service
@Transactional
public class PurchaseOrderHistoryServiceImpl implements PurchaseOrderHistoryService {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderHistoryServiceImpl.class);

    @Autowired
    private PurchaseOrderHistoryRepository purchaseOrderRepository;
    

    @Override
    public PurchaseOrderHistory save(PurchaseOrderHistory purchaseOrderHistory) {
        log.debug("Request to save PurchaseOrder : {}", purchaseOrderHistory);
        return purchaseOrderRepository.save(purchaseOrderHistory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderHistory> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseOrders");
        return purchaseOrderRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseOrderHistory> findOne(Long id) {
        log.debug("Request to get PurchaseOrder : {}", id);
        return purchaseOrderRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseOrder : {}", id);
        purchaseOrderRepository.deleteById(id);
    }

	@Override
	public List<PurchaseOrderHistory> findAll() {
		return purchaseOrderRepository.findAll();
	}

	@Override
	public PurchaseOrderHistory findByOrderNo(String orderNo) {
		List<PurchaseOrderHistory> purchaseOrders = purchaseOrderRepository.findByOrderNo(new Long(orderNo));
		if (Optional.ofNullable(purchaseOrders).isPresent() && purchaseOrders.size() > 0) {
			return purchaseOrders.get(0);
		}
		return null;
	}


	@Override
	public Optional<PurchaseOrderHistory> findOne(Example<PurchaseOrderHistory> example) {
		return purchaseOrderRepository.findOne(example);
	}

}
	
