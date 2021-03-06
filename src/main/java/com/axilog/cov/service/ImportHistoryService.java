package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.ImportHistory;
import com.axilog.cov.dto.representation.ImportHistoryDetail;

/**
 * Service Interface for managing {@link Category}.
 */
public interface ImportHistoryService {

	public ImportHistory save(ImportHistory importHistory);


	Page<ImportHistory> findAll(Pageable pageable);
	
	List<ImportHistoryDetail> findAll();

	Optional<ImportHistory> findOne(Integer id);
}
