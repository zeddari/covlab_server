package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.Tickets;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.domain.Product;
import com.axilog.cov.repository.TicketsRepository;
import com.axilog.cov.service.TicketsService;
import com.axilog.cov.service.dto.TicketsCriteria;
import com.axilog.cov.service.TicketsQueryService;

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
 * Integration tests for the {@link TicketsResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TicketsResourceIT {

    private static final Long DEFAULT_TICKET_NO = 1L;
    private static final Long UPDATED_TICKET_NO = 2L;
    private static final Long SMALLER_TICKET_NO = 1L - 1L;

    private static final String DEFAULT_TICKET_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TICKET_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TICKET_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_TICKET_STATUS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TICKET_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TICKET_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TICKET_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_TICKET_PRIORITY = "AAAAAAAAAA";
    private static final String UPDATED_TICKET_PRIORITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TICKET_CREATED_ON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TICKET_CREATED_ON = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TICKET_CREATED_ON = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TICKET_UPDATE_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TICKET_UPDATE_AT = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TICKET_UPDATE_AT = LocalDate.ofEpochDay(-1L);

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private TicketsService ticketsService;

    @Autowired
    private TicketsQueryService ticketsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTicketsMockMvc;

    private Tickets tickets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tickets createEntity(EntityManager em) {
        Tickets tickets = new Tickets()
            .ticketNo(DEFAULT_TICKET_NO)
            .ticketType(DEFAULT_TICKET_TYPE)
            .ticketStatus(DEFAULT_TICKET_STATUS)
            .ticketDueDate(DEFAULT_TICKET_DUE_DATE)
            .ticketPriority(DEFAULT_TICKET_PRIORITY)
            .ticketCreatedOn(DEFAULT_TICKET_CREATED_ON)
            .ticketUpdateAt(DEFAULT_TICKET_UPDATE_AT);
        return tickets;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tickets createUpdatedEntity(EntityManager em) {
        Tickets tickets = new Tickets()
            .ticketNo(UPDATED_TICKET_NO)
            .ticketType(UPDATED_TICKET_TYPE)
            .ticketStatus(UPDATED_TICKET_STATUS)
            .ticketDueDate(UPDATED_TICKET_DUE_DATE)
            .ticketPriority(UPDATED_TICKET_PRIORITY)
            .ticketCreatedOn(UPDATED_TICKET_CREATED_ON)
            .ticketUpdateAt(UPDATED_TICKET_UPDATE_AT);
        return tickets;
    }

    @BeforeEach
    public void initTest() {
        tickets = createEntity(em);
    }

    @Test
    @Transactional
    public void createTickets() throws Exception {
        int databaseSizeBeforeCreate = ticketsRepository.findAll().size();
        // Create the Tickets
        restTicketsMockMvc.perform(post("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tickets)))
            .andExpect(status().isCreated());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeCreate + 1);
        Tickets testTickets = ticketsList.get(ticketsList.size() - 1);
        assertThat(testTickets.getTicketNo()).isEqualTo(DEFAULT_TICKET_NO);
        assertThat(testTickets.getTicketType()).isEqualTo(DEFAULT_TICKET_TYPE);
        assertThat(testTickets.getTicketStatus()).isEqualTo(DEFAULT_TICKET_STATUS);
        assertThat(testTickets.getTicketDueDate()).isEqualTo(DEFAULT_TICKET_DUE_DATE);
        assertThat(testTickets.getTicketPriority()).isEqualTo(DEFAULT_TICKET_PRIORITY);
        assertThat(testTickets.getTicketCreatedOn()).isEqualTo(DEFAULT_TICKET_CREATED_ON);
        assertThat(testTickets.getTicketUpdateAt()).isEqualTo(DEFAULT_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void createTicketsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ticketsRepository.findAll().size();

        // Create the Tickets with an existing ID
        tickets.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTicketsMockMvc.perform(post("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tickets)))
            .andExpect(status().isBadRequest());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList
        restTicketsMockMvc.perform(get("/api/tickets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickets.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticketNo").value(hasItem(DEFAULT_TICKET_NO.intValue())))
            .andExpect(jsonPath("$.[*].ticketType").value(hasItem(DEFAULT_TICKET_TYPE)))
            .andExpect(jsonPath("$.[*].ticketStatus").value(hasItem(DEFAULT_TICKET_STATUS)))
            .andExpect(jsonPath("$.[*].ticketDueDate").value(hasItem(DEFAULT_TICKET_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ticketPriority").value(hasItem(DEFAULT_TICKET_PRIORITY)))
            .andExpect(jsonPath("$.[*].ticketCreatedOn").value(hasItem(DEFAULT_TICKET_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].ticketUpdateAt").value(hasItem(DEFAULT_TICKET_UPDATE_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getTickets() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get the tickets
        restTicketsMockMvc.perform(get("/api/tickets/{id}", tickets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tickets.getId().intValue()))
            .andExpect(jsonPath("$.ticketNo").value(DEFAULT_TICKET_NO.intValue()))
            .andExpect(jsonPath("$.ticketType").value(DEFAULT_TICKET_TYPE))
            .andExpect(jsonPath("$.ticketStatus").value(DEFAULT_TICKET_STATUS))
            .andExpect(jsonPath("$.ticketDueDate").value(DEFAULT_TICKET_DUE_DATE.toString()))
            .andExpect(jsonPath("$.ticketPriority").value(DEFAULT_TICKET_PRIORITY))
            .andExpect(jsonPath("$.ticketCreatedOn").value(DEFAULT_TICKET_CREATED_ON.toString()))
            .andExpect(jsonPath("$.ticketUpdateAt").value(DEFAULT_TICKET_UPDATE_AT.toString()));
    }


    @Test
    @Transactional
    public void getTicketsByIdFiltering() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        Long id = tickets.getId();

        defaultTicketsShouldBeFound("id.equals=" + id);
        defaultTicketsShouldNotBeFound("id.notEquals=" + id);

        defaultTicketsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTicketsShouldNotBeFound("id.greaterThan=" + id);

        defaultTicketsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTicketsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo equals to DEFAULT_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.equals=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo equals to UPDATED_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.equals=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo not equals to DEFAULT_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.notEquals=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo not equals to UPDATED_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.notEquals=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo in DEFAULT_TICKET_NO or UPDATED_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.in=" + DEFAULT_TICKET_NO + "," + UPDATED_TICKET_NO);

        // Get all the ticketsList where ticketNo equals to UPDATED_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.in=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo is not null
        defaultTicketsShouldBeFound("ticketNo.specified=true");

        // Get all the ticketsList where ticketNo is null
        defaultTicketsShouldNotBeFound("ticketNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo is greater than or equal to DEFAULT_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.greaterThanOrEqual=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo is greater than or equal to UPDATED_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.greaterThanOrEqual=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo is less than or equal to DEFAULT_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.lessThanOrEqual=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo is less than or equal to SMALLER_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.lessThanOrEqual=" + SMALLER_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsLessThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo is less than DEFAULT_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.lessThan=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo is less than UPDATED_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.lessThan=" + UPDATED_TICKET_NO);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketNo is greater than DEFAULT_TICKET_NO
        defaultTicketsShouldNotBeFound("ticketNo.greaterThan=" + DEFAULT_TICKET_NO);

        // Get all the ticketsList where ticketNo is greater than SMALLER_TICKET_NO
        defaultTicketsShouldBeFound("ticketNo.greaterThan=" + SMALLER_TICKET_NO);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType equals to DEFAULT_TICKET_TYPE
        defaultTicketsShouldBeFound("ticketType.equals=" + DEFAULT_TICKET_TYPE);

        // Get all the ticketsList where ticketType equals to UPDATED_TICKET_TYPE
        defaultTicketsShouldNotBeFound("ticketType.equals=" + UPDATED_TICKET_TYPE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType not equals to DEFAULT_TICKET_TYPE
        defaultTicketsShouldNotBeFound("ticketType.notEquals=" + DEFAULT_TICKET_TYPE);

        // Get all the ticketsList where ticketType not equals to UPDATED_TICKET_TYPE
        defaultTicketsShouldBeFound("ticketType.notEquals=" + UPDATED_TICKET_TYPE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketTypeIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType in DEFAULT_TICKET_TYPE or UPDATED_TICKET_TYPE
        defaultTicketsShouldBeFound("ticketType.in=" + DEFAULT_TICKET_TYPE + "," + UPDATED_TICKET_TYPE);

        // Get all the ticketsList where ticketType equals to UPDATED_TICKET_TYPE
        defaultTicketsShouldNotBeFound("ticketType.in=" + UPDATED_TICKET_TYPE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType is not null
        defaultTicketsShouldBeFound("ticketType.specified=true");

        // Get all the ticketsList where ticketType is null
        defaultTicketsShouldNotBeFound("ticketType.specified=false");
    }
                @Test
    @Transactional
    public void getAllTicketsByTicketTypeContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType contains DEFAULT_TICKET_TYPE
        defaultTicketsShouldBeFound("ticketType.contains=" + DEFAULT_TICKET_TYPE);

        // Get all the ticketsList where ticketType contains UPDATED_TICKET_TYPE
        defaultTicketsShouldNotBeFound("ticketType.contains=" + UPDATED_TICKET_TYPE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketTypeNotContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketType does not contain DEFAULT_TICKET_TYPE
        defaultTicketsShouldNotBeFound("ticketType.doesNotContain=" + DEFAULT_TICKET_TYPE);

        // Get all the ticketsList where ticketType does not contain UPDATED_TICKET_TYPE
        defaultTicketsShouldBeFound("ticketType.doesNotContain=" + UPDATED_TICKET_TYPE);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus equals to DEFAULT_TICKET_STATUS
        defaultTicketsShouldBeFound("ticketStatus.equals=" + DEFAULT_TICKET_STATUS);

        // Get all the ticketsList where ticketStatus equals to UPDATED_TICKET_STATUS
        defaultTicketsShouldNotBeFound("ticketStatus.equals=" + UPDATED_TICKET_STATUS);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus not equals to DEFAULT_TICKET_STATUS
        defaultTicketsShouldNotBeFound("ticketStatus.notEquals=" + DEFAULT_TICKET_STATUS);

        // Get all the ticketsList where ticketStatus not equals to UPDATED_TICKET_STATUS
        defaultTicketsShouldBeFound("ticketStatus.notEquals=" + UPDATED_TICKET_STATUS);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus in DEFAULT_TICKET_STATUS or UPDATED_TICKET_STATUS
        defaultTicketsShouldBeFound("ticketStatus.in=" + DEFAULT_TICKET_STATUS + "," + UPDATED_TICKET_STATUS);

        // Get all the ticketsList where ticketStatus equals to UPDATED_TICKET_STATUS
        defaultTicketsShouldNotBeFound("ticketStatus.in=" + UPDATED_TICKET_STATUS);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus is not null
        defaultTicketsShouldBeFound("ticketStatus.specified=true");

        // Get all the ticketsList where ticketStatus is null
        defaultTicketsShouldNotBeFound("ticketStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllTicketsByTicketStatusContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus contains DEFAULT_TICKET_STATUS
        defaultTicketsShouldBeFound("ticketStatus.contains=" + DEFAULT_TICKET_STATUS);

        // Get all the ticketsList where ticketStatus contains UPDATED_TICKET_STATUS
        defaultTicketsShouldNotBeFound("ticketStatus.contains=" + UPDATED_TICKET_STATUS);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketStatusNotContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketStatus does not contain DEFAULT_TICKET_STATUS
        defaultTicketsShouldNotBeFound("ticketStatus.doesNotContain=" + DEFAULT_TICKET_STATUS);

        // Get all the ticketsList where ticketStatus does not contain UPDATED_TICKET_STATUS
        defaultTicketsShouldBeFound("ticketStatus.doesNotContain=" + UPDATED_TICKET_STATUS);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate equals to DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.equals=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate equals to UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.equals=" + UPDATED_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate not equals to DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.notEquals=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate not equals to UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.notEquals=" + UPDATED_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate in DEFAULT_TICKET_DUE_DATE or UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.in=" + DEFAULT_TICKET_DUE_DATE + "," + UPDATED_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate equals to UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.in=" + UPDATED_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate is not null
        defaultTicketsShouldBeFound("ticketDueDate.specified=true");

        // Get all the ticketsList where ticketDueDate is null
        defaultTicketsShouldNotBeFound("ticketDueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate is greater than or equal to DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.greaterThanOrEqual=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate is greater than or equal to UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.greaterThanOrEqual=" + UPDATED_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate is less than or equal to DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.lessThanOrEqual=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate is less than or equal to SMALLER_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.lessThanOrEqual=" + SMALLER_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate is less than DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.lessThan=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate is less than UPDATED_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.lessThan=" + UPDATED_TICKET_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketDueDate is greater than DEFAULT_TICKET_DUE_DATE
        defaultTicketsShouldNotBeFound("ticketDueDate.greaterThan=" + DEFAULT_TICKET_DUE_DATE);

        // Get all the ticketsList where ticketDueDate is greater than SMALLER_TICKET_DUE_DATE
        defaultTicketsShouldBeFound("ticketDueDate.greaterThan=" + SMALLER_TICKET_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority equals to DEFAULT_TICKET_PRIORITY
        defaultTicketsShouldBeFound("ticketPriority.equals=" + DEFAULT_TICKET_PRIORITY);

        // Get all the ticketsList where ticketPriority equals to UPDATED_TICKET_PRIORITY
        defaultTicketsShouldNotBeFound("ticketPriority.equals=" + UPDATED_TICKET_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority not equals to DEFAULT_TICKET_PRIORITY
        defaultTicketsShouldNotBeFound("ticketPriority.notEquals=" + DEFAULT_TICKET_PRIORITY);

        // Get all the ticketsList where ticketPriority not equals to UPDATED_TICKET_PRIORITY
        defaultTicketsShouldBeFound("ticketPriority.notEquals=" + UPDATED_TICKET_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority in DEFAULT_TICKET_PRIORITY or UPDATED_TICKET_PRIORITY
        defaultTicketsShouldBeFound("ticketPriority.in=" + DEFAULT_TICKET_PRIORITY + "," + UPDATED_TICKET_PRIORITY);

        // Get all the ticketsList where ticketPriority equals to UPDATED_TICKET_PRIORITY
        defaultTicketsShouldNotBeFound("ticketPriority.in=" + UPDATED_TICKET_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority is not null
        defaultTicketsShouldBeFound("ticketPriority.specified=true");

        // Get all the ticketsList where ticketPriority is null
        defaultTicketsShouldNotBeFound("ticketPriority.specified=false");
    }
                @Test
    @Transactional
    public void getAllTicketsByTicketPriorityContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority contains DEFAULT_TICKET_PRIORITY
        defaultTicketsShouldBeFound("ticketPriority.contains=" + DEFAULT_TICKET_PRIORITY);

        // Get all the ticketsList where ticketPriority contains UPDATED_TICKET_PRIORITY
        defaultTicketsShouldNotBeFound("ticketPriority.contains=" + UPDATED_TICKET_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketPriorityNotContainsSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketPriority does not contain DEFAULT_TICKET_PRIORITY
        defaultTicketsShouldNotBeFound("ticketPriority.doesNotContain=" + DEFAULT_TICKET_PRIORITY);

        // Get all the ticketsList where ticketPriority does not contain UPDATED_TICKET_PRIORITY
        defaultTicketsShouldBeFound("ticketPriority.doesNotContain=" + UPDATED_TICKET_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn equals to DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.equals=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn equals to UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.equals=" + UPDATED_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn not equals to DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.notEquals=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn not equals to UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.notEquals=" + UPDATED_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn in DEFAULT_TICKET_CREATED_ON or UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.in=" + DEFAULT_TICKET_CREATED_ON + "," + UPDATED_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn equals to UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.in=" + UPDATED_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn is not null
        defaultTicketsShouldBeFound("ticketCreatedOn.specified=true");

        // Get all the ticketsList where ticketCreatedOn is null
        defaultTicketsShouldNotBeFound("ticketCreatedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn is greater than or equal to DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.greaterThanOrEqual=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn is greater than or equal to UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.greaterThanOrEqual=" + UPDATED_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn is less than or equal to DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.lessThanOrEqual=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn is less than or equal to SMALLER_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.lessThanOrEqual=" + SMALLER_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsLessThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn is less than DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.lessThan=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn is less than UPDATED_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.lessThan=" + UPDATED_TICKET_CREATED_ON);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketCreatedOnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketCreatedOn is greater than DEFAULT_TICKET_CREATED_ON
        defaultTicketsShouldNotBeFound("ticketCreatedOn.greaterThan=" + DEFAULT_TICKET_CREATED_ON);

        // Get all the ticketsList where ticketCreatedOn is greater than SMALLER_TICKET_CREATED_ON
        defaultTicketsShouldBeFound("ticketCreatedOn.greaterThan=" + SMALLER_TICKET_CREATED_ON);
    }


    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt equals to DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.equals=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt equals to UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.equals=" + UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt not equals to DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.notEquals=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt not equals to UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.notEquals=" + UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsInShouldWork() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt in DEFAULT_TICKET_UPDATE_AT or UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.in=" + DEFAULT_TICKET_UPDATE_AT + "," + UPDATED_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt equals to UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.in=" + UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt is not null
        defaultTicketsShouldBeFound("ticketUpdateAt.specified=true");

        // Get all the ticketsList where ticketUpdateAt is null
        defaultTicketsShouldNotBeFound("ticketUpdateAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt is greater than or equal to DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.greaterThanOrEqual=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt is greater than or equal to UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.greaterThanOrEqual=" + UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt is less than or equal to DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.lessThanOrEqual=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt is less than or equal to SMALLER_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.lessThanOrEqual=" + SMALLER_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsLessThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt is less than DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.lessThan=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt is less than UPDATED_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.lessThan=" + UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void getAllTicketsByTicketUpdateAtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);

        // Get all the ticketsList where ticketUpdateAt is greater than DEFAULT_TICKET_UPDATE_AT
        defaultTicketsShouldNotBeFound("ticketUpdateAt.greaterThan=" + DEFAULT_TICKET_UPDATE_AT);

        // Get all the ticketsList where ticketUpdateAt is greater than SMALLER_TICKET_UPDATE_AT
        defaultTicketsShouldBeFound("ticketUpdateAt.greaterThan=" + SMALLER_TICKET_UPDATE_AT);
    }


    @Test
    @Transactional
    public void getAllTicketsByOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);
        Outlet outlet = OutletResourceIT.createEntity(em);
        em.persist(outlet);
        em.flush();
        tickets.setOutlet(outlet);
        ticketsRepository.saveAndFlush(tickets);
        Long outletId = outlet.getId();

        // Get all the ticketsList where outlet equals to outletId
        defaultTicketsShouldBeFound("outletId.equals=" + outletId);

        // Get all the ticketsList where outlet equals to outletId + 1
        defaultTicketsShouldNotBeFound("outletId.equals=" + (outletId + 1));
    }


    @Test
    @Transactional
    public void getAllTicketsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        ticketsRepository.saveAndFlush(tickets);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        tickets.setProduct(product);
        ticketsRepository.saveAndFlush(tickets);
        Long productId = product.getId();

        // Get all the ticketsList where product equals to productId
        defaultTicketsShouldBeFound("productId.equals=" + productId);

        // Get all the ticketsList where product equals to productId + 1
        defaultTicketsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTicketsShouldBeFound(String filter) throws Exception {
        restTicketsMockMvc.perform(get("/api/tickets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tickets.getId().intValue())))
            .andExpect(jsonPath("$.[*].ticketNo").value(hasItem(DEFAULT_TICKET_NO.intValue())))
            .andExpect(jsonPath("$.[*].ticketType").value(hasItem(DEFAULT_TICKET_TYPE)))
            .andExpect(jsonPath("$.[*].ticketStatus").value(hasItem(DEFAULT_TICKET_STATUS)))
            .andExpect(jsonPath("$.[*].ticketDueDate").value(hasItem(DEFAULT_TICKET_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].ticketPriority").value(hasItem(DEFAULT_TICKET_PRIORITY)))
            .andExpect(jsonPath("$.[*].ticketCreatedOn").value(hasItem(DEFAULT_TICKET_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].ticketUpdateAt").value(hasItem(DEFAULT_TICKET_UPDATE_AT.toString())));

        // Check, that the count call also returns 1
        restTicketsMockMvc.perform(get("/api/tickets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTicketsShouldNotBeFound(String filter) throws Exception {
        restTicketsMockMvc.perform(get("/api/tickets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTicketsMockMvc.perform(get("/api/tickets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTickets() throws Exception {
        // Get the tickets
        restTicketsMockMvc.perform(get("/api/tickets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTickets() throws Exception {
        // Initialize the database
        ticketsService.save(tickets);

        int databaseSizeBeforeUpdate = ticketsRepository.findAll().size();

        // Update the tickets
        Tickets updatedTickets = ticketsRepository.findById(tickets.getId()).get();
        // Disconnect from session so that the updates on updatedTickets are not directly saved in db
        em.detach(updatedTickets);
        updatedTickets
            .ticketNo(UPDATED_TICKET_NO)
            .ticketType(UPDATED_TICKET_TYPE)
            .ticketStatus(UPDATED_TICKET_STATUS)
            .ticketDueDate(UPDATED_TICKET_DUE_DATE)
            .ticketPriority(UPDATED_TICKET_PRIORITY)
            .ticketCreatedOn(UPDATED_TICKET_CREATED_ON)
            .ticketUpdateAt(UPDATED_TICKET_UPDATE_AT);

        restTicketsMockMvc.perform(put("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTickets)))
            .andExpect(status().isOk());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeUpdate);
        Tickets testTickets = ticketsList.get(ticketsList.size() - 1);
        assertThat(testTickets.getTicketNo()).isEqualTo(UPDATED_TICKET_NO);
        assertThat(testTickets.getTicketType()).isEqualTo(UPDATED_TICKET_TYPE);
        assertThat(testTickets.getTicketStatus()).isEqualTo(UPDATED_TICKET_STATUS);
        assertThat(testTickets.getTicketDueDate()).isEqualTo(UPDATED_TICKET_DUE_DATE);
        assertThat(testTickets.getTicketPriority()).isEqualTo(UPDATED_TICKET_PRIORITY);
        assertThat(testTickets.getTicketCreatedOn()).isEqualTo(UPDATED_TICKET_CREATED_ON);
        assertThat(testTickets.getTicketUpdateAt()).isEqualTo(UPDATED_TICKET_UPDATE_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTickets() throws Exception {
        int databaseSizeBeforeUpdate = ticketsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTicketsMockMvc.perform(put("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tickets)))
            .andExpect(status().isBadRequest());

        // Validate the Tickets in the database
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTickets() throws Exception {
        // Initialize the database
        ticketsService.save(tickets);

        int databaseSizeBeforeDelete = ticketsRepository.findAll().size();

        // Delete the tickets
        restTicketsMockMvc.perform(delete("/api/tickets/{id}", tickets.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tickets> ticketsList = ticketsRepository.findAll();
        assertThat(ticketsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
