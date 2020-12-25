package com.axilog.cov.web.rest;

import com.axilog.cov.CovlabServerApp;
import com.axilog.cov.domain.DeviceOverviewStats;
import com.axilog.cov.domain.Product;
import com.axilog.cov.domain.Outlet;
import com.axilog.cov.repository.DeviceOverviewStatsRepository;
import com.axilog.cov.service.DeviceOverviewStatsService;
import com.axilog.cov.service.dto.DeviceOverviewStatsCriteria;
import com.axilog.cov.service.DeviceOverviewStatsQueryService;

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
 * Integration tests for the {@link DeviceOverviewStatsResource} REST controller.
 */
@SpringBootTest(classes = CovlabServerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DeviceOverviewStatsResourceIT {

    private static final String DEFAULT_DEVICE_ID = "AAAAAAAAAA";
    private static final String UPDATED_DEVICE_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TIMESTAMP = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TIMESTAMP = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TIMESTAMP = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final Double DEFAULT_HUMIDITY = 1D;
    private static final Double UPDATED_HUMIDITY = 2D;
    private static final Double SMALLER_HUMIDITY = 1D - 1D;

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;
    private static final Double SMALLER_TEMPERATURE = 1D - 1D;

    private static final Double DEFAULT_CO_2 = 1D;
    private static final Double UPDATED_CO_2 = 2D;
    private static final Double SMALLER_CO_2 = 1D - 1D;

    private static final Double DEFAULT_PRESSURE = 1D;
    private static final Double UPDATED_PRESSURE = 2D;
    private static final Double SMALLER_PRESSURE = 1D - 1D;

    private static final Double DEFAULT_DIFFERENTIAL_PRESSURE = 1D;
    private static final Double UPDATED_DIFFERENTIAL_PRESSURE = 2D;
    private static final Double SMALLER_DIFFERENTIAL_PRESSURE = 1D - 1D;

    @Autowired
    private DeviceOverviewStatsRepository deviceOverviewStatsRepository;

    @Autowired
    private DeviceOverviewStatsService deviceOverviewStatsService;

    @Autowired
    private DeviceOverviewStatsQueryService deviceOverviewStatsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviceOverviewStatsMockMvc;

    private DeviceOverviewStats deviceOverviewStats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceOverviewStats createEntity(EntityManager em) {
        DeviceOverviewStats deviceOverviewStats = new DeviceOverviewStats()
            .deviceId(DEFAULT_DEVICE_ID)
            .timestamp(DEFAULT_TIMESTAMP)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .humidity(DEFAULT_HUMIDITY)
            .temperature(DEFAULT_TEMPERATURE)
            .co2(DEFAULT_CO_2)
            .pressure(DEFAULT_PRESSURE)
            .differentialPressure(DEFAULT_DIFFERENTIAL_PRESSURE);
        return deviceOverviewStats;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeviceOverviewStats createUpdatedEntity(EntityManager em) {
        DeviceOverviewStats deviceOverviewStats = new DeviceOverviewStats()
            .deviceId(UPDATED_DEVICE_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .humidity(UPDATED_HUMIDITY)
            .temperature(UPDATED_TEMPERATURE)
            .co2(UPDATED_CO_2)
            .pressure(UPDATED_PRESSURE)
            .differentialPressure(UPDATED_DIFFERENTIAL_PRESSURE);
        return deviceOverviewStats;
    }

    @BeforeEach
    public void initTest() {
        deviceOverviewStats = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeviceOverviewStats() throws Exception {
        int databaseSizeBeforeCreate = deviceOverviewStatsRepository.findAll().size();
        // Create the DeviceOverviewStats
        restDeviceOverviewStatsMockMvc.perform(post("/api/device-overview-stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceOverviewStats)))
            .andExpect(status().isCreated());

        // Validate the DeviceOverviewStats in the database
        List<DeviceOverviewStats> deviceOverviewStatsList = deviceOverviewStatsRepository.findAll();
        assertThat(deviceOverviewStatsList).hasSize(databaseSizeBeforeCreate + 1);
        DeviceOverviewStats testDeviceOverviewStats = deviceOverviewStatsList.get(deviceOverviewStatsList.size() - 1);
        assertThat(testDeviceOverviewStats.getDeviceId()).isEqualTo(DEFAULT_DEVICE_ID);
        assertThat(testDeviceOverviewStats.getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
        assertThat(testDeviceOverviewStats.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testDeviceOverviewStats.getHumidity()).isEqualTo(DEFAULT_HUMIDITY);
        assertThat(testDeviceOverviewStats.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testDeviceOverviewStats.getCo2()).isEqualTo(DEFAULT_CO_2);
        assertThat(testDeviceOverviewStats.getPressure()).isEqualTo(DEFAULT_PRESSURE);
        assertThat(testDeviceOverviewStats.getDifferentialPressure()).isEqualTo(DEFAULT_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void createDeviceOverviewStatsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deviceOverviewStatsRepository.findAll().size();

        // Create the DeviceOverviewStats with an existing ID
        deviceOverviewStats.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviceOverviewStatsMockMvc.perform(post("/api/device-overview-stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceOverviewStats)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceOverviewStats in the database
        List<DeviceOverviewStats> deviceOverviewStatsList = deviceOverviewStatsRepository.findAll();
        assertThat(deviceOverviewStatsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStats() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceOverviewStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].co2").value(hasItem(DEFAULT_CO_2.doubleValue())))
            .andExpect(jsonPath("$.[*].pressure").value(hasItem(DEFAULT_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].differentialPressure").value(hasItem(DEFAULT_DIFFERENTIAL_PRESSURE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDeviceOverviewStats() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get the deviceOverviewStats
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats/{id}", deviceOverviewStats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deviceOverviewStats.getId().intValue()))
            .andExpect(jsonPath("$.deviceId").value(DEFAULT_DEVICE_ID))
            .andExpect(jsonPath("$.timestamp").value(DEFAULT_TIMESTAMP.toString()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.humidity").value(DEFAULT_HUMIDITY.doubleValue()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.co2").value(DEFAULT_CO_2.doubleValue()))
            .andExpect(jsonPath("$.pressure").value(DEFAULT_PRESSURE.doubleValue()))
            .andExpect(jsonPath("$.differentialPressure").value(DEFAULT_DIFFERENTIAL_PRESSURE.doubleValue()));
    }


    @Test
    @Transactional
    public void getDeviceOverviewStatsByIdFiltering() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        Long id = deviceOverviewStats.getId();

        defaultDeviceOverviewStatsShouldBeFound("id.equals=" + id);
        defaultDeviceOverviewStatsShouldNotBeFound("id.notEquals=" + id);

        defaultDeviceOverviewStatsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeviceOverviewStatsShouldNotBeFound("id.greaterThan=" + id);

        defaultDeviceOverviewStatsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeviceOverviewStatsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId equals to DEFAULT_DEVICE_ID
        defaultDeviceOverviewStatsShouldBeFound("deviceId.equals=" + DEFAULT_DEVICE_ID);

        // Get all the deviceOverviewStatsList where deviceId equals to UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.equals=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId not equals to DEFAULT_DEVICE_ID
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.notEquals=" + DEFAULT_DEVICE_ID);

        // Get all the deviceOverviewStatsList where deviceId not equals to UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldBeFound("deviceId.notEquals=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId in DEFAULT_DEVICE_ID or UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldBeFound("deviceId.in=" + DEFAULT_DEVICE_ID + "," + UPDATED_DEVICE_ID);

        // Get all the deviceOverviewStatsList where deviceId equals to UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.in=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId is not null
        defaultDeviceOverviewStatsShouldBeFound("deviceId.specified=true");

        // Get all the deviceOverviewStatsList where deviceId is null
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdContainsSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId contains DEFAULT_DEVICE_ID
        defaultDeviceOverviewStatsShouldBeFound("deviceId.contains=" + DEFAULT_DEVICE_ID);

        // Get all the deviceOverviewStatsList where deviceId contains UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.contains=" + UPDATED_DEVICE_ID);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDeviceIdNotContainsSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where deviceId does not contain DEFAULT_DEVICE_ID
        defaultDeviceOverviewStatsShouldNotBeFound("deviceId.doesNotContain=" + DEFAULT_DEVICE_ID);

        // Get all the deviceOverviewStatsList where deviceId does not contain UPDATED_DEVICE_ID
        defaultDeviceOverviewStatsShouldBeFound("deviceId.doesNotContain=" + UPDATED_DEVICE_ID);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp equals to DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.equals=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp equals to UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.equals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp not equals to DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.notEquals=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp not equals to UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.notEquals=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp in DEFAULT_TIMESTAMP or UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.in=" + DEFAULT_TIMESTAMP + "," + UPDATED_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp equals to UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.in=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp is not null
        defaultDeviceOverviewStatsShouldBeFound("timestamp.specified=true");

        // Get all the deviceOverviewStatsList where timestamp is null
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp is greater than or equal to DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.greaterThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp is greater than or equal to UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.greaterThanOrEqual=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp is less than or equal to DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.lessThanOrEqual=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp is less than or equal to SMALLER_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.lessThanOrEqual=" + SMALLER_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp is less than DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.lessThan=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp is less than UPDATED_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.lessThan=" + UPDATED_TIMESTAMP);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTimestampIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where timestamp is greater than DEFAULT_TIMESTAMP
        defaultDeviceOverviewStatsShouldNotBeFound("timestamp.greaterThan=" + DEFAULT_TIMESTAMP);

        // Get all the deviceOverviewStatsList where timestamp is greater than SMALLER_TIMESTAMP
        defaultDeviceOverviewStatsShouldBeFound("timestamp.greaterThan=" + SMALLER_TIMESTAMP);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the deviceOverviewStatsList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber not equals to DEFAULT_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.notEquals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the deviceOverviewStatsList where serialNumber not equals to UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.notEquals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the deviceOverviewStatsList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber is not null
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.specified=true");

        // Get all the deviceOverviewStatsList where serialNumber is null
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber contains DEFAULT_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER);

        // Get all the deviceOverviewStatsList where serialNumber contains UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where serialNumber does not contain DEFAULT_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldNotBeFound("serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER);

        // Get all the deviceOverviewStatsList where serialNumber does not contain UPDATED_SERIAL_NUMBER
        defaultDeviceOverviewStatsShouldBeFound("serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity equals to DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.equals=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity equals to UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.equals=" + UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity not equals to DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.notEquals=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity not equals to UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.notEquals=" + UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity in DEFAULT_HUMIDITY or UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.in=" + DEFAULT_HUMIDITY + "," + UPDATED_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity equals to UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.in=" + UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity is not null
        defaultDeviceOverviewStatsShouldBeFound("humidity.specified=true");

        // Get all the deviceOverviewStatsList where humidity is null
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity is greater than or equal to DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.greaterThanOrEqual=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity is greater than or equal to UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.greaterThanOrEqual=" + UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity is less than or equal to DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.lessThanOrEqual=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity is less than or equal to SMALLER_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.lessThanOrEqual=" + SMALLER_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity is less than DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.lessThan=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity is less than UPDATED_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.lessThan=" + UPDATED_HUMIDITY);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByHumidityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where humidity is greater than DEFAULT_HUMIDITY
        defaultDeviceOverviewStatsShouldNotBeFound("humidity.greaterThan=" + DEFAULT_HUMIDITY);

        // Get all the deviceOverviewStatsList where humidity is greater than SMALLER_HUMIDITY
        defaultDeviceOverviewStatsShouldBeFound("humidity.greaterThan=" + SMALLER_HUMIDITY);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature equals to DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature equals to UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature not equals to DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature not equals to UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature equals to UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature is not null
        defaultDeviceOverviewStatsShouldBeFound("temperature.specified=true");

        // Get all the deviceOverviewStatsList where temperature is null
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature is less than DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature is less than UPDATED_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where temperature is greater than DEFAULT_TEMPERATURE
        defaultDeviceOverviewStatsShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the deviceOverviewStatsList where temperature is greater than SMALLER_TEMPERATURE
        defaultDeviceOverviewStatsShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 equals to DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.equals=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 equals to UPDATED_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.equals=" + UPDATED_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 not equals to DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.notEquals=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 not equals to UPDATED_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.notEquals=" + UPDATED_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 in DEFAULT_CO_2 or UPDATED_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.in=" + DEFAULT_CO_2 + "," + UPDATED_CO_2);

        // Get all the deviceOverviewStatsList where co2 equals to UPDATED_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.in=" + UPDATED_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 is not null
        defaultDeviceOverviewStatsShouldBeFound("co2.specified=true");

        // Get all the deviceOverviewStatsList where co2 is null
        defaultDeviceOverviewStatsShouldNotBeFound("co2.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 is greater than or equal to DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.greaterThanOrEqual=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 is greater than or equal to UPDATED_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.greaterThanOrEqual=" + UPDATED_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 is less than or equal to DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.lessThanOrEqual=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 is less than or equal to SMALLER_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.lessThanOrEqual=" + SMALLER_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 is less than DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.lessThan=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 is less than UPDATED_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.lessThan=" + UPDATED_CO_2);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByCo2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where co2 is greater than DEFAULT_CO_2
        defaultDeviceOverviewStatsShouldNotBeFound("co2.greaterThan=" + DEFAULT_CO_2);

        // Get all the deviceOverviewStatsList where co2 is greater than SMALLER_CO_2
        defaultDeviceOverviewStatsShouldBeFound("co2.greaterThan=" + SMALLER_CO_2);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure equals to DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.equals=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure equals to UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.equals=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure not equals to DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.notEquals=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure not equals to UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.notEquals=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure in DEFAULT_PRESSURE or UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.in=" + DEFAULT_PRESSURE + "," + UPDATED_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure equals to UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.in=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure is not null
        defaultDeviceOverviewStatsShouldBeFound("pressure.specified=true");

        // Get all the deviceOverviewStatsList where pressure is null
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure is greater than or equal to DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.greaterThanOrEqual=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure is greater than or equal to UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.greaterThanOrEqual=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure is less than or equal to DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.lessThanOrEqual=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure is less than or equal to SMALLER_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.lessThanOrEqual=" + SMALLER_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure is less than DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.lessThan=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure is less than UPDATED_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.lessThan=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByPressureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where pressure is greater than DEFAULT_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("pressure.greaterThan=" + DEFAULT_PRESSURE);

        // Get all the deviceOverviewStatsList where pressure is greater than SMALLER_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("pressure.greaterThan=" + SMALLER_PRESSURE);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure equals to DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.equals=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure equals to UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.equals=" + UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure not equals to DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.notEquals=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure not equals to UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.notEquals=" + UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsInShouldWork() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure in DEFAULT_DIFFERENTIAL_PRESSURE or UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.in=" + DEFAULT_DIFFERENTIAL_PRESSURE + "," + UPDATED_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure equals to UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.in=" + UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsNullOrNotNull() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure is not null
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.specified=true");

        // Get all the deviceOverviewStatsList where differentialPressure is null
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure is greater than or equal to DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.greaterThanOrEqual=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure is greater than or equal to UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.greaterThanOrEqual=" + UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure is less than or equal to DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.lessThanOrEqual=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure is less than or equal to SMALLER_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.lessThanOrEqual=" + SMALLER_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsLessThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure is less than DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.lessThan=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure is less than UPDATED_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.lessThan=" + UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByDifferentialPressureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);

        // Get all the deviceOverviewStatsList where differentialPressure is greater than DEFAULT_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldNotBeFound("differentialPressure.greaterThan=" + DEFAULT_DIFFERENTIAL_PRESSURE);

        // Get all the deviceOverviewStatsList where differentialPressure is greater than SMALLER_DIFFERENTIAL_PRESSURE
        defaultDeviceOverviewStatsShouldBeFound("differentialPressure.greaterThan=" + SMALLER_DIFFERENTIAL_PRESSURE);
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        deviceOverviewStats.addProduct(product);
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);
        Long productId = product.getId();

        // Get all the deviceOverviewStatsList where product equals to productId
        defaultDeviceOverviewStatsShouldBeFound("productId.equals=" + productId);

        // Get all the deviceOverviewStatsList where product equals to productId + 1
        defaultDeviceOverviewStatsShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllDeviceOverviewStatsByOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);
        Outlet outlet = OutletResourceIT.createEntity(em);
        em.persist(outlet);
        em.flush();
        deviceOverviewStats.setOutlet(outlet);
        deviceOverviewStatsRepository.saveAndFlush(deviceOverviewStats);
        Long outletId = outlet.getId();

        // Get all the deviceOverviewStatsList where outlet equals to outletId
        defaultDeviceOverviewStatsShouldBeFound("outletId.equals=" + outletId);

        // Get all the deviceOverviewStatsList where outlet equals to outletId + 1
        defaultDeviceOverviewStatsShouldNotBeFound("outletId.equals=" + (outletId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeviceOverviewStatsShouldBeFound(String filter) throws Exception {
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deviceOverviewStats.getId().intValue())))
            .andExpect(jsonPath("$.[*].deviceId").value(hasItem(DEFAULT_DEVICE_ID)))
            .andExpect(jsonPath("$.[*].timestamp").value(hasItem(DEFAULT_TIMESTAMP.toString())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].humidity").value(hasItem(DEFAULT_HUMIDITY.doubleValue())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].co2").value(hasItem(DEFAULT_CO_2.doubleValue())))
            .andExpect(jsonPath("$.[*].pressure").value(hasItem(DEFAULT_PRESSURE.doubleValue())))
            .andExpect(jsonPath("$.[*].differentialPressure").value(hasItem(DEFAULT_DIFFERENTIAL_PRESSURE.doubleValue())));

        // Check, that the count call also returns 1
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeviceOverviewStatsShouldNotBeFound(String filter) throws Exception {
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDeviceOverviewStats() throws Exception {
        // Get the deviceOverviewStats
        restDeviceOverviewStatsMockMvc.perform(get("/api/device-overview-stats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeviceOverviewStats() throws Exception {
        // Initialize the database
        deviceOverviewStatsService.save(deviceOverviewStats);

        int databaseSizeBeforeUpdate = deviceOverviewStatsRepository.findAll().size();

        // Update the deviceOverviewStats
        DeviceOverviewStats updatedDeviceOverviewStats = deviceOverviewStatsRepository.findById(deviceOverviewStats.getId()).get();
        // Disconnect from session so that the updates on updatedDeviceOverviewStats are not directly saved in db
        em.detach(updatedDeviceOverviewStats);
        updatedDeviceOverviewStats
            .deviceId(UPDATED_DEVICE_ID)
            .timestamp(UPDATED_TIMESTAMP)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .humidity(UPDATED_HUMIDITY)
            .temperature(UPDATED_TEMPERATURE)
            .co2(UPDATED_CO_2)
            .pressure(UPDATED_PRESSURE)
            .differentialPressure(UPDATED_DIFFERENTIAL_PRESSURE);

        restDeviceOverviewStatsMockMvc.perform(put("/api/device-overview-stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeviceOverviewStats)))
            .andExpect(status().isOk());

        // Validate the DeviceOverviewStats in the database
        List<DeviceOverviewStats> deviceOverviewStatsList = deviceOverviewStatsRepository.findAll();
        assertThat(deviceOverviewStatsList).hasSize(databaseSizeBeforeUpdate);
        DeviceOverviewStats testDeviceOverviewStats = deviceOverviewStatsList.get(deviceOverviewStatsList.size() - 1);
        assertThat(testDeviceOverviewStats.getDeviceId()).isEqualTo(UPDATED_DEVICE_ID);
        assertThat(testDeviceOverviewStats.getTimestamp()).isEqualTo(UPDATED_TIMESTAMP);
        assertThat(testDeviceOverviewStats.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testDeviceOverviewStats.getHumidity()).isEqualTo(UPDATED_HUMIDITY);
        assertThat(testDeviceOverviewStats.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testDeviceOverviewStats.getCo2()).isEqualTo(UPDATED_CO_2);
        assertThat(testDeviceOverviewStats.getPressure()).isEqualTo(UPDATED_PRESSURE);
        assertThat(testDeviceOverviewStats.getDifferentialPressure()).isEqualTo(UPDATED_DIFFERENTIAL_PRESSURE);
    }

    @Test
    @Transactional
    public void updateNonExistingDeviceOverviewStats() throws Exception {
        int databaseSizeBeforeUpdate = deviceOverviewStatsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviceOverviewStatsMockMvc.perform(put("/api/device-overview-stats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deviceOverviewStats)))
            .andExpect(status().isBadRequest());

        // Validate the DeviceOverviewStats in the database
        List<DeviceOverviewStats> deviceOverviewStatsList = deviceOverviewStatsRepository.findAll();
        assertThat(deviceOverviewStatsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeviceOverviewStats() throws Exception {
        // Initialize the database
        deviceOverviewStatsService.save(deviceOverviewStats);

        int databaseSizeBeforeDelete = deviceOverviewStatsRepository.findAll().size();

        // Delete the deviceOverviewStats
        restDeviceOverviewStatsMockMvc.perform(delete("/api/device-overview-stats/{id}", deviceOverviewStats.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeviceOverviewStats> deviceOverviewStatsList = deviceOverviewStatsRepository.findAll();
        assertThat(deviceOverviewStatsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
