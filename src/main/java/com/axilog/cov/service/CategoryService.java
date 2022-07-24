package com.axilog.cov.service;

import java.util.List;
import java.util.Optional;

import com.axilog.cov.domain.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.axilog.cov.domain.Category;

/**
 * Service Interface for managing {@link Category}.
 */
public interface CategoryService {

    /**
     * Save a category.
     *
     * @param category the entity to save.
     * @return the persisted entity.
     */
    Category save(Category category);

    /**
     * Get all the categories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Category> findAll(Pageable pageable);

    List<Category> findAll();


    /**
     * Get the "id" category.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Category> findOne(Long id);

    /**
     * Delete the "id" category.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CategoryType> findAllCategoriesType();
}
