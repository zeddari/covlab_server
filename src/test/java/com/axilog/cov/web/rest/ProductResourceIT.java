package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.Tickets;
import com.axilog.cov.domain.Category;
import com.axilog.cov.repository.ProductRepository;
import com.axilog.cov.service.ProductService;
import com.axilog.cov.service.dto.ProductCriteria;
import com.axilog.cov.service.ProductQueryService;

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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductResourceIT {

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;
    private static final Long SMALLER_PRODUCT_ID = 1L - 1L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .productId(DEFAULT_PRODUCT_ID)
            .description(DEFAULT_DESCRIPTION)
            .productCode(DEFAULT_PRODUCT_CODE)
            .temperature(DEFAULT_TEMPERATURE);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .productCode(UPDATED_PRODUCT_CODE)
            .temperature(UPDATED_TEMPERATURE);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE));
    }


    @Test
    @Transactional
    public void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsByProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId equals to DEFAULT_PRODUCT_ID
        defaultProductShouldBeFound("productId.equals=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId equals to UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.equals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId not equals to DEFAULT_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.notEquals=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId not equals to UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.notEquals=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId in DEFAULT_PRODUCT_ID or UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.in=" + DEFAULT_PRODUCT_ID + "," + UPDATED_PRODUCT_ID);

        // Get all the productList where productId equals to UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.in=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is not null
        defaultProductShouldBeFound("productId.specified=true");

        // Get all the productList where productId is null
        defaultProductShouldNotBeFound("productId.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is greater than or equal to DEFAULT_PRODUCT_ID
        defaultProductShouldBeFound("productId.greaterThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId is greater than or equal to UPDATED_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.greaterThanOrEqual=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is less than or equal to DEFAULT_PRODUCT_ID
        defaultProductShouldBeFound("productId.lessThanOrEqual=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId is less than or equal to SMALLER_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.lessThanOrEqual=" + SMALLER_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is less than DEFAULT_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.lessThan=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId is less than UPDATED_PRODUCT_ID
        defaultProductShouldBeFound("productId.lessThan=" + UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllProductsByProductIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productId is greater than DEFAULT_PRODUCT_ID
        defaultProductShouldNotBeFound("productId.greaterThan=" + DEFAULT_PRODUCT_ID);

        // Get all the productList where productId is greater than SMALLER_PRODUCT_ID
        defaultProductShouldBeFound("productId.greaterThan=" + SMALLER_PRODUCT_ID);
    }


    @Test
    @Transactional
    public void getAllProductsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description equals to DEFAULT_DESCRIPTION
        defaultProductShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description not equals to DEFAULT_DESCRIPTION
        defaultProductShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description not equals to UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description is not null
        defaultProductShouldBeFound("description.specified=true");

        // Get all the productList where description is null
        defaultProductShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description contains DEFAULT_DESCRIPTION
        defaultProductShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description contains UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description does not contain DEFAULT_DESCRIPTION
        defaultProductShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description does not contain UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductsByProductCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode equals to DEFAULT_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.equals=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode equals to UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.equals=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode not equals to DEFAULT_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.notEquals=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode not equals to UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.notEquals=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode in DEFAULT_PRODUCT_CODE or UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.in=" + DEFAULT_PRODUCT_CODE + "," + UPDATED_PRODUCT_CODE);

        // Get all the productList where productCode equals to UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.in=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode is not null
        defaultProductShouldBeFound("productCode.specified=true");

        // Get all the productList where productCode is null
        defaultProductShouldNotBeFound("productCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductCodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode contains DEFAULT_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.contains=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode contains UPDATED_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.contains=" + UPDATED_PRODUCT_CODE);
    }

    @Test
    @Transactional
    public void getAllProductsByProductCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where productCode does not contain DEFAULT_PRODUCT_CODE
        defaultProductShouldNotBeFound("productCode.doesNotContain=" + DEFAULT_PRODUCT_CODE);

        // Get all the productList where productCode does not contain UPDATED_PRODUCT_CODE
        defaultProductShouldBeFound("productCode.doesNotContain=" + UPDATED_PRODUCT_CODE);
    }


    @Test
    @Transactional
    public void getAllProductsByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature equals to DEFAULT_TEMPERATURE
        defaultProductShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the productList where temperature equals to UPDATED_TEMPERATURE
        defaultProductShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllProductsByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature not equals to DEFAULT_TEMPERATURE
        defaultProductShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the productList where temperature not equals to UPDATED_TEMPERATURE
        defaultProductShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllProductsByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultProductShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the productList where temperature equals to UPDATED_TEMPERATURE
        defaultProductShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllProductsByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature is not null
        defaultProductShouldBeFound("temperature.specified=true");

        // Get all the productList where temperature is null
        defaultProductShouldNotBeFound("temperature.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByTemperatureContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature contains DEFAULT_TEMPERATURE
        defaultProductShouldBeFound("temperature.contains=" + DEFAULT_TEMPERATURE);

        // Get all the productList where temperature contains UPDATED_TEMPERATURE
        defaultProductShouldNotBeFound("temperature.contains=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllProductsByTemperatureNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where temperature does not contain DEFAULT_TEMPERATURE
        defaultProductShouldNotBeFound("temperature.doesNotContain=" + DEFAULT_TEMPERATURE);

        // Get all the productList where temperature does not contain UPDATED_TEMPERATURE
        defaultProductShouldBeFound("temperature.doesNotContain=" + UPDATED_TEMPERATURE);
    }


    @Test
    @Transactional
    public void getAllProductsByInventoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Inventory inventories = InventoryResourceIT.createEntity(em);
        em.persist(inventories);
        em.flush();
        product.addInventories(inventories);
        productRepository.saveAndFlush(product);
        Long inventoriesId = inventories.getId();

        // Get all the productList where inventories equals to inventoriesId
        defaultProductShouldBeFound("inventoriesId.equals=" + inventoriesId);

        // Get all the productList where inventories equals to inventoriesId + 1
        defaultProductShouldNotBeFound("inventoriesId.equals=" + (inventoriesId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByPurchaseOrdersIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        PurchaseOrder purchaseOrders = PurchaseOrderResourceIT.createEntity(em);
        em.persist(purchaseOrders);
        em.flush();
        product.addPurchaseOrders(purchaseOrders);
        productRepository.saveAndFlush(product);
        Long purchaseOrdersId = purchaseOrders.getId();

        // Get all the productList where purchaseOrders equals to purchaseOrdersId
        defaultProductShouldBeFound("purchaseOrdersId.equals=" + purchaseOrdersId);

        // Get all the productList where purchaseOrders equals to purchaseOrdersId + 1
        defaultProductShouldNotBeFound("purchaseOrdersId.equals=" + (purchaseOrdersId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByTicketsIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Tickets tickets = TicketsResourceIT.createEntity(em);
        em.persist(tickets);
        em.flush();
        product.addTickets(tickets);
        productRepository.saveAndFlush(product);
        Long ticketsId = tickets.getId();

        // Get all the productList where tickets equals to ticketsId
        defaultProductShouldBeFound("ticketsId.equals=" + ticketsId);

        // Get all the productList where tickets equals to ticketsId + 1
        defaultProductShouldNotBeFound("ticketsId.equals=" + (ticketsId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Category category = CategoryResourceIT.createEntity(em);
        em.persist(category);
        em.flush();
        product.setCategory(category);
        productRepository.saveAndFlush(product);
        Long categoryId = category.getId();

        // Get all the productList where category equals to categoryId
        defaultProductShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the productList where category equals to categoryId + 1
        defaultProductShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE)));

        // Check, that the count call also returns 1
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .productId(UPDATED_PRODUCT_ID)
            .description(UPDATED_DESCRIPTION)
            .productCode(UPDATED_PRODUCT_CODE)
            .temperature(UPDATED_TEMPERATURE);

        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
