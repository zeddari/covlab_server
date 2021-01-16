package com.axilog.cov.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Category;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.CategoryRepository;
import com.axilog.cov.service.CategoryQueryService;
import com.axilog.cov.service.CategoryService;

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

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

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
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .categoryDescription(DEFAULT_CATEGORY_DESCRIPTION);
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
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION);
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
        assertThat(testCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(DEFAULT_CATEGORY_DESCRIPTION);
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
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)));
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
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE))
            .andExpect(jsonPath("$.categoryDescription").value(DEFAULT_CATEGORY_DESCRIPTION));
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
    public void getAllCategoriesByCategoryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode equals to DEFAULT_CATEGORY_CODE
        defaultCategoryShouldBeFound("categoryCode.equals=" + DEFAULT_CATEGORY_CODE);

        // Get all the categoryList where categoryCode equals to UPDATED_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("categoryCode.equals=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode not equals to DEFAULT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("categoryCode.notEquals=" + DEFAULT_CATEGORY_CODE);

        // Get all the categoryList where categoryCode not equals to UPDATED_CATEGORY_CODE
        defaultCategoryShouldBeFound("categoryCode.notEquals=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode in DEFAULT_CATEGORY_CODE or UPDATED_CATEGORY_CODE
        defaultCategoryShouldBeFound("categoryCode.in=" + DEFAULT_CATEGORY_CODE + "," + UPDATED_CATEGORY_CODE);

        // Get all the categoryList where categoryCode equals to UPDATED_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("categoryCode.in=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode is not null
        defaultCategoryShouldBeFound("categoryCode.specified=true");

        // Get all the categoryList where categoryCode is null
        defaultCategoryShouldNotBeFound("categoryCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByCategoryCodeContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode contains DEFAULT_CATEGORY_CODE
        defaultCategoryShouldBeFound("categoryCode.contains=" + DEFAULT_CATEGORY_CODE);

        // Get all the categoryList where categoryCode contains UPDATED_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("categoryCode.contains=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryCodeNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryCode does not contain DEFAULT_CATEGORY_CODE
        defaultCategoryShouldNotBeFound("categoryCode.doesNotContain=" + DEFAULT_CATEGORY_CODE);

        // Get all the categoryList where categoryCode does not contain UPDATED_CATEGORY_CODE
        defaultCategoryShouldBeFound("categoryCode.doesNotContain=" + UPDATED_CATEGORY_CODE);
    }


    @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription equals to DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.equals=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.equals=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription not equals to DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.notEquals=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription not equals to UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.notEquals=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription in DEFAULT_CATEGORY_DESCRIPTION or UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.in=" + DEFAULT_CATEGORY_DESCRIPTION + "," + UPDATED_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.in=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription is not null
        defaultCategoryShouldBeFound("categoryDescription.specified=true");

        // Get all the categoryList where categoryDescription is null
        defaultCategoryShouldNotBeFound("categoryDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription contains DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.contains=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription contains UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.contains=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCategoriesByCategoryDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);

        // Get all the categoryList where categoryDescription does not contain DEFAULT_CATEGORY_DESCRIPTION
        defaultCategoryShouldNotBeFound("categoryDescription.doesNotContain=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the categoryList where categoryDescription does not contain UPDATED_CATEGORY_DESCRIPTION
        defaultCategoryShouldBeFound("categoryDescription.doesNotContain=" + UPDATED_CATEGORY_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCategoriesByProductsIsEqualToSomething() throws Exception {
        // Initialize the database
        categoryRepository.saveAndFlush(category);
        Product products = ProductResourceIT.createEntity(em);
        em.persist(products);
        em.flush();
        category.addProducts(products);
        categoryRepository.saveAndFlush(category);
        Long productsId = products.getId();

        // Get all the categoryList where products equals to productsId
        defaultCategoryShouldBeFound("productsId.equals=" + productsId);

        // Get all the categoryList where products equals to productsId + 1
        defaultCategoryShouldNotBeFound("productsId.equals=" + (productsId + 1));
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
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)));

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
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION);

        restCategoryMockMvc.perform(put("/api/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCategory)))
            .andExpect(status().isOk());

        // Validate the Category in the database
        List<Category> categoryList = categoryRepository.findAll();
        assertThat(categoryList).hasSize(databaseSizeBeforeUpdate);
        Category testCategory = categoryList.get(categoryList.size() - 1);
        assertThat(testCategory.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testCategory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
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
