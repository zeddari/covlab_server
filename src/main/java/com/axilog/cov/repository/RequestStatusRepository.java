package com.axilog.cov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.RequestStatus;

/**
 * Spring Data  repository for the RequestStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long>, JpaSpecificationExecutor<RequestStatus> {
}
