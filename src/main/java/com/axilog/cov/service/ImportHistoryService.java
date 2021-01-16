package com.axilog.cov.service;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.ImportHistory;

/**
 * Service Interface for managing {@link Category}.
 */
public interface ImportHistoryService {

	public ImportHistory save(ImportHistory importHistory);
}
