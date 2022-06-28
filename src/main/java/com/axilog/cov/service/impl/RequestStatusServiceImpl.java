package com.axilog.cov.service.impl;

import com.axilog.cov.domain.RequestStatus;
import com.axilog.cov.repository.RequestStatusRepository;
import com.axilog.cov.service.RequestStatusService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RequestStatus}.
 */
@Service
@Transactional
public class RequestStatusServiceImpl implements RequestStatusService {
    private final Logger log = LoggerFactory.getLogger(RequestStatusServiceImpl.class);

    private final RequestStatusRepository requestStatusRepository;

    public RequestStatusServiceImpl(RequestStatusRepository requestStatusRepository) {
        this.requestStatusRepository = requestStatusRepository;
    }

    @Override
    public RequestStatus save(RequestStatus requestStatus) {
        log.debug("Request to save RequestStatus : {}", requestStatus);
        RequestStatus result = requestStatusRepository.save(requestStatus);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestStatus> findAll(Pageable pageable) {
        log.debug("Request to get all RequestStatuses");
        return requestStatusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestStatus> findOne(Long id) {
        log.debug("Request to get RequestStatus : {}", id);
        return requestStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {}

    @Override
    public Page<RequestStatus> search(String query, Pageable pageable) {
        return null;
    }
}
