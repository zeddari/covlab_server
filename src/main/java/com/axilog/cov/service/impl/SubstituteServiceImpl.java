package com.axilog.cov.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.Substitute;
import com.axilog.cov.repository.SubstituteRepository;
import com.axilog.cov.service.SubstituteService;

public class SubstituteServiceImpl implements SubstituteService {

	@Autowired
	private SubstituteRepository substituteRepository;
	
	@Override
	public Substitute save(Substitute substitute) {
		return substituteRepository.save(substitute);
	}

	@Override
	public Page<Substitute> findAll(Pageable pageable) {
		return substituteRepository.findAll(pageable);
	}

	@Override
	public List<Substitute> findAll() {
		return substituteRepository.findAll();
	}

	@Override
	public Optional<Substitute> findOne(Long id) {
		return substituteRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		substituteRepository.deleteById(id);
	}

}
