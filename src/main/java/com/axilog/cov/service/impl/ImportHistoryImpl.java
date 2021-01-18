package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.repository.ImportHistoryRepository;
import com.axilog.cov.service.ImportHistoryService;

@Service
@Transactional
public class ImportHistoryImpl implements ImportHistoryService {

    private final Logger log = LoggerFactory.getLogger(ImportHistoryImpl.class);

	@Autowired
	private ImportHistoryRepository importHistoryRepository;
	
	@Override
	public ImportHistory save(ImportHistory importHistory) {
		return importHistoryRepository.save(importHistory);
	}
	
    @Override
    @Transactional(readOnly = true)
    public Page<ImportHistory> findAll(Pageable pageable) {
        log.debug("Request to get all ImportHistory");
        return importHistoryRepository.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Optional<ImportHistory> findOne(Integer id) {
        log.debug("Request to get Category : {}", id);
        return importHistoryRepository.findById(id);
    }

	@Override
	public List<ImportHistory> findAll() {
		return importHistoryRepository.findAll();
	}

}
