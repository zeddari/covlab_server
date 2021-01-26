package com.axilog.cov.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.axilog.cov.domain.GrnHistSequence;

public interface GrnHistSequenceRepository extends JpaRepository<GrnHistSequence, Serializable> {

	@Query("from GrnHistSequence where id=1")
	GrnHistSequence curVal();
	
}
