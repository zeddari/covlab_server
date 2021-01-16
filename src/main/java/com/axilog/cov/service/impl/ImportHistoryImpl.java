package com.axilog.cov.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.repository.ImportHistoryRepository;
import com.axilog.cov.service.ImportHistoryService;

@Service
public class ImportHistoryImpl implements ImportHistoryService {

	@Autowired
	private ImportHistoryRepository importHistoryRepository;
	
	@Override
	public ImportHistory save(ImportHistory importHistory) {
		return importHistoryRepository.save(importHistory);
	}

}
