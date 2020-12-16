package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.CategoryRepository;
import com.axilog.cov.service.CategoryService;
import com.axilog.cov.service.dto.CategoryCriteria;
import com.axilog.cov.service.CategoryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CategoryResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CategoryResourceIT {

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;
    private static final Long SMALLER_CATEGORY_ID = 1L - 1L;

    private static final String DEFAULT_DESCRIPTION_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryQueryService categoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoryMockMvc;

    private Category category;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createEntity(EntityManager em) {
        Category category = new Category()
            .categoryId(DEFAULT_CATEGORY_ID)
            .descriptionCategory(DEFAULT_DESCRIPTION_CATEGORY);
        return category;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Category createUpdatedEntity(EntityManager em) {
        Category category = new Category()
            .categoryId(UPDATED_CATEGORY_ID)
            .descriptionCategory(UPDATED_DESCRIPTION_CATEGORY);
        return category;
    }

    @BeforeEach
    public void initTest() {
        category = createEntity(em);
    }

    @Test
    @Transactional
    public void createCategory() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();
        // Create the Category
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isCreated());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate + 1);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testCategory.getDescriptionCategory()).isEqualTo(DEFAULT_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void createCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = categoryRepository.findAll().size();

        // Create the Category with an existing ID
        category.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoryMockMvc.perform(post("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCategories() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].descriptionCategory").value(hasItem(DEFAULT_DESCRIPTION_CATEGORY)));
    }
    
    @Test
    @Transactional
    public void getCategory() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", category.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(category.getId().intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.descriptionCategory").value(DEFAULT_DESCRIPTION_CATEGORY));
    }


    @Test
    @Transactional
    public void getCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        Long id = category.getId();

        defaultCategoryShouldBeFound("id.equals=" + id);
        defaultCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId equals to DEFAULT_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.equals=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId equals to UPDATED_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.equals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId not equals to DEFAULT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.notEquals=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId not equals to UPDATED_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.notEquals=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId in DEFAULT_CATEGORY_ID or UPDATED_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.in=" + DEFAULT_CATEGORY_ID + "," + UPDATED_CATEGORY_ID);

        // Get all the categoryList where categoryId equals to UPDATED_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.in=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId is not null
        defaultCategoryShouldBeFound("categoryId.specified=true");

        // Get all the categoryList where categoryId is null
        defaultCategoryShouldNotBeFound("categoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId is greater than or equal to DEFAULT_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.greaterThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId is greater than or equal to UPDATED_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.greaterThanOrEqual=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId is less than or equal to DEFAULT_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.lessThanOrEqual=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId is less than or equal to SMALLER_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.lessThanOrEqual=" + SMALLER_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId is less than DEFAULT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.lessThan=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId is less than UPDATED_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.lessThan=" + UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryId is greater than DEFAULT_CATEGORY_ID
        defaultCategoryShouldNotBeFound("categoryId.greaterThan=" + DEFAULT_CATEGORY_ID);

        // Get all the categoryList where categoryId is greater than SMALLER_CATEGORY_ID
        defaultCategoryShouldBeFound("categoryId.greaterThan=" + SMALLER_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory equals to DEFAULT_DESCRIPTION_CATEGORY
        defaultCategoryShouldBeFound("descriptionCategory.equals=" + DEFAULT_DESCRIPTION_CATEGORY);

        // Get all the categoryList where descriptionCategory equals to UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldNotBeFound("descriptionCategory.equals=" + UPDATED_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory not equals to DEFAULT_DESCRIPTION_CATEGORY
        defaultCategoryShouldNotBeFound("descriptionCategory.notEquals=" + DEFAULT_DESCRIPTION_CATEGORY);

        // Get all the categoryList where descriptionCategory not equals to UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldBeFound("descriptionCategory.notEquals=" + UPDATED_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory in DEFAULT_DESCRIPTION_CATEGORY or UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldBeFound("descriptionCategory.in=" + DEFAULT_DESCRIPTION_CATEGORY + "," + UPDATED_DESCRIPTION_CATEGORY);

        // Get all the categoryList where descriptionCategory equals to UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldNotBeFound("descriptionCategory.in=" + UPDATED_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory is not null
        defaultCategoryShouldBeFound("descriptionCategory.specified=true");

        // Get all the categoryList where descriptionCategory is null
        defaultCategoryShouldNotBeFound("descriptionCategory.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory contains DEFAULT_DESCRIPTION_CATEGORY
        defaultCategoryShouldBeFound("descriptionCategory.contains=" + DEFAULT_DESCRIPTION_CATEGORY);

        // Get all the categoryList where descriptionCategory contains UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldNotBeFound("descriptionCategory.contains=" + UPDATED_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllCategoriesByDescriptionCategoryNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where descriptionCategory does not contain DEFAULT_DESCRIPTION_CATEGORY
        defaultCategoryShouldNotBeFound("descriptionCategory.doesNotContain=" + DEFAULT_DESCRIPTION_CATEGORY);

        // Get all the categoryList where descriptionCategory does not contain UPDATED_DESCRIPTION_CATEGORY
        defaultCategoryShouldBeFound("descriptionCategory.doesNotContain=" + UPDATED_DESCRIPTION_CATEGORY);
    }


    @Test
    @Transactional
    public void getAllCategoriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        category.addProduct(product);
        categoryRepository.saveAndFlush(category);
        Long productId = product.getId();

        // Get all the categoryList where product equals to productId
        defaultCategoryShouldBeFound("productId.equals=" + productId);

        // Get all the categoryList where product equals to productId + 1
        defaultCategoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCategoryShouldBeFound(String filter) throws Exception {
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(category.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].descriptionCategory").value(hasItem(DEFAULT_DESCRIPTION_CATEGORY)));

        // Check, that the count call also returns 1
        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCategoryShouldNotBeFound(String filter) throws Exception {
        restCategoryMockMvc.perform(get("/api/categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCategoryMockMvc.perform(get("/api/categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCategory() throws Exception {
        // Get the category
        restCategoryMockMvc.perform(get("/api/categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // Update the category
        Category updatedCategory = categoryRepository.findById(category.getId()).get();
        // Disconnect from session so that the updates on updatedCategory are not directly saved in db
        em.detach(updatedCategory);
        updatedCategory
            .categoryId(UPDATED_CATEGORY_ID)
            .descriptionCategory(UPDATED_DESCRIPTION_CATEGORY);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategory)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategory.getDescriptionCategory()).isEqualTo(UPDATED_DESCRIPTION_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingCategory() throws Exception {
        int databaseSizeBeforeUpdate = categoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(category)))
            .andExpect(status().isBadRequest());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // Initialize the database
        categoryService.save(category);

        int databaseSizeBeforeDelete = categoryRepository.findAll().size();

        // Delete the category
        restCategoryMockMvc.perform(delete("/api/categories/{id}", category.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
