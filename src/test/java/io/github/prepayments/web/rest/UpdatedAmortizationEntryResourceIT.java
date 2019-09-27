package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.UpdatedAmortizationEntry;
import io.github.prepayments.domain.PrepaymentEntry;
import io.github.prepayments.repository.UpdatedAmortizationEntryRepository;
import io.github.prepayments.repository.search.UpdatedAmortizationEntrySearchRepository;
import io.github.prepayments.service.UpdatedAmortizationEntryService;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import io.github.prepayments.service.mapper.UpdatedAmortizationEntryMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryCriteria;
import io.github.prepayments.service.UpdatedAmortizationEntryQueryService;

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
 * Integration tests for the {@Link UpdatedAmortizationEntryResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class UpdatedAmortizationEntryResourceIT {

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

    private static final String DEFAULT_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_AMORTIZATION_TAG = "AAAAAAAAAA";
    private static final String UPDATED_AMORTIZATION_TAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ORPHANED = false;
    private static final Boolean UPDATED_ORPHANED = true;

    private static final LocalDate DEFAULT_DATE_OF_UPDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_UPDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REASON_FOR_UPDATE = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_UPDATE = "BBBBBBBBBB";

    @Autowired
    private UpdatedAmortizationEntryRepository updatedAmortizationEntryRepository;

    @Autowired
    private UpdatedAmortizationEntryMapper updatedAmortizationEntryMapper;

    @Autowired
    private UpdatedAmortizationEntryService updatedAmortizationEntryService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.UpdatedAmortizationEntrySearchRepositoryMockConfiguration
     */
    @Autowired
    private UpdatedAmortizationEntrySearchRepository mockUpdatedAmortizationEntrySearchRepository;

    @Autowired
    private UpdatedAmortizationEntryQueryService updatedAmortizationEntryQueryService;

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

    private MockMvc restUpdatedAmortizationEntryMockMvc;

    private UpdatedAmortizationEntry updatedAmortizationEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UpdatedAmortizationEntryResource updatedAmortizationEntryResource = new UpdatedAmortizationEntryResource(updatedAmortizationEntryService, updatedAmortizationEntryQueryService);
        this.restUpdatedAmortizationEntryMockMvc = MockMvcBuilders.standaloneSetup(updatedAmortizationEntryResource)
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
    public static UpdatedAmortizationEntry createEntity(EntityManager em) {
        UpdatedAmortizationEntry updatedAmortizationEntry = new UpdatedAmortizationEntry()
            .amortizationDate(DEFAULT_AMORTIZATION_DATE)
            .amortizationAmount(DEFAULT_AMORTIZATION_AMOUNT)
            .particulars(DEFAULT_PARTICULARS)
            .prepaymentServiceOutlet(DEFAULT_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(DEFAULT_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)
            .originatingFileToken(DEFAULT_ORIGINATING_FILE_TOKEN)
            .amortizationTag(DEFAULT_AMORTIZATION_TAG)
            .orphaned(DEFAULT_ORPHANED)
            .dateOfUpdate(DEFAULT_DATE_OF_UPDATE)
            .reasonForUpdate(DEFAULT_REASON_FOR_UPDATE);
        return updatedAmortizationEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpdatedAmortizationEntry createUpdatedEntity(EntityManager em) {
        UpdatedAmortizationEntry updatedAmortizationEntry = new UpdatedAmortizationEntry()
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .prepaymentServiceOutlet(UPDATED_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(UPDATED_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(UPDATED_AMORTIZATION_ACCOUNT_NUMBER)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN)
            .amortizationTag(UPDATED_AMORTIZATION_TAG)
            .orphaned(UPDATED_ORPHANED)
            .dateOfUpdate(UPDATED_DATE_OF_UPDATE)
            .reasonForUpdate(UPDATED_REASON_FOR_UPDATE);
        return updatedAmortizationEntry;
    }

    @BeforeEach
    public void initTest() {
        updatedAmortizationEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpdatedAmortizationEntry() throws Exception {
        int databaseSizeBeforeCreate = updatedAmortizationEntryRepository.findAll().size();

        // Create the UpdatedAmortizationEntry
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);
        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the UpdatedAmortizationEntry in the database
        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeCreate + 1);
        UpdatedAmortizationEntry testUpdatedAmortizationEntry = updatedAmortizationEntryList.get(updatedAmortizationEntryList.size() - 1);
        assertThat(testUpdatedAmortizationEntry.getAmortizationDate()).isEqualTo(DEFAULT_AMORTIZATION_DATE);
        assertThat(testUpdatedAmortizationEntry.getAmortizationAmount()).isEqualTo(DEFAULT_AMORTIZATION_AMOUNT);
        assertThat(testUpdatedAmortizationEntry.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testUpdatedAmortizationEntry.getPrepaymentServiceOutlet()).isEqualTo(DEFAULT_PREPAYMENT_SERVICE_OUTLET);
        assertThat(testUpdatedAmortizationEntry.getPrepaymentAccountNumber()).isEqualTo(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testUpdatedAmortizationEntry.getAmortizationServiceOutlet()).isEqualTo(DEFAULT_AMORTIZATION_SERVICE_OUTLET);
        assertThat(testUpdatedAmortizationEntry.getAmortizationAccountNumber()).isEqualTo(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER);
        assertThat(testUpdatedAmortizationEntry.getOriginatingFileToken()).isEqualTo(DEFAULT_ORIGINATING_FILE_TOKEN);
        assertThat(testUpdatedAmortizationEntry.getAmortizationTag()).isEqualTo(DEFAULT_AMORTIZATION_TAG);
        assertThat(testUpdatedAmortizationEntry.isOrphaned()).isEqualTo(DEFAULT_ORPHANED);
        assertThat(testUpdatedAmortizationEntry.getDateOfUpdate()).isEqualTo(DEFAULT_DATE_OF_UPDATE);
        assertThat(testUpdatedAmortizationEntry.getReasonForUpdate()).isEqualTo(DEFAULT_REASON_FOR_UPDATE);

        // Validate the UpdatedAmortizationEntry in Elasticsearch
        verify(mockUpdatedAmortizationEntrySearchRepository, times(1)).save(testUpdatedAmortizationEntry);
    }

    @Test
    @Transactional
    public void createUpdatedAmortizationEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = updatedAmortizationEntryRepository.findAll().size();

        // Create the UpdatedAmortizationEntry with an existing ID
        updatedAmortizationEntry.setId(1L);
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UpdatedAmortizationEntry in the database
        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeCreate);

        // Validate the UpdatedAmortizationEntry in Elasticsearch
        verify(mockUpdatedAmortizationEntrySearchRepository, times(0)).save(updatedAmortizationEntry);
    }


    @Test
    @Transactional
    public void checkAmortizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setAmortizationDate(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setAmortizationAmount(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setPrepaymentServiceOutlet(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrepaymentAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setPrepaymentAccountNumber(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setAmortizationServiceOutlet(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setAmortizationAccountNumber(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfUpdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = updatedAmortizationEntryRepository.findAll().size();
        // set the field null
        updatedAmortizationEntry.setDateOfUpdate(null);

        // Create the UpdatedAmortizationEntry, which fails.
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(post("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntries() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(updatedAmortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG.toString())))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfUpdate").value(hasItem(DEFAULT_DATE_OF_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getUpdatedAmortizationEntry() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get the updatedAmortizationEntry
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries/{id}", updatedAmortizationEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(updatedAmortizationEntry.getId().intValue()))
            .andExpect(jsonPath("$.amortizationDate").value(DEFAULT_AMORTIZATION_DATE.toString()))
            .andExpect(jsonPath("$.amortizationAmount").value(DEFAULT_AMORTIZATION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.prepaymentServiceOutlet").value(DEFAULT_PREPAYMENT_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.prepaymentAccountNumber").value(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.amortizationServiceOutlet").value(DEFAULT_AMORTIZATION_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.amortizationAccountNumber").value(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.originatingFileToken").value(DEFAULT_ORIGINATING_FILE_TOKEN.toString()))
            .andExpect(jsonPath("$.amortizationTag").value(DEFAULT_AMORTIZATION_TAG.toString()))
            .andExpect(jsonPath("$.orphaned").value(DEFAULT_ORPHANED.booleanValue()))
            .andExpect(jsonPath("$.dateOfUpdate").value(DEFAULT_DATE_OF_UPDATE.toString()))
            .andExpect(jsonPath("$.reasonForUpdate").value(DEFAULT_REASON_FOR_UPDATE.toString()));
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationDate equals to DEFAULT_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationDate.equals=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the updatedAmortizationEntryList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationDate.equals=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationDate in DEFAULT_AMORTIZATION_DATE or UPDATED_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationDate.in=" + DEFAULT_AMORTIZATION_DATE + "," + UPDATED_AMORTIZATION_DATE);

        // Get all the updatedAmortizationEntryList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationDate.in=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationDate is not null
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationDate.specified=true");

        // Get all the updatedAmortizationEntryList where amortizationDate is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationDate greater than or equals to DEFAULT_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationDate.greaterOrEqualThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the updatedAmortizationEntryList where amortizationDate greater than or equals to UPDATED_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationDate.greaterOrEqualThan=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationDate less than or equals to DEFAULT_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationDate.lessThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the updatedAmortizationEntryList where amortizationDate less than or equals to UPDATED_AMORTIZATION_DATE
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationDate.lessThan=" + UPDATED_AMORTIZATION_DATE);
    }


    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAmount equals to DEFAULT_AMORTIZATION_AMOUNT
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAmount.equals=" + DEFAULT_AMORTIZATION_AMOUNT);

        // Get all the updatedAmortizationEntryList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAmount.equals=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAmount in DEFAULT_AMORTIZATION_AMOUNT or UPDATED_AMORTIZATION_AMOUNT
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAmount.in=" + DEFAULT_AMORTIZATION_AMOUNT + "," + UPDATED_AMORTIZATION_AMOUNT);

        // Get all the updatedAmortizationEntryList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAmount.in=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAmount is not null
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAmount.specified=true");

        // Get all the updatedAmortizationEntryList where amortizationAmount is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByParticularsIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where particulars equals to DEFAULT_PARTICULARS
        defaultUpdatedAmortizationEntryShouldBeFound("particulars.equals=" + DEFAULT_PARTICULARS);

        // Get all the updatedAmortizationEntryList where particulars equals to UPDATED_PARTICULARS
        defaultUpdatedAmortizationEntryShouldNotBeFound("particulars.equals=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByParticularsIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where particulars in DEFAULT_PARTICULARS or UPDATED_PARTICULARS
        defaultUpdatedAmortizationEntryShouldBeFound("particulars.in=" + DEFAULT_PARTICULARS + "," + UPDATED_PARTICULARS);

        // Get all the updatedAmortizationEntryList where particulars equals to UPDATED_PARTICULARS
        defaultUpdatedAmortizationEntryShouldNotBeFound("particulars.in=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByParticularsIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where particulars is not null
        defaultUpdatedAmortizationEntryShouldBeFound("particulars.specified=true");

        // Get all the updatedAmortizationEntryList where particulars is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("particulars.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet equals to DEFAULT_PREPAYMENT_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentServiceOutlet.equals=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET);

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet equals to UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentServiceOutlet.equals=" + UPDATED_PREPAYMENT_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet in DEFAULT_PREPAYMENT_SERVICE_OUTLET or UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentServiceOutlet.in=" + DEFAULT_PREPAYMENT_SERVICE_OUTLET + "," + UPDATED_PREPAYMENT_SERVICE_OUTLET);

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet equals to UPDATED_PREPAYMENT_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentServiceOutlet.in=" + UPDATED_PREPAYMENT_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet is not null
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentServiceOutlet.specified=true");

        // Get all the updatedAmortizationEntryList where prepaymentServiceOutlet is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentServiceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber equals to DEFAULT_PREPAYMENT_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentAccountNumber.equals=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentAccountNumber.equals=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber in DEFAULT_PREPAYMENT_ACCOUNT_NUMBER or UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentAccountNumber.in=" + DEFAULT_PREPAYMENT_ACCOUNT_NUMBER + "," + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber equals to UPDATED_PREPAYMENT_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentAccountNumber.in=" + UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber is not null
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentAccountNumber.specified=true");

        // Get all the updatedAmortizationEntryList where prepaymentAccountNumber is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet equals to DEFAULT_AMORTIZATION_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationServiceOutlet.equals=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET);

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet equals to UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationServiceOutlet.equals=" + UPDATED_AMORTIZATION_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet in DEFAULT_AMORTIZATION_SERVICE_OUTLET or UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationServiceOutlet.in=" + DEFAULT_AMORTIZATION_SERVICE_OUTLET + "," + UPDATED_AMORTIZATION_SERVICE_OUTLET);

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet equals to UPDATED_AMORTIZATION_SERVICE_OUTLET
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationServiceOutlet.in=" + UPDATED_AMORTIZATION_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet is not null
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationServiceOutlet.specified=true");

        // Get all the updatedAmortizationEntryList where amortizationServiceOutlet is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationServiceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber equals to DEFAULT_AMORTIZATION_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAccountNumber.equals=" + DEFAULT_AMORTIZATION_ACCOUNT_NUMBER);

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber equals to UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAccountNumber.equals=" + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber in DEFAULT_AMORTIZATION_ACCOUNT_NUMBER or UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAccountNumber.in=" + DEFAULT_AMORTIZATION_ACCOUNT_NUMBER + "," + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber equals to UPDATED_AMORTIZATION_ACCOUNT_NUMBER
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAccountNumber.in=" + UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber is not null
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationAccountNumber.specified=true");

        // Get all the updatedAmortizationEntryList where amortizationAccountNumber is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationAccountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOriginatingFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where originatingFileToken equals to DEFAULT_ORIGINATING_FILE_TOKEN
        defaultUpdatedAmortizationEntryShouldBeFound("originatingFileToken.equals=" + DEFAULT_ORIGINATING_FILE_TOKEN);

        // Get all the updatedAmortizationEntryList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultUpdatedAmortizationEntryShouldNotBeFound("originatingFileToken.equals=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOriginatingFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where originatingFileToken in DEFAULT_ORIGINATING_FILE_TOKEN or UPDATED_ORIGINATING_FILE_TOKEN
        defaultUpdatedAmortizationEntryShouldBeFound("originatingFileToken.in=" + DEFAULT_ORIGINATING_FILE_TOKEN + "," + UPDATED_ORIGINATING_FILE_TOKEN);

        // Get all the updatedAmortizationEntryList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultUpdatedAmortizationEntryShouldNotBeFound("originatingFileToken.in=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOriginatingFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where originatingFileToken is not null
        defaultUpdatedAmortizationEntryShouldBeFound("originatingFileToken.specified=true");

        // Get all the updatedAmortizationEntryList where originatingFileToken is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("originatingFileToken.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationTagIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationTag equals to DEFAULT_AMORTIZATION_TAG
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationTag.equals=" + DEFAULT_AMORTIZATION_TAG);

        // Get all the updatedAmortizationEntryList where amortizationTag equals to UPDATED_AMORTIZATION_TAG
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationTag.equals=" + UPDATED_AMORTIZATION_TAG);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationTagIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationTag in DEFAULT_AMORTIZATION_TAG or UPDATED_AMORTIZATION_TAG
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationTag.in=" + DEFAULT_AMORTIZATION_TAG + "," + UPDATED_AMORTIZATION_TAG);

        // Get all the updatedAmortizationEntryList where amortizationTag equals to UPDATED_AMORTIZATION_TAG
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationTag.in=" + UPDATED_AMORTIZATION_TAG);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByAmortizationTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where amortizationTag is not null
        defaultUpdatedAmortizationEntryShouldBeFound("amortizationTag.specified=true");

        // Get all the updatedAmortizationEntryList where amortizationTag is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("amortizationTag.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOrphanedIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where orphaned equals to DEFAULT_ORPHANED
        defaultUpdatedAmortizationEntryShouldBeFound("orphaned.equals=" + DEFAULT_ORPHANED);

        // Get all the updatedAmortizationEntryList where orphaned equals to UPDATED_ORPHANED
        defaultUpdatedAmortizationEntryShouldNotBeFound("orphaned.equals=" + UPDATED_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOrphanedIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where orphaned in DEFAULT_ORPHANED or UPDATED_ORPHANED
        defaultUpdatedAmortizationEntryShouldBeFound("orphaned.in=" + DEFAULT_ORPHANED + "," + UPDATED_ORPHANED);

        // Get all the updatedAmortizationEntryList where orphaned equals to UPDATED_ORPHANED
        defaultUpdatedAmortizationEntryShouldNotBeFound("orphaned.in=" + UPDATED_ORPHANED);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByOrphanedIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where orphaned is not null
        defaultUpdatedAmortizationEntryShouldBeFound("orphaned.specified=true");

        // Get all the updatedAmortizationEntryList where orphaned is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("orphaned.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByDateOfUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where dateOfUpdate equals to DEFAULT_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("dateOfUpdate.equals=" + DEFAULT_DATE_OF_UPDATE);

        // Get all the updatedAmortizationEntryList where dateOfUpdate equals to UPDATED_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("dateOfUpdate.equals=" + UPDATED_DATE_OF_UPDATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByDateOfUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where dateOfUpdate in DEFAULT_DATE_OF_UPDATE or UPDATED_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("dateOfUpdate.in=" + DEFAULT_DATE_OF_UPDATE + "," + UPDATED_DATE_OF_UPDATE);

        // Get all the updatedAmortizationEntryList where dateOfUpdate equals to UPDATED_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("dateOfUpdate.in=" + UPDATED_DATE_OF_UPDATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByDateOfUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where dateOfUpdate is not null
        defaultUpdatedAmortizationEntryShouldBeFound("dateOfUpdate.specified=true");

        // Get all the updatedAmortizationEntryList where dateOfUpdate is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("dateOfUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByDateOfUpdateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where dateOfUpdate greater than or equals to DEFAULT_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("dateOfUpdate.greaterOrEqualThan=" + DEFAULT_DATE_OF_UPDATE);

        // Get all the updatedAmortizationEntryList where dateOfUpdate greater than or equals to UPDATED_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("dateOfUpdate.greaterOrEqualThan=" + UPDATED_DATE_OF_UPDATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByDateOfUpdateIsLessThanSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where dateOfUpdate less than or equals to DEFAULT_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("dateOfUpdate.lessThan=" + DEFAULT_DATE_OF_UPDATE);

        // Get all the updatedAmortizationEntryList where dateOfUpdate less than or equals to UPDATED_DATE_OF_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("dateOfUpdate.lessThan=" + UPDATED_DATE_OF_UPDATE);
    }


    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByReasonForUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where reasonForUpdate equals to DEFAULT_REASON_FOR_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("reasonForUpdate.equals=" + DEFAULT_REASON_FOR_UPDATE);

        // Get all the updatedAmortizationEntryList where reasonForUpdate equals to UPDATED_REASON_FOR_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("reasonForUpdate.equals=" + UPDATED_REASON_FOR_UPDATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByReasonForUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where reasonForUpdate in DEFAULT_REASON_FOR_UPDATE or UPDATED_REASON_FOR_UPDATE
        defaultUpdatedAmortizationEntryShouldBeFound("reasonForUpdate.in=" + DEFAULT_REASON_FOR_UPDATE + "," + UPDATED_REASON_FOR_UPDATE);

        // Get all the updatedAmortizationEntryList where reasonForUpdate equals to UPDATED_REASON_FOR_UPDATE
        defaultUpdatedAmortizationEntryShouldNotBeFound("reasonForUpdate.in=" + UPDATED_REASON_FOR_UPDATE);
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByReasonForUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        // Get all the updatedAmortizationEntryList where reasonForUpdate is not null
        defaultUpdatedAmortizationEntryShouldBeFound("reasonForUpdate.specified=true");

        // Get all the updatedAmortizationEntryList where reasonForUpdate is null
        defaultUpdatedAmortizationEntryShouldNotBeFound("reasonForUpdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUpdatedAmortizationEntriesByPrepaymentEntryIsEqualToSomething() throws Exception {
        // Initialize the database
        PrepaymentEntry prepaymentEntry = PrepaymentEntryResourceIT.createEntity(em);
        em.persist(prepaymentEntry);
        em.flush();
        updatedAmortizationEntry.setPrepaymentEntry(prepaymentEntry);
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);
        Long prepaymentEntryId = prepaymentEntry.getId();

        // Get all the updatedAmortizationEntryList where prepaymentEntry equals to prepaymentEntryId
        defaultUpdatedAmortizationEntryShouldBeFound("prepaymentEntryId.equals=" + prepaymentEntryId);

        // Get all the updatedAmortizationEntryList where prepaymentEntry equals to prepaymentEntryId + 1
        defaultUpdatedAmortizationEntryShouldNotBeFound("prepaymentEntryId.equals=" + (prepaymentEntryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUpdatedAmortizationEntryShouldBeFound(String filter) throws Exception {
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(updatedAmortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG)))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfUpdate").value(hasItem(DEFAULT_DATE_OF_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE)));

        // Check, that the count call also returns 1
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUpdatedAmortizationEntryShouldNotBeFound(String filter) throws Exception {
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUpdatedAmortizationEntry() throws Exception {
        // Get the updatedAmortizationEntry
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/updated-amortization-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpdatedAmortizationEntry() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        int databaseSizeBeforeUpdate = updatedAmortizationEntryRepository.findAll().size();

        // Update the updatedAmortizationEntry
        UpdatedAmortizationEntry updatedUpdatedAmortizationEntry = updatedAmortizationEntryRepository.findById(updatedAmortizationEntry.getId()).get();
        // Disconnect from session so that the updates on updatedUpdatedAmortizationEntry are not directly saved in db
        em.detach(updatedUpdatedAmortizationEntry);
        updatedUpdatedAmortizationEntry
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .prepaymentServiceOutlet(UPDATED_PREPAYMENT_SERVICE_OUTLET)
            .prepaymentAccountNumber(UPDATED_PREPAYMENT_ACCOUNT_NUMBER)
            .amortizationServiceOutlet(UPDATED_AMORTIZATION_SERVICE_OUTLET)
            .amortizationAccountNumber(UPDATED_AMORTIZATION_ACCOUNT_NUMBER)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN)
            .amortizationTag(UPDATED_AMORTIZATION_TAG)
            .orphaned(UPDATED_ORPHANED)
            .dateOfUpdate(UPDATED_DATE_OF_UPDATE)
            .reasonForUpdate(UPDATED_REASON_FOR_UPDATE);
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedUpdatedAmortizationEntry);

        restUpdatedAmortizationEntryMockMvc.perform(put("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isOk());

        // Validate the UpdatedAmortizationEntry in the database
        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeUpdate);
        UpdatedAmortizationEntry testUpdatedAmortizationEntry = updatedAmortizationEntryList.get(updatedAmortizationEntryList.size() - 1);
        assertThat(testUpdatedAmortizationEntry.getAmortizationDate()).isEqualTo(UPDATED_AMORTIZATION_DATE);
        assertThat(testUpdatedAmortizationEntry.getAmortizationAmount()).isEqualTo(UPDATED_AMORTIZATION_AMOUNT);
        assertThat(testUpdatedAmortizationEntry.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testUpdatedAmortizationEntry.getPrepaymentServiceOutlet()).isEqualTo(UPDATED_PREPAYMENT_SERVICE_OUTLET);
        assertThat(testUpdatedAmortizationEntry.getPrepaymentAccountNumber()).isEqualTo(UPDATED_PREPAYMENT_ACCOUNT_NUMBER);
        assertThat(testUpdatedAmortizationEntry.getAmortizationServiceOutlet()).isEqualTo(UPDATED_AMORTIZATION_SERVICE_OUTLET);
        assertThat(testUpdatedAmortizationEntry.getAmortizationAccountNumber()).isEqualTo(UPDATED_AMORTIZATION_ACCOUNT_NUMBER);
        assertThat(testUpdatedAmortizationEntry.getOriginatingFileToken()).isEqualTo(UPDATED_ORIGINATING_FILE_TOKEN);
        assertThat(testUpdatedAmortizationEntry.getAmortizationTag()).isEqualTo(UPDATED_AMORTIZATION_TAG);
        assertThat(testUpdatedAmortizationEntry.isOrphaned()).isEqualTo(UPDATED_ORPHANED);
        assertThat(testUpdatedAmortizationEntry.getDateOfUpdate()).isEqualTo(UPDATED_DATE_OF_UPDATE);
        assertThat(testUpdatedAmortizationEntry.getReasonForUpdate()).isEqualTo(UPDATED_REASON_FOR_UPDATE);

        // Validate the UpdatedAmortizationEntry in Elasticsearch
        verify(mockUpdatedAmortizationEntrySearchRepository, times(1)).save(testUpdatedAmortizationEntry);
    }

    @Test
    @Transactional
    public void updateNonExistingUpdatedAmortizationEntry() throws Exception {
        int databaseSizeBeforeUpdate = updatedAmortizationEntryRepository.findAll().size();

        // Create the UpdatedAmortizationEntry
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO = updatedAmortizationEntryMapper.toDto(updatedAmortizationEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpdatedAmortizationEntryMockMvc.perform(put("/api/updated-amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAmortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UpdatedAmortizationEntry in the database
        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UpdatedAmortizationEntry in Elasticsearch
        verify(mockUpdatedAmortizationEntrySearchRepository, times(0)).save(updatedAmortizationEntry);
    }

    @Test
    @Transactional
    public void deleteUpdatedAmortizationEntry() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);

        int databaseSizeBeforeDelete = updatedAmortizationEntryRepository.findAll().size();

        // Delete the updatedAmortizationEntry
        restUpdatedAmortizationEntryMockMvc.perform(delete("/api/updated-amortization-entries/{id}", updatedAmortizationEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<UpdatedAmortizationEntry> updatedAmortizationEntryList = updatedAmortizationEntryRepository.findAll();
        assertThat(updatedAmortizationEntryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UpdatedAmortizationEntry in Elasticsearch
        verify(mockUpdatedAmortizationEntrySearchRepository, times(1)).deleteById(updatedAmortizationEntry.getId());
    }

    @Test
    @Transactional
    public void searchUpdatedAmortizationEntry() throws Exception {
        // Initialize the database
        updatedAmortizationEntryRepository.saveAndFlush(updatedAmortizationEntry);
        when(mockUpdatedAmortizationEntrySearchRepository.search(queryStringQuery("id:" + updatedAmortizationEntry.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(updatedAmortizationEntry), PageRequest.of(0, 1), 1));
        // Search the updatedAmortizationEntry
        restUpdatedAmortizationEntryMockMvc.perform(get("/api/_search/updated-amortization-entries?query=id:" + updatedAmortizationEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(updatedAmortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].prepaymentServiceOutlet").value(hasItem(DEFAULT_PREPAYMENT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].prepaymentAccountNumber").value(hasItem(DEFAULT_PREPAYMENT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].amortizationServiceOutlet").value(hasItem(DEFAULT_AMORTIZATION_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].amortizationAccountNumber").value(hasItem(DEFAULT_AMORTIZATION_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].amortizationTag").value(hasItem(DEFAULT_AMORTIZATION_TAG)))
            .andExpect(jsonPath("$.[*].orphaned").value(hasItem(DEFAULT_ORPHANED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateOfUpdate").value(hasItem(DEFAULT_DATE_OF_UPDATE.toString())))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpdatedAmortizationEntry.class);
        UpdatedAmortizationEntry updatedAmortizationEntry1 = new UpdatedAmortizationEntry();
        updatedAmortizationEntry1.setId(1L);
        UpdatedAmortizationEntry updatedAmortizationEntry2 = new UpdatedAmortizationEntry();
        updatedAmortizationEntry2.setId(updatedAmortizationEntry1.getId());
        assertThat(updatedAmortizationEntry1).isEqualTo(updatedAmortizationEntry2);
        updatedAmortizationEntry2.setId(2L);
        assertThat(updatedAmortizationEntry1).isNotEqualTo(updatedAmortizationEntry2);
        updatedAmortizationEntry1.setId(null);
        assertThat(updatedAmortizationEntry1).isNotEqualTo(updatedAmortizationEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpdatedAmortizationEntryDTO.class);
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO1 = new UpdatedAmortizationEntryDTO();
        updatedAmortizationEntryDTO1.setId(1L);
        UpdatedAmortizationEntryDTO updatedAmortizationEntryDTO2 = new UpdatedAmortizationEntryDTO();
        assertThat(updatedAmortizationEntryDTO1).isNotEqualTo(updatedAmortizationEntryDTO2);
        updatedAmortizationEntryDTO2.setId(updatedAmortizationEntryDTO1.getId());
        assertThat(updatedAmortizationEntryDTO1).isEqualTo(updatedAmortizationEntryDTO2);
        updatedAmortizationEntryDTO2.setId(2L);
        assertThat(updatedAmortizationEntryDTO1).isNotEqualTo(updatedAmortizationEntryDTO2);
        updatedAmortizationEntryDTO1.setId(null);
        assertThat(updatedAmortizationEntryDTO1).isNotEqualTo(updatedAmortizationEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(updatedAmortizationEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(updatedAmortizationEntryMapper.fromId(null)).isNull();
    }
}
