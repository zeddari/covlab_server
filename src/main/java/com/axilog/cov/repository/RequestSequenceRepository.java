package com.axilog.cov.repository;

import com.axilog.cov.domain.RequestSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.io.Serializable;

@Repository
public interface RequestSequenceRepository extends JpaRepository<RequestSequence, Serializable> {

	@Query("from RequestSequence where id=1 ")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
    RequestSequence curVal();

}
