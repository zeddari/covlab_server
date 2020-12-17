package com.axilog.cov.service.impl;

import com.axilog.cov.service.PoStatusService;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.repository.PoStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PoStatus}.
 */
@Service
@Transactional
public class PoStatusServiceImpl implements PoStatusService {

    private final Logger log = LoggerFactory.getLogger(PoStatusServiceImpl.class);

    private final PoStatusRepository poStatusRepository;

    public PoStatusServiceImpl(PoStatusRepository poStatusRepository) {
        this.poStatusRepository = poStatusRepository;
    }

    @Override
    public PoStatus save(PoStatus poStatus) {
        log.debug("Request to save PoStatus : {}", poStatus);
        return poStatusRepository.save(poStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PoStatus> findAll(Pageable pageable) {
        log.debug("Request to get all PoStatuses");
        return poStatusRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PoStatus> findOne(Long id) {
        log.debug("Request to get PoStatus : {}", id);
        return poStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PoStatus : {}", id);
        poStatusRepository.deleteById(id);
    }
}
