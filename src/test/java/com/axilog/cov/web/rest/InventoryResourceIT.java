package com.axilog.cov.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.InventoryRepository;
import com.axilog.cov.service.InventoryQueryService;
import com.axilog.cov.service.InventoryService;
import com.axilog.cov.service.dto.InventoryCriteria;
import java.time.LocalDate;
import java.time.ZoneId;
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

/**
 * Integration tests for the {@link InventoryResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryResourceIT {
    private static final String DEFAULT_INVENTORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_INVENTORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITIES_IN_HAND = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITIES_IN_HAND = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITIES_IN_TRANSIT = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITIES_IN_TRANSIT = "BBBBBBBBBB";

    private static final String DEFAULT_UOM = "AAAAAAAAAA";
    private static final String UPDATED_UOM = "BBBBBBBBBB";

    private static final String DEFAULT_ACTUAL_DAILY_CONSUMPTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTUAL_DAILY_CONSUMPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RECORD_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_SUGGESTED_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_SUGGESTED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_EXPECTED_COVERING_DAY = "AAAAAAAAAA";
    private static final String UPDATED_EXPECTED_COVERING_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_QUANTITY = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_LASTER_UPDATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LASTER_UPDATED = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_LASTER_UPDATED = LocalDate.ofEpochDay(-1L);

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
            .itemCode(DEFAULT_ITEM_CODE)
            .description(DEFAULT_DESCRIPTION)
            .quantitiesInHand(DEFAULT_QUANTITIES_IN_HAND)
            .quantitiesInTransit(DEFAULT_QUANTITIES_IN_TRANSIT)
            .uom(DEFAULT_UOM)
            .actualDailyConsumption(DEFAULT_ACTUAL_DAILY_CONSUMPTION)
            .recordLevel(DEFAULT_RECORD_LEVEL)
            .suggestedQuantity(DEFAULT_SUGGESTED_QUANTITY)
            .expectedCoveringDay(DEFAULT_EXPECTED_COVERING_DAY)
            .quantity(DEFAULT_QUANTITY)
            .location(DEFAULT_LOCATION)
            .lasterUpdated(DEFAULT_LASTER_UPDATED);
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
            .itemCode(UPDATED_ITEM_CODE)
            .description(UPDATED_DESCRIPTION)
            .quantitiesInHand(UPDATED_QUANTITIES_IN_HAND)
            .quantitiesInTransit(UPDATED_QUANTITIES_IN_TRANSIT)
            .uom(UPDATED_UOM)
            .actualDailyConsumption(UPDATED_ACTUAL_DAILY_CONSUMPTION)
            .recordLevel(UPDATED_RECORD_LEVEL)
            .suggestedQuantity(UPDATED_SUGGESTED_QUANTITY)
            .expectedCoveringDay(UPDATED_EXPECTED_COVERING_DAY)
            .quantity(UPDATED_QUANTITY)
            .location(UPDATED_LOCATION)
            .lasterUpdated(UPDATED_LASTER_UPDATED);
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
        restInventoryMockMvc
            .perform(post("/api/inventories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventory)))
            .andExpect(status().isCreated());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeCreate + 1);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInventoryId()).isEqualTo(DEFAULT_INVENTORY_ID);
        assertThat(testInventory.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testInventory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testInventory.getQuantitiesInHand()).isEqualTo(DEFAULT_QUANTITIES_IN_HAND);
        assertThat(testInventory.getQuantitiesInTransit()).isEqualTo(DEFAULT_QUANTITIES_IN_TRANSIT);
        assertThat(testInventory.getUom()).isEqualTo(DEFAULT_UOM);
        assertThat(testInventory.getActualDailyConsumption()).isEqualTo(DEFAULT_ACTUAL_DAILY_CONSUMPTION);
        assertThat(testInventory.getRecordLevel()).isEqualTo(DEFAULT_RECORD_LEVEL);
        assertThat(testInventory.getSuggestedQuantity()).isEqualTo(DEFAULT_SUGGESTED_QUANTITY);
        assertThat(testInventory.getExpectedCoveringDay()).isEqualTo(DEFAULT_EXPECTED_COVERING_DAY);
        assertThat(testInventory.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInventory.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testInventory.getLasterUpdated()).isEqualTo(DEFAULT_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void createInventoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryRepository.findAll().size();

        // Create the Inventory with an existing ID
        inventory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryMockMvc
            .perform(post("/api/inventories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventory)))
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
        restInventoryMockMvc
            .perform(get("/api/inventories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryId").value(hasItem(DEFAULT_INVENTORY_ID)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantitiesInHand").value(hasItem(DEFAULT_QUANTITIES_IN_HAND)))
            .andExpect(jsonPath("$.[*].quantitiesInTransit").value(hasItem(DEFAULT_QUANTITIES_IN_TRANSIT)))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM)))
            .andExpect(jsonPath("$.[*].actualDailyConsumption").value(hasItem(DEFAULT_ACTUAL_DAILY_CONSUMPTION)))
            .andExpect(jsonPath("$.[*].recordLevel").value(hasItem(DEFAULT_RECORD_LEVEL)))
            .andExpect(jsonPath("$.[*].suggestedQuantity").value(hasItem(DEFAULT_SUGGESTED_QUANTITY)))
            .andExpect(jsonPath("$.[*].expectedCoveringDay").value(hasItem(DEFAULT_EXPECTED_COVERING_DAY)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].lasterUpdated").value(hasItem(DEFAULT_LASTER_UPDATED.toString())));
    }

    @Test
    @Transactional
    public void getInventory() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get the inventory
        restInventoryMockMvc
            .perform(get("/api/inventories/{id}", inventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventory.getId().intValue()))
            .andExpect(jsonPath("$.inventoryId").value(DEFAULT_INVENTORY_ID))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.quantitiesInHand").value(DEFAULT_QUANTITIES_IN_HAND))
            .andExpect(jsonPath("$.quantitiesInTransit").value(DEFAULT_QUANTITIES_IN_TRANSIT))
            .andExpect(jsonPath("$.uom").value(DEFAULT_UOM))
            .andExpect(jsonPath("$.actualDailyConsumption").value(DEFAULT_ACTUAL_DAILY_CONSUMPTION))
            .andExpect(jsonPath("$.recordLevel").value(DEFAULT_RECORD_LEVEL))
            .andExpect(jsonPath("$.suggestedQuantity").value(DEFAULT_SUGGESTED_QUANTITY))
            .andExpect(jsonPath("$.expectedCoveringDay").value(DEFAULT_EXPECTED_COVERING_DAY))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.lasterUpdated").value(DEFAULT_LASTER_UPDATED.toString()));
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
    public void getAllInventoriesByInventoryIdContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId contains DEFAULT_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.contains=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId contains UPDATED_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.contains=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByInventoryIdNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where inventoryId does not contain DEFAULT_INVENTORY_ID
        defaultInventoryShouldNotBeFound("inventoryId.doesNotContain=" + DEFAULT_INVENTORY_ID);

        // Get all the inventoryList where inventoryId does not contain UPDATED_INVENTORY_ID
        defaultInventoryShouldBeFound("inventoryId.doesNotContain=" + UPDATED_INVENTORY_ID);
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode equals to DEFAULT_ITEM_CODE
        defaultInventoryShouldBeFound("itemCode.equals=" + DEFAULT_ITEM_CODE);

        // Get all the inventoryList where itemCode equals to UPDATED_ITEM_CODE
        defaultInventoryShouldNotBeFound("itemCode.equals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode not equals to DEFAULT_ITEM_CODE
        defaultInventoryShouldNotBeFound("itemCode.notEquals=" + DEFAULT_ITEM_CODE);

        // Get all the inventoryList where itemCode not equals to UPDATED_ITEM_CODE
        defaultInventoryShouldBeFound("itemCode.notEquals=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode in DEFAULT_ITEM_CODE or UPDATED_ITEM_CODE
        defaultInventoryShouldBeFound("itemCode.in=" + DEFAULT_ITEM_CODE + "," + UPDATED_ITEM_CODE);

        // Get all the inventoryList where itemCode equals to UPDATED_ITEM_CODE
        defaultInventoryShouldNotBeFound("itemCode.in=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode is not null
        defaultInventoryShouldBeFound("itemCode.specified=true");

        // Get all the inventoryList where itemCode is null
        defaultInventoryShouldNotBeFound("itemCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode contains DEFAULT_ITEM_CODE
        defaultInventoryShouldBeFound("itemCode.contains=" + DEFAULT_ITEM_CODE);

        // Get all the inventoryList where itemCode contains UPDATED_ITEM_CODE
        defaultInventoryShouldNotBeFound("itemCode.contains=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByItemCodeNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where itemCode does not contain DEFAULT_ITEM_CODE
        defaultInventoryShouldNotBeFound("itemCode.doesNotContain=" + DEFAULT_ITEM_CODE);

        // Get all the inventoryList where itemCode does not contain UPDATED_ITEM_CODE
        defaultInventoryShouldBeFound("itemCode.doesNotContain=" + UPDATED_ITEM_CODE);
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description equals to DEFAULT_DESCRIPTION
        defaultInventoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryList where description equals to UPDATED_DESCRIPTION
        defaultInventoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description not equals to DEFAULT_DESCRIPTION
        defaultInventoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryList where description not equals to UPDATED_DESCRIPTION
        defaultInventoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultInventoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the inventoryList where description equals to UPDATED_DESCRIPTION
        defaultInventoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description is not null
        defaultInventoryShouldBeFound("description.specified=true");

        // Get all the inventoryList where description is null
        defaultInventoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description contains DEFAULT_DESCRIPTION
        defaultInventoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryList where description contains UPDATED_DESCRIPTION
        defaultInventoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where description does not contain DEFAULT_DESCRIPTION
        defaultInventoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the inventoryList where description does not contain UPDATED_DESCRIPTION
        defaultInventoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
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
    public void getAllInventoriesByQuantitiesInHandContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand contains DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.contains=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand contains UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.contains=" + UPDATED_QUANTITIES_IN_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInHandNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInHand does not contain DEFAULT_QUANTITIES_IN_HAND
        defaultInventoryShouldNotBeFound("quantitiesInHand.doesNotContain=" + DEFAULT_QUANTITIES_IN_HAND);

        // Get all the inventoryList where quantitiesInHand does not contain UPDATED_QUANTITIES_IN_HAND
        defaultInventoryShouldBeFound("quantitiesInHand.doesNotContain=" + UPDATED_QUANTITIES_IN_HAND);
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
    public void getAllInventoriesByQuantitiesInTransitContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit contains DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.contains=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit contains UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.contains=" + UPDATED_QUANTITIES_IN_TRANSIT);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantitiesInTransitNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantitiesInTransit does not contain DEFAULT_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldNotBeFound("quantitiesInTransit.doesNotContain=" + DEFAULT_QUANTITIES_IN_TRANSIT);

        // Get all the inventoryList where quantitiesInTransit does not contain UPDATED_QUANTITIES_IN_TRANSIT
        defaultInventoryShouldBeFound("quantitiesInTransit.doesNotContain=" + UPDATED_QUANTITIES_IN_TRANSIT);
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
        defaultInventoryShouldBeFound(
            "actualDailyConsumption.in=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION + "," + UPDATED_ACTUAL_DAILY_CONSUMPTION
        );

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
    public void getAllInventoriesByActualDailyConsumptionContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption contains DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.contains=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption contains UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.contains=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByActualDailyConsumptionNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where actualDailyConsumption does not contain DEFAULT_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldNotBeFound("actualDailyConsumption.doesNotContain=" + DEFAULT_ACTUAL_DAILY_CONSUMPTION);

        // Get all the inventoryList where actualDailyConsumption does not contain UPDATED_ACTUAL_DAILY_CONSUMPTION
        defaultInventoryShouldBeFound("actualDailyConsumption.doesNotContain=" + UPDATED_ACTUAL_DAILY_CONSUMPTION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel equals to DEFAULT_RECORD_LEVEL
        defaultInventoryShouldBeFound("recordLevel.equals=" + DEFAULT_RECORD_LEVEL);

        // Get all the inventoryList where recordLevel equals to UPDATED_RECORD_LEVEL
        defaultInventoryShouldNotBeFound("recordLevel.equals=" + UPDATED_RECORD_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel not equals to DEFAULT_RECORD_LEVEL
        defaultInventoryShouldNotBeFound("recordLevel.notEquals=" + DEFAULT_RECORD_LEVEL);

        // Get all the inventoryList where recordLevel not equals to UPDATED_RECORD_LEVEL
        defaultInventoryShouldBeFound("recordLevel.notEquals=" + UPDATED_RECORD_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel in DEFAULT_RECORD_LEVEL or UPDATED_RECORD_LEVEL
        defaultInventoryShouldBeFound("recordLevel.in=" + DEFAULT_RECORD_LEVEL + "," + UPDATED_RECORD_LEVEL);

        // Get all the inventoryList where recordLevel equals to UPDATED_RECORD_LEVEL
        defaultInventoryShouldNotBeFound("recordLevel.in=" + UPDATED_RECORD_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel is not null
        defaultInventoryShouldBeFound("recordLevel.specified=true");

        // Get all the inventoryList where recordLevel is null
        defaultInventoryShouldNotBeFound("recordLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel contains DEFAULT_RECORD_LEVEL
        defaultInventoryShouldBeFound("recordLevel.contains=" + DEFAULT_RECORD_LEVEL);

        // Get all the inventoryList where recordLevel contains UPDATED_RECORD_LEVEL
        defaultInventoryShouldNotBeFound("recordLevel.contains=" + UPDATED_RECORD_LEVEL);
    }

    @Test
    @Transactional
    public void getAllInventoriesByRecordLevelNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where recordLevel does not contain DEFAULT_RECORD_LEVEL
        defaultInventoryShouldNotBeFound("recordLevel.doesNotContain=" + DEFAULT_RECORD_LEVEL);

        // Get all the inventoryList where recordLevel does not contain UPDATED_RECORD_LEVEL
        defaultInventoryShouldBeFound("recordLevel.doesNotContain=" + UPDATED_RECORD_LEVEL);
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
    public void getAllInventoriesBySuggestedQuantityContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity contains DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.contains=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity contains UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.contains=" + UPDATED_SUGGESTED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesBySuggestedQuantityNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where suggestedQuantity does not contain DEFAULT_SUGGESTED_QUANTITY
        defaultInventoryShouldNotBeFound("suggestedQuantity.doesNotContain=" + DEFAULT_SUGGESTED_QUANTITY);

        // Get all the inventoryList where suggestedQuantity does not contain UPDATED_SUGGESTED_QUANTITY
        defaultInventoryShouldBeFound("suggestedQuantity.doesNotContain=" + UPDATED_SUGGESTED_QUANTITY);
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
    public void getAllInventoriesByExpectedCoveringDayContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay contains DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.contains=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay contains UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.contains=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByExpectedCoveringDayNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where expectedCoveringDay does not contain DEFAULT_EXPECTED_COVERING_DAY
        defaultInventoryShouldNotBeFound("expectedCoveringDay.doesNotContain=" + DEFAULT_EXPECTED_COVERING_DAY);

        // Get all the inventoryList where expectedCoveringDay does not contain UPDATED_EXPECTED_COVERING_DAY
        defaultInventoryShouldBeFound("expectedCoveringDay.doesNotContain=" + UPDATED_EXPECTED_COVERING_DAY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity equals to DEFAULT_QUANTITY
        defaultInventoryShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the inventoryList where quantity equals to UPDATED_QUANTITY
        defaultInventoryShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity not equals to DEFAULT_QUANTITY
        defaultInventoryShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the inventoryList where quantity not equals to UPDATED_QUANTITY
        defaultInventoryShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultInventoryShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the inventoryList where quantity equals to UPDATED_QUANTITY
        defaultInventoryShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity is not null
        defaultInventoryShouldBeFound("quantity.specified=true");

        // Get all the inventoryList where quantity is null
        defaultInventoryShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity contains DEFAULT_QUANTITY
        defaultInventoryShouldBeFound("quantity.contains=" + DEFAULT_QUANTITY);

        // Get all the inventoryList where quantity contains UPDATED_QUANTITY
        defaultInventoryShouldNotBeFound("quantity.contains=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByQuantityNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where quantity does not contain DEFAULT_QUANTITY
        defaultInventoryShouldNotBeFound("quantity.doesNotContain=" + DEFAULT_QUANTITY);

        // Get all the inventoryList where quantity does not contain UPDATED_QUANTITY
        defaultInventoryShouldBeFound("quantity.doesNotContain=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location equals to DEFAULT_LOCATION
        defaultInventoryShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the inventoryList where location equals to UPDATED_LOCATION
        defaultInventoryShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location not equals to DEFAULT_LOCATION
        defaultInventoryShouldNotBeFound("location.notEquals=" + DEFAULT_LOCATION);

        // Get all the inventoryList where location not equals to UPDATED_LOCATION
        defaultInventoryShouldBeFound("location.notEquals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultInventoryShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the inventoryList where location equals to UPDATED_LOCATION
        defaultInventoryShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location is not null
        defaultInventoryShouldBeFound("location.specified=true");

        // Get all the inventoryList where location is null
        defaultInventoryShouldNotBeFound("location.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location contains DEFAULT_LOCATION
        defaultInventoryShouldBeFound("location.contains=" + DEFAULT_LOCATION);

        // Get all the inventoryList where location contains UPDATED_LOCATION
        defaultInventoryShouldNotBeFound("location.contains=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLocationNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where location does not contain DEFAULT_LOCATION
        defaultInventoryShouldNotBeFound("location.doesNotContain=" + DEFAULT_LOCATION);

        // Get all the inventoryList where location does not contain UPDATED_LOCATION
        defaultInventoryShouldBeFound("location.doesNotContain=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated equals to DEFAULT_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.equals=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated equals to UPDATED_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.equals=" + UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated not equals to DEFAULT_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.notEquals=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated not equals to UPDATED_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.notEquals=" + UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated in DEFAULT_LASTER_UPDATED or UPDATED_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.in=" + DEFAULT_LASTER_UPDATED + "," + UPDATED_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated equals to UPDATED_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.in=" + UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated is not null
        defaultInventoryShouldBeFound("lasterUpdated.specified=true");

        // Get all the inventoryList where lasterUpdated is null
        defaultInventoryShouldNotBeFound("lasterUpdated.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated is greater than or equal to DEFAULT_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.greaterThanOrEqual=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated is greater than or equal to UPDATED_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.greaterThanOrEqual=" + UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated is less than or equal to DEFAULT_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.lessThanOrEqual=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated is less than or equal to SMALLER_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.lessThanOrEqual=" + SMALLER_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated is less than DEFAULT_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.lessThan=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated is less than UPDATED_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.lessThan=" + UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void getAllInventoriesByLasterUpdatedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryRepository.saveAndFlush(inventory);

        // Get all the inventoryList where lasterUpdated is greater than DEFAULT_LASTER_UPDATED
        defaultInventoryShouldNotBeFound("lasterUpdated.greaterThan=" + DEFAULT_LASTER_UPDATED);

        // Get all the inventoryList where lasterUpdated is greater than SMALLER_LASTER_UPDATED
        defaultInventoryShouldBeFound("lasterUpdated.greaterThan=" + SMALLER_LASTER_UPDATED);
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
        restInventoryMockMvc
            .perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inventoryId").value(hasItem(DEFAULT_INVENTORY_ID)))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quantitiesInHand").value(hasItem(DEFAULT_QUANTITIES_IN_HAND)))
            .andExpect(jsonPath("$.[*].quantitiesInTransit").value(hasItem(DEFAULT_QUANTITIES_IN_TRANSIT)))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM)))
            .andExpect(jsonPath("$.[*].actualDailyConsumption").value(hasItem(DEFAULT_ACTUAL_DAILY_CONSUMPTION)))
            .andExpect(jsonPath("$.[*].recordLevel").value(hasItem(DEFAULT_RECORD_LEVEL)))
            .andExpect(jsonPath("$.[*].suggestedQuantity").value(hasItem(DEFAULT_SUGGESTED_QUANTITY)))
            .andExpect(jsonPath("$.[*].expectedCoveringDay").value(hasItem(DEFAULT_EXPECTED_COVERING_DAY)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].lasterUpdated").value(hasItem(DEFAULT_LASTER_UPDATED.toString())));

        // Check, that the count call also returns 1
        restInventoryMockMvc
            .perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryShouldNotBeFound(String filter) throws Exception {
        restInventoryMockMvc
            .perform(get("/api/inventories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryMockMvc
            .perform(get("/api/inventories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventory() throws Exception {
        // Get the inventory
        restInventoryMockMvc.perform(get("/api/inventories/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
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
            .itemCode(UPDATED_ITEM_CODE)
            .description(UPDATED_DESCRIPTION)
            .quantitiesInHand(UPDATED_QUANTITIES_IN_HAND)
            .quantitiesInTransit(UPDATED_QUANTITIES_IN_TRANSIT)
            .uom(UPDATED_UOM)
            .actualDailyConsumption(UPDATED_ACTUAL_DAILY_CONSUMPTION)
            .recordLevel(UPDATED_RECORD_LEVEL)
            .suggestedQuantity(UPDATED_SUGGESTED_QUANTITY)
            .expectedCoveringDay(UPDATED_EXPECTED_COVERING_DAY)
            .quantity(UPDATED_QUANTITY)
            .location(UPDATED_LOCATION)
            .lasterUpdated(UPDATED_LASTER_UPDATED);

        restInventoryMockMvc
            .perform(
                put("/api/inventories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedInventory))
            )
            .andExpect(status().isOk());

        // Validate the Inventory in the database
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeUpdate);
        Inventory testInventory = inventoryList.get(inventoryList.size() - 1);
        assertThat(testInventory.getInventoryId()).isEqualTo(UPDATED_INVENTORY_ID);
        assertThat(testInventory.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testInventory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testInventory.getQuantitiesInHand()).isEqualTo(UPDATED_QUANTITIES_IN_HAND);
        assertThat(testInventory.getQuantitiesInTransit()).isEqualTo(UPDATED_QUANTITIES_IN_TRANSIT);
        assertThat(testInventory.getUom()).isEqualTo(UPDATED_UOM);
        assertThat(testInventory.getActualDailyConsumption()).isEqualTo(UPDATED_ACTUAL_DAILY_CONSUMPTION);
        assertThat(testInventory.getRecordLevel()).isEqualTo(UPDATED_RECORD_LEVEL);
        assertThat(testInventory.getSuggestedQuantity()).isEqualTo(UPDATED_SUGGESTED_QUANTITY);
        assertThat(testInventory.getExpectedCoveringDay()).isEqualTo(UPDATED_EXPECTED_COVERING_DAY);
        assertThat(testInventory.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInventory.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testInventory.getLasterUpdated()).isEqualTo(UPDATED_LASTER_UPDATED);
    }

    @Test
    @Transactional
    public void updateNonExistingInventory() throws Exception {
        int databaseSizeBeforeUpdate = inventoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryMockMvc
            .perform(put("/api/inventories").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inventory)))
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
        restInventoryMockMvc
            .perform(delete("/api/inventories/{id}", inventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventory> inventoryList = inventoryRepository.findAll();
        assertThat(inventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
