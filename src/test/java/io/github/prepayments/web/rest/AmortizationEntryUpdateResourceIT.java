package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AmortizationEntryUpdate;
import io.github.prepayments.repository.AmortizationEntryUpdateRepository;
import io.github.prepayments.repository.search.AmortizationEntryUpdateSearchRepository;
import io.github.prepayments.service.AmortizationEntryUpdateService;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.mapper.AmortizationEntryUpdateMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AmortizationEntryUpdateCriteria;
import io.github.prepayments.service.AmortizationEntryUpdateQueryService;

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
 * Integration tests for the {@Link AmortizationEntryUpdateResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationEntryUpdateResourceIT {

    private static final Long DEFAULT_AMORTIZATION_ENTRY_ID = 1L;
    private static final Long UPDATED_AMORTIZATION_ENTRY_ID = 2L;

    private static final LocalDate DEFAULT_AMORTIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AMORTIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMORTIZATION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMORTIZATION_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_PARTICULARS = "AAAAAAAAAA";
    private static final String UPDATED_PARTICULARS = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_SERVICE_OUTLET = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_SERVICE_OUTLET = "BBBBBBBBBB";

    private static final String DEFAULT_PREPAYMENT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PREPAYMENT_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_SERVICE_OUTLET = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_SERVICE_OUTLET = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_PREPAYMENT_ENTRY_ID = 1L;
    private static final Long UPDATED_PREPAYMENT_ENTRY_ID = 2L;

    private static final String DEFAULT_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_TAG = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_TAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ORPHANED = false;
    private static final Boolean UPDATED_ORPHANED = true;

    @Autowired
    private AmortizationEntryUpdateRepository amortizationEntryUpdateRepository;

    @Autowired
    private AmortizationEntryUpdateMapper amortizationEntryUpdateMapper;

    @Autowired
    private AmortizationEntryUpdateService amortizationEntryUpdateService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.AmortizationEntryUpdateSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationEntryUpdateSearchRepository mockAmortizationEntryUpdateSearchRepository;

    @Autowired
    private AmortizationEntryUpdateQueryService amortizationEntryUpdateQueryService;

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

    private MockMvc restAmortizationEntryUpdateMockMvc;

    private AmortizationEntryUpdate amortizationEntryUpdate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationEntryUpdateResource amortizationEntryUpdateResource = new AmortizationEntryUpdateResource(amortizationEntryUpdateService, amortizationEntryUpdateQueryService);
        this.restAmortizationEntryUpdateMockMvc = MockMvcBuilders.standaloneSetup(amortizationEntryUpdateResource)
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
    public static AmortizationEntryUpdate createEntity(EntityManager em) {
        AmortizationEntryUpdate amortizationEntryUpdate = new AmortizationEntryUpdate()
            .amortizationEntryId(DEFAULT_AMORTIZATION_ENTRY_ID)
            .amortizationDate(DEFAULT_AMORTIZATION_DATE)
            .amortizationAmount(DEFAULT_AMORTIZATION_AMOUNT)
            .particulars(DEFAULT_PARTICULARS)
            .prepaymentServiceOutlet(DEFAULT_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(DEFAULT_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)
            .prepaymentEntryId(DEFAULT_PREPAYMENT_ENTRY_ID)
            .originatingFileToken(DEFAULT_ORIGINATING_FILE_TOKEN)
            .amortizationTag(DEFAULT_AMORTIZATION_TAG)
            .orphaned(DEFAULT_ORPHANED);
        return amortizationEntryUpdate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationEntryUpdate createUpdatedEntity(EntityManager em) {
        AmortizationEntryUpdate amortizationEntryUpdate = new AmortizationEntryUpdate()
            .amortizationEntryId(UPDATED_AMORTIZATION_ENTRY_ID)
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .prepaymentServiceOutlet(UPDATED_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(UPDATED_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(UPDATED_AMORTIZATION_ACCOUNT_NUMBER)
            .prepaymentEntryId(UPDATED_PREPAYMENT_ENTRY_ID)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN)
            .amortizationTag(UPDATED_AMORTIZATION_TAG)
            .orphaned(UPDATED_ORPHANED);
        return amortizationEntryUpdate;
    }

    @BeforeEach
    public void initTest() {
        amortizationEntryUpdate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationEntryUpdate() throws Exception {
        int databaseSizeBeforeCreate = amortizationEntryUpdateRepository.findAll().size();

        // Create the AmortizationEntryUpdate
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);
        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationEntryUpdate in the database
        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationEntryUpdate testAmortizationEntryUpdate = amortizationEntryUpdateList.get(amortizationEntryUpdateList.size() - 1);
        assertThat(testAmortizationEntryUpdate.getAmortizationEntryId()).isEqualTo(DEFAULT_AMORTIZATION_ENTRY_ID);
        assertThat(testAmortizationEntryUpdate.getAmortizationDate()).isEqualTo(DEFAULT_AMORTIZATION_DATE);
        assertThat(testAmortizationEntryUpdate.getAmortizationAmount()).isEqualTo(DEFAULT_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationEntryUpdate.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testAmortizationEntryUpdate.getPrepaymentServiceOutlet()).isEqualTo(DEFAULT_PREPAYMENT_SERVICE_OUTLET);
        assertThat(testAmortizationEntryUpdate.getPrepaymentAccountNumber()).isEqualTo(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntryUpdate.getAmortizationServiceOutlet()).isEqualTo(DEFAULT_AMORTIZATION_SERVICE_OUTLET);
        assertThat(testAmortizationEntryUpdate.getAmortizationAccountNumber()).isEqualTo(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntryUpdate.getPrepaymentEntryId()).isEqualTo(DEFAULT_PREPAYMENT_ENTRY_ID);
        assertThat(testAmortizationEntryUpdate.getOriginatingFileToken()).isEqualTo(DEFAULT_ORIGINATING_FILE_TOKEN);
        assertThat(testAmortizationEntryUpdate.getAmortizationTag()).isEqualTo(DEFAULT_AMORTIZATION_TAG);
        assertThat(testAmortizationEntryUpdate.isOrphaned()).isEqualTo(DEFAULT_ORPHANED);

        // Validate the AmortizationEntryUpdate in Elasticsearch
        verify(mockAmortizationEntryUpdateSearchRepository, times(1)).save(testAmortizationEntryUpdate);
    }

    @Test
    @Transactional
    public void createAmortizationEntryUpdateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationEntryUpdateRepository.findAll().size();

        // Create the AmortizationEntryUpdate with an existing ID
        amortizationEntryUpdate.setId(1L);
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationEntryUpdate in the database
        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmortizationEntryUpdate in Elasticsearch
        verify(mockAmortizationEntryUpdateSearchRepository, times(0)).save(amortizationEntryUpdate);
    }


    @Test
    @Transactional
    public void checkAmortizationEntryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setAmortizationEntryId(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setAmortizationDate(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setAmortizationAmount(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setPrepaymentServiceOutlet(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setPrepaymentAccountNumber(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setAmortizationServiceOutlet(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setAmortizationAccountNumber(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentEntryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryUpdateRepository.findAll().size();
        // set the field null
        amortizationEntryUpdate.setPrepaymentEntryId(null);

        // Create the AmortizationEntryUpdate, which fails.
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(post("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdates() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntryUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationEntryId").value(hasItem(DEFAULT_AMORTIZATION_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].prepaymentEntryId").value(hasItem(DEFAULT_PREPAYMENT_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG.toString())))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAmortizationEntryUpdate() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get the amortizationEntryUpdate
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates/{id}", amortizationEntryUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationEntryUpdate.getId().intValue()))
            .andExpect(jsonPath("$.amortizationEntryId").value(DEFAULT_AMORTIZATION_ENTRY_ID.intValue()))
            .andExpect(jsonPath("$.amortizationDate").value(DEFAULT_AMORTIZATION_DATE.toString()))
            .andExpect(jsonPath("$.amortizationAmount").value(DEFAULT_AMORTIZATION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.prepaymentServiceOutlet").value(DEFAULT_PREPAYMENT_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.prepaymentAccountNumber").value(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.amortizationServiceOutlet").value(DEFAULT_AMORTIZATION_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.amortizationAccountNumber").value(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.prepaymentEntryId").value(DEFAULT_PREPAYMENT_ENTRY_ID.intValue()))
            .andExpect(jsonPath("$.originatingFileToken").value(DEFAULT_ORIGINATING_FILE_TOKEN.toString()))
            .andExpect(jsonPath("$.amortizationTag").value(DEFAULT_AMORTIZATION_TAG.toString()))
            .andExpect(jsonPath("$.orphaned").value(DEFAULT_ORPHANED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationEntryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationEntryId equals to DEFAULT_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("amortizationEntryId.equals=" + DEFAULT_AMORTIZATION_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where amortizationEntryId equals to UPDATED_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationEntryId.equals=" + UPDATED_AMORTIZATION_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationEntryIdIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationEntryId in DEFAULT_AMORTIZATION_ENTRY_ID or UPDATED_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("amortizationEntryId.in=" + DEFAULT_AMORTIZATION_ENTRY_ID + "," + UPDATED_AMORTIZATION_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where amortizationEntryId equals to UPDATED_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationEntryId.in=" + UPDATED_AMORTIZATION_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationEntryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationEntryId is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationEntryId.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationEntryId is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationEntryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationEntryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationEntryId greater than or equals to DEFAULT_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("amortizationEntryId.greaterOrEqualThan=" + DEFAULT_AMORTIZATION_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where amortizationEntryId greater than or equals to UPDATED_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationEntryId.greaterOrEqualThan=" + UPDATED_AMORTIZATION_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationEntryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationEntryId less than or equals to DEFAULT_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationEntryId.lessThan=" + DEFAULT_AMORTIZATION_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where amortizationEntryId less than or equals to UPDATED_AMORTIZATION_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("amortizationEntryId.lessThan=" + UPDATED_AMORTIZATION_ENTRY_ID);
    }


    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationDate equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldBeFound("amortizationDate.equals=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryUpdateList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationDate.equals=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationDate in DEFAULT_AMORTIZATION_DATE or UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldBeFound("amortizationDate.in=" + DEFAULT_AMORTIZATION_DATE + "," + UPDATED_AMORTIZATION_DATE);

        // Get all the amortizationEntryUpdateList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationDate.in=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationDate is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationDate.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationDate is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationDate greater than or equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldBeFound("amortizationDate.greaterOrEqualThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryUpdateList where amortizationDate greater than or equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationDate.greaterOrEqualThan=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationDate less than or equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationDate.lessThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryUpdateList where amortizationDate less than or equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryUpdateShouldBeFound("amortizationDate.lessThan=" + UPDATED_AMORTIZATION_DATE);
    }


    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAmount equals to DEFAULT_AMORTIZATION_AMOUNT
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAmount.equals=" + DEFAULT_AMORTIZATION_AMOUNT);

        // Get all the amortizationEntryUpdateList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAmount.equals=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAmount in DEFAULT_AMORTIZATION_AMOUNT or UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAmount.in=" + DEFAULT_AMORTIZATION_AMOUNT + "," + UPDATED_AMORTIZATION_AMOUNT);

        // Get all the amortizationEntryUpdateList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAmount.in=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAmount is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAmount.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationAmount is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByParticularsIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where particulars equals to DEFAULT_PARTICULARS
        defaultAmortizationEntryUpdateShouldBeFound("particulars.equals=" + DEFAULT_PARTICULARS);

        // Get all the amortizationEntryUpdateList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationEntryUpdateShouldNotBeFound("particulars.equals=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByParticularsIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where particulars in DEFAULT_PARTICULARS or UPDATED_PARTICULARS
        defaultAmortizationEntryUpdateShouldBeFound("particulars.in=" + DEFAULT_PARTICULARS + "," + UPDATED_PARTICULARS);

        // Get all the amortizationEntryUpdateList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationEntryUpdateShouldNotBeFound("particulars.in=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByParticularsIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where particulars is not null
        defaultAmortizationEntryUpdateShouldBeFound("particulars.specified=true");

        // Get all the amortizationEntryUpdateList where particulars is null
        defaultAmortizationEntryUpdateShouldNotBeFound("particulars.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet equals to DEFAULT_PREPAYMENT_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentServiceOutlet.equals=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET);

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet equals to UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentServiceOutlet.equals=" + UPDATED_PREPAYMENT_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet in DEFAULT_PREPAYMENT_SERVICE_OUTLET or UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentServiceOutlet.in=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET + "," + UPDATED_PREPAYMENT_SERVICE_OUTLET);

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet equals to UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentServiceOutlet.in=" + UPDATED_PREPAYMENT_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet is not null
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentServiceOutlet.specified=true");

        // Get all the amortizationEntryUpdateList where prepaymentServiceOutlet is null
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentServiceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber equals to DEFAULT_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentAccountNumber.equals=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentAccountNumber.equals=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber in DEFAULT_PREPAYMENT_ACCOUNT_NUMBER or UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentAccountNumber.in=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER + "," + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentAccountNumber.in=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber is not null
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentAccountNumber.specified=true");

        // Get all the amortizationEntryUpdateList where prepaymentAccountNumber is null
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet equals to DEFAULT_AMORTIZATION_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldBeFound("amortizationServiceOutlet.equals=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET);

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet equals to UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationServiceOutlet.equals=" + UPDATED_AMORTIZATION_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet in DEFAULT_AMORTIZATION_SERVICE_OUTLET or UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldBeFound("amortizationServiceOutlet.in=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET + "," + UPDATED_AMORTIZATION_SERVICE_OUTLET);

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet equals to UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationServiceOutlet.in=" + UPDATED_AMORTIZATION_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationServiceOutlet.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationServiceOutlet is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationServiceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber equals to DEFAULT_AMORTIZATION_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAccountNumber.equals=" + DEFAULT_AMORTIZATION_ACCOUNT_NUMBER);

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber equals to UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAccountNumber.equals=" + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber in DEFAULT_AMORTIZATION_ACCOUNT_NUMBER or UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAccountNumber.in=" + DEFAULT_AMORTIZATION_ACCOUNT_NUMBER + "," + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber equals to UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAccountNumber.in=" + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationAccountNumber.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationAccountNumber is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentEntryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId equals to DEFAULT_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentEntryId.equals=" + DEFAULT_PREPAYMENT_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId equals to UPDATED_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentEntryId.equals=" + UPDATED_PREPAYMENT_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentEntryIdIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId in DEFAULT_PREPAYMENT_ENTRY_ID or UPDATED_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentEntryId.in=" + DEFAULT_PREPAYMENT_ENTRY_ID + "," + UPDATED_PREPAYMENT_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId equals to UPDATED_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentEntryId.in=" + UPDATED_PREPAYMENT_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentEntryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId is not null
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentEntryId.specified=true");

        // Get all the amortizationEntryUpdateList where prepaymentEntryId is null
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentEntryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentEntryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId greater than or equals to DEFAULT_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentEntryId.greaterOrEqualThan=" + DEFAULT_PREPAYMENT_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId greater than or equals to UPDATED_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentEntryId.greaterOrEqualThan=" + UPDATED_PREPAYMENT_ENTRY_ID);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByPrepaymentEntryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId less than or equals to DEFAULT_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldNotBeFound("prepaymentEntryId.lessThan=" + DEFAULT_PREPAYMENT_ENTRY_ID);

        // Get all the amortizationEntryUpdateList where prepaymentEntryId less than or equals to UPDATED_PREPAYMENT_ENTRY_ID
        defaultAmortizationEntryUpdateShouldBeFound("prepaymentEntryId.lessThan=" + UPDATED_PREPAYMENT_ENTRY_ID);
    }


    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOriginatingFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where originatingFileToken equals to DEFAULT_ORIGINATING_FILE_TOKEN
        defaultAmortizationEntryUpdateShouldBeFound("originatingFileToken.equals=" + DEFAULT_ORIGINATING_FILE_TOKEN);

        // Get all the amortizationEntryUpdateList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationEntryUpdateShouldNotBeFound("originatingFileToken.equals=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOriginatingFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where originatingFileToken in DEFAULT_ORIGINATING_FILE_TOKEN or UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationEntryUpdateShouldBeFound("originatingFileToken.in=" + DEFAULT_ORIGINATING_FILE_TOKEN + "," + UPDATED_ORIGINATING_FILE_TOKEN);

        // Get all the amortizationEntryUpdateList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultAmortizationEntryUpdateShouldNotBeFound("originatingFileToken.in=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOriginatingFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where originatingFileToken is not null
        defaultAmortizationEntryUpdateShouldBeFound("originatingFileToken.specified=true");

        // Get all the amortizationEntryUpdateList where originatingFileToken is null
        defaultAmortizationEntryUpdateShouldNotBeFound("originatingFileToken.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationTagIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationTag equals to DEFAULT_AMORTIZATION_TAG
        defaultAmortizationEntryUpdateShouldBeFound("amortizationTag.equals=" + DEFAULT_AMORTIZATION_TAG);

        // Get all the amortizationEntryUpdateList where amortizationTag equals to UPDATED_AMORTIZATION_TAG
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationTag.equals=" + UPDATED_AMORTIZATION_TAG);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationTagIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationTag in DEFAULT_AMORTIZATION_TAG or UPDATED_AMORTIZATION_TAG
        defaultAmortizationEntryUpdateShouldBeFound("amortizationTag.in=" + DEFAULT_AMORTIZATION_TAG + "," + UPDATED_AMORTIZATION_TAG);

        // Get all the amortizationEntryUpdateList where amortizationTag equals to UPDATED_AMORTIZATION_TAG
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationTag.in=" + UPDATED_AMORTIZATION_TAG);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByAmortizationTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where amortizationTag is not null
        defaultAmortizationEntryUpdateShouldBeFound("amortizationTag.specified=true");

        // Get all the amortizationEntryUpdateList where amortizationTag is null
        defaultAmortizationEntryUpdateShouldNotBeFound("amortizationTag.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOrphanedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where orphaned equals to DEFAULT_ORPHANED
        defaultAmortizationEntryUpdateShouldBeFound("orphaned.equals=" + DEFAULT_ORPHANED);

        // Get all the amortizationEntryUpdateList where orphaned equals to UPDATED_ORPHANED
        defaultAmortizationEntryUpdateShouldNotBeFound("orphaned.equals=" + UPDATED_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOrphanedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where orphaned in DEFAULT_ORPHANED or UPDATED_ORPHANED
        defaultAmortizationEntryUpdateShouldBeFound("orphaned.in=" + DEFAULT_ORPHANED + "," + UPDATED_ORPHANED);

        // Get all the amortizationEntryUpdateList where orphaned equals to UPDATED_ORPHANED
        defaultAmortizationEntryUpdateShouldNotBeFound("orphaned.in=" + UPDATED_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntryUpdatesByOrphanedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        // Get all the amortizationEntryUpdateList where orphaned is not null
        defaultAmortizationEntryUpdateShouldBeFound("orphaned.specified=true");

        // Get all the amortizationEntryUpdateList where orphaned is null
        defaultAmortizationEntryUpdateShouldNotBeFound("orphaned.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationEntryUpdateShouldBeFound(String filter) throws Exception {
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntryUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationEntryId").value(hasItem(DEFAULT_AMORTIZATION_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].prepaymentEntryId").value(hasItem(DEFAULT_PREPAYMENT_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG)))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())));

        // Check, that the count call also returns 1
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationEntryUpdateShouldNotBeFound(String filter) throws Exception {
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationEntryUpdate() throws Exception {
        // Get the amortizationEntryUpdate
        restAmortizationEntryUpdateMockMvc.perform(get("/api/amortization-entry-updates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationEntryUpdate() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        int databaseSizeBeforeUpdate = amortizationEntryUpdateRepository.findAll().size();

        // Update the amortizationEntryUpdate
        AmortizationEntryUpdate updatedAmortizationEntryUpdate = amortizationEntryUpdateRepository.findById(amortizationEntryUpdate.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationEntryUpdate are not directly saved in db
        em.detach(updatedAmortizationEntryUpdate);
        updatedAmortizationEntryUpdate
            .amortizationEntryId(UPDATED_AMORTIZATION_ENTRY_ID)
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .prepaymentServiceOutlet(UPDATED_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(UPDATED_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(UPDATED_AMORTIZATION_ACCOUNT_NUMBER)
            .prepaymentEntryId(UPDATED_PREPAYMENT_ENTRY_ID)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN)
            .amortizationTag(UPDATED_AMORTIZATION_TAG)
            .orphaned(UPDATED_ORPHANED);
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(updatedAmortizationEntryUpdate);

        restAmortizationEntryUpdateMockMvc.perform(put("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationEntryUpdate in the database
        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeUpdate);
        AmortizationEntryUpdate testAmortizationEntryUpdate = amortizationEntryUpdateList.get(amortizationEntryUpdateList.size() - 1);
        assertThat(testAmortizationEntryUpdate.getAmortizationEntryId()).isEqualTo(UPDATED_AMORTIZATION_ENTRY_ID);
        assertThat(testAmortizationEntryUpdate.getAmortizationDate()).isEqualTo(UPDATED_AMORTIZATION_DATE);
        assertThat(testAmortizationEntryUpdate.getAmortizationAmount()).isEqualTo(UPDATED_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationEntryUpdate.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testAmortizationEntryUpdate.getPrepaymentServiceOutlet()).isEqualTo(UPDATED_PREPAYMENT_SERVICE_OUTLET);
        assertThat(testAmortizationEntryUpdate.getPrepaymentAccountNumber()).isEqualTo(UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntryUpdate.getAmortizationServiceOutlet()).isEqualTo(UPDATED_AMORTIZATION_SERVICE_OUTLET);
        assertThat(testAmortizationEntryUpdate.getAmortizationAccountNumber()).isEqualTo(UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntryUpdate.getPrepaymentEntryId()).isEqualTo(UPDATED_PREPAYMENT_ENTRY_ID);
        assertThat(testAmortizationEntryUpdate.getOriginatingFileToken()).isEqualTo(UPDATED_ORIGINATING_FILE_TOKEN);
        assertThat(testAmortizationEntryUpdate.getAmortizationTag()).isEqualTo(UPDATED_AMORTIZATION_TAG);
        assertThat(testAmortizationEntryUpdate.isOrphaned()).isEqualTo(UPDATED_ORPHANED);

        // Validate the AmortizationEntryUpdate in Elasticsearch
        verify(mockAmortizationEntryUpdateSearchRepository, times(1)).save(testAmortizationEntryUpdate);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationEntryUpdate() throws Exception {
        int databaseSizeBeforeUpdate = amortizationEntryUpdateRepository.findAll().size();

        // Create the AmortizationEntryUpdate
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO = amortizationEntryUpdateMapper.toDto(amortizationEntryUpdate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationEntryUpdateMockMvc.perform(put("/api/amortization-entry-updates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryUpdateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationEntryUpdate in the database
        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmortizationEntryUpdate in Elasticsearch
        verify(mockAmortizationEntryUpdateSearchRepository, times(0)).save(amortizationEntryUpdate);
    }

    @Test
    @Transactional
    public void deleteAmortizationEntryUpdate() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);

        int databaseSizeBeforeDelete = amortizationEntryUpdateRepository.findAll().size();

        // Delete the amortizationEntryUpdate
        restAmortizationEntryUpdateMockMvc.perform(delete("/api/amortization-entry-updates/{id}", amortizationEntryUpdate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationEntryUpdate> amortizationEntryUpdateList = amortizationEntryUpdateRepository.findAll();
        assertThat(amortizationEntryUpdateList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmortizationEntryUpdate in Elasticsearch
        verify(mockAmortizationEntryUpdateSearchRepository, times(1)).deleteById(amortizationEntryUpdate.getId());
    }

    @Test
    @Transactional
    public void searchAmortizationEntryUpdate() throws Exception {
        // Initialize the database
        amortizationEntryUpdateRepository.saveAndFlush(amortizationEntryUpdate);
        when(mockAmortizationEntryUpdateSearchRepository.search(queryStringQuery("id:" + amortizationEntryUpdate.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizationEntryUpdate), PageRequest.of(0, 1), 1));
        // Search the amortizationEntryUpdate
        restAmortizationEntryUpdateMockMvc.perform(get("/api/_search/amortization-entry-updates?query=id:" + amortizationEntryUpdate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntryUpdate.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationEntryId").value(hasItem(DEFAULT_AMORTIZATION_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].prepaymentEntryId").value(hasItem(DEFAULT_PREPAYMENT_ENTRY_ID.intValue())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG)))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationEntryUpdate.class);
        AmortizationEntryUpdate amortizationEntryUpdate1 = new AmortizationEntryUpdate();
        amortizationEntryUpdate1.setId(1L);
        AmortizationEntryUpdate amortizationEntryUpdate2 = new AmortizationEntryUpdate();
        amortizationEntryUpdate2.setId(amortizationEntryUpdate1.getId());
        assertThat(amortizationEntryUpdate1).isEqualTo(amortizationEntryUpdate2);
        amortizationEntryUpdate2.setId(2L);
        assertThat(amortizationEntryUpdate1).isNotEqualTo(amortizationEntryUpdate2);
        amortizationEntryUpdate1.setId(null);
        assertThat(amortizationEntryUpdate1).isNotEqualTo(amortizationEntryUpdate2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationEntryUpdateDTO.class);
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO1 = new AmortizationEntryUpdateDTO();
        amortizationEntryUpdateDTO1.setId(1L);
        AmortizationEntryUpdateDTO amortizationEntryUpdateDTO2 = new AmortizationEntryUpdateDTO();
        assertThat(amortizationEntryUpdateDTO1).isNotEqualTo(amortizationEntryUpdateDTO2);
        amortizationEntryUpdateDTO2.setId(amortizationEntryUpdateDTO1.getId());
        assertThat(amortizationEntryUpdateDTO1).isEqualTo(amortizationEntryUpdateDTO2);
        amortizationEntryUpdateDTO2.setId(2L);
        assertThat(amortizationEntryUpdateDTO1).isNotEqualTo(amortizationEntryUpdateDTO2);
        amortizationEntryUpdateDTO1.setId(null);
        assertThat(amortizationEntryUpdateDTO1).isNotEqualTo(amortizationEntryUpdateDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationEntryUpdateMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationEntryUpdateMapper.fromId(null)).isNull();
    }
}
