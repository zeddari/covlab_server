package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.service.InventoryService;

/**
 * Service Implementation for managing {@link Inventory}.
 */
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final Logger log = LoggerFactory.getLogger(InventoryServiceImpl.class);

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        log.debug("Request to save Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inventory> findAll(Pageable pageable) {
        log.debug("Request to get all Inventories");
        return inventoryRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Inventory> findOne(Long id) {
        log.debug("Request to get Inventory : {}", id);
        return inventoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inventory : {}", id);
        inventoryRepository.deleteById(id);
    }

	@Override
	public List<Inventory> findAll() {
		return inventoryRepository.findAll();
	}

	@Override
	public List<String> getOutletWithTemperatureIssue() {
		return null;
	}

	@Override
	public List<Inventory> findByStatusIn(List<String> status) {
		return inventoryRepository.findByStatusIn(status);
		
	}

	@Override
	public List<Inventory> findByStatusInAndIsLastInstance(List<String> status, Boolean isLastInstance) {
		return inventoryRepository.findByStatusInAndIsLastInstance(status, isLastInstance);
	}

	@Override
	public List<Inventory> findByStatusInAndIsLastInstanceAndCapacityLessThan(List<String> status,
			Boolean isLastInstance, Double capapcity) {
		return inventoryRepository.findByStatusInAndIsLastInstanceAndCapacityLessThan(status, isLastInstance, capapcity);
	}

	@Override
	public Optional<Inventory> findByExample(Example<Inventory> inventoryExample) {
		return inventoryRepository.findOne(inventoryExample);
	}
	
	@Override
	public List<Inventory> findByOutletAndProductAndIsLastInstance(Outlet outlet, Product product, Boolean isLastInstance) {
		return inventoryRepository.findByOutletAndProductAndIsLastInstance(outlet, product, isLastInstance);
	}

	@Override
	public List<Inventory> findByStatusInAndIsLastInstanceAndOutlet(List<String> status, Boolean isLastInstance, Outlet outlet) {
		return inventoryRepository.findByStatusInAndIsLastInstanceAndOutlet(status, isLastInstance, outlet);
	}

}
