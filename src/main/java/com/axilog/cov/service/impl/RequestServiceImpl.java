package com.axilog.cov.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.Request;
import com.axilog.cov.repository.RequestRepository;
import com.axilog.cov.repository.search.RequestSearchRepository;
import com.axilog.cov.service.RequestService;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestSearchRepository requestSearchRepository;

    public RequestServiceImpl(RequestRepository requestRepository, RequestSearchRepository requestSearchRepository) {
        this.requestRepository = requestRepository;
        this.requestSearchRepository = requestSearchRepository;
    }

    @Override
    public Request save(Request request) {
        log.debug("Request to save Request : {}", request);
        Request result = requestRepository.save(request);
        requestSearchRepository.save(result);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Request> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        return requestRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Request> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
        requestSearchRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Request> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Requests for query {}", query);
        return requestSearchRepository.search(queryStringQuery(query), pageable);    }
}
