package com.axilog.cov.service.impl;

import com.axilog.cov.service.StatusPOService;
import com.axilog.cov.domain.StatusPO;
import com.axilog.cov.repository.StatusPORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link StatusPO}.
 */
@Service
@Transactional
public class StatusPOServiceImpl implements StatusPOService {

    private final Logger log = LoggerFactory.getLogger(StatusPOServiceImpl.class);

    private final StatusPORepository statusPORepository;

    public StatusPOServiceImpl(StatusPORepository statusPORepository) {
        this.statusPORepository = statusPORepository;
    }

    @Override
    public StatusPO save(StatusPO statusPO) {
        log.debug("Request to save StatusPO : {}", statusPO);
        return statusPORepository.save(statusPO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StatusPO> findAll(Pageable pageable) {
        log.debug("Request to get all StatusPOS");
        return statusPORepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<StatusPO> findOne(Long id) {
        log.debug("Request to get StatusPO : {}", id);
        return statusPORepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete StatusPO : {}", id);
        statusPORepository.deleteById(id);
    }
}
