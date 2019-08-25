package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AmortizationUpload;
import io.github.prepayments.repository.AmortizationUploadRepository;
import io.github.prepayments.repository.search.AmortizationUploadSearchRepository;
import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.service.mapper.AmortizationUploadMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.AmortizationUploadQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static io.github.prepayments.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AmortizationUploadResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationUploadResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARTICULARS = "AAAAAAAAAA";
    private static final String UPDATED_PARTICULARS = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSE_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSE_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PREPAYMENT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREPAYMENT_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREPAYMENT_TRANSACTION_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMORTIZATION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMORTIZATION_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_NUMBER_OF_AMORTIZATIONS = 1;
    private static final Integer UPDATED_NUMBER_OF_AMORTIZATIONS = 2;

    private static final LocalDate DEFAULT_FIRST_AMORTIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIRST_AMORTIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MONTHLY_AMORTIZATION_DATE = "0";
    private static final String UPDATED_MONTHLY_AMORTIZATION_DATE = "24";

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_ORPHANED = false;
    private static final Boolean UPDATED_UPLOAD_ORPHANED = true;

    private static final String DEFAULT_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private AmortizationUploadRepository amortizationUploadRepository;

    @Autowired
    private AmortizationUploadMapper amortizationUploadMapper;

    @Autowired
    private AmortizationUploadService amortizationUploadService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.AmortizationUploadSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationUploadSearchRepository mockAmortizationUploadSearchRepository;

    @Autowired
    private AmortizationUploadQueryService amortizationUploadQueryService;

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

    private MockMvc restAmortizationUploadMockMvc;

    private AmortizationUpload amortizationUpload;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationUploadResource amortizationUploadResource = new AmortizationUploadResource(amortizationUploadService, amortizationUploadQueryService);
        this.restAmortizationUploadMockMvc = MockMvcBuilders.standaloneSetup(amortizationUploadResource)
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
    public static AmortizationUpload createEntity(EntityManager em) {
        AmortizationUpload amortizationUpload = new AmortizationUpload()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .particulars(DEFAULT_PARTICULARS)
            .amortizationServiceOutletCode(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE)
            .prepaymentServiceOutletCode(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE)
            .prepaymentAccountNumber(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)
            .expenseAccountNumber(DEFAULT_EXPENSE_ACCOUNT_NUMBER)
            .prepaymentTransactionId(DEFAULT_PREPAYMENT_TRANSACTION_ID)
            .prepaymentTransactionDate(DEFAULT_PREPAYMENT_TRANSACTION_DATE)
            .prepaymentTransactionAmount(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT)
            .amortizationAmount(DEFAULT_AMORTIZATION_AMOUNT)
            .numberOfAmortizations(DEFAULT_NUMBER_OF_AMORTIZATIONS)
            .firstAmortizationDate(DEFAULT_FIRST_AMORTIZATION_DATE)
            .monthlyAmortizationDate(DEFAULT_MONTHLY_AMORTIZATION_DATE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadOrphaned(DEFAULT_UPLOAD_ORPHANED)
            .originatingFileToken(DEFAULT_ORIGINATING_FILE_TOKEN);
        return amortizationUpload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationUpload createUpdatedEntity(EntityManager em) {
        AmortizationUpload amortizationUpload = new AmortizationUpload()
            .accountName(UPDATED_ACCOUNT_NAME)
            .particulars(UPDATED_PARTICULARS)
            .amortizationServiceOutletCode(UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE)
            .prepaymentServiceOutletCode(UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .expenseAccountNumber(UPDATED_EXPENSE_ACCOUNT_NUMBER)
            .prepaymentTransactionId(UPDATED_PREPAYMENT_TRANSACTION_ID)
            .prepaymentTransactionDate(UPDATED_PREPAYMENT_TRANSACTION_DATE)
            .prepaymentTransactionAmount(UPDATED_PREPAYMENT_TRANSACTION_AMOUNT)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .numberOfAmortizations(UPDATED_NUMBER_OF_AMORTIZATIONS)
            .firstAmortizationDate(UPDATED_FIRST_AMORTIZATION_DATE)
            .monthlyAmortizationDate(UPDATED_MONTHLY_AMORTIZATION_DATE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadOrphaned(UPDATED_UPLOAD_ORPHANED)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN);
        return amortizationUpload;
    }

    @BeforeEach
    public void initTest() {
        amortizationUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationUpload() throws Exception {
        int databaseSizeBeforeCreate = amortizationUploadRepository.findAll().size();

        // Create the AmortizationUpload
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);
        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationUpload in the database
        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationUpload testAmortizationUpload = amortizationUploadList.get(amortizationUploadList.size() - 1);
        assertThat(testAmortizationUpload.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testAmortizationUpload.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testAmortizationUpload.getAmortizationServiceOutletCode()).isEqualTo(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE);
        assertThat(testAmortizationUpload.getPrepaymentServiceOutletCode()).isEqualTo(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE);
        assertThat(testAmortizationUpload.getPrepaymentAccountNumber()).isEqualTo(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testAmortizationUpload.getExpenseAccountNumber()).isEqualTo(DEFAULT_EXPENSE_ACCOUNT_NUMBER);
        assertThat(testAmortizationUpload.getPrepaymentTransactionId()).isEqualTo(DEFAULT_PREPAYMENT_TRANSACTION_ID);
        assertThat(testAmortizationUpload.getPrepaymentTransactionDate()).isEqualTo(DEFAULT_PREPAYMENT_TRANSACTION_DATE);
        assertThat(testAmortizationUpload.getPrepaymentTransactionAmount()).isEqualTo(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT);
        assertThat(testAmortizationUpload.getAmortizationAmount()).isEqualTo(DEFAULT_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationUpload.getNumberOfAmortizations()).isEqualTo(DEFAULT_NUMBER_OF_AMORTIZATIONS);
        assertThat(testAmortizationUpload.getFirstAmortizationDate()).isEqualTo(DEFAULT_FIRST_AMORTIZATION_DATE);
        assertThat(testAmortizationUpload.getMonthlyAmortizationDate()).isEqualTo(DEFAULT_MONTHLY_AMORTIZATION_DATE);
        assertThat(testAmortizationUpload.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUpload.isUploadOrphaned()).isEqualTo(DEFAULT_UPLOAD_ORPHANED);
        assertThat(testAmortizationUpload.getOriginatingFileToken()).isEqualTo(DEFAULT_ORIGINATING_FILE_TOKEN);

        // Validate the AmortizationUpload in Elasticsearch
        verify(mockAmortizationUploadSearchRepository, times(1)).save(testAmortizationUpload);
    }

    @Test
    @Transactional
    public void createAmortizationUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationUploadRepository.findAll().size();

        // Create the AmortizationUpload with an existing ID
        amortizationUpload.setId(1L);
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUpload in the database
        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmortizationUpload in Elasticsearch
        verify(mockAmortizationUploadSearchRepository, times(0)).save(amortizationUpload);
    }


    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setAccountName(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParticularsIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setParticulars(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setAmortizationServiceOutletCode(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setPrepaymentServiceOutletCode(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setPrepaymentAccountNumber(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExpenseAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setExpenseAccountNumber(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setPrepaymentTransactionId(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setPrepaymentTransactionDate(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setAmortizationAmount(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfAmortizationsIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setNumberOfAmortizations(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstAmortizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadRepository.findAll().size();
        // set the field null
        amortizationUpload.setFirstAmortizationDate(null);

        // Create the AmortizationUpload, which fails.
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        restAmortizationUploadMockMvc.perform(post("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploads() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].amortizationServiceOutletCode").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutletCode").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].expenseAccountNumber").value(hasItem(DEFAULT_EXPENSE_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].prepaymentTransactionId").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].prepaymentTransactionDate").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].prepaymentTransactionAmount").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].numberOfAmortizations").value(hasItem(DEFAULT_NUMBER_OF_AMORTIZATIONS)))
            .andExpect(jsonPath("$.[*].firstAmortizationDate").value(hasItem(DEFAULT_FIRST_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyAmortizationDate").value(hasItem(DEFAULT_MONTHLY_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadOrphaned").value(hasItem(DEFAULT_UPLOAD_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortizationUpload() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get the amortizationUpload
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads/{id}", amortizationUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationUpload.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.amortizationServiceOutletCode").value(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.prepaymentServiceOutletCode").value(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.prepaymentAccountNumber").value(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.expenseAccountNumber").value(DEFAULT_EXPENSE_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.prepaymentTransactionId").value(DEFAULT_PREPAYMENT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.prepaymentTransactionDate").value(DEFAULT_PREPAYMENT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.prepaymentTransactionAmount").value(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.amortizationAmount").value(DEFAULT_AMORTIZATION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.numberOfAmortizations").value(DEFAULT_NUMBER_OF_AMORTIZATIONS))
            .andExpect(jsonPath("$.firstAmortizationDate").value(DEFAULT_FIRST_AMORTIZATION_DATE.toString()))
            .andExpect(jsonPath("$.monthlyAmortizationDate").value(DEFAULT_MONTHLY_AMORTIZATION_DATE.toString()))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadOrphaned").value(DEFAULT_UPLOAD_ORPHANED.booleanValue()))
            .andExpect(jsonPath("$.originatingFileToken").value(DEFAULT_ORIGINATING_FILE_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultAmortizationUploadShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the amortizationUploadList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAmortizationUploadShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultAmortizationUploadShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the amortizationUploadList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAmortizationUploadShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where accountName is not null
        defaultAmortizationUploadShouldBeFound("accountName.specified=true");

        // Get all the amortizationUploadList where accountName is null
        defaultAmortizationUploadShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByParticularsIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where particulars equals to DEFAULT_PARTICULARS
        defaultAmortizationUploadShouldBeFound("particulars.equals=" + DEFAULT_PARTICULARS);

        // Get all the amortizationUploadList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationUploadShouldNotBeFound("particulars.equals=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByParticularsIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where particulars in DEFAULT_PARTICULARS or UPDATED_PARTICULARS
        defaultAmortizationUploadShouldBeFound("particulars.in=" + DEFAULT_PARTICULARS + "," + UPDATED_PARTICULARS);

        // Get all the amortizationUploadList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationUploadShouldNotBeFound("particulars.in=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByParticularsIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where particulars is not null
        defaultAmortizationUploadShouldBeFound("particulars.specified=true");

        // Get all the amortizationUploadList where particulars is null
        defaultAmortizationUploadShouldNotBeFound("particulars.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationServiceOutletCode equals to DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldBeFound("amortizationServiceOutletCode.equals=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE);

        // Get all the amortizationUploadList where amortizationServiceOutletCode equals to UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldNotBeFound("amortizationServiceOutletCode.equals=" + UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationServiceOutletCode in DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE or UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldBeFound("amortizationServiceOutletCode.in=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE + "," + UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE);

        // Get all the amortizationUploadList where amortizationServiceOutletCode equals to UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldNotBeFound("amortizationServiceOutletCode.in=" + UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationServiceOutletCode is not null
        defaultAmortizationUploadShouldBeFound("amortizationServiceOutletCode.specified=true");

        // Get all the amortizationUploadList where amortizationServiceOutletCode is null
        defaultAmortizationUploadShouldNotBeFound("amortizationServiceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentServiceOutletCode equals to DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldBeFound("prepaymentServiceOutletCode.equals=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE);

        // Get all the amortizationUploadList where prepaymentServiceOutletCode equals to UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldNotBeFound("prepaymentServiceOutletCode.equals=" + UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentServiceOutletCode in DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE or UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldBeFound("prepaymentServiceOutletCode.in=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE + "," + UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE);

        // Get all the amortizationUploadList where prepaymentServiceOutletCode equals to UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE
        defaultAmortizationUploadShouldNotBeFound("prepaymentServiceOutletCode.in=" + UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentServiceOutletCode is not null
        defaultAmortizationUploadShouldBeFound("prepaymentServiceOutletCode.specified=true");

        // Get all the amortizationUploadList where prepaymentServiceOutletCode is null
        defaultAmortizationUploadShouldNotBeFound("prepaymentServiceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentAccountNumber equals to DEFAULT_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldBeFound("prepaymentAccountNumber.equals=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the amortizationUploadList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldNotBeFound("prepaymentAccountNumber.equals=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentAccountNumber in DEFAULT_PREPAYMENT_ACCOUNT_NUMBER or UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldBeFound("prepaymentAccountNumber.in=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER + "," + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the amortizationUploadList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldNotBeFound("prepaymentAccountNumber.in=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentAccountNumber is not null
        defaultAmortizationUploadShouldBeFound("prepaymentAccountNumber.specified=true");

        // Get all the amortizationUploadList where prepaymentAccountNumber is null
        defaultAmortizationUploadShouldNotBeFound("prepaymentAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByExpenseAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where expenseAccountNumber equals to DEFAULT_EXPENSE_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldBeFound("expenseAccountNumber.equals=" + DEFAULT_EXPENSE_ACCOUNT_NUMBER);

        // Get all the amortizationUploadList where expenseAccountNumber equals to UPDATED_EXPENSE_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldNotBeFound("expenseAccountNumber.equals=" + UPDATED_EXPENSE_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByExpenseAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where expenseAccountNumber in DEFAULT_EXPENSE_ACCOUNT_NUMBER or UPDATED_EXPENSE_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldBeFound("expenseAccountNumber.in=" + DEFAULT_EXPENSE_ACCOUNT_NUMBER + "," + UPDATED_EXPENSE_ACCOUNT_NUMBER);

        // Get all the amortizationUploadList where expenseAccountNumber equals to UPDATED_EXPENSE_ACCOUNT_NUMBER
        defaultAmortizationUploadShouldNotBeFound("expenseAccountNumber.in=" + UPDATED_EXPENSE_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByExpenseAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where expenseAccountNumber is not null
        defaultAmortizationUploadShouldBeFound("expenseAccountNumber.specified=true");

        // Get all the amortizationUploadList where expenseAccountNumber is null
        defaultAmortizationUploadShouldNotBeFound("expenseAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionId equals to DEFAULT_PREPAYMENT_TRANSACTION_ID
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionId.equals=" + DEFAULT_PREPAYMENT_TRANSACTION_ID);

        // Get all the amortizationUploadList where prepaymentTransactionId equals to UPDATED_PREPAYMENT_TRANSACTION_ID
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionId.equals=" + UPDATED_PREPAYMENT_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionId in DEFAULT_PREPAYMENT_TRANSACTION_ID or UPDATED_PREPAYMENT_TRANSACTION_ID
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionId.in=" + DEFAULT_PREPAYMENT_TRANSACTION_ID + "," + UPDATED_PREPAYMENT_TRANSACTION_ID);

        // Get all the amortizationUploadList where prepaymentTransactionId equals to UPDATED_PREPAYMENT_TRANSACTION_ID
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionId.in=" + UPDATED_PREPAYMENT_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionId is not null
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionId.specified=true");

        // Get all the amortizationUploadList where prepaymentTransactionId is null
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionDate equals to DEFAULT_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionDate.equals=" + DEFAULT_PREPAYMENT_TRANSACTION_DATE);

        // Get all the amortizationUploadList where prepaymentTransactionDate equals to UPDATED_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionDate.equals=" + UPDATED_PREPAYMENT_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionDate in DEFAULT_PREPAYMENT_TRANSACTION_DATE or UPDATED_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionDate.in=" + DEFAULT_PREPAYMENT_TRANSACTION_DATE + "," + UPDATED_PREPAYMENT_TRANSACTION_DATE);

        // Get all the amortizationUploadList where prepaymentTransactionDate equals to UPDATED_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionDate.in=" + UPDATED_PREPAYMENT_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionDate is not null
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionDate.specified=true");

        // Get all the amortizationUploadList where prepaymentTransactionDate is null
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionDate greater than or equals to DEFAULT_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionDate.greaterOrEqualThan=" + DEFAULT_PREPAYMENT_TRANSACTION_DATE);

        // Get all the amortizationUploadList where prepaymentTransactionDate greater than or equals to UPDATED_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionDate.greaterOrEqualThan=" + UPDATED_PREPAYMENT_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionDate less than or equals to DEFAULT_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionDate.lessThan=" + DEFAULT_PREPAYMENT_TRANSACTION_DATE);

        // Get all the amortizationUploadList where prepaymentTransactionDate less than or equals to UPDATED_PREPAYMENT_TRANSACTION_DATE
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionDate.lessThan=" + UPDATED_PREPAYMENT_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionAmount equals to DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionAmount.equals=" + DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT);

        // Get all the amortizationUploadList where prepaymentTransactionAmount equals to UPDATED_PREPAYMENT_TRANSACTION_AMOUNT
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionAmount.equals=" + UPDATED_PREPAYMENT_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionAmount in DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT or UPDATED_PREPAYMENT_TRANSACTION_AMOUNT
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionAmount.in=" + DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT + "," + UPDATED_PREPAYMENT_TRANSACTION_AMOUNT);

        // Get all the amortizationUploadList where prepaymentTransactionAmount equals to UPDATED_PREPAYMENT_TRANSACTION_AMOUNT
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionAmount.in=" + UPDATED_PREPAYMENT_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByPrepaymentTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where prepaymentTransactionAmount is not null
        defaultAmortizationUploadShouldBeFound("prepaymentTransactionAmount.specified=true");

        // Get all the amortizationUploadList where prepaymentTransactionAmount is null
        defaultAmortizationUploadShouldNotBeFound("prepaymentTransactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationAmount equals to DEFAULT_AMORTIZATION_AMOUNT
        defaultAmortizationUploadShouldBeFound("amortizationAmount.equals=" + DEFAULT_AMORTIZATION_AMOUNT);

        // Get all the amortizationUploadList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationUploadShouldNotBeFound("amortizationAmount.equals=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationAmount in DEFAULT_AMORTIZATION_AMOUNT or UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationUploadShouldBeFound("amortizationAmount.in=" + DEFAULT_AMORTIZATION_AMOUNT + "," + UPDATED_AMORTIZATION_AMOUNT);

        // Get all the amortizationUploadList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationUploadShouldNotBeFound("amortizationAmount.in=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByAmortizationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where amortizationAmount is not null
        defaultAmortizationUploadShouldBeFound("amortizationAmount.specified=true");

        // Get all the amortizationUploadList where amortizationAmount is null
        defaultAmortizationUploadShouldNotBeFound("amortizationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByNumberOfAmortizationsIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where numberOfAmortizations equals to DEFAULT_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldBeFound("numberOfAmortizations.equals=" + DEFAULT_NUMBER_OF_AMORTIZATIONS);

        // Get all the amortizationUploadList where numberOfAmortizations equals to UPDATED_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldNotBeFound("numberOfAmortizations.equals=" + UPDATED_NUMBER_OF_AMORTIZATIONS);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByNumberOfAmortizationsIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where numberOfAmortizations in DEFAULT_NUMBER_OF_AMORTIZATIONS or UPDATED_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldBeFound("numberOfAmortizations.in=" + DEFAULT_NUMBER_OF_AMORTIZATIONS + "," + UPDATED_NUMBER_OF_AMORTIZATIONS);

        // Get all the amortizationUploadList where numberOfAmortizations equals to UPDATED_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldNotBeFound("numberOfAmortizations.in=" + UPDATED_NUMBER_OF_AMORTIZATIONS);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByNumberOfAmortizationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where numberOfAmortizations is not null
        defaultAmortizationUploadShouldBeFound("numberOfAmortizations.specified=true");

        // Get all the amortizationUploadList where numberOfAmortizations is null
        defaultAmortizationUploadShouldNotBeFound("numberOfAmortizations.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByNumberOfAmortizationsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where numberOfAmortizations greater than or equals to DEFAULT_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldBeFound("numberOfAmortizations.greaterOrEqualThan=" + DEFAULT_NUMBER_OF_AMORTIZATIONS);

        // Get all the amortizationUploadList where numberOfAmortizations greater than or equals to UPDATED_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldNotBeFound("numberOfAmortizations.greaterOrEqualThan=" + UPDATED_NUMBER_OF_AMORTIZATIONS);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByNumberOfAmortizationsIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where numberOfAmortizations less than or equals to DEFAULT_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldNotBeFound("numberOfAmortizations.lessThan=" + DEFAULT_NUMBER_OF_AMORTIZATIONS);

        // Get all the amortizationUploadList where numberOfAmortizations less than or equals to UPDATED_NUMBER_OF_AMORTIZATIONS
        defaultAmortizationUploadShouldBeFound("numberOfAmortizations.lessThan=" + UPDATED_NUMBER_OF_AMORTIZATIONS);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadsByFirstAmortizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where firstAmortizationDate equals to DEFAULT_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("firstAmortizationDate.equals=" + DEFAULT_FIRST_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where firstAmortizationDate equals to UPDATED_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("firstAmortizationDate.equals=" + UPDATED_FIRST_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByFirstAmortizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where firstAmortizationDate in DEFAULT_FIRST_AMORTIZATION_DATE or UPDATED_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("firstAmortizationDate.in=" + DEFAULT_FIRST_AMORTIZATION_DATE + "," + UPDATED_FIRST_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where firstAmortizationDate equals to UPDATED_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("firstAmortizationDate.in=" + UPDATED_FIRST_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByFirstAmortizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where firstAmortizationDate is not null
        defaultAmortizationUploadShouldBeFound("firstAmortizationDate.specified=true");

        // Get all the amortizationUploadList where firstAmortizationDate is null
        defaultAmortizationUploadShouldNotBeFound("firstAmortizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByFirstAmortizationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where firstAmortizationDate greater than or equals to DEFAULT_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("firstAmortizationDate.greaterOrEqualThan=" + DEFAULT_FIRST_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where firstAmortizationDate greater than or equals to UPDATED_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("firstAmortizationDate.greaterOrEqualThan=" + UPDATED_FIRST_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByFirstAmortizationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where firstAmortizationDate less than or equals to DEFAULT_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("firstAmortizationDate.lessThan=" + DEFAULT_FIRST_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where firstAmortizationDate less than or equals to UPDATED_FIRST_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("firstAmortizationDate.lessThan=" + UPDATED_FIRST_AMORTIZATION_DATE);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadsByMonthlyAmortizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where monthlyAmortizationDate equals to DEFAULT_MONTHLY_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("monthlyAmortizationDate.equals=" + DEFAULT_MONTHLY_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where monthlyAmortizationDate equals to UPDATED_MONTHLY_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("monthlyAmortizationDate.equals=" + UPDATED_MONTHLY_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByMonthlyAmortizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where monthlyAmortizationDate in DEFAULT_MONTHLY_AMORTIZATION_DATE or UPDATED_MONTHLY_AMORTIZATION_DATE
        defaultAmortizationUploadShouldBeFound("monthlyAmortizationDate.in=" + DEFAULT_MONTHLY_AMORTIZATION_DATE + "," + UPDATED_MONTHLY_AMORTIZATION_DATE);

        // Get all the amortizationUploadList where monthlyAmortizationDate equals to UPDATED_MONTHLY_AMORTIZATION_DATE
        defaultAmortizationUploadShouldNotBeFound("monthlyAmortizationDate.in=" + UPDATED_MONTHLY_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByMonthlyAmortizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where monthlyAmortizationDate is not null
        defaultAmortizationUploadShouldBeFound("monthlyAmortizationDate.specified=true");

        // Get all the amortizationUploadList where monthlyAmortizationDate is null
        defaultAmortizationUploadShouldNotBeFound("monthlyAmortizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadSuccessful is not null
        defaultAmortizationUploadShouldBeFound("uploadSuccessful.specified=true");

        // Get all the amortizationUploadList where uploadSuccessful is null
        defaultAmortizationUploadShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadOrphanedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadOrphaned equals to DEFAULT_UPLOAD_ORPHANED
        defaultAmortizationUploadShouldBeFound("uploadOrphaned.equals=" + DEFAULT_UPLOAD_ORPHANED);

        // Get all the amortizationUploadList where uploadOrphaned equals to UPDATED_UPLOAD_ORPHANED
        defaultAmortizationUploadShouldNotBeFound("uploadOrphaned.equals=" + UPDATED_UPLOAD_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadOrphanedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadOrphaned in DEFAULT_UPLOAD_ORPHANED or UPDATED_UPLOAD_ORPHANED
        defaultAmortizationUploadShouldBeFound("uploadOrphaned.in=" + DEFAULT_UPLOAD_ORPHANED + "," + UPDATED_UPLOAD_ORPHANED);

        // Get all the amortizationUploadList where uploadOrphaned equals to UPDATED_UPLOAD_ORPHANED
        defaultAmortizationUploadShouldNotBeFound("uploadOrphaned.in=" + UPDATED_UPLOAD_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByUploadOrphanedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where uploadOrphaned is not null
        defaultAmortizationUploadShouldBeFound("uploadOrphaned.specified=true");

        // Get all the amortizationUploadList where uploadOrphaned is null
        defaultAmortizationUploadShouldNotBeFound("uploadOrphaned.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByOriginatingFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where originatingFileToken equals to DEFAULT_ORIGINATING_FILE_TOKEN
        defaultAmortizationUploadShouldBeFound("originatingFileToken.equals=" + DEFAULT_ORIGINATING_FILE_TOKEN);

        // Get all the amortizationUploadList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationUploadShouldNotBeFound("originatingFileToken.equals=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByOriginatingFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where originatingFileToken in DEFAULT_ORIGINATING_FILE_TOKEN or UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationUploadShouldBeFound("originatingFileToken.in=" + DEFAULT_ORIGINATING_FILE_TOKEN + "," + UPDATED_ORIGINATING_FILE_TOKEN);

        // Get all the amortizationUploadList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationUploadShouldNotBeFound("originatingFileToken.in=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadsByOriginatingFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        // Get all the amortizationUploadList where originatingFileToken is not null
        defaultAmortizationUploadShouldBeFound("originatingFileToken.specified=true");

        // Get all the amortizationUploadList where originatingFileToken is null
        defaultAmortizationUploadShouldNotBeFound("originatingFileToken.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationUploadShouldBeFound(String filter) throws Exception {
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutletCode").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutletCode").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].expenseAccountNumber").value(hasItem(DEFAULT_EXPENSE_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].prepaymentTransactionId").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].prepaymentTransactionDate").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].prepaymentTransactionAmount").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].numberOfAmortizations").value(hasItem(DEFAULT_NUMBER_OF_AMORTIZATIONS)))
            .andExpect(jsonPath("$.[*].firstAmortizationDate").value(hasItem(DEFAULT_FIRST_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyAmortizationDate").value(hasItem(DEFAULT_MONTHLY_AMORTIZATION_DATE)))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadOrphaned").value(hasItem(DEFAULT_UPLOAD_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)));

        // Check, that the count call also returns 1
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationUploadShouldNotBeFound(String filter) throws Exception {
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationUpload() throws Exception {
        // Get the amortizationUpload
        restAmortizationUploadMockMvc.perform(get("/api/amortization-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationUpload() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        int databaseSizeBeforeUpdate = amortizationUploadRepository.findAll().size();

        // Update the amortizationUpload
        AmortizationUpload updatedAmortizationUpload = amortizationUploadRepository.findById(amortizationUpload.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationUpload are not directly saved in db
        em.detach(updatedAmortizationUpload);
        updatedAmortizationUpload
            .accountName(UPDATED_ACCOUNT_NAME)
            .particulars(UPDATED_PARTICULARS)
            .amortizationServiceOutletCode(UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE)
            .prepaymentServiceOutletCode(UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .expenseAccountNumber(UPDATED_EXPENSE_ACCOUNT_NUMBER)
            .prepaymentTransactionId(UPDATED_PREPAYMENT_TRANSACTION_ID)
            .prepaymentTransactionDate(UPDATED_PREPAYMENT_TRANSACTION_DATE)
            .prepaymentTransactionAmount(UPDATED_PREPAYMENT_TRANSACTION_AMOUNT)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .numberOfAmortizations(UPDATED_NUMBER_OF_AMORTIZATIONS)
            .firstAmortizationDate(UPDATED_FIRST_AMORTIZATION_DATE)
            .monthlyAmortizationDate(UPDATED_MONTHLY_AMORTIZATION_DATE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadOrphaned(UPDATED_UPLOAD_ORPHANED)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN);
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(updatedAmortizationUpload);

        restAmortizationUploadMockMvc.perform(put("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationUpload in the database
        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeUpdate);
        AmortizationUpload testAmortizationUpload = amortizationUploadList.get(amortizationUploadList.size() - 1);
        assertThat(testAmortizationUpload.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testAmortizationUpload.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testAmortizationUpload.getAmortizationServiceOutletCode()).isEqualTo(UPDATED_AMORTIZATION_SERVICE_OUTLET_CODE);
        assertThat(testAmortizationUpload.getPrepaymentServiceOutletCode()).isEqualTo(UPDATED_PREPAYMENT_SERVICE_OUTLET_CODE);
        assertThat(testAmortizationUpload.getPrepaymentAccountNumber()).isEqualTo(UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testAmortizationUpload.getExpenseAccountNumber()).isEqualTo(UPDATED_EXPENSE_ACCOUNT_NUMBER);
        assertThat(testAmortizationUpload.getPrepaymentTransactionId()).isEqualTo(UPDATED_PREPAYMENT_TRANSACTION_ID);
        assertThat(testAmortizationUpload.getPrepaymentTransactionDate()).isEqualTo(UPDATED_PREPAYMENT_TRANSACTION_DATE);
        assertThat(testAmortizationUpload.getPrepaymentTransactionAmount()).isEqualTo(UPDATED_PREPAYMENT_TRANSACTION_AMOUNT);
        assertThat(testAmortizationUpload.getAmortizationAmount()).isEqualTo(UPDATED_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationUpload.getNumberOfAmortizations()).isEqualTo(UPDATED_NUMBER_OF_AMORTIZATIONS);
        assertThat(testAmortizationUpload.getFirstAmortizationDate()).isEqualTo(UPDATED_FIRST_AMORTIZATION_DATE);
        assertThat(testAmortizationUpload.getMonthlyAmortizationDate()).isEqualTo(UPDATED_MONTHLY_AMORTIZATION_DATE);
        assertThat(testAmortizationUpload.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUpload.isUploadOrphaned()).isEqualTo(UPDATED_UPLOAD_ORPHANED);
        assertThat(testAmortizationUpload.getOriginatingFileToken()).isEqualTo(UPDATED_ORIGINATING_FILE_TOKEN);

        // Validate the AmortizationUpload in Elasticsearch
        verify(mockAmortizationUploadSearchRepository, times(1)).save(testAmortizationUpload);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationUpload() throws Exception {
        int databaseSizeBeforeUpdate = amortizationUploadRepository.findAll().size();

        // Create the AmortizationUpload
        AmortizationUploadDTO amortizationUploadDTO = amortizationUploadMapper.toDto(amortizationUpload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationUploadMockMvc.perform(put("/api/amortization-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUpload in the database
        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmortizationUpload in Elasticsearch
        verify(mockAmortizationUploadSearchRepository, times(0)).save(amortizationUpload);
    }

    @Test
    @Transactional
    public void deleteAmortizationUpload() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);

        int databaseSizeBeforeDelete = amortizationUploadRepository.findAll().size();

        // Delete the amortizationUpload
        restAmortizationUploadMockMvc.perform(delete("/api/amortization-uploads/{id}", amortizationUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationUpload> amortizationUploadList = amortizationUploadRepository.findAll();
        assertThat(amortizationUploadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmortizationUpload in Elasticsearch
        verify(mockAmortizationUploadSearchRepository, times(1)).deleteById(amortizationUpload.getId());
    }

    @Test
    @Transactional
    public void searchAmortizationUpload() throws Exception {
        // Initialize the database
        amortizationUploadRepository.saveAndFlush(amortizationUpload);
        when(mockAmortizationUploadSearchRepository.search(queryStringQuery("id:" + amortizationUpload.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizationUpload), PageRequest.of(0, 1), 1));
        // Search the amortizationUpload
        restAmortizationUploadMockMvc.perform(get("/api/_search/amortization-uploads?query=id:" + amortizationUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutletCode").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutletCode").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].expenseAccountNumber").value(hasItem(DEFAULT_EXPENSE_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].prepaymentTransactionId").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].prepaymentTransactionDate").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].prepaymentTransactionAmount").value(hasItem(DEFAULT_PREPAYMENT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].numberOfAmortizations").value(hasItem(DEFAULT_NUMBER_OF_AMORTIZATIONS)))
            .andExpect(jsonPath("$.[*].firstAmortizationDate").value(hasItem(DEFAULT_FIRST_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].monthlyAmortizationDate").value(hasItem(DEFAULT_MONTHLY_AMORTIZATION_DATE)))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadOrphaned").value(hasItem(DEFAULT_UPLOAD_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUpload.class);
        AmortizationUpload amortizationUpload1 = new AmortizationUpload();
        amortizationUpload1.setId(1L);
        AmortizationUpload amortizationUpload2 = new AmortizationUpload();
        amortizationUpload2.setId(amortizationUpload1.getId());
        assertThat(amortizationUpload1).isEqualTo(amortizationUpload2);
        amortizationUpload2.setId(2L);
        assertThat(amortizationUpload1).isNotEqualTo(amortizationUpload2);
        amortizationUpload1.setId(null);
        assertThat(amortizationUpload1).isNotEqualTo(amortizationUpload2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUploadDTO.class);
        AmortizationUploadDTO amortizationUploadDTO1 = new AmortizationUploadDTO();
        amortizationUploadDTO1.setId(1L);
        AmortizationUploadDTO amortizationUploadDTO2 = new AmortizationUploadDTO();
        assertThat(amortizationUploadDTO1).isNotEqualTo(amortizationUploadDTO2);
        amortizationUploadDTO2.setId(amortizationUploadDTO1.getId());
        assertThat(amortizationUploadDTO1).isEqualTo(amortizationUploadDTO2);
        amortizationUploadDTO2.setId(2L);
        assertThat(amortizationUploadDTO1).isNotEqualTo(amortizationUploadDTO2);
        amortizationUploadDTO1.setId(null);
        assertThat(amortizationUploadDTO1).isNotEqualTo(amortizationUploadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationUploadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationUploadMapper.fromId(null)).isNull();
    }
}
