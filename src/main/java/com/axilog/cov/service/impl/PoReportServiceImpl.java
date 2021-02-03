package com.axilog.cov.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.axilog.cov.domain.PoReport;
import com.axilog.cov.repository.PoReportRepository;
import com.axilog.cov.service.PoReportService;

@Service
public class PoReportServiceImpl implements PoReportService {

	@Autowired
	PoReportRepository poReportRepository;
	
	@Override
	public PoReport save(PoReport poReport) {
		return poReportRepository.save(poReport);
	}

	@Override
	public Page<PoReport> findAll(Pageable pageable) {
		return poReportRepository.findAll(pageable);
	}

	@Override
	public Optional<PoReport> findOne(Long id) {
		return poReportRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		poReportRepository.deleteById(id);
	}

}
