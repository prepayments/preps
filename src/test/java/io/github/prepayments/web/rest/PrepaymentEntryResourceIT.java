package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.PrepaymentEntry;
import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.repository.PrepaymentEntryRepository;
import io.github.prepayments.repository.search.PrepaymentEntrySearchRepository;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import io.github.prepayments.service.mapper.PrepaymentEntryMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import io.github.prepayments.service.PrepaymentEntryQueryService;

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
 * Integration tests for the {@Link PrepaymentEntryResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class PrepaymentEntryResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "7598062602";
    private static final String UPDATED_ACCOUNT_NUMBER = "24495057705";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PREPAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PREPAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PARTICULARS = "AAAAAAAAAA";
    private static final String UPDATED_PARTICULARS = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET = "685";
    private static final String UPDATED_SERVICE_OUTLET = "130";

    private static final BigDecimal DEFAULT_PREPAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PREPAYMENT_AMOUNT = new BigDecimal(2);

    private static final Integer DEFAULT_MONTHS = 1;
    private static final Integer UPDATED_MONTHS = 2;

    private static final String DEFAULT_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_SCANNED_DOCUMENT_ID = 1L;
    private static final Long UPDATED_SCANNED_DOCUMENT_ID = 2L;

    @Autowired
    private PrepaymentEntryRepository prepaymentEntryRepository;

    @Autowired
    private PrepaymentEntryMapper prepaymentEntryMapper;

    @Autowired
    private PrepaymentEntryService prepaymentEntryService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.PrepaymentEntrySearchRepositoryMockConfiguration
     */
    @Autowired
    private PrepaymentEntrySearchRepository mockPrepaymentEntrySearchRepository;

    @Autowired
    private PrepaymentEntryQueryService prepaymentEntryQueryService;

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

    private MockMvc restPrepaymentEntryMockMvc;

    private PrepaymentEntry prepaymentEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrepaymentEntryResource prepaymentEntryResource = new PrepaymentEntryResource(prepaymentEntryService, prepaymentEntryQueryService);
        this.restPrepaymentEntryMockMvc = MockMvcBuilders.standaloneSetup(prepaymentEntryResource)
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
    public static PrepaymentEntry createEntity(EntityManager em) {
        PrepaymentEntry prepaymentEntry = new PrepaymentEntry()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .prepaymentId(DEFAULT_PREPAYMENT_ID)
            .prepaymentDate(DEFAULT_PREPAYMENT_DATE)
            .particulars(DEFAULT_PARTICULARS)
            .serviceOutlet(DEFAULT_SERVICE_OUTLET)
            .prepaymentAmount(DEFAULT_PREPAYMENT_AMOUNT)
            .months(DEFAULT_MONTHS)
            .supplierName(DEFAULT_SUPPLIER_NAME)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .scannedDocumentId(DEFAULT_SCANNED_DOCUMENT_ID);
        return prepaymentEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrepaymentEntry createUpdatedEntity(EntityManager em) {
        PrepaymentEntry prepaymentEntry = new PrepaymentEntry()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .prepaymentId(UPDATED_PREPAYMENT_ID)
            .prepaymentDate(UPDATED_PREPAYMENT_DATE)
            .particulars(UPDATED_PARTICULARS)
            .serviceOutlet(UPDATED_SERVICE_OUTLET)
            .prepaymentAmount(UPDATED_PREPAYMENT_AMOUNT)
            .months(UPDATED_MONTHS)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID);
        return prepaymentEntry;
    }

    @BeforeEach
    public void initTest() {
        prepaymentEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrepaymentEntry() throws Exception {
        int databaseSizeBeforeCreate = prepaymentEntryRepository.findAll().size();

        // Create the PrepaymentEntry
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);
        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the PrepaymentEntry in the database
        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeCreate + 1);
        PrepaymentEntry testPrepaymentEntry = prepaymentEntryList.get(prepaymentEntryList.size() - 1);
        assertThat(testPrepaymentEntry.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPrepaymentEntry.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testPrepaymentEntry.getPrepaymentId()).isEqualTo(DEFAULT_PREPAYMENT_ID);
        assertThat(testPrepaymentEntry.getPrepaymentDate()).isEqualTo(DEFAULT_PREPAYMENT_DATE);
        assertThat(testPrepaymentEntry.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testPrepaymentEntry.getServiceOutlet()).isEqualTo(DEFAULT_SERVICE_OUTLET);
        assertThat(testPrepaymentEntry.getPrepaymentAmount()).isEqualTo(DEFAULT_PREPAYMENT_AMOUNT);
        assertThat(testPrepaymentEntry.getMonths()).isEqualTo(DEFAULT_MONTHS);
        assertThat(testPrepaymentEntry.getSupplierName()).isEqualTo(DEFAULT_SUPPLIER_NAME);
        assertThat(testPrepaymentEntry.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testPrepaymentEntry.getScannedDocumentId()).isEqualTo(DEFAULT_SCANNED_DOCUMENT_ID);

        // Validate the PrepaymentEntry in Elasticsearch
        verify(mockPrepaymentEntrySearchRepository, times(1)).save(testPrepaymentEntry);
    }

    @Test
    @Transactional
    public void createPrepaymentEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prepaymentEntryRepository.findAll().size();

        // Create the PrepaymentEntry with an existing ID
        prepaymentEntry.setId(1L);
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrepaymentEntry in the database
        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeCreate);

        // Validate the PrepaymentEntry in Elasticsearch
        verify(mockPrepaymentEntrySearchRepository, times(0)).save(prepaymentEntry);
    }


    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setAccountNumber(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setAccountName(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setPrepaymentId(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setPrepaymentDate(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setServiceOutlet(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentEntryRepository.findAll().size();
        // set the field null
        prepaymentEntry.setPrepaymentAmount(null);

        // Create the PrepaymentEntry, which fails.
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        restPrepaymentEntryMockMvc.perform(post("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntries() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepaymentEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].prepaymentId").value(hasItem(DEFAULT_PREPAYMENT_ID.toString())))
            .andExpect(jsonPath("$.[*].prepaymentDate").value(hasItem(DEFAULT_PREPAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].prepaymentAmount").value(hasItem(DEFAULT_PREPAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS)))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME.toString())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getPrepaymentEntry() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get the prepaymentEntry
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries/{id}", prepaymentEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prepaymentEntry.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.prepaymentId").value(DEFAULT_PREPAYMENT_ID.toString()))
            .andExpect(jsonPath("$.prepaymentDate").value(DEFAULT_PREPAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.serviceOutlet").value(DEFAULT_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.prepaymentAmount").value(DEFAULT_PREPAYMENT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.months").value(DEFAULT_MONTHS))
            .andExpect(jsonPath("$.supplierName").value(DEFAULT_SUPPLIER_NAME.toString()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.scannedDocumentId").value(DEFAULT_SCANNED_DOCUMENT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultPrepaymentEntryShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the prepaymentEntryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPrepaymentEntryShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultPrepaymentEntryShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the prepaymentEntryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPrepaymentEntryShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountNumber is not null
        defaultPrepaymentEntryShouldBeFound("accountNumber.specified=true");

        // Get all the prepaymentEntryList where accountNumber is null
        defaultPrepaymentEntryShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultPrepaymentEntryShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the prepaymentEntryList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultPrepaymentEntryShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultPrepaymentEntryShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the prepaymentEntryList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultPrepaymentEntryShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where accountName is not null
        defaultPrepaymentEntryShouldBeFound("accountName.specified=true");

        // Get all the prepaymentEntryList where accountName is null
        defaultPrepaymentEntryShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentId equals to DEFAULT_PREPAYMENT_ID
        defaultPrepaymentEntryShouldBeFound("prepaymentId.equals=" + DEFAULT_PREPAYMENT_ID);

        // Get all the prepaymentEntryList where prepaymentId equals to UPDATED_PREPAYMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("prepaymentId.equals=" + UPDATED_PREPAYMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentIdIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentId in DEFAULT_PREPAYMENT_ID or UPDATED_PREPAYMENT_ID
        defaultPrepaymentEntryShouldBeFound("prepaymentId.in=" + DEFAULT_PREPAYMENT_ID + "," + UPDATED_PREPAYMENT_ID);

        // Get all the prepaymentEntryList where prepaymentId equals to UPDATED_PREPAYMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("prepaymentId.in=" + UPDATED_PREPAYMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentId is not null
        defaultPrepaymentEntryShouldBeFound("prepaymentId.specified=true");

        // Get all the prepaymentEntryList where prepaymentId is null
        defaultPrepaymentEntryShouldNotBeFound("prepaymentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentDate equals to DEFAULT_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldBeFound("prepaymentDate.equals=" + DEFAULT_PREPAYMENT_DATE);

        // Get all the prepaymentEntryList where prepaymentDate equals to UPDATED_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldNotBeFound("prepaymentDate.equals=" + UPDATED_PREPAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentDate in DEFAULT_PREPAYMENT_DATE or UPDATED_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldBeFound("prepaymentDate.in=" + DEFAULT_PREPAYMENT_DATE + "," + UPDATED_PREPAYMENT_DATE);

        // Get all the prepaymentEntryList where prepaymentDate equals to UPDATED_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldNotBeFound("prepaymentDate.in=" + UPDATED_PREPAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentDate is not null
        defaultPrepaymentEntryShouldBeFound("prepaymentDate.specified=true");

        // Get all the prepaymentEntryList where prepaymentDate is null
        defaultPrepaymentEntryShouldNotBeFound("prepaymentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentDate greater than or equals to DEFAULT_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldBeFound("prepaymentDate.greaterOrEqualThan=" + DEFAULT_PREPAYMENT_DATE);

        // Get all the prepaymentEntryList where prepaymentDate greater than or equals to UPDATED_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldNotBeFound("prepaymentDate.greaterOrEqualThan=" + UPDATED_PREPAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentDate less than or equals to DEFAULT_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldNotBeFound("prepaymentDate.lessThan=" + DEFAULT_PREPAYMENT_DATE);

        // Get all the prepaymentEntryList where prepaymentDate less than or equals to UPDATED_PREPAYMENT_DATE
        defaultPrepaymentEntryShouldBeFound("prepaymentDate.lessThan=" + UPDATED_PREPAYMENT_DATE);
    }


    @Test
    @Transactional
    public void getAllPrepaymentEntriesByParticularsIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where particulars equals to DEFAULT_PARTICULARS
        defaultPrepaymentEntryShouldBeFound("particulars.equals=" + DEFAULT_PARTICULARS);

        // Get all the prepaymentEntryList where particulars equals to UPDATED_PARTICULARS
        defaultPrepaymentEntryShouldNotBeFound("particulars.equals=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByParticularsIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where particulars in DEFAULT_PARTICULARS or UPDATED_PARTICULARS
        defaultPrepaymentEntryShouldBeFound("particulars.in=" + DEFAULT_PARTICULARS + "," + UPDATED_PARTICULARS);

        // Get all the prepaymentEntryList where particulars equals to UPDATED_PARTICULARS
        defaultPrepaymentEntryShouldNotBeFound("particulars.in=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByParticularsIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where particulars is not null
        defaultPrepaymentEntryShouldBeFound("particulars.specified=true");

        // Get all the prepaymentEntryList where particulars is null
        defaultPrepaymentEntryShouldNotBeFound("particulars.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where serviceOutlet equals to DEFAULT_SERVICE_OUTLET
        defaultPrepaymentEntryShouldBeFound("serviceOutlet.equals=" + DEFAULT_SERVICE_OUTLET);

        // Get all the prepaymentEntryList where serviceOutlet equals to UPDATED_SERVICE_OUTLET
        defaultPrepaymentEntryShouldNotBeFound("serviceOutlet.equals=" + UPDATED_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where serviceOutlet in DEFAULT_SERVICE_OUTLET or UPDATED_SERVICE_OUTLET
        defaultPrepaymentEntryShouldBeFound("serviceOutlet.in=" + DEFAULT_SERVICE_OUTLET + "," + UPDATED_SERVICE_OUTLET);

        // Get all the prepaymentEntryList where serviceOutlet equals to UPDATED_SERVICE_OUTLET
        defaultPrepaymentEntryShouldNotBeFound("serviceOutlet.in=" + UPDATED_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where serviceOutlet is not null
        defaultPrepaymentEntryShouldBeFound("serviceOutlet.specified=true");

        // Get all the prepaymentEntryList where serviceOutlet is null
        defaultPrepaymentEntryShouldNotBeFound("serviceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentAmount equals to DEFAULT_PREPAYMENT_AMOUNT
        defaultPrepaymentEntryShouldBeFound("prepaymentAmount.equals=" + DEFAULT_PREPAYMENT_AMOUNT);

        // Get all the prepaymentEntryList where prepaymentAmount equals to UPDATED_PREPAYMENT_AMOUNT
        defaultPrepaymentEntryShouldNotBeFound("prepaymentAmount.equals=" + UPDATED_PREPAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentAmountIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentAmount in DEFAULT_PREPAYMENT_AMOUNT or UPDATED_PREPAYMENT_AMOUNT
        defaultPrepaymentEntryShouldBeFound("prepaymentAmount.in=" + DEFAULT_PREPAYMENT_AMOUNT + "," + UPDATED_PREPAYMENT_AMOUNT);

        // Get all the prepaymentEntryList where prepaymentAmount equals to UPDATED_PREPAYMENT_AMOUNT
        defaultPrepaymentEntryShouldNotBeFound("prepaymentAmount.in=" + UPDATED_PREPAYMENT_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByPrepaymentAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where prepaymentAmount is not null
        defaultPrepaymentEntryShouldBeFound("prepaymentAmount.specified=true");

        // Get all the prepaymentEntryList where prepaymentAmount is null
        defaultPrepaymentEntryShouldNotBeFound("prepaymentAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByMonthsIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where months equals to DEFAULT_MONTHS
        defaultPrepaymentEntryShouldBeFound("months.equals=" + DEFAULT_MONTHS);

        // Get all the prepaymentEntryList where months equals to UPDATED_MONTHS
        defaultPrepaymentEntryShouldNotBeFound("months.equals=" + UPDATED_MONTHS);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByMonthsIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where months in DEFAULT_MONTHS or UPDATED_MONTHS
        defaultPrepaymentEntryShouldBeFound("months.in=" + DEFAULT_MONTHS + "," + UPDATED_MONTHS);

        // Get all the prepaymentEntryList where months equals to UPDATED_MONTHS
        defaultPrepaymentEntryShouldNotBeFound("months.in=" + UPDATED_MONTHS);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByMonthsIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where months is not null
        defaultPrepaymentEntryShouldBeFound("months.specified=true");

        // Get all the prepaymentEntryList where months is null
        defaultPrepaymentEntryShouldNotBeFound("months.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByMonthsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where months greater than or equals to DEFAULT_MONTHS
        defaultPrepaymentEntryShouldBeFound("months.greaterOrEqualThan=" + DEFAULT_MONTHS);

        // Get all the prepaymentEntryList where months greater than or equals to UPDATED_MONTHS
        defaultPrepaymentEntryShouldNotBeFound("months.greaterOrEqualThan=" + UPDATED_MONTHS);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByMonthsIsLessThanSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where months less than or equals to DEFAULT_MONTHS
        defaultPrepaymentEntryShouldNotBeFound("months.lessThan=" + DEFAULT_MONTHS);

        // Get all the prepaymentEntryList where months less than or equals to UPDATED_MONTHS
        defaultPrepaymentEntryShouldBeFound("months.lessThan=" + UPDATED_MONTHS);
    }


    @Test
    @Transactional
    public void getAllPrepaymentEntriesBySupplierNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where supplierName equals to DEFAULT_SUPPLIER_NAME
        defaultPrepaymentEntryShouldBeFound("supplierName.equals=" + DEFAULT_SUPPLIER_NAME);

        // Get all the prepaymentEntryList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultPrepaymentEntryShouldNotBeFound("supplierName.equals=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesBySupplierNameIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where supplierName in DEFAULT_SUPPLIER_NAME or UPDATED_SUPPLIER_NAME
        defaultPrepaymentEntryShouldBeFound("supplierName.in=" + DEFAULT_SUPPLIER_NAME + "," + UPDATED_SUPPLIER_NAME);

        // Get all the prepaymentEntryList where supplierName equals to UPDATED_SUPPLIER_NAME
        defaultPrepaymentEntryShouldNotBeFound("supplierName.in=" + UPDATED_SUPPLIER_NAME);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesBySupplierNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where supplierName is not null
        defaultPrepaymentEntryShouldBeFound("supplierName.specified=true");

        // Get all the prepaymentEntryList where supplierName is null
        defaultPrepaymentEntryShouldNotBeFound("supplierName.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultPrepaymentEntryShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the prepaymentEntryList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultPrepaymentEntryShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultPrepaymentEntryShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the prepaymentEntryList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultPrepaymentEntryShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where invoiceNumber is not null
        defaultPrepaymentEntryShouldBeFound("invoiceNumber.specified=true");

        // Get all the prepaymentEntryList where invoiceNumber is null
        defaultPrepaymentEntryShouldNotBeFound("invoiceNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByScannedDocumentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where scannedDocumentId equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldBeFound("scannedDocumentId.equals=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the prepaymentEntryList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("scannedDocumentId.equals=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByScannedDocumentIdIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where scannedDocumentId in DEFAULT_SCANNED_DOCUMENT_ID or UPDATED_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldBeFound("scannedDocumentId.in=" + DEFAULT_SCANNED_DOCUMENT_ID + "," + UPDATED_SCANNED_DOCUMENT_ID);

        // Get all the prepaymentEntryList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("scannedDocumentId.in=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByScannedDocumentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where scannedDocumentId is not null
        defaultPrepaymentEntryShouldBeFound("scannedDocumentId.specified=true");

        // Get all the prepaymentEntryList where scannedDocumentId is null
        defaultPrepaymentEntryShouldNotBeFound("scannedDocumentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByScannedDocumentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where scannedDocumentId greater than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldBeFound("scannedDocumentId.greaterOrEqualThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the prepaymentEntryList where scannedDocumentId greater than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("scannedDocumentId.greaterOrEqualThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllPrepaymentEntriesByScannedDocumentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        // Get all the prepaymentEntryList where scannedDocumentId less than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldNotBeFound("scannedDocumentId.lessThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the prepaymentEntryList where scannedDocumentId less than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultPrepaymentEntryShouldBeFound("scannedDocumentId.lessThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }


    @Test
    @Transactional
    public void getAllPrepaymentEntriesByAmortizationEntryIsEqualToSomething() throws Exception {
        // Initialize the database
        AmortizationEntry amortizationEntry = AmortizationEntryResourceIT.createEntity(em);
        em.persist(amortizationEntry);
        em.flush();
        prepaymentEntry.addAmortizationEntry(amortizationEntry);
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);
        Long amortizationEntryId = amortizationEntry.getId();

        // Get all the prepaymentEntryList where amortizationEntry equals to amortizationEntryId
        defaultPrepaymentEntryShouldBeFound("amortizationEntryId.equals=" + amortizationEntryId);

        // Get all the prepaymentEntryList where amortizationEntry equals to amortizationEntryId + 1
        defaultPrepaymentEntryShouldNotBeFound("amortizationEntryId.equals=" + (amortizationEntryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrepaymentEntryShouldBeFound(String filter) throws Exception {
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepaymentEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].prepaymentId").value(hasItem(DEFAULT_PREPAYMENT_ID)))
            .andExpect(jsonPath("$.[*].prepaymentDate").value(hasItem(DEFAULT_PREPAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAmount").value(hasItem(DEFAULT_PREPAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS)))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));

        // Check, that the count call also returns 1
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrepaymentEntryShouldNotBeFound(String filter) throws Exception {
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrepaymentEntry() throws Exception {
        // Get the prepaymentEntry
        restPrepaymentEntryMockMvc.perform(get("/api/prepayment-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrepaymentEntry() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        int databaseSizeBeforeUpdate = prepaymentEntryRepository.findAll().size();

        // Update the prepaymentEntry
        PrepaymentEntry updatedPrepaymentEntry = prepaymentEntryRepository.findById(prepaymentEntry.getId()).get();
        // Disconnect from session so that the updates on updatedPrepaymentEntry are not directly saved in db
        em.detach(updatedPrepaymentEntry);
        updatedPrepaymentEntry
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME)
            .prepaymentId(UPDATED_PREPAYMENT_ID)
            .prepaymentDate(UPDATED_PREPAYMENT_DATE)
            .particulars(UPDATED_PARTICULARS)
            .serviceOutlet(UPDATED_SERVICE_OUTLET)
            .prepaymentAmount(UPDATED_PREPAYMENT_AMOUNT)
            .months(UPDATED_MONTHS)
            .supplierName(UPDATED_SUPPLIER_NAME)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID);
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(updatedPrepaymentEntry);

        restPrepaymentEntryMockMvc.perform(put("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isOk());

        // Validate the PrepaymentEntry in the database
        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeUpdate);
        PrepaymentEntry testPrepaymentEntry = prepaymentEntryList.get(prepaymentEntryList.size() - 1);
        assertThat(testPrepaymentEntry.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPrepaymentEntry.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testPrepaymentEntry.getPrepaymentId()).isEqualTo(UPDATED_PREPAYMENT_ID);
        assertThat(testPrepaymentEntry.getPrepaymentDate()).isEqualTo(UPDATED_PREPAYMENT_DATE);
        assertThat(testPrepaymentEntry.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testPrepaymentEntry.getServiceOutlet()).isEqualTo(UPDATED_SERVICE_OUTLET);
        assertThat(testPrepaymentEntry.getPrepaymentAmount()).isEqualTo(UPDATED_PREPAYMENT_AMOUNT);
        assertThat(testPrepaymentEntry.getMonths()).isEqualTo(UPDATED_MONTHS);
        assertThat(testPrepaymentEntry.getSupplierName()).isEqualTo(UPDATED_SUPPLIER_NAME);
        assertThat(testPrepaymentEntry.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testPrepaymentEntry.getScannedDocumentId()).isEqualTo(UPDATED_SCANNED_DOCUMENT_ID);

        // Validate the PrepaymentEntry in Elasticsearch
        verify(mockPrepaymentEntrySearchRepository, times(1)).save(testPrepaymentEntry);
    }

    @Test
    @Transactional
    public void updateNonExistingPrepaymentEntry() throws Exception {
        int databaseSizeBeforeUpdate = prepaymentEntryRepository.findAll().size();

        // Create the PrepaymentEntry
        PrepaymentEntryDTO prepaymentEntryDTO = prepaymentEntryMapper.toDto(prepaymentEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrepaymentEntryMockMvc.perform(put("/api/prepayment-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrepaymentEntry in the database
        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the PrepaymentEntry in Elasticsearch
        verify(mockPrepaymentEntrySearchRepository, times(0)).save(prepaymentEntry);
    }

    @Test
    @Transactional
    public void deletePrepaymentEntry() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);

        int databaseSizeBeforeDelete = prepaymentEntryRepository.findAll().size();

        // Delete the prepaymentEntry
        restPrepaymentEntryMockMvc.perform(delete("/api/prepayment-entries/{id}", prepaymentEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PrepaymentEntry> prepaymentEntryList = prepaymentEntryRepository.findAll();
        assertThat(prepaymentEntryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the PrepaymentEntry in Elasticsearch
        verify(mockPrepaymentEntrySearchRepository, times(1)).deleteById(prepaymentEntry.getId());
    }

    @Test
    @Transactional
    public void searchPrepaymentEntry() throws Exception {
        // Initialize the database
        prepaymentEntryRepository.saveAndFlush(prepaymentEntry);
        when(mockPrepaymentEntrySearchRepository.search(queryStringQuery("id:" + prepaymentEntry.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(prepaymentEntry), PageRequest.of(0, 1), 1));
        // Search the prepaymentEntry
        restPrepaymentEntryMockMvc.perform(get("/api/_search/prepayment-entries?query=id:" + prepaymentEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepaymentEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].prepaymentId").value(hasItem(DEFAULT_PREPAYMENT_ID)))
            .andExpect(jsonPath("$.[*].prepaymentDate").value(hasItem(DEFAULT_PREPAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAmount").value(hasItem(DEFAULT_PREPAYMENT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].months").value(hasItem(DEFAULT_MONTHS)))
            .andExpect(jsonPath("$.[*].supplierName").value(hasItem(DEFAULT_SUPPLIER_NAME)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrepaymentEntry.class);
        PrepaymentEntry prepaymentEntry1 = new PrepaymentEntry();
        prepaymentEntry1.setId(1L);
        PrepaymentEntry prepaymentEntry2 = new PrepaymentEntry();
        prepaymentEntry2.setId(prepaymentEntry1.getId());
        assertThat(prepaymentEntry1).isEqualTo(prepaymentEntry2);
        prepaymentEntry2.setId(2L);
        assertThat(prepaymentEntry1).isNotEqualTo(prepaymentEntry2);
        prepaymentEntry1.setId(null);
        assertThat(prepaymentEntry1).isNotEqualTo(prepaymentEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrepaymentEntryDTO.class);
        PrepaymentEntryDTO prepaymentEntryDTO1 = new PrepaymentEntryDTO();
        prepaymentEntryDTO1.setId(1L);
        PrepaymentEntryDTO prepaymentEntryDTO2 = new PrepaymentEntryDTO();
        assertThat(prepaymentEntryDTO1).isNotEqualTo(prepaymentEntryDTO2);
        prepaymentEntryDTO2.setId(prepaymentEntryDTO1.getId());
        assertThat(prepaymentEntryDTO1).isEqualTo(prepaymentEntryDTO2);
        prepaymentEntryDTO2.setId(2L);
        assertThat(prepaymentEntryDTO1).isNotEqualTo(prepaymentEntryDTO2);
        prepaymentEntryDTO1.setId(null);
        assertThat(prepaymentEntryDTO1).isNotEqualTo(prepaymentEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prepaymentEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prepaymentEntryMapper.fromId(null)).isNull();
    }
}
