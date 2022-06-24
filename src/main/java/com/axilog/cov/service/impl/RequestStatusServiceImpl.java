package com.axilog.cov.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.RequestStatus;
import com.axilog.cov.repository.RequestStatusRepository;
import com.axilog.cov.repository.search.RequestStatusSearchRepository;
import com.axilog.cov.service.RequestStatusService;

/**
 * Service Implementation for managing {@link RequestStatus}.
 */
@Service
@Transactional
public class RequestStatusServiceImpl implements RequestStatusService {

    private final Logger log = LoggerFactory.getLogger(RequestStatusServiceImpl.class);

    private final RequestStatusRepository requestStatusRepository;

    private final RequestStatusSearchRepository requestStatusSearchRepository;

    public RequestStatusServiceImpl(RequestStatusRepository requestStatusRepository, RequestStatusSearchRepository requestStatusSearchRepository) {
        this.requestStatusRepository = requestStatusRepository;
        this.requestStatusSearchRepository = requestStatusSearchRepository;
    }

    @Override
    public RequestStatus save(RequestStatus requestStatus) {
        log.debug("Request to save RequestStatus : {}", requestStatus);
        RequestStatus result = requestStatusRepository.save(requestStatus);
        requestStatusSearchRepository.save(result);
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
    public void delete(Long id) {
        log.debug("Request to delete RequestStatus : {}", id);
        requestStatusRepository.deleteById(id);
        requestStatusSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RequestStatus> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RequestStatuses for query {}", query);
        return requestStatusSearchRepository.search(queryStringQuery(query), pageable);    }
}
