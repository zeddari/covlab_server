package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.PoStatus;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.repository.PoStatusRepository;
import com.axilog.cov.service.PoStatusService;
import com.axilog.cov.service.dto.PoStatusCriteria;
import com.axilog.cov.service.PoStatusQueryService;

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
 * Integration tests for the {@link PoStatusResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PoStatusResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_UPDATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATED_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_UPDATED_AT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PoStatusRepository poStatusRepository;

    @Autowired
    private PoStatusService poStatusService;

    @Autowired
    private PoStatusQueryService poStatusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPoStatusMockMvc;

    private PoStatus poStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoStatus createEntity(EntityManager em) {
        PoStatus poStatus = new PoStatus()
            .status(DEFAULT_STATUS)
            .updatedAt(DEFAULT_UPDATED_AT);
        return poStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PoStatus createUpdatedEntity(EntityManager em) {
        PoStatus poStatus = new PoStatus()
            .status(UPDATED_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);
        return poStatus;
    }

    @BeforeEach
    public void initTest() {
        poStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoStatus() throws Exception {
        int databaseSizeBeforeCreate = poStatusRepository.findAll().size();
        // Create the PoStatus
        restPoStatusMockMvc.perform(post("/api/po-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poStatus)))
            .andExpect(status().isCreated());

        // Validate the PoStatus in the database
        List<PoStatus> poStatusList = poStatusRepository.findAll();
        assertThat(poStatusList).hasSize(databaseSizeBeforeCreate + 1);
        PoStatus testPoStatus = poStatusList.get(poStatusList.size() - 1);
        assertThat(testPoStatus.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPoStatus.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPoStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poStatusRepository.findAll().size();

        // Create the PoStatus with an existing ID
        poStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoStatusMockMvc.perform(post("/api/po-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PoStatus in the database
        List<PoStatus> poStatusList = poStatusRepository.findAll();
        assertThat(poStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPoStatuses() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList
        restPoStatusMockMvc.perform(get("/api/po-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPoStatus() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get the poStatus
        restPoStatusMockMvc.perform(get("/api/po-statuses/{id}", poStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(poStatus.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }


    @Test
    @Transactional
    public void getPoStatusesByIdFiltering() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        Long id = poStatus.getId();

        defaultPoStatusShouldBeFound("id.equals=" + id);
        defaultPoStatusShouldNotBeFound("id.notEquals=" + id);

        defaultPoStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPoStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultPoStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPoStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPoStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status equals to DEFAULT_STATUS
        defaultPoStatusShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the poStatusList where status equals to UPDATED_STATUS
        defaultPoStatusShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status not equals to DEFAULT_STATUS
        defaultPoStatusShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the poStatusList where status not equals to UPDATED_STATUS
        defaultPoStatusShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPoStatusShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the poStatusList where status equals to UPDATED_STATUS
        defaultPoStatusShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status is not null
        defaultPoStatusShouldBeFound("status.specified=true");

        // Get all the poStatusList where status is null
        defaultPoStatusShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllPoStatusesByStatusContainsSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status contains DEFAULT_STATUS
        defaultPoStatusShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the poStatusList where status contains UPDATED_STATUS
        defaultPoStatusShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where status does not contain DEFAULT_STATUS
        defaultPoStatusShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the poStatusList where status does not contain UPDATED_STATUS
        defaultPoStatusShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }


    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt equals to DEFAULT_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.equals=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.equals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt not equals to DEFAULT_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.notEquals=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt not equals to UPDATED_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.notEquals=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt in DEFAULT_UPDATED_AT or UPDATED_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.in=" + DEFAULT_UPDATED_AT + "," + UPDATED_UPDATED_AT);

        // Get all the poStatusList where updatedAt equals to UPDATED_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.in=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt is not null
        defaultPoStatusShouldBeFound("updatedAt.specified=true");

        // Get all the poStatusList where updatedAt is null
        defaultPoStatusShouldNotBeFound("updatedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt is greater than or equal to DEFAULT_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.greaterThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt is greater than or equal to UPDATED_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.greaterThanOrEqual=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt is less than or equal to DEFAULT_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.lessThanOrEqual=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt is less than or equal to SMALLER_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.lessThanOrEqual=" + SMALLER_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsLessThanSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt is less than DEFAULT_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.lessThan=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt is less than UPDATED_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.lessThan=" + UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void getAllPoStatusesByUpdatedAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);

        // Get all the poStatusList where updatedAt is greater than DEFAULT_UPDATED_AT
        defaultPoStatusShouldNotBeFound("updatedAt.greaterThan=" + DEFAULT_UPDATED_AT);

        // Get all the poStatusList where updatedAt is greater than SMALLER_UPDATED_AT
        defaultPoStatusShouldBeFound("updatedAt.greaterThan=" + SMALLER_UPDATED_AT);
    }


    @Test
    @Transactional
    public void getAllPoStatusesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        poStatusRepository.saveAndFlush(poStatus);
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIT.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        poStatus.setPurchaseOrder(purchaseOrder);
        poStatusRepository.saveAndFlush(poStatus);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the poStatusList where purchaseOrder equals to purchaseOrderId
        defaultPoStatusShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the poStatusList where purchaseOrder equals to purchaseOrderId + 1
        defaultPoStatusShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPoStatusShouldBeFound(String filter) throws Exception {
        restPoStatusMockMvc.perform(get("/api/po-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));

        // Check, that the count call also returns 1
        restPoStatusMockMvc.perform(get("/api/po-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPoStatusShouldNotBeFound(String filter) throws Exception {
        restPoStatusMockMvc.perform(get("/api/po-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPoStatusMockMvc.perform(get("/api/po-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPoStatus() throws Exception {
        // Get the poStatus
        restPoStatusMockMvc.perform(get("/api/po-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoStatus() throws Exception {
        // Initialize the database
        poStatusService.save(poStatus);

        int databaseSizeBeforeUpdate = poStatusRepository.findAll().size();

        // Update the poStatus
        PoStatus updatedPoStatus = poStatusRepository.findById(poStatus.getId()).get();
        // Disconnect from session so that the updates on updatedPoStatus are not directly saved in db
        em.detach(updatedPoStatus);
        updatedPoStatus
            .status(UPDATED_STATUS)
            .updatedAt(UPDATED_UPDATED_AT);

        restPoStatusMockMvc.perform(put("/api/po-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoStatus)))
            .andExpect(status().isOk());

        // Validate the PoStatus in the database
        List<PoStatus> poStatusList = poStatusRepository.findAll();
        assertThat(poStatusList).hasSize(databaseSizeBeforeUpdate);
        PoStatus testPoStatus = poStatusList.get(poStatusList.size() - 1);
        assertThat(testPoStatus.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPoStatus.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPoStatus() throws Exception {
        int databaseSizeBeforeUpdate = poStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoStatusMockMvc.perform(put("/api/po-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(poStatus)))
            .andExpect(status().isBadRequest());

        // Validate the PoStatus in the database
        List<PoStatus> poStatusList = poStatusRepository.findAll();
        assertThat(poStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePoStatus() throws Exception {
        // Initialize the database
        poStatusService.save(poStatus);

        int databaseSizeBeforeDelete = poStatusRepository.findAll().size();

        // Delete the poStatus
        restPoStatusMockMvc.perform(delete("/api/po-statuses/{id}", poStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PoStatus> poStatusList = poStatusRepository.findAll();
        assertThat(poStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
