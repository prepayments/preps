package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.RegisteredSupplier;
import io.github.prepayments.repository.RegisteredSupplierRepository;
import io.github.prepayments.service.RegisteredSupplierService;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import io.github.prepayments.service.mapper.RegisteredSupplierMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.RegisteredSupplierCriteria;
import io.github.prepayments.service.RegisteredSupplierQueryService;

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
 * Integration tests for the {@Link RegisteredSupplierResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class RegisteredSupplierResourceIT {

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_BANK_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_BANK_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BANK_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_PHYSICAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BANK_PHYSICAL_ADDRESS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LOCALLY_DOMICILED = false;
    private static final Boolean UPDATED_LOCALLY_DOMICILED = true;

    private static final String DEFAULT_TAX_AUTHORITY_PIN = "AAAAAAAAAA";
    private static final String UPDATED_TAX_AUTHORITY_PIN = "BBBBBBBBBB";

    @Autowired
    private RegisteredSupplierRepository registeredSupplierRepository;

    @Autowired
    private RegisteredSupplierMapper registeredSupplierMapper;

    @Autowired
    private RegisteredSupplierService registeredSupplierService;

    @Autowired
    private RegisteredSupplierQueryService registeredSupplierQueryService;

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

    private MockMvc restRegisteredSupplierMockMvc;

    private RegisteredSupplier registeredSupplier;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegisteredSupplierResource registeredSupplierResource = new RegisteredSupplierResource(registeredSupplierService, registeredSupplierQueryService);
        this.restRegisteredSupplierMockMvc = MockMvcBuilders.standaloneSetup(registeredSupplierResource)
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
    public static RegisteredSupplier createEntity(EntityManager em) {
        RegisteredSupplier registeredSupplier = new RegisteredSupplier()
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .supplierAddress(DEFAULT_SUPPLIER_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .supplierEmail(DEFAULT_SUPPLIER_EMAIL)
            .bankAccountName(DEFAULT_BANK_ACCOUNT_NAME)
            .bankAccountNumber(DEFAULT_BANK_ACCOUNT_NUMBER)
            .supplierBankName(DEFAULT_SUPPLIER_BANK_NAME)
            .supplierBankBranch(DEFAULT_SUPPLIER_BANK_BRANCH)
            .bankSwiftCode(DEFAULT_BANK_SWIFT_CODE)
            .bankPhysicalAddress(DEFAULT_BANK_PHYSICAL_ADDRESS)
            .locallyDomiciled(DEFAULT_LOCALLY_DOMICILED)
            .taxAuthorityPIN(DEFAULT_TAX_AUTHORITY_PIN);
        return registeredSupplier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegisteredSupplier createUpdatedEntity(EntityManager em) {
        RegisteredSupplier registeredSupplier = new RegisteredSupplier()
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankBranch(UPDATED_SUPPLIER_BANK_BRANCH)
            .bankSwiftCode(UPDATED_BANK_SWIFT_CODE)
            .bankPhysicalAddress(UPDATED_BANK_PHYSICAL_ADDRESS)
            .locallyDomiciled(UPDATED_LOCALLY_DOMICILED)
            .taxAuthorityPIN(UPDATED_TAX_AUTHORITY_PIN);
        return registeredSupplier;
    }

    @BeforeEach
    public void initTest() {
        registeredSupplier = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegisteredSupplier() throws Exception {
        int databaseSizeBeforeCreate = registeredSupplierRepository.findAll().size();

        // Create the RegisteredSupplier
        RegisteredSupplierDTO registeredSupplierDTO = registeredSupplierMapper.toDto(registeredSupplier);
        restRegisteredSupplierMockMvc.perform(post("/api/registered-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredSupplierDTO)))
            .andExpect(status().isCreated());

        // Validate the RegisteredSupplier in the database
        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeCreate + 1);
        RegisteredSupplier testRegisteredSupplier = registeredSupplierList.get(registeredSupplierList.size() - 1);
        assertThat(testRegisteredSupplier.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testRegisteredSupplier.getSupplierAddress()).isEqualTo(DEFAULT_SUPPLIER_ADDRESS);
        assertThat(testRegisteredSupplier.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testRegisteredSupplier.getSupplierEmail()).isEqualTo(DEFAULT_SUPPLIER_EMAIL);
        assertThat(testRegisteredSupplier.getBankAccountName()).isEqualTo(DEFAULT_BANK_ACCOUNT_NAME);
        assertThat(testRegisteredSupplier.getBankAccountNumber()).isEqualTo(DEFAULT_BANK_ACCOUNT_NUMBER);
        assertThat(testRegisteredSupplier.getSupplierBankName()).isEqualTo(DEFAULT_SUPPLIER_BANK_NAME);
        assertThat(testRegisteredSupplier.getSupplierBankBranch()).isEqualTo(DEFAULT_SUPPLIER_BANK_BRANCH);
        assertThat(testRegisteredSupplier.getBankSwiftCode()).isEqualTo(DEFAULT_BANK_SWIFT_CODE);
        assertThat(testRegisteredSupplier.getBankPhysicalAddress()).isEqualTo(DEFAULT_BANK_PHYSICAL_ADDRESS);
        assertThat(testRegisteredSupplier.isLocallyDomiciled()).isEqualTo(DEFAULT_LOCALLY_DOMICILED);
        assertThat(testRegisteredSupplier.getTaxAuthorityPIN()).isEqualTo(DEFAULT_TAX_AUTHORITY_PIN);
    }

    @Test
    @Transactional
    public void createRegisteredSupplierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registeredSupplierRepository.findAll().size();

        // Create the RegisteredSupplier with an existing ID
        registeredSupplier.setId(1L);
        RegisteredSupplierDTO registeredSupplierDTO = registeredSupplierMapper.toDto(registeredSupplier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegisteredSupplierMockMvc.perform(post("/api/registered-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredSupplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegisteredSupplier in the database
        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSupplierNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = registeredSupplierRepository.findAll().size();
        // set the field null
        registeredSupplier.setSupplierName(null);

        // Create the RegisteredSupplier, which fails.
        RegisteredSupplierDTO registeredSupplierDTO = registeredSupplierMapper.toDto(registeredSupplier);

        restRegisteredSupplierMockMvc.perform(post("/api/registered-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredSupplierDTO)))
            .andExpect(status().isBadRequest());

        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliers() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registeredSupplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].supplierEmail").value(hasItem(DEFAULT_SUPPLIER_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].supplierBankName").value(hasItem(DEFAULT_SUPPLIER_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].supplierBankBranch").value(hasItem(DEFAULT_SUPPLIER_BANK_BRANCH.toString())))
            .andExpect(jsonPath("$.[*].bankSwiftCode").value(hasItem(DEFAULT_BANK_SWIFT_CODE.toString())))
            .andExpect(jsonPath("$.[*].bankPhysicalAddress").value(hasItem(DEFAULT_BANK_PHYSICAL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].locallyDomiciled").value(hasItem(DEFAULT_LOCALLY_DOMICILED.booleanValue())))
            .andExpect(jsonPath("$.[*].taxAuthorityPIN").value(hasItem(DEFAULT_TAX_AUTHORITY_PIN.toString())));
    }
    
    @Test
    @Transactional
    public void getRegisteredSupplier() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get the registeredSupplier
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers/{id}", registeredSupplier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registeredSupplier.getId().intValue()))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.supplierAddress").value(DEFAULT_SUPPLIER_ADDRESS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.supplierEmail").value(DEFAULT_SUPPLIER_EMAIL.toString()))
            .andExpect(jsonPath("$.bankAccountName").value(DEFAULT_BANK_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.bankAccountNumber").value(DEFAULT_BANK_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.supplierBankName").value(DEFAULT_SUPPLIER_BANK_NAME.toString()))
            .andExpect(jsonPath("$.supplierBankBranch").value(DEFAULT_SUPPLIER_BANK_BRANCH.toString()))
            .andExpect(jsonPath("$.bankSwiftCode").value(DEFAULT_BANK_SWIFT_CODE.toString()))
            .andExpect(jsonPath("$.bankPhysicalAddress").value(DEFAULT_BANK_PHYSICAL_ADDRESS.toString()))
            .andExpect(jsonPath("$.locallyDomiciled").value(DEFAULT_LOCALLY_DOMICILED.booleanValue()))
            .andExpect(jsonPath("$.taxAuthorityPIN").value(DEFAULT_TAX_AUTHORITY_PIN.toString()));
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierNameIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierName equals to DEFAULT_SUPPLIER_NAME
        defaultRegisteredSupplierShouldBeFound("supplierName.equals=" + DEFAULT_SUPPLIER_NAME);

        // Get all the registeredSupplierList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultRegisteredSupplierShouldNotBeFound("supplierName.equals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierNameIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierName in DEFAULT_SUPPLIER_NAME or UPDATED_SUPPLIER_NAME
        defaultRegisteredSupplierShouldBeFound("supplierName.in=" + DEFAULT_SUPPLIER_NAME + "," + UPDATED_SUPPLIER_NAME);

        // Get all the registeredSupplierList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultRegisteredSupplierShouldNotBeFound("supplierName.in=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierName is not null
        defaultRegisteredSupplierShouldBeFound("supplierName.specified=true");

        // Get all the registeredSupplierList where supplierName is null
        defaultRegisteredSupplierShouldNotBeFound("supplierName.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierAddress equals to DEFAULT_SUPPLIER_ADDRESS
        defaultRegisteredSupplierShouldBeFound("supplierAddress.equals=" + DEFAULT_SUPPLIER_ADDRESS);

        // Get all the registeredSupplierList where supplierAddress equals to UPDATED_SUPPLIER_ADDRESS
        defaultRegisteredSupplierShouldNotBeFound("supplierAddress.equals=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierAddressIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierAddress in DEFAULT_SUPPLIER_ADDRESS or UPDATED_SUPPLIER_ADDRESS
        defaultRegisteredSupplierShouldBeFound("supplierAddress.in=" + DEFAULT_SUPPLIER_ADDRESS + "," + UPDATED_SUPPLIER_ADDRESS);

        // Get all the registeredSupplierList where supplierAddress equals to UPDATED_SUPPLIER_ADDRESS
        defaultRegisteredSupplierShouldNotBeFound("supplierAddress.in=" + UPDATED_SUPPLIER_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierAddress is not null
        defaultRegisteredSupplierShouldBeFound("supplierAddress.specified=true");

        // Get all the registeredSupplierList where supplierAddress is null
        defaultRegisteredSupplierShouldNotBeFound("supplierAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultRegisteredSupplierShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the registeredSupplierList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultRegisteredSupplierShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultRegisteredSupplierShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the registeredSupplierList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultRegisteredSupplierShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where phoneNumber is not null
        defaultRegisteredSupplierShouldBeFound("phoneNumber.specified=true");

        // Get all the registeredSupplierList where phoneNumber is null
        defaultRegisteredSupplierShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierEmail equals to DEFAULT_SUPPLIER_EMAIL
        defaultRegisteredSupplierShouldBeFound("supplierEmail.equals=" + DEFAULT_SUPPLIER_EMAIL);

        // Get all the registeredSupplierList where supplierEmail equals to UPDATED_SUPPLIER_EMAIL
        defaultRegisteredSupplierShouldNotBeFound("supplierEmail.equals=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierEmailIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierEmail in DEFAULT_SUPPLIER_EMAIL or UPDATED_SUPPLIER_EMAIL
        defaultRegisteredSupplierShouldBeFound("supplierEmail.in=" + DEFAULT_SUPPLIER_EMAIL + "," + UPDATED_SUPPLIER_EMAIL);

        // Get all the registeredSupplierList where supplierEmail equals to UPDATED_SUPPLIER_EMAIL
        defaultRegisteredSupplierShouldNotBeFound("supplierEmail.in=" + UPDATED_SUPPLIER_EMAIL);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierEmail is not null
        defaultRegisteredSupplierShouldBeFound("supplierEmail.specified=true");

        // Get all the registeredSupplierList where supplierEmail is null
        defaultRegisteredSupplierShouldNotBeFound("supplierEmail.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountName equals to DEFAULT_BANK_ACCOUNT_NAME
        defaultRegisteredSupplierShouldBeFound("bankAccountName.equals=" + DEFAULT_BANK_ACCOUNT_NAME);

        // Get all the registeredSupplierList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultRegisteredSupplierShouldNotBeFound("bankAccountName.equals=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountName in DEFAULT_BANK_ACCOUNT_NAME or UPDATED_BANK_ACCOUNT_NAME
        defaultRegisteredSupplierShouldBeFound("bankAccountName.in=" + DEFAULT_BANK_ACCOUNT_NAME + "," + UPDATED_BANK_ACCOUNT_NAME);

        // Get all the registeredSupplierList where bankAccountName equals to UPDATED_BANK_ACCOUNT_NAME
        defaultRegisteredSupplierShouldNotBeFound("bankAccountName.in=" + UPDATED_BANK_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountName is not null
        defaultRegisteredSupplierShouldBeFound("bankAccountName.specified=true");

        // Get all the registeredSupplierList where bankAccountName is null
        defaultRegisteredSupplierShouldNotBeFound("bankAccountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountNumber equals to DEFAULT_BANK_ACCOUNT_NUMBER
        defaultRegisteredSupplierShouldBeFound("bankAccountNumber.equals=" + DEFAULT_BANK_ACCOUNT_NUMBER);

        // Get all the registeredSupplierList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultRegisteredSupplierShouldNotBeFound("bankAccountNumber.equals=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountNumber in DEFAULT_BANK_ACCOUNT_NUMBER or UPDATED_BANK_ACCOUNT_NUMBER
        defaultRegisteredSupplierShouldBeFound("bankAccountNumber.in=" + DEFAULT_BANK_ACCOUNT_NUMBER + "," + UPDATED_BANK_ACCOUNT_NUMBER);

        // Get all the registeredSupplierList where bankAccountNumber equals to UPDATED_BANK_ACCOUNT_NUMBER
        defaultRegisteredSupplierShouldNotBeFound("bankAccountNumber.in=" + UPDATED_BANK_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankAccountNumber is not null
        defaultRegisteredSupplierShouldBeFound("bankAccountNumber.specified=true");

        // Get all the registeredSupplierList where bankAccountNumber is null
        defaultRegisteredSupplierShouldNotBeFound("bankAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankName equals to DEFAULT_SUPPLIER_BANK_NAME
        defaultRegisteredSupplierShouldBeFound("supplierBankName.equals=" + DEFAULT_SUPPLIER_BANK_NAME);

        // Get all the registeredSupplierList where supplierBankName equals to UPDATED_SUPPLIER_BANK_NAME
        defaultRegisteredSupplierShouldNotBeFound("supplierBankName.equals=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankName in DEFAULT_SUPPLIER_BANK_NAME or UPDATED_SUPPLIER_BANK_NAME
        defaultRegisteredSupplierShouldBeFound("supplierBankName.in=" + DEFAULT_SUPPLIER_BANK_NAME + "," + UPDATED_SUPPLIER_BANK_NAME);

        // Get all the registeredSupplierList where supplierBankName equals to UPDATED_SUPPLIER_BANK_NAME
        defaultRegisteredSupplierShouldNotBeFound("supplierBankName.in=" + UPDATED_SUPPLIER_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankName is not null
        defaultRegisteredSupplierShouldBeFound("supplierBankName.specified=true");

        // Get all the registeredSupplierList where supplierBankName is null
        defaultRegisteredSupplierShouldNotBeFound("supplierBankName.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankBranch equals to DEFAULT_SUPPLIER_BANK_BRANCH
        defaultRegisteredSupplierShouldBeFound("supplierBankBranch.equals=" + DEFAULT_SUPPLIER_BANK_BRANCH);

        // Get all the registeredSupplierList where supplierBankBranch equals to UPDATED_SUPPLIER_BANK_BRANCH
        defaultRegisteredSupplierShouldNotBeFound("supplierBankBranch.equals=" + UPDATED_SUPPLIER_BANK_BRANCH);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankBranchIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankBranch in DEFAULT_SUPPLIER_BANK_BRANCH or UPDATED_SUPPLIER_BANK_BRANCH
        defaultRegisteredSupplierShouldBeFound("supplierBankBranch.in=" + DEFAULT_SUPPLIER_BANK_BRANCH + "," + UPDATED_SUPPLIER_BANK_BRANCH);

        // Get all the registeredSupplierList where supplierBankBranch equals to UPDATED_SUPPLIER_BANK_BRANCH
        defaultRegisteredSupplierShouldNotBeFound("supplierBankBranch.in=" + UPDATED_SUPPLIER_BANK_BRANCH);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersBySupplierBankBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where supplierBankBranch is not null
        defaultRegisteredSupplierShouldBeFound("supplierBankBranch.specified=true");

        // Get all the registeredSupplierList where supplierBankBranch is null
        defaultRegisteredSupplierShouldNotBeFound("supplierBankBranch.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankSwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankSwiftCode equals to DEFAULT_BANK_SWIFT_CODE
        defaultRegisteredSupplierShouldBeFound("bankSwiftCode.equals=" + DEFAULT_BANK_SWIFT_CODE);

        // Get all the registeredSupplierList where bankSwiftCode equals to UPDATED_BANK_SWIFT_CODE
        defaultRegisteredSupplierShouldNotBeFound("bankSwiftCode.equals=" + UPDATED_BANK_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankSwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankSwiftCode in DEFAULT_BANK_SWIFT_CODE or UPDATED_BANK_SWIFT_CODE
        defaultRegisteredSupplierShouldBeFound("bankSwiftCode.in=" + DEFAULT_BANK_SWIFT_CODE + "," + UPDATED_BANK_SWIFT_CODE);

        // Get all the registeredSupplierList where bankSwiftCode equals to UPDATED_BANK_SWIFT_CODE
        defaultRegisteredSupplierShouldNotBeFound("bankSwiftCode.in=" + UPDATED_BANK_SWIFT_CODE);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankSwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankSwiftCode is not null
        defaultRegisteredSupplierShouldBeFound("bankSwiftCode.specified=true");

        // Get all the registeredSupplierList where bankSwiftCode is null
        defaultRegisteredSupplierShouldNotBeFound("bankSwiftCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankPhysicalAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankPhysicalAddress equals to DEFAULT_BANK_PHYSICAL_ADDRESS
        defaultRegisteredSupplierShouldBeFound("bankPhysicalAddress.equals=" + DEFAULT_BANK_PHYSICAL_ADDRESS);

        // Get all the registeredSupplierList where bankPhysicalAddress equals to UPDATED_BANK_PHYSICAL_ADDRESS
        defaultRegisteredSupplierShouldNotBeFound("bankPhysicalAddress.equals=" + UPDATED_BANK_PHYSICAL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankPhysicalAddressIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankPhysicalAddress in DEFAULT_BANK_PHYSICAL_ADDRESS or UPDATED_BANK_PHYSICAL_ADDRESS
        defaultRegisteredSupplierShouldBeFound("bankPhysicalAddress.in=" + DEFAULT_BANK_PHYSICAL_ADDRESS + "," + UPDATED_BANK_PHYSICAL_ADDRESS);

        // Get all the registeredSupplierList where bankPhysicalAddress equals to UPDATED_BANK_PHYSICAL_ADDRESS
        defaultRegisteredSupplierShouldNotBeFound("bankPhysicalAddress.in=" + UPDATED_BANK_PHYSICAL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByBankPhysicalAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where bankPhysicalAddress is not null
        defaultRegisteredSupplierShouldBeFound("bankPhysicalAddress.specified=true");

        // Get all the registeredSupplierList where bankPhysicalAddress is null
        defaultRegisteredSupplierShouldNotBeFound("bankPhysicalAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByLocallyDomiciledIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where locallyDomiciled equals to DEFAULT_LOCALLY_DOMICILED
        defaultRegisteredSupplierShouldBeFound("locallyDomiciled.equals=" + DEFAULT_LOCALLY_DOMICILED);

        // Get all the registeredSupplierList where locallyDomiciled equals to UPDATED_LOCALLY_DOMICILED
        defaultRegisteredSupplierShouldNotBeFound("locallyDomiciled.equals=" + UPDATED_LOCALLY_DOMICILED);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByLocallyDomiciledIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where locallyDomiciled in DEFAULT_LOCALLY_DOMICILED or UPDATED_LOCALLY_DOMICILED
        defaultRegisteredSupplierShouldBeFound("locallyDomiciled.in=" + DEFAULT_LOCALLY_DOMICILED + "," + UPDATED_LOCALLY_DOMICILED);

        // Get all the registeredSupplierList where locallyDomiciled equals to UPDATED_LOCALLY_DOMICILED
        defaultRegisteredSupplierShouldNotBeFound("locallyDomiciled.in=" + UPDATED_LOCALLY_DOMICILED);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByLocallyDomiciledIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where locallyDomiciled is not null
        defaultRegisteredSupplierShouldBeFound("locallyDomiciled.specified=true");

        // Get all the registeredSupplierList where locallyDomiciled is null
        defaultRegisteredSupplierShouldNotBeFound("locallyDomiciled.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByTaxAuthorityPINIsEqualToSomething() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where taxAuthorityPIN equals to DEFAULT_TAX_AUTHORITY_PIN
        defaultRegisteredSupplierShouldBeFound("taxAuthorityPIN.equals=" + DEFAULT_TAX_AUTHORITY_PIN);

        // Get all the registeredSupplierList where taxAuthorityPIN equals to UPDATED_TAX_AUTHORITY_PIN
        defaultRegisteredSupplierShouldNotBeFound("taxAuthorityPIN.equals=" + UPDATED_TAX_AUTHORITY_PIN);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByTaxAuthorityPINIsInShouldWork() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where taxAuthorityPIN in DEFAULT_TAX_AUTHORITY_PIN or UPDATED_TAX_AUTHORITY_PIN
        defaultRegisteredSupplierShouldBeFound("taxAuthorityPIN.in=" + DEFAULT_TAX_AUTHORITY_PIN + "," + UPDATED_TAX_AUTHORITY_PIN);

        // Get all the registeredSupplierList where taxAuthorityPIN equals to UPDATED_TAX_AUTHORITY_PIN
        defaultRegisteredSupplierShouldNotBeFound("taxAuthorityPIN.in=" + UPDATED_TAX_AUTHORITY_PIN);
    }

    @Test
    @Transactional
    public void getAllRegisteredSuppliersByTaxAuthorityPINIsNullOrNotNull() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        // Get all the registeredSupplierList where taxAuthorityPIN is not null
        defaultRegisteredSupplierShouldBeFound("taxAuthorityPIN.specified=true");

        // Get all the registeredSupplierList where taxAuthorityPIN is null
        defaultRegisteredSupplierShouldNotBeFound("taxAuthorityPIN.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRegisteredSupplierShouldBeFound(String filter) throws Exception {
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registeredSupplier.getId().intValue())))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].supplierAddress").value(hasItem(DEFAULT_SUPPLIER_ADDRESS)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierEmail").value(hasItem(DEFAULT_SUPPLIER_EMAIL)))
            .andExpect(jsonPath("$.[*].bankAccountName").value(hasItem(DEFAULT_BANK_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].bankAccountNumber").value(hasItem(DEFAULT_BANK_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].supplierBankName").value(hasItem(DEFAULT_SUPPLIER_BANK_NAME)))
            .andExpect(jsonPath("$.[*].supplierBankBranch").value(hasItem(DEFAULT_SUPPLIER_BANK_BRANCH)))
            .andExpect(jsonPath("$.[*].bankSwiftCode").value(hasItem(DEFAULT_BANK_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].bankPhysicalAddress").value(hasItem(DEFAULT_BANK_PHYSICAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].locallyDomiciled").value(hasItem(DEFAULT_LOCALLY_DOMICILED.booleanValue())))
            .andExpect(jsonPath("$.[*].taxAuthorityPIN").value(hasItem(DEFAULT_TAX_AUTHORITY_PIN)));

        // Check, that the count call also returns 1
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRegisteredSupplierShouldNotBeFound(String filter) throws Exception {
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegisteredSupplier() throws Exception {
        // Get the registeredSupplier
        restRegisteredSupplierMockMvc.perform(get("/api/registered-suppliers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegisteredSupplier() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        int databaseSizeBeforeUpdate = registeredSupplierRepository.findAll().size();

        // Update the registeredSupplier
        RegisteredSupplier updatedRegisteredSupplier = registeredSupplierRepository.findById(registeredSupplier.getId()).get();
        // Disconnect from session so that the updates on updatedRegisteredSupplier are not directly saved in db
        em.detach(updatedRegisteredSupplier);
        updatedRegisteredSupplier
            .supplierName(UPDATED_SUPPLIER_NAME)
            .supplierAddress(UPDATED_SUPPLIER_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .supplierEmail(UPDATED_SUPPLIER_EMAIL)
            .bankAccountName(UPDATED_BANK_ACCOUNT_NAME)
            .bankAccountNumber(UPDATED_BANK_ACCOUNT_NUMBER)
            .supplierBankName(UPDATED_SUPPLIER_BANK_NAME)
            .supplierBankBranch(UPDATED_SUPPLIER_BANK_BRANCH)
            .bankSwiftCode(UPDATED_BANK_SWIFT_CODE)
            .bankPhysicalAddress(UPDATED_BANK_PHYSICAL_ADDRESS)
            .locallyDomiciled(UPDATED_LOCALLY_DOMICILED)
            .taxAuthorityPIN(UPDATED_TAX_AUTHORITY_PIN);
        RegisteredSupplierDTO registeredSupplierDTO = registeredSupplierMapper.toDto(updatedRegisteredSupplier);

        restRegisteredSupplierMockMvc.perform(put("/api/registered-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredSupplierDTO)))
            .andExpect(status().isOk());

        // Validate the RegisteredSupplier in the database
        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeUpdate);
        RegisteredSupplier testRegisteredSupplier = registeredSupplierList.get(registeredSupplierList.size() - 1);
        assertThat(testRegisteredSupplier.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testRegisteredSupplier.getSupplierAddress()).isEqualTo(UPDATED_SUPPLIER_ADDRESS);
        assertThat(testRegisteredSupplier.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testRegisteredSupplier.getSupplierEmail()).isEqualTo(UPDATED_SUPPLIER_EMAIL);
        assertThat(testRegisteredSupplier.getBankAccountName()).isEqualTo(UPDATED_BANK_ACCOUNT_NAME);
        assertThat(testRegisteredSupplier.getBankAccountNumber()).isEqualTo(UPDATED_BANK_ACCOUNT_NUMBER);
        assertThat(testRegisteredSupplier.getSupplierBankName()).isEqualTo(UPDATED_SUPPLIER_BANK_NAME);
        assertThat(testRegisteredSupplier.getSupplierBankBranch()).isEqualTo(UPDATED_SUPPLIER_BANK_BRANCH);
        assertThat(testRegisteredSupplier.getBankSwiftCode()).isEqualTo(UPDATED_BANK_SWIFT_CODE);
        assertThat(testRegisteredSupplier.getBankPhysicalAddress()).isEqualTo(UPDATED_BANK_PHYSICAL_ADDRESS);
        assertThat(testRegisteredSupplier.isLocallyDomiciled()).isEqualTo(UPDATED_LOCALLY_DOMICILED);
        assertThat(testRegisteredSupplier.getTaxAuthorityPIN()).isEqualTo(UPDATED_TAX_AUTHORITY_PIN);
    }

    @Test
    @Transactional
    public void updateNonExistingRegisteredSupplier() throws Exception {
        int databaseSizeBeforeUpdate = registeredSupplierRepository.findAll().size();

        // Create the RegisteredSupplier
        RegisteredSupplierDTO registeredSupplierDTO = registeredSupplierMapper.toDto(registeredSupplier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegisteredSupplierMockMvc.perform(put("/api/registered-suppliers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registeredSupplierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RegisteredSupplier in the database
        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegisteredSupplier() throws Exception {
        // Initialize the database
        registeredSupplierRepository.saveAndFlush(registeredSupplier);

        int databaseSizeBeforeDelete = registeredSupplierRepository.findAll().size();

        // Delete the registeredSupplier
        restRegisteredSupplierMockMvc.perform(delete("/api/registered-suppliers/{id}", registeredSupplier.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<RegisteredSupplier> registeredSupplierList = registeredSupplierRepository.findAll();
        assertThat(registeredSupplierList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegisteredSupplier.class);
        RegisteredSupplier registeredSupplier1 = new RegisteredSupplier();
        registeredSupplier1.setId(1L);
        RegisteredSupplier registeredSupplier2 = new RegisteredSupplier();
        registeredSupplier2.setId(registeredSupplier1.getId());
        assertThat(registeredSupplier1).isEqualTo(registeredSupplier2);
        registeredSupplier2.setId(2L);
        assertThat(registeredSupplier1).isNotEqualTo(registeredSupplier2);
        registeredSupplier1.setId(null);
        assertThat(registeredSupplier1).isNotEqualTo(registeredSupplier2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegisteredSupplierDTO.class);
        RegisteredSupplierDTO registeredSupplierDTO1 = new RegisteredSupplierDTO();
        registeredSupplierDTO1.setId(1L);
        RegisteredSupplierDTO registeredSupplierDTO2 = new RegisteredSupplierDTO();
        assertThat(registeredSupplierDTO1).isNotEqualTo(registeredSupplierDTO2);
        registeredSupplierDTO2.setId(registeredSupplierDTO1.getId());
        assertThat(registeredSupplierDTO1).isEqualTo(registeredSupplierDTO2);
        registeredSupplierDTO2.setId(2L);
        assertThat(registeredSupplierDTO1).isNotEqualTo(registeredSupplierDTO2);
        registeredSupplierDTO1.setId(null);
        assertThat(registeredSupplierDTO1).isNotEqualTo(registeredSupplierDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(registeredSupplierMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(registeredSupplierMapper.fromId(null)).isNull();
    }
}
