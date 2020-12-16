package com.axilog.cov.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.repository.OutletRepository;
import com.axilog.cov.service.OutletQueryService;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.dto.OutletCriteria;
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
 * Integration tests for the {@link OutletResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutletResourceIT {
    private static final String DEFAULT_OUTLET_ID = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_LAT = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_LAT = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_LONG = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_LONG = "BBBBBBBBBB";

    @Autowired
    private OutletRepository outletRepository;

    @Autowired
    private OutletService outletService;

    @Autowired
    private OutletQueryService outletQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOutletMockMvc;

    private Outlet outlet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outlet createEntity(EntityManager em) {
        Outlet outlet = new Outlet()
            .outletId(DEFAULT_OUTLET_ID)
            .outletName(DEFAULT_OUTLET_NAME)
            .outletLocation(DEFAULT_OUTLET_LOCATION)
            .outletLat(DEFAULT_OUTLET_LAT)
            .outletLong(DEFAULT_OUTLET_LONG);
        return outlet;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Outlet createUpdatedEntity(EntityManager em) {
        Outlet outlet = new Outlet()
            .outletId(UPDATED_OUTLET_ID)
            .outletName(UPDATED_OUTLET_NAME)
            .outletLocation(UPDATED_OUTLET_LOCATION)
            .outletLat(UPDATED_OUTLET_LAT)
            .outletLong(UPDATED_OUTLET_LONG);
        return outlet;
    }

    @BeforeEach
    public void initTest() {
        outlet = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutlet() throws Exception {
        int databaseSizeBeforeCreate = outletRepository.findAll().size();
        // Create the Outlet
        restOutletMockMvc
            .perform(post("/api/outlets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outlet)))
            .andExpect(status().isCreated());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeCreate + 1);
        Outlet testOutlet = outletList.get(outletList.size() - 1);
        assertThat(testOutlet.getOutletId()).isEqualTo(DEFAULT_OUTLET_ID);
        assertThat(testOutlet.getOutletName()).isEqualTo(DEFAULT_OUTLET_NAME);
        assertThat(testOutlet.getOutletLocation()).isEqualTo(DEFAULT_OUTLET_LOCATION);
        assertThat(testOutlet.getOutletLat()).isEqualTo(DEFAULT_OUTLET_LAT);
        assertThat(testOutlet.getOutletLong()).isEqualTo(DEFAULT_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void createOutletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outletRepository.findAll().size();

        // Create the Outlet with an existing ID
        outlet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutletMockMvc
            .perform(post("/api/outlets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outlet)))
            .andExpect(status().isBadRequest());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOutlets() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList
        restOutletMockMvc
            .perform(get("/api/outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].outletId").value(hasItem(DEFAULT_OUTLET_ID)))
            .andExpect(jsonPath("$.[*].outletName").value(hasItem(DEFAULT_OUTLET_NAME)))
            .andExpect(jsonPath("$.[*].outletLocation").value(hasItem(DEFAULT_OUTLET_LOCATION)))
            .andExpect(jsonPath("$.[*].outletLat").value(hasItem(DEFAULT_OUTLET_LAT)))
            .andExpect(jsonPath("$.[*].outletLong").value(hasItem(DEFAULT_OUTLET_LONG)));
    }

    @Test
    @Transactional
    public void getOutlet() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get the outlet
        restOutletMockMvc
            .perform(get("/api/outlets/{id}", outlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outlet.getId().intValue()))
            .andExpect(jsonPath("$.outletId").value(DEFAULT_OUTLET_ID))
            .andExpect(jsonPath("$.outletName").value(DEFAULT_OUTLET_NAME))
            .andExpect(jsonPath("$.outletLocation").value(DEFAULT_OUTLET_LOCATION))
            .andExpect(jsonPath("$.outletLat").value(DEFAULT_OUTLET_LAT))
            .andExpect(jsonPath("$.outletLong").value(DEFAULT_OUTLET_LONG));
    }

    @Test
    @Transactional
    public void getOutletsByIdFiltering() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        Long id = outlet.getId();

        defaultOutletShouldBeFound("id.equals=" + id);
        defaultOutletShouldNotBeFound("id.notEquals=" + id);

        defaultOutletShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOutletShouldNotBeFound("id.greaterThan=" + id);

        defaultOutletShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOutletShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId equals to DEFAULT_OUTLET_ID
        defaultOutletShouldBeFound("outletId.equals=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId equals to UPDATED_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.equals=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId not equals to DEFAULT_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.notEquals=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId not equals to UPDATED_OUTLET_ID
        defaultOutletShouldBeFound("outletId.notEquals=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId in DEFAULT_OUTLET_ID or UPDATED_OUTLET_ID
        defaultOutletShouldBeFound("outletId.in=" + DEFAULT_OUTLET_ID + "," + UPDATED_OUTLET_ID);

        // Get all the outletList where outletId equals to UPDATED_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.in=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId is not null
        defaultOutletShouldBeFound("outletId.specified=true");

        // Get all the outletList where outletId is null
        defaultOutletShouldNotBeFound("outletId.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId contains DEFAULT_OUTLET_ID
        defaultOutletShouldBeFound("outletId.contains=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId contains UPDATED_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.contains=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId does not contain DEFAULT_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.doesNotContain=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId does not contain UPDATED_OUTLET_ID
        defaultOutletShouldBeFound("outletId.doesNotContain=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName equals to DEFAULT_OUTLET_NAME
        defaultOutletShouldBeFound("outletName.equals=" + DEFAULT_OUTLET_NAME);

        // Get all the outletList where outletName equals to UPDATED_OUTLET_NAME
        defaultOutletShouldNotBeFound("outletName.equals=" + UPDATED_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName not equals to DEFAULT_OUTLET_NAME
        defaultOutletShouldNotBeFound("outletName.notEquals=" + DEFAULT_OUTLET_NAME);

        // Get all the outletList where outletName not equals to UPDATED_OUTLET_NAME
        defaultOutletShouldBeFound("outletName.notEquals=" + UPDATED_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName in DEFAULT_OUTLET_NAME or UPDATED_OUTLET_NAME
        defaultOutletShouldBeFound("outletName.in=" + DEFAULT_OUTLET_NAME + "," + UPDATED_OUTLET_NAME);

        // Get all the outletList where outletName equals to UPDATED_OUTLET_NAME
        defaultOutletShouldNotBeFound("outletName.in=" + UPDATED_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName is not null
        defaultOutletShouldBeFound("outletName.specified=true");

        // Get all the outletList where outletName is null
        defaultOutletShouldNotBeFound("outletName.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName contains DEFAULT_OUTLET_NAME
        defaultOutletShouldBeFound("outletName.contains=" + DEFAULT_OUTLET_NAME);

        // Get all the outletList where outletName contains UPDATED_OUTLET_NAME
        defaultOutletShouldNotBeFound("outletName.contains=" + UPDATED_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletNameNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletName does not contain DEFAULT_OUTLET_NAME
        defaultOutletShouldNotBeFound("outletName.doesNotContain=" + DEFAULT_OUTLET_NAME);

        // Get all the outletList where outletName does not contain UPDATED_OUTLET_NAME
        defaultOutletShouldBeFound("outletName.doesNotContain=" + UPDATED_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation equals to DEFAULT_OUTLET_LOCATION
        defaultOutletShouldBeFound("outletLocation.equals=" + DEFAULT_OUTLET_LOCATION);

        // Get all the outletList where outletLocation equals to UPDATED_OUTLET_LOCATION
        defaultOutletShouldNotBeFound("outletLocation.equals=" + UPDATED_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation not equals to DEFAULT_OUTLET_LOCATION
        defaultOutletShouldNotBeFound("outletLocation.notEquals=" + DEFAULT_OUTLET_LOCATION);

        // Get all the outletList where outletLocation not equals to UPDATED_OUTLET_LOCATION
        defaultOutletShouldBeFound("outletLocation.notEquals=" + UPDATED_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation in DEFAULT_OUTLET_LOCATION or UPDATED_OUTLET_LOCATION
        defaultOutletShouldBeFound("outletLocation.in=" + DEFAULT_OUTLET_LOCATION + "," + UPDATED_OUTLET_LOCATION);

        // Get all the outletList where outletLocation equals to UPDATED_OUTLET_LOCATION
        defaultOutletShouldNotBeFound("outletLocation.in=" + UPDATED_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation is not null
        defaultOutletShouldBeFound("outletLocation.specified=true");

        // Get all the outletList where outletLocation is null
        defaultOutletShouldNotBeFound("outletLocation.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation contains DEFAULT_OUTLET_LOCATION
        defaultOutletShouldBeFound("outletLocation.contains=" + DEFAULT_OUTLET_LOCATION);

        // Get all the outletList where outletLocation contains UPDATED_OUTLET_LOCATION
        defaultOutletShouldNotBeFound("outletLocation.contains=" + UPDATED_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLocationNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLocation does not contain DEFAULT_OUTLET_LOCATION
        defaultOutletShouldNotBeFound("outletLocation.doesNotContain=" + DEFAULT_OUTLET_LOCATION);

        // Get all the outletList where outletLocation does not contain UPDATED_OUTLET_LOCATION
        defaultOutletShouldBeFound("outletLocation.doesNotContain=" + UPDATED_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat equals to DEFAULT_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.equals=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat equals to UPDATED_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.equals=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat not equals to DEFAULT_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.notEquals=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat not equals to UPDATED_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.notEquals=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat in DEFAULT_OUTLET_LAT or UPDATED_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.in=" + DEFAULT_OUTLET_LAT + "," + UPDATED_OUTLET_LAT);

        // Get all the outletList where outletLat equals to UPDATED_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.in=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat is not null
        defaultOutletShouldBeFound("outletLat.specified=true");

        // Get all the outletList where outletLat is null
        defaultOutletShouldNotBeFound("outletLat.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat contains DEFAULT_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.contains=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat contains UPDATED_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.contains=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat does not contain DEFAULT_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.doesNotContain=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat does not contain UPDATED_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.doesNotContain=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong equals to DEFAULT_OUTLET_LONG
        defaultOutletShouldBeFound("outletLong.equals=" + DEFAULT_OUTLET_LONG);

        // Get all the outletList where outletLong equals to UPDATED_OUTLET_LONG
        defaultOutletShouldNotBeFound("outletLong.equals=" + UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong not equals to DEFAULT_OUTLET_LONG
        defaultOutletShouldNotBeFound("outletLong.notEquals=" + DEFAULT_OUTLET_LONG);

        // Get all the outletList where outletLong not equals to UPDATED_OUTLET_LONG
        defaultOutletShouldBeFound("outletLong.notEquals=" + UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong in DEFAULT_OUTLET_LONG or UPDATED_OUTLET_LONG
        defaultOutletShouldBeFound("outletLong.in=" + DEFAULT_OUTLET_LONG + "," + UPDATED_OUTLET_LONG);

        // Get all the outletList where outletLong equals to UPDATED_OUTLET_LONG
        defaultOutletShouldNotBeFound("outletLong.in=" + UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong is not null
        defaultOutletShouldBeFound("outletLong.specified=true");

        // Get all the outletList where outletLong is null
        defaultOutletShouldNotBeFound("outletLong.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong contains DEFAULT_OUTLET_LONG
        defaultOutletShouldBeFound("outletLong.contains=" + DEFAULT_OUTLET_LONG);

        // Get all the outletList where outletLong contains UPDATED_OUTLET_LONG
        defaultOutletShouldNotBeFound("outletLong.contains=" + UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLongNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLong does not contain DEFAULT_OUTLET_LONG
        defaultOutletShouldNotBeFound("outletLong.doesNotContain=" + DEFAULT_OUTLET_LONG);

        // Get all the outletList where outletLong does not contain UPDATED_OUTLET_LONG
        defaultOutletShouldBeFound("outletLong.doesNotContain=" + UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void getAllOutletsByInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);
        Inventory inventory = InventoryResourceIT.createEntity(em);
        em.persist(inventory);
        em.flush();
        outlet.addInventory(inventory);
        outletRepository.saveAndFlush(outlet);
        Long inventoryId = inventory.getId();

        // Get all the outletList where inventory equals to inventoryId
        defaultOutletShouldBeFound("inventoryId.equals=" + inventoryId);

        // Get all the outletList where inventory equals to inventoryId + 1
        defaultOutletShouldNotBeFound("inventoryId.equals=" + (inventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOutletShouldBeFound(String filter) throws Exception {
        restOutletMockMvc
            .perform(get("/api/outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].outletId").value(hasItem(DEFAULT_OUTLET_ID)))
            .andExpect(jsonPath("$.[*].outletName").value(hasItem(DEFAULT_OUTLET_NAME)))
            .andExpect(jsonPath("$.[*].outletLocation").value(hasItem(DEFAULT_OUTLET_LOCATION)))
            .andExpect(jsonPath("$.[*].outletLat").value(hasItem(DEFAULT_OUTLET_LAT)))
            .andExpect(jsonPath("$.[*].outletLong").value(hasItem(DEFAULT_OUTLET_LONG)));

        // Check, that the count call also returns 1
        restOutletMockMvc
            .perform(get("/api/outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOutletShouldNotBeFound(String filter) throws Exception {
        restOutletMockMvc
            .perform(get("/api/outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOutletMockMvc
            .perform(get("/api/outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOutlet() throws Exception {
        // Get the outlet
        restOutletMockMvc.perform(get("/api/outlets/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutlet() throws Exception {
        // Initialize the database
        outletService.save(outlet);

        int databaseSizeBeforeUpdate = outletRepository.findAll().size();

        // Update the outlet
        Outlet updatedOutlet = outletRepository.findById(outlet.getId()).get();
        // Disconnect from session so that the updates on updatedOutlet are not directly saved in db
        em.detach(updatedOutlet);
        updatedOutlet
            .outletId(UPDATED_OUTLET_ID)
            .outletName(UPDATED_OUTLET_NAME)
            .outletLocation(UPDATED_OUTLET_LOCATION)
            .outletLat(UPDATED_OUTLET_LAT)
            .outletLong(UPDATED_OUTLET_LONG);

        restOutletMockMvc
            .perform(put("/api/outlets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedOutlet)))
            .andExpect(status().isOk());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeUpdate);
        Outlet testOutlet = outletList.get(outletList.size() - 1);
        assertThat(testOutlet.getOutletId()).isEqualTo(UPDATED_OUTLET_ID);
        assertThat(testOutlet.getOutletName()).isEqualTo(UPDATED_OUTLET_NAME);
        assertThat(testOutlet.getOutletLocation()).isEqualTo(UPDATED_OUTLET_LOCATION);
        assertThat(testOutlet.getOutletLat()).isEqualTo(UPDATED_OUTLET_LAT);
        assertThat(testOutlet.getOutletLong()).isEqualTo(UPDATED_OUTLET_LONG);
    }

    @Test
    @Transactional
    public void updateNonExistingOutlet() throws Exception {
        int databaseSizeBeforeUpdate = outletRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutletMockMvc
            .perform(put("/api/outlets").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(outlet)))
            .andExpect(status().isBadRequest());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOutlet() throws Exception {
        // Initialize the database
        outletService.save(outlet);

        int databaseSizeBeforeDelete = outletRepository.findAll().size();

        // Delete the outlet
        restOutletMockMvc
            .perform(delete("/api/outlets/{id}", outlet.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
