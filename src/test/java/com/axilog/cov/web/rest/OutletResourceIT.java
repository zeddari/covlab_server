package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Inventory;
import com.axilog.cov.repository.OutletRepository;
import com.axilog.cov.service.OutletService;
import com.axilog.cov.service.dto.OutletCriteria;
import com.axilog.cov.service.OutletQueryService;

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
 * Integration tests for the {@link OutletResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OutletResourceIT {

    private static final Long DEFAULT_OUTLET_ID = 1L;
    private static final Long UPDATED_OUTLET_ID = 2L;
    private static final Long SMALLER_OUTLET_ID = 1L - 1L;

    private static final String DEFAULT_OUTLET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_REGION = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_OUTLET_ADRESS = "AAAAAAAAAA";
    private static final String UPDATED_OUTLET_ADRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_OUTLET_LAT = 1D;
    private static final Double UPDATED_OUTLET_LAT = 2D;
    private static final Double SMALLER_OUTLET_LAT = 1D - 1D;

    private static final Double DEFAULT_OUTLET_LNG = 1D;
    private static final Double UPDATED_OUTLET_LNG = 2D;
    private static final Double SMALLER_OUTLET_LNG = 1D - 1D;

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
            .outletRegion(DEFAULT_OUTLET_REGION)
            .outletAdress(DEFAULT_OUTLET_ADRESS)
            .outletLat(DEFAULT_OUTLET_LAT)
            .outletLng(DEFAULT_OUTLET_LNG);
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
            .outletRegion(UPDATED_OUTLET_REGION)
            .outletAdress(UPDATED_OUTLET_ADRESS)
            .outletLat(UPDATED_OUTLET_LAT)
            .outletLng(UPDATED_OUTLET_LNG);
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
        restOutletMockMvc.perform(post("/api/outlets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outlet)))
            .andExpect(status().isCreated());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeCreate + 1);
        Outlet testOutlet = outletList.get(outletList.size() - 1);
        assertThat(testOutlet.getOutletId()).isEqualTo(DEFAULT_OUTLET_ID);
        assertThat(testOutlet.getOutletName()).isEqualTo(DEFAULT_OUTLET_NAME);
        assertThat(testOutlet.getOutletRegion()).isEqualTo(DEFAULT_OUTLET_REGION);
        assertThat(testOutlet.getOutletAdress()).isEqualTo(DEFAULT_OUTLET_ADRESS);
        assertThat(testOutlet.getOutletLat()).isEqualTo(DEFAULT_OUTLET_LAT);
        assertThat(testOutlet.getOutletLng()).isEqualTo(DEFAULT_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void createOutletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outletRepository.findAll().size();

        // Create the Outlet with an existing ID
        outlet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutletMockMvc.perform(post("/api/outlets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outlet)))
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
        restOutletMockMvc.perform(get("/api/outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].outletId").value(hasItem(DEFAULT_OUTLET_ID.intValue())))
            .andExpect(jsonPath("$.[*].outletName").value(hasItem(DEFAULT_OUTLET_NAME)))
            .andExpect(jsonPath("$.[*].outletRegion").value(hasItem(DEFAULT_OUTLET_REGION)))
            .andExpect(jsonPath("$.[*].outletAdress").value(hasItem(DEFAULT_OUTLET_ADRESS)))
            .andExpect(jsonPath("$.[*].outletLat").value(hasItem(DEFAULT_OUTLET_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].outletLng").value(hasItem(DEFAULT_OUTLET_LNG.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getOutlet() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get the outlet
        restOutletMockMvc.perform(get("/api/outlets/{id}", outlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(outlet.getId().intValue()))
            .andExpect(jsonPath("$.outletId").value(DEFAULT_OUTLET_ID.intValue()))
            .andExpect(jsonPath("$.outletName").value(DEFAULT_OUTLET_NAME))
            .andExpect(jsonPath("$.outletRegion").value(DEFAULT_OUTLET_REGION))
            .andExpect(jsonPath("$.outletAdress").value(DEFAULT_OUTLET_ADRESS))
            .andExpect(jsonPath("$.outletLat").value(DEFAULT_OUTLET_LAT.doubleValue()))
            .andExpect(jsonPath("$.outletLng").value(DEFAULT_OUTLET_LNG.doubleValue()));
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
    public void getAllOutletsByOutletIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId is greater than or equal to DEFAULT_OUTLET_ID
        defaultOutletShouldBeFound("outletId.greaterThanOrEqual=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId is greater than or equal to UPDATED_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.greaterThanOrEqual=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId is less than or equal to DEFAULT_OUTLET_ID
        defaultOutletShouldBeFound("outletId.lessThanOrEqual=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId is less than or equal to SMALLER_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.lessThanOrEqual=" + SMALLER_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsLessThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId is less than DEFAULT_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.lessThan=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId is less than UPDATED_OUTLET_ID
        defaultOutletShouldBeFound("outletId.lessThan=" + UPDATED_OUTLET_ID);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletId is greater than DEFAULT_OUTLET_ID
        defaultOutletShouldNotBeFound("outletId.greaterThan=" + DEFAULT_OUTLET_ID);

        // Get all the outletList where outletId is greater than SMALLER_OUTLET_ID
        defaultOutletShouldBeFound("outletId.greaterThan=" + SMALLER_OUTLET_ID);
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
    public void getAllOutletsByOutletRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion equals to DEFAULT_OUTLET_REGION
        defaultOutletShouldBeFound("outletRegion.equals=" + DEFAULT_OUTLET_REGION);

        // Get all the outletList where outletRegion equals to UPDATED_OUTLET_REGION
        defaultOutletShouldNotBeFound("outletRegion.equals=" + UPDATED_OUTLET_REGION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletRegionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion not equals to DEFAULT_OUTLET_REGION
        defaultOutletShouldNotBeFound("outletRegion.notEquals=" + DEFAULT_OUTLET_REGION);

        // Get all the outletList where outletRegion not equals to UPDATED_OUTLET_REGION
        defaultOutletShouldBeFound("outletRegion.notEquals=" + UPDATED_OUTLET_REGION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletRegionIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion in DEFAULT_OUTLET_REGION or UPDATED_OUTLET_REGION
        defaultOutletShouldBeFound("outletRegion.in=" + DEFAULT_OUTLET_REGION + "," + UPDATED_OUTLET_REGION);

        // Get all the outletList where outletRegion equals to UPDATED_OUTLET_REGION
        defaultOutletShouldNotBeFound("outletRegion.in=" + UPDATED_OUTLET_REGION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion is not null
        defaultOutletShouldBeFound("outletRegion.specified=true");

        // Get all the outletList where outletRegion is null
        defaultOutletShouldNotBeFound("outletRegion.specified=false");
    }
                @Test
    @Transactional
    public void getAllOutletsByOutletRegionContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion contains DEFAULT_OUTLET_REGION
        defaultOutletShouldBeFound("outletRegion.contains=" + DEFAULT_OUTLET_REGION);

        // Get all the outletList where outletRegion contains UPDATED_OUTLET_REGION
        defaultOutletShouldNotBeFound("outletRegion.contains=" + UPDATED_OUTLET_REGION);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletRegionNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletRegion does not contain DEFAULT_OUTLET_REGION
        defaultOutletShouldNotBeFound("outletRegion.doesNotContain=" + DEFAULT_OUTLET_REGION);

        // Get all the outletList where outletRegion does not contain UPDATED_OUTLET_REGION
        defaultOutletShouldBeFound("outletRegion.doesNotContain=" + UPDATED_OUTLET_REGION);
    }


    @Test
    @Transactional
    public void getAllOutletsByOutletAdressIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress equals to DEFAULT_OUTLET_ADRESS
        defaultOutletShouldBeFound("outletAdress.equals=" + DEFAULT_OUTLET_ADRESS);

        // Get all the outletList where outletAdress equals to UPDATED_OUTLET_ADRESS
        defaultOutletShouldNotBeFound("outletAdress.equals=" + UPDATED_OUTLET_ADRESS);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletAdressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress not equals to DEFAULT_OUTLET_ADRESS
        defaultOutletShouldNotBeFound("outletAdress.notEquals=" + DEFAULT_OUTLET_ADRESS);

        // Get all the outletList where outletAdress not equals to UPDATED_OUTLET_ADRESS
        defaultOutletShouldBeFound("outletAdress.notEquals=" + UPDATED_OUTLET_ADRESS);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletAdressIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress in DEFAULT_OUTLET_ADRESS or UPDATED_OUTLET_ADRESS
        defaultOutletShouldBeFound("outletAdress.in=" + DEFAULT_OUTLET_ADRESS + "," + UPDATED_OUTLET_ADRESS);

        // Get all the outletList where outletAdress equals to UPDATED_OUTLET_ADRESS
        defaultOutletShouldNotBeFound("outletAdress.in=" + UPDATED_OUTLET_ADRESS);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletAdressIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress is not null
        defaultOutletShouldBeFound("outletAdress.specified=true");

        // Get all the outletList where outletAdress is null
        defaultOutletShouldNotBeFound("outletAdress.specified=false");
    }
                @Test
    @Transactional
    public void getAllOutletsByOutletAdressContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress contains DEFAULT_OUTLET_ADRESS
        defaultOutletShouldBeFound("outletAdress.contains=" + DEFAULT_OUTLET_ADRESS);

        // Get all the outletList where outletAdress contains UPDATED_OUTLET_ADRESS
        defaultOutletShouldNotBeFound("outletAdress.contains=" + UPDATED_OUTLET_ADRESS);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletAdressNotContainsSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletAdress does not contain DEFAULT_OUTLET_ADRESS
        defaultOutletShouldNotBeFound("outletAdress.doesNotContain=" + DEFAULT_OUTLET_ADRESS);

        // Get all the outletList where outletAdress does not contain UPDATED_OUTLET_ADRESS
        defaultOutletShouldBeFound("outletAdress.doesNotContain=" + UPDATED_OUTLET_ADRESS);
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
    public void getAllOutletsByOutletLatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat is greater than or equal to DEFAULT_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.greaterThanOrEqual=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat is greater than or equal to UPDATED_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.greaterThanOrEqual=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat is less than or equal to DEFAULT_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.lessThanOrEqual=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat is less than or equal to SMALLER_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.lessThanOrEqual=" + SMALLER_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsLessThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat is less than DEFAULT_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.lessThan=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat is less than UPDATED_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.lessThan=" + UPDATED_OUTLET_LAT);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLat is greater than DEFAULT_OUTLET_LAT
        defaultOutletShouldNotBeFound("outletLat.greaterThan=" + DEFAULT_OUTLET_LAT);

        // Get all the outletList where outletLat is greater than SMALLER_OUTLET_LAT
        defaultOutletShouldBeFound("outletLat.greaterThan=" + SMALLER_OUTLET_LAT);
    }


    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng equals to DEFAULT_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.equals=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng equals to UPDATED_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.equals=" + UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsNotEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng not equals to DEFAULT_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.notEquals=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng not equals to UPDATED_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.notEquals=" + UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsInShouldWork() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng in DEFAULT_OUTLET_LNG or UPDATED_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.in=" + DEFAULT_OUTLET_LNG + "," + UPDATED_OUTLET_LNG);

        // Get all the outletList where outletLng equals to UPDATED_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.in=" + UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsNullOrNotNull() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng is not null
        defaultOutletShouldBeFound("outletLng.specified=true");

        // Get all the outletList where outletLng is null
        defaultOutletShouldNotBeFound("outletLng.specified=false");
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng is greater than or equal to DEFAULT_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.greaterThanOrEqual=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng is greater than or equal to UPDATED_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.greaterThanOrEqual=" + UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng is less than or equal to DEFAULT_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.lessThanOrEqual=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng is less than or equal to SMALLER_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.lessThanOrEqual=" + SMALLER_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsLessThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng is less than DEFAULT_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.lessThan=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng is less than UPDATED_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.lessThan=" + UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void getAllOutletsByOutletLngIsGreaterThanSomething() throws Exception {
        // Initialize the database
        outletRepository.saveAndFlush(outlet);

        // Get all the outletList where outletLng is greater than DEFAULT_OUTLET_LNG
        defaultOutletShouldNotBeFound("outletLng.greaterThan=" + DEFAULT_OUTLET_LNG);

        // Get all the outletList where outletLng is greater than SMALLER_OUTLET_LNG
        defaultOutletShouldBeFound("outletLng.greaterThan=" + SMALLER_OUTLET_LNG);
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
        restOutletMockMvc.perform(get("/api/outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].outletId").value(hasItem(DEFAULT_OUTLET_ID.intValue())))
            .andExpect(jsonPath("$.[*].outletName").value(hasItem(DEFAULT_OUTLET_NAME)))
            .andExpect(jsonPath("$.[*].outletRegion").value(hasItem(DEFAULT_OUTLET_REGION)))
            .andExpect(jsonPath("$.[*].outletAdress").value(hasItem(DEFAULT_OUTLET_ADRESS)))
            .andExpect(jsonPath("$.[*].outletLat").value(hasItem(DEFAULT_OUTLET_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].outletLng").value(hasItem(DEFAULT_OUTLET_LNG.doubleValue())));

        // Check, that the count call also returns 1
        restOutletMockMvc.perform(get("/api/outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOutletShouldNotBeFound(String filter) throws Exception {
        restOutletMockMvc.perform(get("/api/outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOutletMockMvc.perform(get("/api/outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOutlet() throws Exception {
        // Get the outlet
        restOutletMockMvc.perform(get("/api/outlets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
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
            .outletRegion(UPDATED_OUTLET_REGION)
            .outletAdress(UPDATED_OUTLET_ADRESS)
            .outletLat(UPDATED_OUTLET_LAT)
            .outletLng(UPDATED_OUTLET_LNG);

        restOutletMockMvc.perform(put("/api/outlets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutlet)))
            .andExpect(status().isOk());

        // Validate the Outlet in the database
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeUpdate);
        Outlet testOutlet = outletList.get(outletList.size() - 1);
        assertThat(testOutlet.getOutletId()).isEqualTo(UPDATED_OUTLET_ID);
        assertThat(testOutlet.getOutletName()).isEqualTo(UPDATED_OUTLET_NAME);
        assertThat(testOutlet.getOutletRegion()).isEqualTo(UPDATED_OUTLET_REGION);
        assertThat(testOutlet.getOutletAdress()).isEqualTo(UPDATED_OUTLET_ADRESS);
        assertThat(testOutlet.getOutletLat()).isEqualTo(UPDATED_OUTLET_LAT);
        assertThat(testOutlet.getOutletLng()).isEqualTo(UPDATED_OUTLET_LNG);
    }

    @Test
    @Transactional
    public void updateNonExistingOutlet() throws Exception {
        int databaseSizeBeforeUpdate = outletRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOutletMockMvc.perform(put("/api/outlets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(outlet)))
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
        restOutletMockMvc.perform(delete("/api/outlets/{id}", outlet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Outlet> outletList = outletRepository.findAll();
        assertThat(outletList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
