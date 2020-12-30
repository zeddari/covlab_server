package com.axilog.cov.repository;

import java.io.Serializable;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.axilog.cov.domain.Sequence;

public interface SequenceRepository extends JpaRepository<Sequence, Serializable> {

	@Query("from Sequence where id=1 ")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Sequence curVal();
	
}
