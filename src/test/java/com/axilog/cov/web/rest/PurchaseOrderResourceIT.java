package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.PurchaseOrderRepository;
import com.axilog.cov.service.PurchaseOrderService;
import com.axilog.cov.service.dto.PurchaseOrderCriteria;
import com.axilog.cov.service.PurchaseOrderQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PurchaseOrderResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PurchaseOrderResourceIT {

    private static final Long DEFAULT_ORDER_NO = 1L;
    private static final Long UPDATED_ORDER_NO = 2L;
    private static final Long SMALLER_ORDER_NO = 1L - 1L;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_ON = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DELIVERED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELIVERED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DELIVERED_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_AT = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_AT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseOrderQueryService purchaseOrderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .orderNo(DEFAULT_ORDER_NO)
            .quantity(DEFAULT_QUANTITY)
            .createdBy(DEFAULT_CREATED_BY)
//            .createdOn(DEFAULT_CREATED_ON)
//            .deliveredDate(DEFAULT_DELIVERED_DATE)
//            .updatedAt(DEFAULT_UPDATED_AT)
//            .createdAt(DEFAULT_CREATED_AT)
            ;
        return purchaseOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createUpdatedEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .orderNo(UPDATED_ORDER_NO)
            .quantity(UPDATED_QUANTITY)
            .createdBy(UPDATED_CREATED_BY)
//            .createdOn(UPDATED_CREATED_ON)
//            .deliveredDate(UPDATED_DELIVERED_DATE)
//            .updatedAt(UPDATED_UPDATED_AT)
//            .createdAt(UPDATED_CREATED_AT)
            ;
        return purchaseOrder;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();
        // Create the PurchaseOrder
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getOrderNo()).isEqualTo(DEFAULT_ORDER_NO);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchaseOrder.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPurchaseOrder.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testPurchaseOrder.getDeliveredDate()).isEqualTo(DEFAULT_DELIVERED_DATE);
        assertThat(testPurchaseOrder.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPurchaseOrder.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    public void createPurchaseOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // Create the PurchaseOrder with an existing ID
        purchaseOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMockMvc.perform(post("/api/purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(DEFAULT_DELIVERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.deliveredDate").value(DEFAULT_DELIVERED_DATE.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getPurchaseOrdersByIdFiltering() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        Long id = purchaseOrder.getId();

        defaultPurchaseOrderShouldBeFound("id.equals=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.notEquals=" + id);

        defaultPurchaseOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultPurchaseOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo equals to DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.equals=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo equals to UPDATED_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.equals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo not equals to DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.notEquals=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo not equals to UPDATED_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.notEquals=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo in DEFAULT_ORDER_NO or UPDATED_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.in=" + DEFAULT_ORDER_NO + "," + UPDATED_ORDER_NO);

        // Get all the purchaseOrderList where orderNo equals to UPDATED_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.in=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo is not null
        defaultPurchaseOrderShouldBeFound("orderNo.specified=true");

        // Get all the purchaseOrderList where orderNo is null
        defaultPurchaseOrderShouldNotBeFound("orderNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo is greater than or equal to DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.greaterThanOrEqual=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo is greater than or equal to UPDATED_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.greaterThanOrEqual=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo is less than or equal to DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.lessThanOrEqual=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo is less than or equal to SMALLER_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.lessThanOrEqual=" + SMALLER_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo is less than DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.lessThan=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo is less than UPDATED_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.lessThan=" + UPDATED_ORDER_NO);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByOrderNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderNo is greater than DEFAULT_ORDER_NO
        defaultPurchaseOrderShouldNotBeFound("orderNo.greaterThan=" + DEFAULT_ORDER_NO);

        // Get all the purchaseOrderList where orderNo is greater than SMALLER_ORDER_NO
        defaultPurchaseOrderShouldBeFound("orderNo.greaterThan=" + SMALLER_ORDER_NO);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity equals to DEFAULT_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity equals to UPDATED_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity not equals to DEFAULT_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity not equals to UPDATED_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the purchaseOrderList where quantity equals to UPDATED_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity is not null
        defaultPurchaseOrderShouldBeFound("quantity.specified=true");

        // Get all the purchaseOrderList where quantity is null
        defaultPurchaseOrderShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity is less than or equal to SMALLER_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity is less than DEFAULT_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity is less than UPDATED_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where quantity is greater than DEFAULT_QUANTITY
        defaultPurchaseOrderShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the purchaseOrderList where quantity is greater than SMALLER_QUANTITY
        defaultPurchaseOrderShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy equals to DEFAULT_CREATED_BY
        defaultPurchaseOrderShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the purchaseOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultPurchaseOrderShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy not equals to DEFAULT_CREATED_BY
        defaultPurchaseOrderShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the purchaseOrderList where createdBy not equals to UPDATED_CREATED_BY
        defaultPurchaseOrderShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultPurchaseOrderShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the purchaseOrderList where createdBy equals to UPDATED_CREATED_BY
        defaultPurchaseOrderShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy is not null
        defaultPurchaseOrderShouldBeFound("createdBy.specified=true");

        // Get all the purchaseOrderList where createdBy is null
        defaultPurchaseOrderShouldNotBeFound("createdBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy contains DEFAULT_CREATED_BY
        defaultPurchaseOrderShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the purchaseOrderList where createdBy contains UPDATED_CREATED_BY
        defaultPurchaseOrderShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdBy does not contain DEFAULT_CREATED_BY
        defaultPurchaseOrderShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the purchaseOrderList where createdBy does not contain UPDATED_CREATED_BY
        defaultPurchaseOrderShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn equals to DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn not equals to DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.notEquals=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn not equals to UPDATED_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.notEquals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the purchaseOrderList where createdOn equals to UPDATED_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn is not null
        defaultPurchaseOrderShouldBeFound("createdOn.specified=true");

        // Get all the purchaseOrderList where createdOn is null
        defaultPurchaseOrderShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn is greater than or equal to DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.greaterThanOrEqual=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn is greater than or equal to UPDATED_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.greaterThanOrEqual=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn is less than or equal to DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.lessThanOrEqual=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn is less than or equal to SMALLER_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.lessThanOrEqual=" + SMALLER_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn is less than DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.lessThan=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn is less than UPDATED_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.lessThan=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdOn is greater than DEFAULT_CREATED_ON
        defaultPurchaseOrderShouldNotBeFound("createdOn.greaterThan=" + DEFAULT_CREATED_ON);

        // Get all the purchaseOrderList where createdOn is greater than SMALLER_CREATED_ON
        defaultPurchaseOrderShouldBeFound("createdOn.greaterThan=" + SMALLER_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate equals to DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.equals=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate equals to UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.equals=" + UPDATED_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate not equals to DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.notEquals=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate not equals to UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.notEquals=" + UPDATED_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate in DEFAULT_DELIVERED_DATE or UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.in=" + DEFAULT_DELIVERED_DATE + "," + UPDATED_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate equals to UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.in=" + UPDATED_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate is not null
        defaultPurchaseOrderShouldBeFound("deliveredDate.specified=true");

        // Get all the purchaseOrderList where deliveredDate is null
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate is greater than or equal to DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.greaterThanOrEqual=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate is greater than or equal to UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.greaterThanOrEqual=" + UPDATED_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate is less than or equal to DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.lessThanOrEqual=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate is less than or equal to SMALLER_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.lessThanOrEqual=" + SMALLER_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate is less than DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.lessThan=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate is less than UPDATED_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.lessThan=" + UPDATED_DELIVERED_DATE);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByDeliveredDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where deliveredDate is greater than DEFAULT_DELIVERED_DATE
        defaultPurchaseOrderShouldNotBeFound("deliveredDate.greaterThan=" + DEFAULT_DELIVERED_DATE);

        // Get all the purchaseOrderList where deliveredDate is greater than SMALLER_DELIVERED_DATE
        defaultPurchaseOrderShouldBeFound("deliveredDate.greaterThan=" + SMALLER_DELIVERED_DATE);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt is not null
        defaultPurchaseOrderShouldBeFound("updatedAt.specified=true");

        // Get all the purchaseOrderList where updatedAt is null
        defaultPurchaseOrderShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt is greater than or equal to DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt is greater than or equal to UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt is less than or equal to DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt is less than or equal to SMALLER_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt is less than DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt is less than UPDATED_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where updatedAt is greater than DEFAULT_UPDATED_AT
        defaultPurchaseOrderShouldNotBeFound("updatedAt.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the purchaseOrderList where updatedAt is greater than SMALLER_UPDATED_AT
        defaultPurchaseOrderShouldBeFound("updatedAt.greaterThan=" + SMALLER_UPDATED_AT);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt equals to DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt equals to UPDATED_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt not equals to DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.notEquals=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt not equals to UPDATED_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.notEquals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the purchaseOrderList where createdAt equals to UPDATED_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt is not null
        defaultPurchaseOrderShouldBeFound("createdAt.specified=true");

        // Get all the purchaseOrderList where createdAt is null
        defaultPurchaseOrderShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt is greater than or equal to DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.greaterThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt is greater than or equal to UPDATED_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.greaterThanOrEqual=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt is less than or equal to DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.lessThanOrEqual=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt is less than or equal to SMALLER_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.lessThanOrEqual=" + SMALLER_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt is less than DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.lessThan=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt is less than UPDATED_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.lessThan=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void getAllPurchaseOrdersByCreatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where createdAt is greater than DEFAULT_CREATED_AT
        defaultPurchaseOrderShouldNotBeFound("createdAt.greaterThan=" + DEFAULT_CREATED_AT);

        // Get all the purchaseOrderList where createdAt is greater than SMALLER_CREATED_AT
        defaultPurchaseOrderShouldBeFound("createdAt.greaterThan=" + SMALLER_CREATED_AT);
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByPoStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        PoStatus poStatus = PoStatusResourceIT.createEntity(em);
        em.persist(poStatus);
        em.flush();
        purchaseOrder.addPoStatus(poStatus);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long poStatusId = poStatus.getId();

        // Get all the purchaseOrderList where poStatus equals to poStatusId
        defaultPurchaseOrderShouldBeFound("poStatusId.equals=" + poStatusId);

        // Get all the purchaseOrderList where poStatus equals to poStatusId + 1
        defaultPurchaseOrderShouldNotBeFound("poStatusId.equals=" + (poStatusId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Outlet outlet = OutletResourceIT.createEntity(em);
        em.persist(outlet);
        em.flush();
        purchaseOrder.setOutlet(outlet);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long outletId = outlet.getId();

        // Get all the purchaseOrderList where outlet equals to outletId
        defaultPurchaseOrderShouldBeFound("outletId.equals=" + outletId);

        // Get all the purchaseOrderList where outlet equals to outletId + 1
        defaultPurchaseOrderShouldNotBeFound("outletId.equals=" + (outletId + 1));
    }


    @Test
    @Transactional
    public void getAllPurchaseOrdersByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
//        purchaseOrder.setProduct(product);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long productId = product.getId();

        // Get all the purchaseOrderList where product equals to productId
        defaultPurchaseOrderShouldBeFound("productId.equals=" + productId);

        // Get all the purchaseOrderList where product equals to productId + 1
        defaultPurchaseOrderShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurchaseOrderShouldBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].deliveredDate").value(hasItem(DEFAULT_DELIVERED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurchaseOrderShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get("/api/purchase-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderService.save(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrder are not directly saved in db
        em.detach(updatedPurchaseOrder);
        updatedPurchaseOrder
            .orderNo(UPDATED_ORDER_NO)
            .quantity(UPDATED_QUANTITY)
            .createdBy(UPDATED_CREATED_BY)
//            .createdOn(UPDATED_CREATED_ON)
//            .deliveredDate(UPDATED_DELIVERED_DATE)
//            .updatedAt(UPDATED_UPDATED_AT)
//            .createdAt(UPDATED_CREATED_AT)
            ;

        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPurchaseOrder)))
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getOrderNo()).isEqualTo(UPDATED_ORDER_NO);
        assertThat(testPurchaseOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchaseOrder.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPurchaseOrder.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testPurchaseOrder.getDeliveredDate()).isEqualTo(UPDATED_DELIVERED_DATE);
        assertThat(testPurchaseOrder.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPurchaseOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc.perform(put("/api/purchase-orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(purchaseOrder)))
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderService.save(purchaseOrder);

        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Delete the purchaseOrder
        restPurchaseOrderMockMvc.perform(delete("/api/purchase-orders/{id}", purchaseOrder.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
