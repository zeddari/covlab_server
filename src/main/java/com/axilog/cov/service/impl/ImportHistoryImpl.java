package com.axilog.cov.service.impl;

import java.util.ArrayList;
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
import com.axilog.cov.dto.projection.DashInventoryStockProjection;
import com.axilog.cov.dto.representation.ChartDetail;
import com.axilog.cov.dto.representation.ImportHistoryDetail;
import com.axilog.cov.dto.representation.SeriesDetail;
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
	public List<ImportHistoryDetail> findAll() {
		List<ImportHistoryDetail> importHistoryDetail = new ArrayList<>();
		List<ImportHistory> imports = importHistoryRepository.findAll();

		if (Optional.ofNullable(imports).isPresent()) {
			imports.forEach(inventory -> {
				importHistoryDetail.add(ImportHistoryDetail.builder()
						.fileName(inventory.getFileName())
						.jobId(inventory.getJobId())
						.outlet(inventory.getOutlet())
						.importedAt(inventory.getImportedAt())
						.imported_by(inventory.getImported_by())
						.status(inventory.getStatus())
						.message(inventory.getMessage())
						.nupcoCode(inventory.getNupcoCode())
						.result(inventory.getResult())
						.build());
				
			});
		
			}
		return importHistoryDetail;
		}
}
