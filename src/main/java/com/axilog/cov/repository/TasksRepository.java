package com.axilog.cov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.axilog.cov.domain.Tasks;

/**
 * Spring Data  repository for the Tasks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
}
