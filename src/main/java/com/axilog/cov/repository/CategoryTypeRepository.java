package com.axilog.cov.repository;

import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Category entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long>, JpaSpecificationExecutor<Category> {
}
