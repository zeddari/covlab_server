package com.axilog.cov.service.impl;

import com.axilog.cov.service.RequestQuotationService;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.RequestQuotation;
import com.axilog.cov.repository.RequestQuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link RequestQuotation}.
 */
@Service
@Transactional
public class RequestQuotationServiceImpl implements RequestQuotationService {

    private final Logger log = LoggerFactory.getLogger(RequestQuotationServiceImpl.class);

    private final RequestQuotationRepository requestQuotationRepository;

    public RequestQuotationServiceImpl(RequestQuotationRepository requestQuotationRepository) {
        this.requestQuotationRepository = requestQuotationRepository;
    }

    @Override
    public RequestQuotation save(RequestQuotation requestQuotation) {
        log.debug("Request to save RequestQuotation : {}", requestQuotation);
        return requestQuotationRepository.save(requestQuotation);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<RequestQuotation> findAll(Pageable pageable) {
        log.debug("Request to get all RequestQuotations");
        return requestQuotationRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RequestQuotation> findOne(Long id) {
        log.debug("Request to get RequestQuotation : {}", id);
        return requestQuotationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RequestQuotation : {}", id);
        requestQuotationRepository.deleteById(id);
    }


	@Override
	public List<RequestQuotation> findAll() {
		return requestQuotationRepository.findAll();
	}

	@Override
	public Optional<RequestQuotation> findOne(Example<RequestQuotation> exampleRequestQuotation) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public List<RequestQuotation> findByRequestQuotationId(Long requestQuotationId) {
		return requestQuotationRepository.findByRequestQuotationId(requestQuotationId);

	}

}
