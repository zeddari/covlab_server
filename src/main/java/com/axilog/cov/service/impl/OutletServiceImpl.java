package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Outlet;
import com.axilog.cov.repository.OutletRepository;
import com.axilog.cov.service.OutletService;

/**
 * Service Implementation for managing {@link Outlet}.
 */
@Service
@Transactional
public class OutletServiceImpl implements OutletService {

    private final Logger log = LoggerFactory.getLogger(OutletServiceImpl.class);

    private final OutletRepository outletRepository;

    public OutletServiceImpl(OutletRepository outletRepository) {
        this.outletRepository = outletRepository;
    }

    @Override
    public Outlet save(Outlet outlet) {
        log.debug("Request to save Outlet : {}", outlet);
        return outletRepository.save(outlet);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Outlet> findAll(Pageable pageable) {
        log.debug("Request to get all Outlets");
        return outletRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Outlet> findOne(Long id) {
        log.debug("Request to get Outlet : {}", id);
        return outletRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Outlet : {}", id);
        outletRepository.deleteById(id);
    }

	@Override
	public List<Outlet> findByOutletRegion(String outletRegion) {
		return outletRepository.findByOutletRegion(outletRegion);
	}

	@Override
	public List<Outlet> findAll() {
		return outletRepository.findAll();
	}
}
