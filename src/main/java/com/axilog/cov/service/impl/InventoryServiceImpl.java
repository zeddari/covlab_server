package com.axilog.cov.service.impl;

import com.axilog.cov.service.InventoryService;

import com.axilog.cov.domain.Inventory;
import com.axilog.cov.dto.projection.ServiceDashProjection;
import com.axilog.cov.dto.representation.ServiceDashRep;
import com.axilog.cov.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	public List<ServiceDashRep> getQuantitiesHandByCategory() {
	
		List<ServiceDashProjection> list = inventoryRepository.findHandByCategorys();
		List<ServiceDashRep> data = new ArrayList();
		if(list!=null && !list.isEmpty()) {
			for(ServiceDashProjection el : list) {
				ServiceDashRep a =	ServiceDashRep.builder().code_categorie(el.getCategory()).total_quantities_inHand(el.getQuantitiesCategory()).build();
				data.add(a);
				log.info(data + "");
			}
			return data;
		}
		
		data.add(ServiceDashRep.builder().build());
		return data;
	}
	public List<ServiceDashRep> getQuantitiesHandByLocation() {
		
		List<ServiceDashProjection> list = inventoryRepository.findHandByLocation();
		List<ServiceDashRep> data = new ArrayList();
		if(list!=null && !list.isEmpty()) {
			for(ServiceDashProjection el : list) {
				ServiceDashRep a =	ServiceDashRep.builder().outlet_name(el.getLocation()).total_quantities_inHand(el.getQuantitiesLocation()).build();
				data.add(a);
				log.info(data + "");
			}
			return data;
		}
		
		data.add(ServiceDashRep.builder().build());
		return data;
	}
}
