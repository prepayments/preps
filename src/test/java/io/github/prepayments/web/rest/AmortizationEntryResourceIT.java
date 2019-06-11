package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.domain.PrepaymentEntry;
import io.github.prepayments.repository.AmortizationEntryRepository;
import io.github.prepayments.repository.search.AmortizationEntrySearchRepository;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.mapper.AmortizationEntryMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AmortizationEntryCriteria;
import io.github.prepayments.service.AmortizationEntryQueryService;

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
 * Integration tests for the {@Link AmortizationEntryResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationEntryResourceIT {

    private static final LocalDate DEFAULT_AMORTIZATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AMORTIZATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_AMORTIZATION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMORTIZATION_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_PARTICULARS = "AAAAAAAAAA";
    private static final String UPDATED_PARTICULARS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_POSTED = false;
    private static final Boolean UPDATED_POSTED = true;

    private static final String DEFAULT_SERVICE_OUTLET = "536";
    private static final String UPDATED_SERVICE_OUTLET = "018";

    private static final String DEFAULT_ACCOUNT_NUMBER = "279662780622";
    private static final String UPDATED_ACCOUNT_NUMBER = "1104552062";

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    @Autowired
    private AmortizationEntryRepository amortizationEntryRepository;

    @Autowired
    private AmortizationEntryMapper amortizationEntryMapper;

    @Autowired
    private AmortizationEntryService amortizationEntryService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.AmortizationEntrySearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationEntrySearchRepository mockAmortizationEntrySearchRepository;

    @Autowired
    private AmortizationEntryQueryService amortizationEntryQueryService;

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

    private MockMvc restAmortizationEntryMockMvc;

    private AmortizationEntry amortizationEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationEntryResource amortizationEntryResource = new AmortizationEntryResource(amortizationEntryService, amortizationEntryQueryService);
        this.restAmortizationEntryMockMvc = MockMvcBuilders.standaloneSetup(amortizationEntryResource)
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
    public static AmortizationEntry createEntity(EntityManager em) {
        AmortizationEntry amortizationEntry = new AmortizationEntry()
            .amortizationDate(DEFAULT_AMORTIZATION_DATE)
            .amortizationAmount(DEFAULT_AMORTIZATION_AMOUNT)
            .particulars(DEFAULT_PARTICULARS)
            .posted(DEFAULT_POSTED)
            .serviceOutlet(DEFAULT_SERVICE_OUTLET)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountName(DEFAULT_ACCOUNT_NAME);
        // Add required entity
        PrepaymentEntry prepaymentEntry;
        if (TestUtil.findAll(em, PrepaymentEntry.class).isEmpty()) {
            prepaymentEntry = PrepaymentEntryResourceIT.createEntity(em);
            em.persist(prepaymentEntry);
            em.flush();
        } else {
            prepaymentEntry = TestUtil.findAll(em, PrepaymentEntry.class).get(0);
        }
        amortizationEntry.setPrepaymentEntry(prepaymentEntry);
        return amortizationEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationEntry createUpdatedEntity(EntityManager em) {
        AmortizationEntry amortizationEntry = new AmortizationEntry()
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .posted(UPDATED_POSTED)
            .serviceOutlet(UPDATED_SERVICE_OUTLET)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME);
        // Add required entity
        PrepaymentEntry prepaymentEntry;
        if (TestUtil.findAll(em, PrepaymentEntry.class).isEmpty()) {
            prepaymentEntry = PrepaymentEntryResourceIT.createUpdatedEntity(em);
            em.persist(prepaymentEntry);
            em.flush();
        } else {
            prepaymentEntry = TestUtil.findAll(em, PrepaymentEntry.class).get(0);
        }
        amortizationEntry.setPrepaymentEntry(prepaymentEntry);
        return amortizationEntry;
    }

    @BeforeEach
    public void initTest() {
        amortizationEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationEntry() throws Exception {
        int databaseSizeBeforeCreate = amortizationEntryRepository.findAll().size();

        // Create the AmortizationEntry
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);
        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationEntry in the database
        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationEntry testAmortizationEntry = amortizationEntryList.get(amortizationEntryList.size() - 1);
        assertThat(testAmortizationEntry.getAmortizationDate()).isEqualTo(DEFAULT_AMORTIZATION_DATE);
        assertThat(testAmortizationEntry.getAmortizationAmount()).isEqualTo(DEFAULT_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationEntry.getParticulars()).isEqualTo(DEFAULT_PARTICULARS);
        assertThat(testAmortizationEntry.isPosted()).isEqualTo(DEFAULT_POSTED);
        assertThat(testAmortizationEntry.getServiceOutlet()).isEqualTo(DEFAULT_SERVICE_OUTLET);
        assertThat(testAmortizationEntry.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntry.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);

        // Validate the AmortizationEntry in Elasticsearch
        verify(mockAmortizationEntrySearchRepository, times(1)).save(testAmortizationEntry);
    }

    @Test
    @Transactional
    public void createAmortizationEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationEntryRepository.findAll().size();

        // Create the AmortizationEntry with an existing ID
        amortizationEntry.setId(1L);
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationEntry in the database
        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmortizationEntry in Elasticsearch
        verify(mockAmortizationEntrySearchRepository, times(0)).save(amortizationEntry);
    }


    @Test
    @Transactional
    public void checkAmortizationDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryRepository.findAll().size();
        // set the field null
        amortizationEntry.setAmortizationDate(null);

        // Create the AmortizationEntry, which fails.
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmortizationAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryRepository.findAll().size();
        // set the field null
        amortizationEntry.setAmortizationAmount(null);

        // Create the AmortizationEntry, which fails.
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOutletIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryRepository.findAll().size();
        // set the field null
        amortizationEntry.setServiceOutlet(null);

        // Create the AmortizationEntry, which fails.
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryRepository.findAll().size();
        // set the field null
        amortizationEntry.setAccountNumber(null);

        // Create the AmortizationEntry, which fails.
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationEntryRepository.findAll().size();
        // set the field null
        amortizationEntry.setAccountName(null);

        // Create the AmortizationEntry, which fails.
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        restAmortizationEntryMockMvc.perform(post("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntries() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS.toString())))
            .andExpect(jsonPath("$.[*].posted").value(hasItem(DEFAULT_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortizationEntry() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get the amortizationEntry
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries/{id}", amortizationEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationEntry.getId().intValue()))
            .andExpect(jsonPath("$.amortizationDate").value(DEFAULT_AMORTIZATION_DATE.toString()))
            .andExpect(jsonPath("$.amortizationAmount").value(DEFAULT_AMORTIZATION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.particulars").value(DEFAULT_PARTICULARS.toString()))
            .andExpect(jsonPath("$.posted").value(DEFAULT_POSTED.booleanValue()))
            .andExpect(jsonPath("$.serviceOutlet").value(DEFAULT_SERVICE_OUTLET.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationDate equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryShouldBeFound("amortizationDate.equals=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryShouldNotBeFound("amortizationDate.equals=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationDateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationDate in DEFAULT_AMORTIZATION_DATE or UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryShouldBeFound("amortizationDate.in=" + DEFAULT_AMORTIZATION_DATE + "," + UPDATED_AMORTIZATION_DATE);

        // Get all the amortizationEntryList where amortizationDate equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryShouldNotBeFound("amortizationDate.in=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationDate is not null
        defaultAmortizationEntryShouldBeFound("amortizationDate.specified=true");

        // Get all the amortizationEntryList where amortizationDate is null
        defaultAmortizationEntryShouldNotBeFound("amortizationDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationDate greater than or equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryShouldBeFound("amortizationDate.greaterOrEqualThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryList where amortizationDate greater than or equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryShouldNotBeFound("amortizationDate.greaterOrEqualThan=" + UPDATED_AMORTIZATION_DATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationDateIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationDate less than or equals to DEFAULT_AMORTIZATION_DATE
        defaultAmortizationEntryShouldNotBeFound("amortizationDate.lessThan=" + DEFAULT_AMORTIZATION_DATE);

        // Get all the amortizationEntryList where amortizationDate less than or equals to UPDATED_AMORTIZATION_DATE
        defaultAmortizationEntryShouldBeFound("amortizationDate.lessThan=" + UPDATED_AMORTIZATION_DATE);
    }


    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationAmount equals to DEFAULT_AMORTIZATION_AMOUNT
        defaultAmortizationEntryShouldBeFound("amortizationAmount.equals=" + DEFAULT_AMORTIZATION_AMOUNT);

        // Get all the amortizationEntryList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryShouldNotBeFound("amortizationAmount.equals=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationAmountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationAmount in DEFAULT_AMORTIZATION_AMOUNT or UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryShouldBeFound("amortizationAmount.in=" + DEFAULT_AMORTIZATION_AMOUNT + "," + UPDATED_AMORTIZATION_AMOUNT);

        // Get all the amortizationEntryList where amortizationAmount equals to UPDATED_AMORTIZATION_AMOUNT
        defaultAmortizationEntryShouldNotBeFound("amortizationAmount.in=" + UPDATED_AMORTIZATION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAmortizationAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where amortizationAmount is not null
        defaultAmortizationEntryShouldBeFound("amortizationAmount.specified=true");

        // Get all the amortizationEntryList where amortizationAmount is null
        defaultAmortizationEntryShouldNotBeFound("amortizationAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByParticularsIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where particulars equals to DEFAULT_PARTICULARS
        defaultAmortizationEntryShouldBeFound("particulars.equals=" + DEFAULT_PARTICULARS);

        // Get all the amortizationEntryList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationEntryShouldNotBeFound("particulars.equals=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByParticularsIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where particulars in DEFAULT_PARTICULARS or UPDATED_PARTICULARS
        defaultAmortizationEntryShouldBeFound("particulars.in=" + DEFAULT_PARTICULARS + "," + UPDATED_PARTICULARS);

        // Get all the amortizationEntryList where particulars equals to UPDATED_PARTICULARS
        defaultAmortizationEntryShouldNotBeFound("particulars.in=" + UPDATED_PARTICULARS);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByParticularsIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where particulars is not null
        defaultAmortizationEntryShouldBeFound("particulars.specified=true");

        // Get all the amortizationEntryList where particulars is null
        defaultAmortizationEntryShouldNotBeFound("particulars.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByPostedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where posted equals to DEFAULT_POSTED
        defaultAmortizationEntryShouldBeFound("posted.equals=" + DEFAULT_POSTED);

        // Get all the amortizationEntryList where posted equals to UPDATED_POSTED
        defaultAmortizationEntryShouldNotBeFound("posted.equals=" + UPDATED_POSTED);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByPostedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where posted in DEFAULT_POSTED or UPDATED_POSTED
        defaultAmortizationEntryShouldBeFound("posted.in=" + DEFAULT_POSTED + "," + UPDATED_POSTED);

        // Get all the amortizationEntryList where posted equals to UPDATED_POSTED
        defaultAmortizationEntryShouldNotBeFound("posted.in=" + UPDATED_POSTED);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByPostedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where posted is not null
        defaultAmortizationEntryShouldBeFound("posted.specified=true");

        // Get all the amortizationEntryList where posted is null
        defaultAmortizationEntryShouldNotBeFound("posted.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByServiceOutletIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where serviceOutlet equals to DEFAULT_SERVICE_OUTLET
        defaultAmortizationEntryShouldBeFound("serviceOutlet.equals=" + DEFAULT_SERVICE_OUTLET);

        // Get all the amortizationEntryList where serviceOutlet equals to UPDATED_SERVICE_OUTLET
        defaultAmortizationEntryShouldNotBeFound("serviceOutlet.equals=" + UPDATED_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByServiceOutletIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where serviceOutlet in DEFAULT_SERVICE_OUTLET or UPDATED_SERVICE_OUTLET
        defaultAmortizationEntryShouldBeFound("serviceOutlet.in=" + DEFAULT_SERVICE_OUTLET + "," + UPDATED_SERVICE_OUTLET);

        // Get all the amortizationEntryList where serviceOutlet equals to UPDATED_SERVICE_OUTLET
        defaultAmortizationEntryShouldNotBeFound("serviceOutlet.in=" + UPDATED_SERVICE_OUTLET);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByServiceOutletIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where serviceOutlet is not null
        defaultAmortizationEntryShouldBeFound("serviceOutlet.specified=true");

        // Get all the amortizationEntryList where serviceOutlet is null
        defaultAmortizationEntryShouldNotBeFound("serviceOutlet.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultAmortizationEntryShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the amortizationEntryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultAmortizationEntryShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultAmortizationEntryShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the amortizationEntryList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultAmortizationEntryShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountNumber is not null
        defaultAmortizationEntryShouldBeFound("accountNumber.specified=true");

        // Get all the amortizationEntryList where accountNumber is null
        defaultAmortizationEntryShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultAmortizationEntryShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the amortizationEntryList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAmortizationEntryShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultAmortizationEntryShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the amortizationEntryList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAmortizationEntryShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        // Get all the amortizationEntryList where accountName is not null
        defaultAmortizationEntryShouldBeFound("accountName.specified=true");

        // Get all the amortizationEntryList where accountName is null
        defaultAmortizationEntryShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationEntriesByPrepaymentEntryIsEqualToSomething() throws Exception {
        // Get already existing entity
        PrepaymentEntry prepaymentEntry = amortizationEntry.getPrepaymentEntry();
        amortizationEntryRepository.saveAndFlush(amortizationEntry);
        Long prepaymentEntryId = prepaymentEntry.getId();

        // Get all the amortizationEntryList where prepaymentEntry equals to prepaymentEntryId
        defaultAmortizationEntryShouldBeFound("prepaymentEntryId.equals=" + prepaymentEntryId);

        // Get all the amortizationEntryList where prepaymentEntry equals to prepaymentEntryId + 1
        defaultAmortizationEntryShouldNotBeFound("prepaymentEntryId.equals=" + (prepaymentEntryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationEntryShouldBeFound(String filter) throws Exception {
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].posted").value(hasItem(DEFAULT_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)));

        // Check, that the count call also returns 1
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationEntryShouldNotBeFound(String filter) throws Exception {
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationEntry() throws Exception {
        // Get the amortizationEntry
        restAmortizationEntryMockMvc.perform(get("/api/amortization-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationEntry() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        int databaseSizeBeforeUpdate = amortizationEntryRepository.findAll().size();

        // Update the amortizationEntry
        AmortizationEntry updatedAmortizationEntry = amortizationEntryRepository.findById(amortizationEntry.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationEntry are not directly saved in db
        em.detach(updatedAmortizationEntry);
        updatedAmortizationEntry
            .amortizationDate(UPDATED_AMORTIZATION_DATE)
            .amortizationAmount(UPDATED_AMORTIZATION_AMOUNT)
            .particulars(UPDATED_PARTICULARS)
            .posted(UPDATED_POSTED)
            .serviceOutlet(UPDATED_SERVICE_OUTLET)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountName(UPDATED_ACCOUNT_NAME);
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(updatedAmortizationEntry);

        restAmortizationEntryMockMvc.perform(put("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationEntry in the database
        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeUpdate);
        AmortizationEntry testAmortizationEntry = amortizationEntryList.get(amortizationEntryList.size() - 1);
        assertThat(testAmortizationEntry.getAmortizationDate()).isEqualTo(UPDATED_AMORTIZATION_DATE);
        assertThat(testAmortizationEntry.getAmortizationAmount()).isEqualTo(UPDATED_AMORTIZATION_AMOUNT);
        assertThat(testAmortizationEntry.getParticulars()).isEqualTo(UPDATED_PARTICULARS);
        assertThat(testAmortizationEntry.isPosted()).isEqualTo(UPDATED_POSTED);
        assertThat(testAmortizationEntry.getServiceOutlet()).isEqualTo(UPDATED_SERVICE_OUTLET);
        assertThat(testAmortizationEntry.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testAmortizationEntry.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);

        // Validate the AmortizationEntry in Elasticsearch
        verify(mockAmortizationEntrySearchRepository, times(1)).save(testAmortizationEntry);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationEntry() throws Exception {
        int databaseSizeBeforeUpdate = amortizationEntryRepository.findAll().size();

        // Create the AmortizationEntry
        AmortizationEntryDTO amortizationEntryDTO = amortizationEntryMapper.toDto(amortizationEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationEntryMockMvc.perform(put("/api/amortization-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationEntryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationEntry in the database
        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmortizationEntry in Elasticsearch
        verify(mockAmortizationEntrySearchRepository, times(0)).save(amortizationEntry);
    }

    @Test
    @Transactional
    public void deleteAmortizationEntry() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);

        int databaseSizeBeforeDelete = amortizationEntryRepository.findAll().size();

        // Delete the amortizationEntry
        restAmortizationEntryMockMvc.perform(delete("/api/amortization-entries/{id}", amortizationEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationEntry> amortizationEntryList = amortizationEntryRepository.findAll();
        assertThat(amortizationEntryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmortizationEntry in Elasticsearch
        verify(mockAmortizationEntrySearchRepository, times(1)).deleteById(amortizationEntry.getId());
    }

    @Test
    @Transactional
    public void searchAmortizationEntry() throws Exception {
        // Initialize the database
        amortizationEntryRepository.saveAndFlush(amortizationEntry);
        when(mockAmortizationEntrySearchRepository.search(queryStringQuery("id:" + amortizationEntry.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizationEntry), PageRequest.of(0, 1), 1));
        // Search the amortizationEntry
        restAmortizationEntryMockMvc.perform(get("/api/_search/amortization-entries?query=id:" + amortizationEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].amortizationDate").value(hasItem(DEFAULT_AMORTIZATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].amortizationAmount").value(hasItem(DEFAULT_AMORTIZATION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].particulars").value(hasItem(DEFAULT_PARTICULARS)))
            .andExpect(jsonPath("$.[*].posted").value(hasItem(DEFAULT_POSTED.booleanValue())))
            .andExpect(jsonPath("$.[*].serviceOutlet").value(hasItem(DEFAULT_SERVICE_OUTLET)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationEntry.class);
        AmortizationEntry amortizationEntry1 = new AmortizationEntry();
        amortizationEntry1.setId(1L);
        AmortizationEntry amortizationEntry2 = new AmortizationEntry();
        amortizationEntry2.setId(amortizationEntry1.getId());
        assertThat(amortizationEntry1).isEqualTo(amortizationEntry2);
        amortizationEntry2.setId(2L);
        assertThat(amortizationEntry1).isNotEqualTo(amortizationEntry2);
        amortizationEntry1.setId(null);
        assertThat(amortizationEntry1).isNotEqualTo(amortizationEntry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationEntryDTO.class);
        AmortizationEntryDTO amortizationEntryDTO1 = new AmortizationEntryDTO();
        amortizationEntryDTO1.setId(1L);
        AmortizationEntryDTO amortizationEntryDTO2 = new AmortizationEntryDTO();
        assertThat(amortizationEntryDTO1).isNotEqualTo(amortizationEntryDTO2);
        amortizationEntryDTO2.setId(amortizationEntryDTO1.getId());
        assertThat(amortizationEntryDTO1).isEqualTo(amortizationEntryDTO2);
        amortizationEntryDTO2.setId(2L);
        assertThat(amortizationEntryDTO1).isNotEqualTo(amortizationEntryDTO2);
        amortizationEntryDTO1.setId(null);
        assertThat(amortizationEntryDTO1).isNotEqualTo(amortizationEntryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationEntryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationEntryMapper.fromId(null)).isNull();
    }
}
