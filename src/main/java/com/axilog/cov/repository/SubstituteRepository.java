package com.axilog.cov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Substitute;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SubstituteRepository extends JpaRepository<Substitute, Long>, JpaSpecificationExecutor<Substitute> {
}
