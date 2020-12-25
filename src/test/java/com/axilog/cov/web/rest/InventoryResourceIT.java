package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.dto.InventoryCriteria;
import com.axilog.cov.util.DateUtil;
import com.axilog.cov.service.InventoryQueryService;

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
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryResourceIT {

    private static final Long DEFAULT_INVENTORY_ID = 1L;
    private static final Long UPDATED_INVENTORY_ID = 2L;
    private static final Long SMALLER_INVENTORY_ID = 1L - 1L;

    private static final Double DEFAULT_QUANTITIES_IN_HAND = 1D;
    private static final Double UPDATED_QUANTITIES_IN_HAND = 2D;
    private static final Double SMALLER_QUANTITIES_IN_HAND = 1D - 1D;

    private static final Double DEFAULT_QUANTITIES_IN_TRANSIT = 1D;
    private static final Double UPDATED_QUANTITIES_IN_TRANSIT = 2D;
    private static final Double SMALLER_QUANTITIES_IN_TRANSIT = 1D - 1D;

    private static final String DEFAULT_UOM = "AAAAAAAAAA";
    private static final String UPDATED_UOM = "BBBBBBBBBB";

    private static final Double DEFAULT_ACTUAL_DAILY_CONSUMPTION = 1D;
    private static final Double UPDATED_ACTUAL_DAILY_CONSUMPTION = 2D;
    private static final Double SMALLER_ACTUAL_DAILY_CONSUMPTION = 1D - 1D;

    private static final Double DEFAULT_ACTUAL_AVG_CONSUMPTION = 1D;
    private static final Double UPDATED_ACTUAL_AVG_CONSUMPTION = 2D;
    private static final Double SMALLER_ACTUAL_AVG_CONSUMPTION = 1D - 1D;

    private static final String DEFAULT_RE_ORDER_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_RE_ORDER_LEVEL = "BBBBBBBBBB";

    private static final Double DEFAULT_SUGGESTED_QUANTITY = 1D;
    private static final Double UPDATED_SUGGESTED_QUANTITY = 2D;
    private static final Double SMALLER_SUGGESTED_QUANTITY = 1D - 1D;

    private static final Double DEFAULT_EXPECTED_COVERING_DAY = 1D;
    private static final Double UPDATED_EXPECTED_COVERING_DAY = 2D;
    private static final Double SMALLER_EXPECTED_COVERING_DAY = 1D - 1D;

    private static final LocalDate DEFAULT_LAST_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LAST_UPDATED_AT = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private InventoryQueryService inventoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryMockMvc;

    private Inventory inventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .inventoryId(DEFAULT_INVENTORY_ID)
            .quantitiesInHand(DEFAULT_QUANTITIES_IN_HAND)
            .quantitiesInTransit(DEFAULT_QUANTITIES_IN_TRANSIT)
            .uom(DEFAULT_UOM)
            .actualDailyConsumption(DEFAULT_ACTUAL_DAILY_CONSUMPTION)
            .actualAvgConsumption(DEFAULT_ACTUAL_AVG_CONSUMPTION)
            .reOrderLevel(DEFAULT_RE_ORDER_LEVEL)
            .suggestedQuantity(DEFAULT_SUGGESTED_QUANTITY)
            .expectedCoveringDay(DEFAULT_EXPECTED_COVERING_DAY)
            .lastUpdatedAt(DateUtil.convertToDateViaInstant(DEFAULT_LAST_UPDATED_AT))
            .status(DEFAULT_STATUS);
        return inventory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventory createUpdatedEntity(EntityManager em) {
        Inventory inventory = new Inventory()
            .inventoryId(UPDATED_INVENTORY_ID)
            .quantitiesInHand(UPDATED_QUANTITIES_IN_HAND)
            .quantitiesInTransit(UPDATED_QUANTITIES_IN_TRANSIT)
            .uom(UPDATED_UOM)
            .actualDailyConsumption(UPDATED_ACTUAL_DAILY_CONSUMPTION)
            .actualAvgConsumption(UPDATED_ACTUAL_AVG_CONSUMPTION)
            .reOrderLevel(UPDATED_RE_ORDER_LEVEL)
            .suggestedQuantity(UPDATED_SUGGESTED_QUANTITY)
            .expectedCoveringDay(UPDATED_EXPECTED_COVERING_DAY)
            .lastUpdatedAt(DateUtil.convertToDateViaInstant(UPDATED_LAST_UPDATED_AT))
            .status(UPDATED_STATUS);
        return inventory;
    }

    @BeforeEach
    public void initTest() {
        inventory = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventory() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();
        // Create the Inventory
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInventoryId()).isEqualTo(DEFAULT_INVENTORY_ID);
        assertThat(testInventory.getQuantitiesInHand()).isEqualTo(DEFAULT_QUANTITIES_IN_HAND);
        assertThat(testInventory.getQuantitiesInTransit()).isEqualTo(DEFAULT_QUANTITIES_IN_TRANSIT);
        assertThat(testInventory.getUom()).isEqualTo(DEFAULT_UOM);
        assertThat(testInventory.getActualDailyConsumption()).isEqualTo(DEFAULT_ACTUAL_DAILY_CONSUMPTION);
        assertThat(testInventory.getActualAvgConsumption()).isEqualTo(DEFAULT_ACTUAL_AVG_CONSUMPTION);
        assertThat(testInventory.getReOrderLevel()).isEqualTo(DEFAULT_RE_ORDER_LEVEL);
        assertThat(testInventory.getSuggestedQuantity()).isEqualTo(DEFAULT_SUGGESTED_QUANTITY);
        assertThat(testInventory.getExpectedCoveringDay()).isEqualTo(DEFAULT_EXPECTED_COVERING_DAY);
        assertThat(testInventory.getLastUpdatedAt()).isEqualTo(DEFAULT_LAST_UPDATED_AT);
        assertThat(testInventory.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc.perform(post("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventories() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryId").value(hasItem(DEFAULT_INVENTORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantitiesInHand").value(hasItem(DEFAULT_QUANTITIES_IN_HAND.doubleValue())))
            .andExpect(jsonPath("$.[*].quantitiesInTransit").value(hasItem(DEFAULT_QUANTITIES_IN_TRANSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM)))
            .andExpect(jsonPath("$.[*].actualDailyConsumption").value(hasItem(DEFAULT_ACTUAL_DAILY_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAvgConsumption").value(hasItem(DEFAULT_ACTUAL_AVG_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].reOrderLevel").value(hasItem(DEFAULT_RE_ORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].suggestedQuantity").value(hasItem(DEFAULT_SUGGESTED_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedCoveringDay").value(hasItem(DEFAULT_EXPECTED_COVERING_DAY.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.inventoryId").value(DEFAULT_INVENTORY_ID.intValue()))
            .andExpect(jsonPath("$.quantitiesInHand").value(DEFAULT_QUANTITIES_IN_HAND.doubleValue()))
            .andExpect(jsonPath("$.quantitiesInTransit").value(DEFAULT_QUANTITIES_IN_TRANSIT.doubleValue()))
            .andExpect(jsonPath("$.uom").value(DEFAULT_UOM))
            .andExpect(jsonPath("$.actualDailyConsumption").value(DEFAULT_ACTUAL_DAILY_CONSUMPTION.doubleValue()))
            .andExpect(jsonPath("$.actualAvgConsumption").value(DEFAULT_ACTUAL_AVG_CONSUMPTION.doubleValue()))
            .andExpect(jsonPath("$.reOrderLevel").value(DEFAULT_RE_ORDER_LEVEL))
            .andExpect(jsonPath("$.suggestedQuantity").value(DEFAULT_SUGGESTED_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.expectedCoveringDay").value(DEFAULT_EXPECTED_COVERING_DAY.doubleValue()))
            .andExpect(jsonPath("$.lastUpdatedAt").value(DEFAULT_LAST_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        Long id = inventory.getId();

        defaultInventoryShouldBeFound("id.equals=" + id);
        defaultInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId equals to DEFAULT_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.equals=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId equals to UPDATED_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.equals=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId not equals to DEFAULT_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.notEquals=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId not equals to UPDATED_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.notEquals=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId in DEFAULT_INVENTORY_ID or UPDATED_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.in=" + DEFAULT_INVENTORY_ID + "," + UPDATED_INVENTORY_ID);

        // Get all the inventoryList where inventoryId equals to UPDATED_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.in=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId is not null
        defaultInventoryShouldBeFound("inventoryId.specified=true");

        // Get all the inventoryList where inventoryId is null
        defaultInventoryShouldNotBeFound("inventoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId is greater than or equal to DEFAULT_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.greaterThanOrEqual=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId is greater than or equal to UPDATED_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.greaterThanOrEqual=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId is less than or equal to DEFAULT_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.lessThanOrEqual=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId is less than or equal to SMALLER_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.lessThanOrEqual=" + SMALLER_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId is less than DEFAULT_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.lessThan=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId is less than UPDATED_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.lessThan=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId is greater than DEFAULT_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.greaterThan=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId is greater than SMALLER_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.greaterThan=" + SMALLER_INVENTORY_ID);
    }


    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand equals to DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.equals=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand equals to UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.equals=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand not equals to DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.notEquals=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand not equals to UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.notEquals=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand in DEFAULT_QUANTITIES_IN_HAND or UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.in=" + DEFAULT_QUANTITIES_IN_HAND + "," + UPDATED_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand equals to UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.in=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand is not null
        defaultInventoryShouldBeFound("quantitiesInHand.specified=true");

        // Get all the inventoryList where quantitiesInHand is null
        defaultInventoryShouldNotBeFound("quantitiesInHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand is greater than or equal to DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.greaterThanOrEqual=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand is greater than or equal to UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.greaterThanOrEqual=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand is less than or equal to DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.lessThanOrEqual=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand is less than or equal to SMALLER_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.lessThanOrEqual=" + SMALLER_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand is less than DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.lessThan=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand is less than UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.lessThan=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand is greater than DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.greaterThan=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand is greater than SMALLER_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.greaterThan=" + SMALLER_QUANTITIES_IN_HAND);
    }


    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit equals to DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.equals=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit equals to UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.equals=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit not equals to DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.notEquals=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit not equals to UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.notEquals=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit in DEFAULT_QUANTITIES_IN_TRANSIT or UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.in=" + DEFAULT_QUANTITIES_IN_TRANSIT + "," + UPDATED_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit equals to UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.in=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit is not null
        defaultInventoryShouldBeFound("quantitiesInTransit.specified=true");

        // Get all the inventoryList where quantitiesInTransit is null
        defaultInventoryShouldNotBeFound("quantitiesInTransit.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit is greater than or equal to DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.greaterThanOrEqual=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit is greater than or equal to UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.greaterThanOrEqual=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit is less than or equal to DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.lessThanOrEqual=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit is less than or equal to SMALLER_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.lessThanOrEqual=" + SMALLER_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit is less than DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.lessThan=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit is less than UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.lessThan=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit is greater than DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.greaterThan=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit is greater than SMALLER_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.greaterThan=" + SMALLER_QUANTITIES_IN_TRANSIT);
    }


    @Test
    @Transactional
    public void getAllInventoriesByUomIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom equals to DEFAULT_UOM
        defaultInventoryShouldBeFound("uom.equals=" + DEFAULT_UOM);

        // Get all the inventoryList where uom equals to UPDATED_UOM
        defaultInventoryShouldNotBeFound("uom.equals=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllInventoriesByUomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom not equals to DEFAULT_UOM
        defaultInventoryShouldNotBeFound("uom.notEquals=" + DEFAULT_UOM);

        // Get all the inventoryList where uom not equals to UPDATED_UOM
        defaultInventoryShouldBeFound("uom.notEquals=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllInventoriesByUomIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom in DEFAULT_UOM or UPDATED_UOM
        defaultInventoryShouldBeFound("uom.in=" + DEFAULT_UOM + "," + UPDATED_UOM);

        // Get all the inventoryList where uom equals to UPDATED_UOM
        defaultInventoryShouldNotBeFound("uom.in=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllInventoriesByUomIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom is not null
        defaultInventoryShouldBeFound("uom.specified=true");

        // Get all the inventoryList where uom is null
        defaultInventoryShouldNotBeFound("uom.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoriesByUomContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom contains DEFAULT_UOM
        defaultInventoryShouldBeFound("uom.contains=" + DEFAULT_UOM);

        // Get all the inventoryList where uom contains UPDATED_UOM
        defaultInventoryShouldNotBeFound("uom.contains=" + UPDATED_UOM);
    }

    @Test
    @Transactional
    public void getAllInventoriesByUomNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where uom does not contain DEFAULT_UOM
        defaultInventoryShouldNotBeFound("uom.doesNotContain=" + DEFAULT_UOM);

        // Get all the inventoryList where uom does not contain UPDATED_UOM
        defaultInventoryShouldBeFound("uom.doesNotContain=" + UPDATED_UOM);
    }


    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption equals to DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.equals=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption equals to UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.equals=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption not equals to DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.notEquals=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption not equals to UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.notEquals=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption in DEFAULT_ACTUAL_DAILY_CONSUMPTION or UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.in=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION + "," + UPDATED_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption equals to UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.in=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption is not null
        defaultInventoryShouldBeFound("actualDailyConsumption.specified=true");

        // Get all the inventoryList where actualDailyConsumption is null
        defaultInventoryShouldNotBeFound("actualDailyConsumption.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption is greater than or equal to DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.greaterThanOrEqual=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption is greater than or equal to UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.greaterThanOrEqual=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption is less than or equal to DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.lessThanOrEqual=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption is less than or equal to SMALLER_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.lessThanOrEqual=" + SMALLER_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption is less than DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.lessThan=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption is less than UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.lessThan=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption is greater than DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.greaterThan=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption is greater than SMALLER_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.greaterThan=" + SMALLER_ACTUAL_DAILY_CONSUMPTION);
    }


    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption equals to DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.equals=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption equals to UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.equals=" + UPDATED_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption not equals to DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.notEquals=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption not equals to UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.notEquals=" + UPDATED_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption in DEFAULT_ACTUAL_AVG_CONSUMPTION or UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.in=" + DEFAULT_ACTUAL_AVG_CONSUMPTION + "," + UPDATED_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption equals to UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.in=" + UPDATED_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption is not null
        defaultInventoryShouldBeFound("actualAvgConsumption.specified=true");

        // Get all the inventoryList where actualAvgConsumption is null
        defaultInventoryShouldNotBeFound("actualAvgConsumption.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption is greater than or equal to DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.greaterThanOrEqual=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption is greater than or equal to UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.greaterThanOrEqual=" + UPDATED_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption is less than or equal to DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.lessThanOrEqual=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption is less than or equal to SMALLER_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.lessThanOrEqual=" + SMALLER_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption is less than DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.lessThan=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption is less than UPDATED_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.lessThan=" + UPDATED_ACTUAL_AVG_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualAvgConsumptionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualAvgConsumption is greater than DEFAULT_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualAvgConsumption.greaterThan=" + DEFAULT_ACTUAL_AVG_CONSUMPTION);

        // Get all the inventoryList where actualAvgConsumption is greater than SMALLER_ACTUAL_AVG_CONSUMPTION
        defaultInventoryShouldBeFound("actualAvgConsumption.greaterThan=" + SMALLER_ACTUAL_AVG_CONSUMPTION);
    }


    @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel equals to DEFAULT_RE_ORDER_LEVEL
        defaultInventoryShouldBeFound("reOrderLevel.equals=" + DEFAULT_RE_ORDER_LEVEL);

        // Get all the inventoryList where reOrderLevel equals to UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldNotBeFound("reOrderLevel.equals=" + UPDATED_RE_ORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel not equals to DEFAULT_RE_ORDER_LEVEL
        defaultInventoryShouldNotBeFound("reOrderLevel.notEquals=" + DEFAULT_RE_ORDER_LEVEL);

        // Get all the inventoryList where reOrderLevel not equals to UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldBeFound("reOrderLevel.notEquals=" + UPDATED_RE_ORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel in DEFAULT_RE_ORDER_LEVEL or UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldBeFound("reOrderLevel.in=" + DEFAULT_RE_ORDER_LEVEL + "," + UPDATED_RE_ORDER_LEVEL);

        // Get all the inventoryList where reOrderLevel equals to UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldNotBeFound("reOrderLevel.in=" + UPDATED_RE_ORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel is not null
        defaultInventoryShouldBeFound("reOrderLevel.specified=true");

        // Get all the inventoryList where reOrderLevel is null
        defaultInventoryShouldNotBeFound("reOrderLevel.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel contains DEFAULT_RE_ORDER_LEVEL
        defaultInventoryShouldBeFound("reOrderLevel.contains=" + DEFAULT_RE_ORDER_LEVEL);

        // Get all the inventoryList where reOrderLevel contains UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldNotBeFound("reOrderLevel.contains=" + UPDATED_RE_ORDER_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByReOrderLevelNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where reOrderLevel does not contain DEFAULT_RE_ORDER_LEVEL
        defaultInventoryShouldNotBeFound("reOrderLevel.doesNotContain=" + DEFAULT_RE_ORDER_LEVEL);

        // Get all the inventoryList where reOrderLevel does not contain UPDATED_RE_ORDER_LEVEL
        defaultInventoryShouldBeFound("reOrderLevel.doesNotContain=" + UPDATED_RE_ORDER_LEVEL);
    }


    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity equals to DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.equals=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity equals to UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.equals=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity not equals to DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.notEquals=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity not equals to UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.notEquals=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity in DEFAULT_SUGGESTED_QUANTITY or UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.in=" + DEFAULT_SUGGESTED_QUANTITY + "," + UPDATED_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity equals to UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.in=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity is not null
        defaultInventoryShouldBeFound("suggestedQuantity.specified=true");

        // Get all the inventoryList where suggestedQuantity is null
        defaultInventoryShouldNotBeFound("suggestedQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity is greater than or equal to DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.greaterThanOrEqual=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity is greater than or equal to UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.greaterThanOrEqual=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity is less than or equal to DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.lessThanOrEqual=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity is less than or equal to SMALLER_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.lessThanOrEqual=" + SMALLER_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity is less than DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.lessThan=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity is less than UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.lessThan=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity is greater than DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.greaterThan=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity is greater than SMALLER_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.greaterThan=" + SMALLER_SUGGESTED_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay equals to DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.equals=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay equals to UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.equals=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay not equals to DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.notEquals=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay not equals to UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.notEquals=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay in DEFAULT_EXPECTED_COVERING_DAY or UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.in=" + DEFAULT_EXPECTED_COVERING_DAY + "," + UPDATED_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay equals to UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.in=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay is not null
        defaultInventoryShouldBeFound("expectedCoveringDay.specified=true");

        // Get all the inventoryList where expectedCoveringDay is null
        defaultInventoryShouldNotBeFound("expectedCoveringDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay is greater than or equal to DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.greaterThanOrEqual=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay is greater than or equal to UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.greaterThanOrEqual=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay is less than or equal to DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.lessThanOrEqual=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay is less than or equal to SMALLER_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.lessThanOrEqual=" + SMALLER_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay is less than DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.lessThan=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay is less than UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.lessThan=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay is greater than DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.greaterThan=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay is greater than SMALLER_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.greaterThan=" + SMALLER_EXPECTED_COVERING_DAY);
    }


    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt equals to DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.equals=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt equals to UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.equals=" + UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt not equals to DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.notEquals=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt not equals to UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.notEquals=" + UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt in DEFAULT_LAST_UPDATED_AT or UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.in=" + DEFAULT_LAST_UPDATED_AT + "," + UPDATED_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt equals to UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.in=" + UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt is not null
        defaultInventoryShouldBeFound("lastUpdatedAt.specified=true");

        // Get all the inventoryList where lastUpdatedAt is null
        defaultInventoryShouldNotBeFound("lastUpdatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt is greater than or equal to DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.greaterThanOrEqual=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt is greater than or equal to UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.greaterThanOrEqual=" + UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt is less than or equal to DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.lessThanOrEqual=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt is less than or equal to SMALLER_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.lessThanOrEqual=" + SMALLER_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt is less than DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.lessThan=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt is less than UPDATED_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.lessThan=" + UPDATED_LAST_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLastUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lastUpdatedAt is greater than DEFAULT_LAST_UPDATED_AT
        defaultInventoryShouldNotBeFound("lastUpdatedAt.greaterThan=" + DEFAULT_LAST_UPDATED_AT);

        // Get all the inventoryList where lastUpdatedAt is greater than SMALLER_LAST_UPDATED_AT
        defaultInventoryShouldBeFound("lastUpdatedAt.greaterThan=" + SMALLER_LAST_UPDATED_AT);
    }


    @Test
    @Transactional
    public void getAllInventoriesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status equals to DEFAULT_STATUS
        defaultInventoryShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the inventoryList where status equals to UPDATED_STATUS
        defaultInventoryShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status not equals to DEFAULT_STATUS
        defaultInventoryShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the inventoryList where status not equals to UPDATED_STATUS
        defaultInventoryShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultInventoryShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the inventoryList where status equals to UPDATED_STATUS
        defaultInventoryShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status is not null
        defaultInventoryShouldBeFound("status.specified=true");

        // Get all the inventoryList where status is null
        defaultInventoryShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoriesByStatusContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status contains DEFAULT_STATUS
        defaultInventoryShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the inventoryList where status contains UPDATED_STATUS
        defaultInventoryShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllInventoriesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where status does not contain DEFAULT_STATUS
        defaultInventoryShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the inventoryList where status does not contain UPDATED_STATUS
        defaultInventoryShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllInventoriesByOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        Outlet outlet = OutletResourceIT.createEntity(em);
        em.persist(outlet);
        em.flush();
        inventory.setOutlet(outlet);
        inventoryRepository.saveAndFlush(inventory);
        Long outletId = outlet.getId();

        // Get all the inventoryList where outlet equals to outletId
        defaultInventoryShouldBeFound("outletId.equals=" + outletId);

        // Get all the inventoryList where outlet equals to outletId + 1
        defaultInventoryShouldNotBeFound("outletId.equals=" + (outletId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        inventory.setProduct(product);
        inventoryRepository.saveAndFlush(inventory);
        Long productId = product.getId();

        // Get all the inventoryList where product equals to productId
        defaultInventoryShouldBeFound("productId.equals=" + productId);

        // Get all the inventoryList where product equals to productId + 1
        defaultInventoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryShouldBeFound(String filter) throws Exception {
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryId").value(hasItem(DEFAULT_INVENTORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].quantitiesInHand").value(hasItem(DEFAULT_QUANTITIES_IN_HAND.doubleValue())))
            .andExpect(jsonPath("$.[*].quantitiesInTransit").value(hasItem(DEFAULT_QUANTITIES_IN_TRANSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM)))
            .andExpect(jsonPath("$.[*].actualDailyConsumption").value(hasItem(DEFAULT_ACTUAL_DAILY_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].actualAvgConsumption").value(hasItem(DEFAULT_ACTUAL_AVG_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].reOrderLevel").value(hasItem(DEFAULT_RE_ORDER_LEVEL)))
            .andExpect(jsonPath("$.[*].suggestedQuantity").value(hasItem(DEFAULT_SUGGESTED_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedCoveringDay").value(hasItem(DEFAULT_EXPECTED_COVERING_DAY.doubleValue())))
            .andExpect(jsonPath("$.[*].lastUpdatedAt").value(hasItem(DEFAULT_LAST_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restInventoryMockMvc.perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryShouldNotBeFound(String filter) throws Exception {
        restInventoryMockMvc.perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryMockMvc.perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventory() throws Exception {
        // Initialize the database
        inventoryService.save(inventory);

        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // Update the inventory
        Inventory updatedInventory = inventoryRepository.findById(inventory.getId()).get();
        // Disconnect from session so that the updates on updatedInventory are not directly saved in db
        em.detach(updatedInventory);
        updatedInventory
            .inventoryId(UPDATED_INVENTORY_ID)
            .quantitiesInHand(UPDATED_QUANTITIES_IN_HAND)
            .quantitiesInTransit(UPDATED_QUANTITIES_IN_TRANSIT)
            .uom(UPDATED_UOM)
            .actualDailyConsumption(UPDATED_ACTUAL_DAILY_CONSUMPTION)
            .actualAvgConsumption(UPDATED_ACTUAL_AVG_CONSUMPTION)
            .reOrderLevel(UPDATED_RE_ORDER_LEVEL)
            .suggestedQuantity(UPDATED_SUGGESTED_QUANTITY)
            .expectedCoveringDay(UPDATED_EXPECTED_COVERING_DAY)
            .lastUpdatedAt(DateUtil.convertToDateViaInstant(UPDATED_LAST_UPDATED_AT))
            .status(UPDATED_STATUS);

        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventory)))
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInventoryId()).isEqualTo(UPDATED_INVENTORY_ID);
        assertThat(testInventory.getQuantitiesInHand()).isEqualTo(UPDATED_QUANTITIES_IN_HAND);
        assertThat(testInventory.getQuantitiesInTransit()).isEqualTo(UPDATED_QUANTITIES_IN_TRANSIT);
        assertThat(testInventory.getUom()).isEqualTo(UPDATED_UOM);
        assertThat(testInventory.getActualDailyConsumption()).isEqualTo(UPDATED_ACTUAL_DAILY_CONSUMPTION);
        assertThat(testInventory.getActualAvgConsumption()).isEqualTo(UPDATED_ACTUAL_AVG_CONSUMPTION);
        assertThat(testInventory.getReOrderLevel()).isEqualTo(UPDATED_RE_ORDER_LEVEL);
        assertThat(testInventory.getSuggestedQuantity()).isEqualTo(UPDATED_SUGGESTED_QUANTITY);
        assertThat(testInventory.getExpectedCoveringDay()).isEqualTo(UPDATED_EXPECTED_COVERING_DAY);
        assertThat(testInventory.getLastUpdatedAt()).isEqualTo(UPDATED_LAST_UPDATED_AT);
        assertThat(testInventory.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc.perform(put("/api/inventories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isBadRequest());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventory() throws Exception {
        // Initialize the database
        inventoryService.save(inventory);

        int databaseSizeBeforeDelete = inventoryRepository.findAll().size();

        // Delete the inventory
        restInventoryMockMvc.perform(delete("/api/inventories/{id}", inventory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
