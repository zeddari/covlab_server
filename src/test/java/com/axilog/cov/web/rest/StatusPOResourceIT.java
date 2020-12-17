package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.StatusPO;
import com.axilog.cov.domain.PurchaseOrder;
import com.axilog.cov.repository.StatusPORepository;
import com.axilog.cov.service.StatusPOService;
import com.axilog.cov.service.dto.StatusPOCriteria;
import com.axilog.cov.service.StatusPOQueryService;

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
 * Integration tests for the {@link StatusPOResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatusPOResourceIT {

    private static final Long DEFAULT_STATUS_PO_ID = 1L;
    private static final Long UPDATED_STATUS_PO_ID = 2L;
    private static final Long SMALLER_STATUS_PO_ID = 1L - 1L;

    private static final String DEFAULT_STATUS_PO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STATUS_PO_NAME = "BBBBBBBBBB";

    @Autowired
    private StatusPORepository statusPORepository;

    @Autowired
    private StatusPOService statusPOService;

    @Autowired
    private StatusPOQueryService statusPOQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusPOMockMvc;

    private StatusPO statusPO;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusPO createEntity(EntityManager em) {
        StatusPO statusPO = new StatusPO()
            .statusPoId(DEFAULT_STATUS_PO_ID)
            .statusPoName(DEFAULT_STATUS_PO_NAME);
        return statusPO;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusPO createUpdatedEntity(EntityManager em) {
        StatusPO statusPO = new StatusPO()
            .statusPoId(UPDATED_STATUS_PO_ID)
            .statusPoName(UPDATED_STATUS_PO_NAME);
        return statusPO;
    }

    @BeforeEach
    public void initTest() {
        statusPO = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusPO() throws Exception {
        int databaseSizeBeforeCreate = statusPORepository.findAll().size();
        // Create the StatusPO
        restStatusPOMockMvc.perform(post("/api/status-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusPO)))
            .andExpect(status().isCreated());

        // Validate the StatusPO in the database
        List<StatusPO> statusPOList = statusPORepository.findAll();
        assertThat(statusPOList).hasSize(databaseSizeBeforeCreate + 1);
        StatusPO testStatusPO = statusPOList.get(statusPOList.size() - 1);
        assertThat(testStatusPO.getStatusPoId()).isEqualTo(DEFAULT_STATUS_PO_ID);
        assertThat(testStatusPO.getStatusPoName()).isEqualTo(DEFAULT_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void createStatusPOWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusPORepository.findAll().size();

        // Create the StatusPO with an existing ID
        statusPO.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusPOMockMvc.perform(post("/api/status-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusPO)))
            .andExpect(status().isBadRequest());

        // Validate the StatusPO in the database
        List<StatusPO> statusPOList = statusPORepository.findAll();
        assertThat(statusPOList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatusPOS() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList
        restStatusPOMockMvc.perform(get("/api/status-pos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusPoId").value(hasItem(DEFAULT_STATUS_PO_ID.intValue())))
            .andExpect(jsonPath("$.[*].statusPoName").value(hasItem(DEFAULT_STATUS_PO_NAME)));
    }
    
    @Test
    @Transactional
    public void getStatusPO() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get the statusPO
        restStatusPOMockMvc.perform(get("/api/status-pos/{id}", statusPO.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusPO.getId().intValue()))
            .andExpect(jsonPath("$.statusPoId").value(DEFAULT_STATUS_PO_ID.intValue()))
            .andExpect(jsonPath("$.statusPoName").value(DEFAULT_STATUS_PO_NAME));
    }


    @Test
    @Transactional
    public void getStatusPOSByIdFiltering() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        Long id = statusPO.getId();

        defaultStatusPOShouldBeFound("id.equals=" + id);
        defaultStatusPOShouldNotBeFound("id.notEquals=" + id);

        defaultStatusPOShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusPOShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusPOShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusPOShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId equals to DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.equals=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId equals to UPDATED_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.equals=" + UPDATED_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId not equals to DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.notEquals=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId not equals to UPDATED_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.notEquals=" + UPDATED_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsInShouldWork() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId in DEFAULT_STATUS_PO_ID or UPDATED_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.in=" + DEFAULT_STATUS_PO_ID + "," + UPDATED_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId equals to UPDATED_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.in=" + UPDATED_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId is not null
        defaultStatusPOShouldBeFound("statusPoId.specified=true");

        // Get all the statusPOList where statusPoId is null
        defaultStatusPOShouldNotBeFound("statusPoId.specified=false");
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId is greater than or equal to DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.greaterThanOrEqual=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId is greater than or equal to UPDATED_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.greaterThanOrEqual=" + UPDATED_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId is less than or equal to DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.lessThanOrEqual=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId is less than or equal to SMALLER_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.lessThanOrEqual=" + SMALLER_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsLessThanSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId is less than DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.lessThan=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId is less than UPDATED_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.lessThan=" + UPDATED_STATUS_PO_ID);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoId is greater than DEFAULT_STATUS_PO_ID
        defaultStatusPOShouldNotBeFound("statusPoId.greaterThan=" + DEFAULT_STATUS_PO_ID);

        // Get all the statusPOList where statusPoId is greater than SMALLER_STATUS_PO_ID
        defaultStatusPOShouldBeFound("statusPoId.greaterThan=" + SMALLER_STATUS_PO_ID);
    }


    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName equals to DEFAULT_STATUS_PO_NAME
        defaultStatusPOShouldBeFound("statusPoName.equals=" + DEFAULT_STATUS_PO_NAME);

        // Get all the statusPOList where statusPoName equals to UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldNotBeFound("statusPoName.equals=" + UPDATED_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName not equals to DEFAULT_STATUS_PO_NAME
        defaultStatusPOShouldNotBeFound("statusPoName.notEquals=" + DEFAULT_STATUS_PO_NAME);

        // Get all the statusPOList where statusPoName not equals to UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldBeFound("statusPoName.notEquals=" + UPDATED_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName in DEFAULT_STATUS_PO_NAME or UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldBeFound("statusPoName.in=" + DEFAULT_STATUS_PO_NAME + "," + UPDATED_STATUS_PO_NAME);

        // Get all the statusPOList where statusPoName equals to UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldNotBeFound("statusPoName.in=" + UPDATED_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName is not null
        defaultStatusPOShouldBeFound("statusPoName.specified=true");

        // Get all the statusPOList where statusPoName is null
        defaultStatusPOShouldNotBeFound("statusPoName.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameContainsSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName contains DEFAULT_STATUS_PO_NAME
        defaultStatusPOShouldBeFound("statusPoName.contains=" + DEFAULT_STATUS_PO_NAME);

        // Get all the statusPOList where statusPoName contains UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldNotBeFound("statusPoName.contains=" + UPDATED_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusPOSByStatusPoNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);

        // Get all the statusPOList where statusPoName does not contain DEFAULT_STATUS_PO_NAME
        defaultStatusPOShouldNotBeFound("statusPoName.doesNotContain=" + DEFAULT_STATUS_PO_NAME);

        // Get all the statusPOList where statusPoName does not contain UPDATED_STATUS_PO_NAME
        defaultStatusPOShouldBeFound("statusPoName.doesNotContain=" + UPDATED_STATUS_PO_NAME);
    }


    @Test
    @Transactional
    public void getAllStatusPOSByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        statusPORepository.saveAndFlush(statusPO);
        PurchaseOrder purchaseOrder = PurchaseOrderResourceIT.createEntity(em);
        em.persist(purchaseOrder);
        em.flush();
        statusPO.setPurchaseOrder(purchaseOrder);
        statusPORepository.saveAndFlush(statusPO);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the statusPOList where purchaseOrder equals to purchaseOrderId
        defaultStatusPOShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the statusPOList where purchaseOrder equals to purchaseOrderId + 1
        defaultStatusPOShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusPOShouldBeFound(String filter) throws Exception {
        restStatusPOMockMvc.perform(get("/api/status-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusPO.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusPoId").value(hasItem(DEFAULT_STATUS_PO_ID.intValue())))
            .andExpect(jsonPath("$.[*].statusPoName").value(hasItem(DEFAULT_STATUS_PO_NAME)));

        // Check, that the count call also returns 1
        restStatusPOMockMvc.perform(get("/api/status-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusPOShouldNotBeFound(String filter) throws Exception {
        restStatusPOMockMvc.perform(get("/api/status-pos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusPOMockMvc.perform(get("/api/status-pos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatusPO() throws Exception {
        // Get the statusPO
        restStatusPOMockMvc.perform(get("/api/status-pos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusPO() throws Exception {
        // Initialize the database
        statusPOService.save(statusPO);

        int databaseSizeBeforeUpdate = statusPORepository.findAll().size();

        // Update the statusPO
        StatusPO updatedStatusPO = statusPORepository.findById(statusPO.getId()).get();
        // Disconnect from session so that the updates on updatedStatusPO are not directly saved in db
        em.detach(updatedStatusPO);
        updatedStatusPO
            .statusPoId(UPDATED_STATUS_PO_ID)
            .statusPoName(UPDATED_STATUS_PO_NAME);

        restStatusPOMockMvc.perform(put("/api/status-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatusPO)))
            .andExpect(status().isOk());

        // Validate the StatusPO in the database
        List<StatusPO> statusPOList = statusPORepository.findAll();
        assertThat(statusPOList).hasSize(databaseSizeBeforeUpdate);
        StatusPO testStatusPO = statusPOList.get(statusPOList.size() - 1);
        assertThat(testStatusPO.getStatusPoId()).isEqualTo(UPDATED_STATUS_PO_ID);
        assertThat(testStatusPO.getStatusPoName()).isEqualTo(UPDATED_STATUS_PO_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusPO() throws Exception {
        int databaseSizeBeforeUpdate = statusPORepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusPOMockMvc.perform(put("/api/status-pos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusPO)))
            .andExpect(status().isBadRequest());

        // Validate the StatusPO in the database
        List<StatusPO> statusPOList = statusPORepository.findAll();
        assertThat(statusPOList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatusPO() throws Exception {
        // Initialize the database
        statusPOService.save(statusPO);

        int databaseSizeBeforeDelete = statusPORepository.findAll().size();

        // Delete the statusPO
        restStatusPOMockMvc.perform(delete("/api/status-pos/{id}", statusPO.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusPO> statusPOList = statusPORepository.findAll();
        assertThat(statusPOList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
