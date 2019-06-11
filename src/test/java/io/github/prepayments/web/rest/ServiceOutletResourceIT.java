package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.ServiceOutlet;
import io.github.prepayments.repository.ServiceOutletRepository;
import io.github.prepayments.service.ServiceOutletService;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import io.github.prepayments.service.mapper.ServiceOutletMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.ServiceOutletCriteria;
import io.github.prepayments.service.ServiceOutletQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.prepayments.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ServiceOutletResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class ServiceOutletResourceIT {

    private static final String DEFAULT_SERVICE_OUTLET_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET_CODE = "943";
    private static final String UPDATED_SERVICE_OUTLET_CODE = "847";

    private static final String DEFAULT_SERVICE_OUTLET_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET_MANAGER = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_MANAGER = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_OF_STAFF = 1;
    private static final Integer UPDATED_NUMBER_OF_STAFF = 2;

    private static final String DEFAULT_BUILDING = "AAAAAAAAAA";
    private static final String UPDATED_BUILDING = "BBBBBBBBBB";

    private static final Integer DEFAULT_FLOOR = 1;
    private static final Integer UPDATED_FLOOR = 2;

    private static final String DEFAULT_POSTAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    @Autowired
    private ServiceOutletRepository serviceOutletRepository;

    @Autowired
    private ServiceOutletMapper serviceOutletMapper;

    @Autowired
    private ServiceOutletService serviceOutletService;

    @Autowired
    private ServiceOutletQueryService serviceOutletQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restServiceOutletMockMvc;

    private ServiceOutlet serviceOutlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOutletResource serviceOutletResource = new ServiceOutletResource(serviceOutletService, serviceOutletQueryService);
        this.restServiceOutletMockMvc = MockMvcBuilders.standaloneSetup(serviceOutletResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOutlet createEntity(EntityManager em) {
        ServiceOutlet serviceOutlet = new ServiceOutlet()
            .serviceOutletName(DEFAULT_SERVICE_OUTLET_NAME)
            .serviceOutletCode(DEFAULT_SERVICE_OUTLET_CODE)
            .serviceOutletLocation(DEFAULT_SERVICE_OUTLET_LOCATION)
            .serviceOutletManager(DEFAULT_SERVICE_OUTLET_MANAGER)
            .numberOfStaff(DEFAULT_NUMBER_OF_STAFF)
            .building(DEFAULT_BUILDING)
            .floor(DEFAULT_FLOOR)
            .postalAddress(DEFAULT_POSTAL_ADDRESS)
            .contactPersonName(DEFAULT_CONTACT_PERSON_NAME)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .street(DEFAULT_STREET);
        return serviceOutlet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOutlet createUpdatedEntity(EntityManager em) {
        ServiceOutlet serviceOutlet = new ServiceOutlet()
            .serviceOutletName(UPDATED_SERVICE_OUTLET_NAME)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .serviceOutletLocation(UPDATED_SERVICE_OUTLET_LOCATION)
            .serviceOutletManager(UPDATED_SERVICE_OUTLET_MANAGER)
            .numberOfStaff(UPDATED_NUMBER_OF_STAFF)
            .building(UPDATED_BUILDING)
            .floor(UPDATED_FLOOR)
            .postalAddress(UPDATED_POSTAL_ADDRESS)
            .contactPersonName(UPDATED_CONTACT_PERSON_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .street(UPDATED_STREET);
        return serviceOutlet;
    }

    @BeforeEach
    public void initTest() {
        serviceOutlet = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOutlet() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);
        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOutlet testServiceOutlet = serviceOutletList.get(serviceOutletList.size() - 1);
        assertThat(testServiceOutlet.getServiceOutletName()).isEqualTo(DEFAULT_SERVICE_OUTLET_NAME);
        assertThat(testServiceOutlet.getServiceOutletCode()).isEqualTo(DEFAULT_SERVICE_OUTLET_CODE);
        assertThat(testServiceOutlet.getServiceOutletLocation()).isEqualTo(DEFAULT_SERVICE_OUTLET_LOCATION);
        assertThat(testServiceOutlet.getServiceOutletManager()).isEqualTo(DEFAULT_SERVICE_OUTLET_MANAGER);
        assertThat(testServiceOutlet.getNumberOfStaff()).isEqualTo(DEFAULT_NUMBER_OF_STAFF);
        assertThat(testServiceOutlet.getBuilding()).isEqualTo(DEFAULT_BUILDING);
        assertThat(testServiceOutlet.getFloor()).isEqualTo(DEFAULT_FLOOR);
        assertThat(testServiceOutlet.getPostalAddress()).isEqualTo(DEFAULT_POSTAL_ADDRESS);
        assertThat(testServiceOutlet.getContactPersonName()).isEqualTo(DEFAULT_CONTACT_PERSON_NAME);
        assertThat(testServiceOutlet.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testServiceOutlet.getStreet()).isEqualTo(DEFAULT_STREET);
    }

    @Test
    @Transactional
    public void createServiceOutletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet with an existing ID
        serviceOutlet.setId(1L);
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkServiceOutletNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletRepository.findAll().size();
        // set the field null
        serviceOutlet.setServiceOutletName(null);

        // Create the ServiceOutlet, which fails.
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletRepository.findAll().size();
        // set the field null
        serviceOutlet.setServiceOutletCode(null);

        // Create the ServiceOutlet, which fails.
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOutlets() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletName").value(hasItem(DEFAULT_SERVICE_OUTLET_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletLocation").value(hasItem(DEFAULT_SERVICE_OUTLET_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletManager").value(hasItem(DEFAULT_SERVICE_OUTLET_MANAGER.toString())))
            .andExpect(jsonPath("$.[*].numberOfStaff").value(hasItem(DEFAULT_NUMBER_OF_STAFF)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING.toString())))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].postalAddress").value(hasItem(DEFAULT_POSTAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contactPersonName").value(hasItem(DEFAULT_CONTACT_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get the serviceOutlet
        restServiceOutletMockMvc.perform(get("/api/service-outlets/{id}", serviceOutlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOutlet.getId().intValue()))
            .andExpect(jsonPath("$.serviceOutletName").value(DEFAULT_SERVICE_OUTLET_NAME.toString()))
            .andExpect(jsonPath("$.serviceOutletCode").value(DEFAULT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.serviceOutletLocation").value(DEFAULT_SERVICE_OUTLET_LOCATION.toString()))
            .andExpect(jsonPath("$.serviceOutletManager").value(DEFAULT_SERVICE_OUTLET_MANAGER.toString()))
            .andExpect(jsonPath("$.numberOfStaff").value(DEFAULT_NUMBER_OF_STAFF))
            .andExpect(jsonPath("$.building").value(DEFAULT_BUILDING.toString()))
            .andExpect(jsonPath("$.floor").value(DEFAULT_FLOOR))
            .andExpect(jsonPath("$.postalAddress").value(DEFAULT_POSTAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.contactPersonName").value(DEFAULT_CONTACT_PERSON_NAME.toString()))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL.toString()))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletName equals to DEFAULT_SERVICE_OUTLET_NAME
        defaultServiceOutletShouldBeFound("serviceOutletName.equals=" + DEFAULT_SERVICE_OUTLET_NAME);

        // Get all the serviceOutletList where serviceOutletName equals to UPDATED_SERVICE_OUTLET_NAME
        defaultServiceOutletShouldNotBeFound("serviceOutletName.equals=" + UPDATED_SERVICE_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletName in DEFAULT_SERVICE_OUTLET_NAME or UPDATED_SERVICE_OUTLET_NAME
        defaultServiceOutletShouldBeFound("serviceOutletName.in=" + DEFAULT_SERVICE_OUTLET_NAME + "," + UPDATED_SERVICE_OUTLET_NAME);

        // Get all the serviceOutletList where serviceOutletName equals to UPDATED_SERVICE_OUTLET_NAME
        defaultServiceOutletShouldNotBeFound("serviceOutletName.in=" + UPDATED_SERVICE_OUTLET_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletName is not null
        defaultServiceOutletShouldBeFound("serviceOutletName.specified=true");

        // Get all the serviceOutletList where serviceOutletName is null
        defaultServiceOutletShouldNotBeFound("serviceOutletName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode equals to DEFAULT_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldBeFound("serviceOutletCode.equals=" + DEFAULT_SERVICE_OUTLET_CODE);

        // Get all the serviceOutletList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.equals=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode in DEFAULT_SERVICE_OUTLET_CODE or UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldBeFound("serviceOutletCode.in=" + DEFAULT_SERVICE_OUTLET_CODE + "," + UPDATED_SERVICE_OUTLET_CODE);

        // Get all the serviceOutletList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.in=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode is not null
        defaultServiceOutletShouldBeFound("serviceOutletCode.specified=true");

        // Get all the serviceOutletList where serviceOutletCode is null
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletLocation equals to DEFAULT_SERVICE_OUTLET_LOCATION
        defaultServiceOutletShouldBeFound("serviceOutletLocation.equals=" + DEFAULT_SERVICE_OUTLET_LOCATION);

        // Get all the serviceOutletList where serviceOutletLocation equals to UPDATED_SERVICE_OUTLET_LOCATION
        defaultServiceOutletShouldNotBeFound("serviceOutletLocation.equals=" + UPDATED_SERVICE_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletLocationIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletLocation in DEFAULT_SERVICE_OUTLET_LOCATION or UPDATED_SERVICE_OUTLET_LOCATION
        defaultServiceOutletShouldBeFound("serviceOutletLocation.in=" + DEFAULT_SERVICE_OUTLET_LOCATION + "," + UPDATED_SERVICE_OUTLET_LOCATION);

        // Get all the serviceOutletList where serviceOutletLocation equals to UPDATED_SERVICE_OUTLET_LOCATION
        defaultServiceOutletShouldNotBeFound("serviceOutletLocation.in=" + UPDATED_SERVICE_OUTLET_LOCATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletLocation is not null
        defaultServiceOutletShouldBeFound("serviceOutletLocation.specified=true");

        // Get all the serviceOutletList where serviceOutletLocation is null
        defaultServiceOutletShouldNotBeFound("serviceOutletLocation.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletManagerIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletManager equals to DEFAULT_SERVICE_OUTLET_MANAGER
        defaultServiceOutletShouldBeFound("serviceOutletManager.equals=" + DEFAULT_SERVICE_OUTLET_MANAGER);

        // Get all the serviceOutletList where serviceOutletManager equals to UPDATED_SERVICE_OUTLET_MANAGER
        defaultServiceOutletShouldNotBeFound("serviceOutletManager.equals=" + UPDATED_SERVICE_OUTLET_MANAGER);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletManagerIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletManager in DEFAULT_SERVICE_OUTLET_MANAGER or UPDATED_SERVICE_OUTLET_MANAGER
        defaultServiceOutletShouldBeFound("serviceOutletManager.in=" + DEFAULT_SERVICE_OUTLET_MANAGER + "," + UPDATED_SERVICE_OUTLET_MANAGER);

        // Get all the serviceOutletList where serviceOutletManager equals to UPDATED_SERVICE_OUTLET_MANAGER
        defaultServiceOutletShouldNotBeFound("serviceOutletManager.in=" + UPDATED_SERVICE_OUTLET_MANAGER);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletManagerIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletManager is not null
        defaultServiceOutletShouldBeFound("serviceOutletManager.specified=true");

        // Get all the serviceOutletList where serviceOutletManager is null
        defaultServiceOutletShouldNotBeFound("serviceOutletManager.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByNumberOfStaffIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where numberOfStaff equals to DEFAULT_NUMBER_OF_STAFF
        defaultServiceOutletShouldBeFound("numberOfStaff.equals=" + DEFAULT_NUMBER_OF_STAFF);

        // Get all the serviceOutletList where numberOfStaff equals to UPDATED_NUMBER_OF_STAFF
        defaultServiceOutletShouldNotBeFound("numberOfStaff.equals=" + UPDATED_NUMBER_OF_STAFF);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByNumberOfStaffIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where numberOfStaff in DEFAULT_NUMBER_OF_STAFF or UPDATED_NUMBER_OF_STAFF
        defaultServiceOutletShouldBeFound("numberOfStaff.in=" + DEFAULT_NUMBER_OF_STAFF + "," + UPDATED_NUMBER_OF_STAFF);

        // Get all the serviceOutletList where numberOfStaff equals to UPDATED_NUMBER_OF_STAFF
        defaultServiceOutletShouldNotBeFound("numberOfStaff.in=" + UPDATED_NUMBER_OF_STAFF);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByNumberOfStaffIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where numberOfStaff is not null
        defaultServiceOutletShouldBeFound("numberOfStaff.specified=true");

        // Get all the serviceOutletList where numberOfStaff is null
        defaultServiceOutletShouldNotBeFound("numberOfStaff.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByNumberOfStaffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where numberOfStaff greater than or equals to DEFAULT_NUMBER_OF_STAFF
        defaultServiceOutletShouldBeFound("numberOfStaff.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_STAFF);

        // Get all the serviceOutletList where numberOfStaff greater than or equals to UPDATED_NUMBER_OF_STAFF
        defaultServiceOutletShouldNotBeFound("numberOfStaff.greaterOrEqualThan=" + UPDATED_NUMBER_OF_STAFF);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByNumberOfStaffIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where numberOfStaff less than or equals to DEFAULT_NUMBER_OF_STAFF
        defaultServiceOutletShouldNotBeFound("numberOfStaff.lessThan=" + DEFAULT_NUMBER_OF_STAFF);

        // Get all the serviceOutletList where numberOfStaff less than or equals to UPDATED_NUMBER_OF_STAFF
        defaultServiceOutletShouldBeFound("numberOfStaff.lessThan=" + UPDATED_NUMBER_OF_STAFF);
    }


    @Test
    @Transactional
    public void getAllServiceOutletsByBuildingIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where building equals to DEFAULT_BUILDING
        defaultServiceOutletShouldBeFound("building.equals=" + DEFAULT_BUILDING);

        // Get all the serviceOutletList where building equals to UPDATED_BUILDING
        defaultServiceOutletShouldNotBeFound("building.equals=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByBuildingIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where building in DEFAULT_BUILDING or UPDATED_BUILDING
        defaultServiceOutletShouldBeFound("building.in=" + DEFAULT_BUILDING + "," + UPDATED_BUILDING);

        // Get all the serviceOutletList where building equals to UPDATED_BUILDING
        defaultServiceOutletShouldNotBeFound("building.in=" + UPDATED_BUILDING);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByBuildingIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where building is not null
        defaultServiceOutletShouldBeFound("building.specified=true");

        // Get all the serviceOutletList where building is null
        defaultServiceOutletShouldNotBeFound("building.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByFloorIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where floor equals to DEFAULT_FLOOR
        defaultServiceOutletShouldBeFound("floor.equals=" + DEFAULT_FLOOR);

        // Get all the serviceOutletList where floor equals to UPDATED_FLOOR
        defaultServiceOutletShouldNotBeFound("floor.equals=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByFloorIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where floor in DEFAULT_FLOOR or UPDATED_FLOOR
        defaultServiceOutletShouldBeFound("floor.in=" + DEFAULT_FLOOR + "," + UPDATED_FLOOR);

        // Get all the serviceOutletList where floor equals to UPDATED_FLOOR
        defaultServiceOutletShouldNotBeFound("floor.in=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByFloorIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where floor is not null
        defaultServiceOutletShouldBeFound("floor.specified=true");

        // Get all the serviceOutletList where floor is null
        defaultServiceOutletShouldNotBeFound("floor.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByFloorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where floor greater than or equals to DEFAULT_FLOOR
        defaultServiceOutletShouldBeFound("floor.greaterOrEqualThan=" + DEFAULT_FLOOR);

        // Get all the serviceOutletList where floor greater than or equals to UPDATED_FLOOR
        defaultServiceOutletShouldNotBeFound("floor.greaterOrEqualThan=" + UPDATED_FLOOR);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByFloorIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where floor less than or equals to DEFAULT_FLOOR
        defaultServiceOutletShouldNotBeFound("floor.lessThan=" + DEFAULT_FLOOR);

        // Get all the serviceOutletList where floor less than or equals to UPDATED_FLOOR
        defaultServiceOutletShouldBeFound("floor.lessThan=" + UPDATED_FLOOR);
    }


    @Test
    @Transactional
    public void getAllServiceOutletsByPostalAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where postalAddress equals to DEFAULT_POSTAL_ADDRESS
        defaultServiceOutletShouldBeFound("postalAddress.equals=" + DEFAULT_POSTAL_ADDRESS);

        // Get all the serviceOutletList where postalAddress equals to UPDATED_POSTAL_ADDRESS
        defaultServiceOutletShouldNotBeFound("postalAddress.equals=" + UPDATED_POSTAL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByPostalAddressIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where postalAddress in DEFAULT_POSTAL_ADDRESS or UPDATED_POSTAL_ADDRESS
        defaultServiceOutletShouldBeFound("postalAddress.in=" + DEFAULT_POSTAL_ADDRESS + "," + UPDATED_POSTAL_ADDRESS);

        // Get all the serviceOutletList where postalAddress equals to UPDATED_POSTAL_ADDRESS
        defaultServiceOutletShouldNotBeFound("postalAddress.in=" + UPDATED_POSTAL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByPostalAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where postalAddress is not null
        defaultServiceOutletShouldBeFound("postalAddress.specified=true");

        // Get all the serviceOutletList where postalAddress is null
        defaultServiceOutletShouldNotBeFound("postalAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactPersonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactPersonName equals to DEFAULT_CONTACT_PERSON_NAME
        defaultServiceOutletShouldBeFound("contactPersonName.equals=" + DEFAULT_CONTACT_PERSON_NAME);

        // Get all the serviceOutletList where contactPersonName equals to UPDATED_CONTACT_PERSON_NAME
        defaultServiceOutletShouldNotBeFound("contactPersonName.equals=" + UPDATED_CONTACT_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactPersonNameIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactPersonName in DEFAULT_CONTACT_PERSON_NAME or UPDATED_CONTACT_PERSON_NAME
        defaultServiceOutletShouldBeFound("contactPersonName.in=" + DEFAULT_CONTACT_PERSON_NAME + "," + UPDATED_CONTACT_PERSON_NAME);

        // Get all the serviceOutletList where contactPersonName equals to UPDATED_CONTACT_PERSON_NAME
        defaultServiceOutletShouldNotBeFound("contactPersonName.in=" + UPDATED_CONTACT_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactPersonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactPersonName is not null
        defaultServiceOutletShouldBeFound("contactPersonName.specified=true");

        // Get all the serviceOutletList where contactPersonName is null
        defaultServiceOutletShouldNotBeFound("contactPersonName.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactEmail equals to DEFAULT_CONTACT_EMAIL
        defaultServiceOutletShouldBeFound("contactEmail.equals=" + DEFAULT_CONTACT_EMAIL);

        // Get all the serviceOutletList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultServiceOutletShouldNotBeFound("contactEmail.equals=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactEmailIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactEmail in DEFAULT_CONTACT_EMAIL or UPDATED_CONTACT_EMAIL
        defaultServiceOutletShouldBeFound("contactEmail.in=" + DEFAULT_CONTACT_EMAIL + "," + UPDATED_CONTACT_EMAIL);

        // Get all the serviceOutletList where contactEmail equals to UPDATED_CONTACT_EMAIL
        defaultServiceOutletShouldNotBeFound("contactEmail.in=" + UPDATED_CONTACT_EMAIL);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByContactEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where contactEmail is not null
        defaultServiceOutletShouldBeFound("contactEmail.specified=true");

        // Get all the serviceOutletList where contactEmail is null
        defaultServiceOutletShouldNotBeFound("contactEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByStreetIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where street equals to DEFAULT_STREET
        defaultServiceOutletShouldBeFound("street.equals=" + DEFAULT_STREET);

        // Get all the serviceOutletList where street equals to UPDATED_STREET
        defaultServiceOutletShouldNotBeFound("street.equals=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByStreetIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where street in DEFAULT_STREET or UPDATED_STREET
        defaultServiceOutletShouldBeFound("street.in=" + DEFAULT_STREET + "," + UPDATED_STREET);

        // Get all the serviceOutletList where street equals to UPDATED_STREET
        defaultServiceOutletShouldNotBeFound("street.in=" + UPDATED_STREET);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByStreetIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where street is not null
        defaultServiceOutletShouldBeFound("street.specified=true");

        // Get all the serviceOutletList where street is null
        defaultServiceOutletShouldNotBeFound("street.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOutletShouldBeFound(String filter) throws Exception {
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletName").value(hasItem(DEFAULT_SERVICE_OUTLET_NAME)))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].serviceOutletLocation").value(hasItem(DEFAULT_SERVICE_OUTLET_LOCATION)))
            .andExpect(jsonPath("$.[*].serviceOutletManager").value(hasItem(DEFAULT_SERVICE_OUTLET_MANAGER)))
            .andExpect(jsonPath("$.[*].numberOfStaff").value(hasItem(DEFAULT_NUMBER_OF_STAFF)))
            .andExpect(jsonPath("$.[*].building").value(hasItem(DEFAULT_BUILDING)))
            .andExpect(jsonPath("$.[*].floor").value(hasItem(DEFAULT_FLOOR)))
            .andExpect(jsonPath("$.[*].postalAddress").value(hasItem(DEFAULT_POSTAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].contactPersonName").value(hasItem(DEFAULT_CONTACT_PERSON_NAME)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)));

        // Check, that the count call also returns 1
        restServiceOutletMockMvc.perform(get("/api/service-outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOutletShouldNotBeFound(String filter) throws Exception {
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOutletMockMvc.perform(get("/api/service-outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceOutlet() throws Exception {
        // Get the serviceOutlet
        restServiceOutletMockMvc.perform(get("/api/service-outlets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        int databaseSizeBeforeUpdate = serviceOutletRepository.findAll().size();

        // Update the serviceOutlet
        ServiceOutlet updatedServiceOutlet = serviceOutletRepository.findById(serviceOutlet.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOutlet are not directly saved in db
        em.detach(updatedServiceOutlet);
        updatedServiceOutlet
            .serviceOutletName(UPDATED_SERVICE_OUTLET_NAME)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .serviceOutletLocation(UPDATED_SERVICE_OUTLET_LOCATION)
            .serviceOutletManager(UPDATED_SERVICE_OUTLET_MANAGER)
            .numberOfStaff(UPDATED_NUMBER_OF_STAFF)
            .building(UPDATED_BUILDING)
            .floor(UPDATED_FLOOR)
            .postalAddress(UPDATED_POSTAL_ADDRESS)
            .contactPersonName(UPDATED_CONTACT_PERSON_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .street(UPDATED_STREET);
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(updatedServiceOutlet);

        restServiceOutletMockMvc.perform(put("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeUpdate);
        ServiceOutlet testServiceOutlet = serviceOutletList.get(serviceOutletList.size() - 1);
        assertThat(testServiceOutlet.getServiceOutletName()).isEqualTo(UPDATED_SERVICE_OUTLET_NAME);
        assertThat(testServiceOutlet.getServiceOutletCode()).isEqualTo(UPDATED_SERVICE_OUTLET_CODE);
        assertThat(testServiceOutlet.getServiceOutletLocation()).isEqualTo(UPDATED_SERVICE_OUTLET_LOCATION);
        assertThat(testServiceOutlet.getServiceOutletManager()).isEqualTo(UPDATED_SERVICE_OUTLET_MANAGER);
        assertThat(testServiceOutlet.getNumberOfStaff()).isEqualTo(UPDATED_NUMBER_OF_STAFF);
        assertThat(testServiceOutlet.getBuilding()).isEqualTo(UPDATED_BUILDING);
        assertThat(testServiceOutlet.getFloor()).isEqualTo(UPDATED_FLOOR);
        assertThat(testServiceOutlet.getPostalAddress()).isEqualTo(UPDATED_POSTAL_ADDRESS);
        assertThat(testServiceOutlet.getContactPersonName()).isEqualTo(UPDATED_CONTACT_PERSON_NAME);
        assertThat(testServiceOutlet.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testServiceOutlet.getStreet()).isEqualTo(UPDATED_STREET);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOutlet() throws Exception {
        int databaseSizeBeforeUpdate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOutletMockMvc.perform(put("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        int databaseSizeBeforeDelete = serviceOutletRepository.findAll().size();

        // Delete the serviceOutlet
        restServiceOutletMockMvc.perform(delete("/api/service-outlets/{id}", serviceOutlet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutlet.class);
        ServiceOutlet serviceOutlet1 = new ServiceOutlet();
        serviceOutlet1.setId(1L);
        ServiceOutlet serviceOutlet2 = new ServiceOutlet();
        serviceOutlet2.setId(serviceOutlet1.getId());
        assertThat(serviceOutlet1).isEqualTo(serviceOutlet2);
        serviceOutlet2.setId(2L);
        assertThat(serviceOutlet1).isNotEqualTo(serviceOutlet2);
        serviceOutlet1.setId(null);
        assertThat(serviceOutlet1).isNotEqualTo(serviceOutlet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutletDTO.class);
        ServiceOutletDTO serviceOutletDTO1 = new ServiceOutletDTO();
        serviceOutletDTO1.setId(1L);
        ServiceOutletDTO serviceOutletDTO2 = new ServiceOutletDTO();
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
        serviceOutletDTO2.setId(serviceOutletDTO1.getId());
        assertThat(serviceOutletDTO1).isEqualTo(serviceOutletDTO2);
        serviceOutletDTO2.setId(2L);
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
        serviceOutletDTO1.setId(null);
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceOutletMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceOutletMapper.fromId(null)).isNull();
    }
}
