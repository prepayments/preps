package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.TransactionAccount;
import io.github.prepayments.repository.TransactionAccountRepository;
import io.github.prepayments.repository.search.TransactionAccountSearchRepository;
import io.github.prepayments.service.TransactionAccountService;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import io.github.prepayments.service.mapper.TransactionAccountMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.TransactionAccountCriteria;
import io.github.prepayments.service.TransactionAccountQueryService;

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

import io.github.prepayments.domain.enumeration.AccountTypes;
/**
 * Integration tests for the {@Link TransactionAccountResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class TransactionAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final AccountTypes DEFAULT_ACCOUNT_TYPE = AccountTypes.PREPAYMENT;
    private static final AccountTypes UPDATED_ACCOUNT_TYPE = AccountTypes.AMORTIZATION;

    private static final LocalDate DEFAULT_OPENING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private TransactionAccountRepository transactionAccountRepository;

    @Autowired
    private TransactionAccountMapper transactionAccountMapper;

    @Autowired
    private TransactionAccountService transactionAccountService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.TransactionAccountSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionAccountSearchRepository mockTransactionAccountSearchRepository;

    @Autowired
    private TransactionAccountQueryService transactionAccountQueryService;

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

    private MockMvc restTransactionAccountMockMvc;

    private TransactionAccount transactionAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionAccountResource transactionAccountResource = new TransactionAccountResource(transactionAccountService, transactionAccountQueryService);
        this.restTransactionAccountMockMvc = MockMvcBuilders.standaloneSetup(transactionAccountResource)
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
    public static TransactionAccount createEntity(EntityManager em) {
        TransactionAccount transactionAccount = new TransactionAccount()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountType(DEFAULT_ACCOUNT_TYPE)
            .openingDate(DEFAULT_OPENING_DATE)
            .originatingFileToken(DEFAULT_ORIGINATING_FILE_TOKEN);
        return transactionAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionAccount createUpdatedEntity(EntityManager em) {
        TransactionAccount transactionAccount = new TransactionAccount()
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .openingDate(UPDATED_OPENING_DATE)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN);
        return transactionAccount;
    }

    @BeforeEach
    public void initTest() {
        transactionAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionAccount() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);
        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionAccount testTransactionAccount = transactionAccountList.get(transactionAccountList.size() - 1);
        assertThat(testTransactionAccount.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testTransactionAccount.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testTransactionAccount.getAccountType()).isEqualTo(DEFAULT_ACCOUNT_TYPE);
        assertThat(testTransactionAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testTransactionAccount.getOriginatingFileToken()).isEqualTo(DEFAULT_ORIGINATING_FILE_TOKEN);

        // Validate the TransactionAccount in Elasticsearch
        verify(mockTransactionAccountSearchRepository, times(1)).save(testTransactionAccount);
    }

    @Test
    @Transactional
    public void createTransactionAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount with an existing ID
        transactionAccount.setId(1L);
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionAccount in Elasticsearch
        verify(mockTransactionAccountSearchRepository, times(0)).save(transactionAccount);
    }


    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountRepository.findAll().size();
        // set the field null
        transactionAccount.setAccountName(null);

        // Create the TransactionAccount, which fails.
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountRepository.findAll().size();
        // set the field null
        transactionAccount.setAccountNumber(null);

        // Create the TransactionAccount, which fails.
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        restTransactionAccountMockMvc.perform(post("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionAccounts() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get the transactionAccount
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/{id}", transactionAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionAccount.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.accountType").value(DEFAULT_ACCOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE.toString()))
            .andExpect(jsonPath("$.originatingFileToken").value(DEFAULT_ORIGINATING_FILE_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultTransactionAccountShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the transactionAccountList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultTransactionAccountShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultTransactionAccountShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the transactionAccountList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultTransactionAccountShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountName is not null
        defaultTransactionAccountShouldBeFound("accountName.specified=true");

        // Get all the transactionAccountList where accountName is null
        defaultTransactionAccountShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultTransactionAccountShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the transactionAccountList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultTransactionAccountShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultTransactionAccountShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the transactionAccountList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultTransactionAccountShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountNumber is not null
        defaultTransactionAccountShouldBeFound("accountNumber.specified=true");

        // Get all the transactionAccountList where accountNumber is null
        defaultTransactionAccountShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountType equals to DEFAULT_ACCOUNT_TYPE
        defaultTransactionAccountShouldBeFound("accountType.equals=" + DEFAULT_ACCOUNT_TYPE);

        // Get all the transactionAccountList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultTransactionAccountShouldNotBeFound("accountType.equals=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountType in DEFAULT_ACCOUNT_TYPE or UPDATED_ACCOUNT_TYPE
        defaultTransactionAccountShouldBeFound("accountType.in=" + DEFAULT_ACCOUNT_TYPE + "," + UPDATED_ACCOUNT_TYPE);

        // Get all the transactionAccountList where accountType equals to UPDATED_ACCOUNT_TYPE
        defaultTransactionAccountShouldNotBeFound("accountType.in=" + UPDATED_ACCOUNT_TYPE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountType is not null
        defaultTransactionAccountShouldBeFound("accountType.specified=true");

        // Get all the transactionAccountList where accountType is null
        defaultTransactionAccountShouldNotBeFound("accountType.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOpeningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where openingDate equals to DEFAULT_OPENING_DATE
        defaultTransactionAccountShouldBeFound("openingDate.equals=" + DEFAULT_OPENING_DATE);

        // Get all the transactionAccountList where openingDate equals to UPDATED_OPENING_DATE
        defaultTransactionAccountShouldNotBeFound("openingDate.equals=" + UPDATED_OPENING_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOpeningDateIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where openingDate in DEFAULT_OPENING_DATE or UPDATED_OPENING_DATE
        defaultTransactionAccountShouldBeFound("openingDate.in=" + DEFAULT_OPENING_DATE + "," + UPDATED_OPENING_DATE);

        // Get all the transactionAccountList where openingDate equals to UPDATED_OPENING_DATE
        defaultTransactionAccountShouldNotBeFound("openingDate.in=" + UPDATED_OPENING_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOpeningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where openingDate is not null
        defaultTransactionAccountShouldBeFound("openingDate.specified=true");

        // Get all the transactionAccountList where openingDate is null
        defaultTransactionAccountShouldNotBeFound("openingDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOpeningDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where openingDate greater than or equals to DEFAULT_OPENING_DATE
        defaultTransactionAccountShouldBeFound("openingDate.greaterOrEqualThan=" + DEFAULT_OPENING_DATE);

        // Get all the transactionAccountList where openingDate greater than or equals to UPDATED_OPENING_DATE
        defaultTransactionAccountShouldNotBeFound("openingDate.greaterOrEqualThan=" + UPDATED_OPENING_DATE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOpeningDateIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where openingDate less than or equals to DEFAULT_OPENING_DATE
        defaultTransactionAccountShouldNotBeFound("openingDate.lessThan=" + DEFAULT_OPENING_DATE);

        // Get all the transactionAccountList where openingDate less than or equals to UPDATED_OPENING_DATE
        defaultTransactionAccountShouldBeFound("openingDate.lessThan=" + UPDATED_OPENING_DATE);
    }


    @Test
    @Transactional
    public void getAllTransactionAccountsByOriginatingFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where originatingFileToken equals to DEFAULT_ORIGINATING_FILE_TOKEN
        defaultTransactionAccountShouldBeFound("originatingFileToken.equals=" + DEFAULT_ORIGINATING_FILE_TOKEN);

        // Get all the transactionAccountList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultTransactionAccountShouldNotBeFound("originatingFileToken.equals=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOriginatingFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where originatingFileToken in DEFAULT_ORIGINATING_FILE_TOKEN or UPDATED_ORIGINATING_FILE_TOKEN
        defaultTransactionAccountShouldBeFound("originatingFileToken.in=" + DEFAULT_ORIGINATING_FILE_TOKEN + "," + UPDATED_ORIGINATING_FILE_TOKEN);

        // Get all the transactionAccountList where originatingFileToken equals to UPDATED_ORIGINATING_FILE_TOKEN
        defaultTransactionAccountShouldNotBeFound("originatingFileToken.in=" + UPDATED_ORIGINATING_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByOriginatingFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where originatingFileToken is not null
        defaultTransactionAccountShouldBeFound("originatingFileToken.specified=true");

        // Get all the transactionAccountList where originatingFileToken is null
        defaultTransactionAccountShouldNotBeFound("originatingFileToken.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionAccountShouldBeFound(String filter) throws Exception {
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)));

        // Check, that the count call also returns 1
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionAccountShouldNotBeFound(String filter) throws Exception {
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionAccount() throws Exception {
        // Get the transactionAccount
        restTransactionAccountMockMvc.perform(get("/api/transaction-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        int databaseSizeBeforeUpdate = transactionAccountRepository.findAll().size();

        // Update the transactionAccount
        TransactionAccount updatedTransactionAccount = transactionAccountRepository.findById(transactionAccount.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionAccount are not directly saved in db
        em.detach(updatedTransactionAccount);
        updatedTransactionAccount
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountType(UPDATED_ACCOUNT_TYPE)
            .openingDate(UPDATED_OPENING_DATE)
            .originatingFileToken(UPDATED_ORIGINATING_FILE_TOKEN);
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(updatedTransactionAccount);

        restTransactionAccountMockMvc.perform(put("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeUpdate);
        TransactionAccount testTransactionAccount = transactionAccountList.get(transactionAccountList.size() - 1);
        assertThat(testTransactionAccount.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testTransactionAccount.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testTransactionAccount.getAccountType()).isEqualTo(UPDATED_ACCOUNT_TYPE);
        assertThat(testTransactionAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testTransactionAccount.getOriginatingFileToken()).isEqualTo(UPDATED_ORIGINATING_FILE_TOKEN);

        // Validate the TransactionAccount in Elasticsearch
        verify(mockTransactionAccountSearchRepository, times(1)).save(testTransactionAccount);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionAccount() throws Exception {
        int databaseSizeBeforeUpdate = transactionAccountRepository.findAll().size();

        // Create the TransactionAccount
        TransactionAccountDTO transactionAccountDTO = transactionAccountMapper.toDto(transactionAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionAccountMockMvc.perform(put("/api/transaction-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAccount in the database
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionAccount in Elasticsearch
        verify(mockTransactionAccountSearchRepository, times(0)).save(transactionAccount);
    }

    @Test
    @Transactional
    public void deleteTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        int databaseSizeBeforeDelete = transactionAccountRepository.findAll().size();

        // Delete the transactionAccount
        restTransactionAccountMockMvc.perform(delete("/api/transaction-accounts/{id}", transactionAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TransactionAccount> transactionAccountList = transactionAccountRepository.findAll();
        assertThat(transactionAccountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionAccount in Elasticsearch
        verify(mockTransactionAccountSearchRepository, times(1)).deleteById(transactionAccount.getId());
    }

    @Test
    @Transactional
    public void searchTransactionAccount() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);
        when(mockTransactionAccountSearchRepository.search(queryStringQuery("id:" + transactionAccount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transactionAccount), PageRequest.of(0, 1), 1));
        // Search the transactionAccount
        restTransactionAccountMockMvc.perform(get("/api/_search/transaction-accounts?query=id:" + transactionAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountType").value(hasItem(DEFAULT_ACCOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].originatingFileToken").value(hasItem(DEFAULT_ORIGINATING_FILE_TOKEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccount.class);
        TransactionAccount transactionAccount1 = new TransactionAccount();
        transactionAccount1.setId(1L);
        TransactionAccount transactionAccount2 = new TransactionAccount();
        transactionAccount2.setId(transactionAccount1.getId());
        assertThat(transactionAccount1).isEqualTo(transactionAccount2);
        transactionAccount2.setId(2L);
        assertThat(transactionAccount1).isNotEqualTo(transactionAccount2);
        transactionAccount1.setId(null);
        assertThat(transactionAccount1).isNotEqualTo(transactionAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccountDTO.class);
        TransactionAccountDTO transactionAccountDTO1 = new TransactionAccountDTO();
        transactionAccountDTO1.setId(1L);
        TransactionAccountDTO transactionAccountDTO2 = new TransactionAccountDTO();
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
        transactionAccountDTO2.setId(transactionAccountDTO1.getId());
        assertThat(transactionAccountDTO1).isEqualTo(transactionAccountDTO2);
        transactionAccountDTO2.setId(2L);
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
        transactionAccountDTO1.setId(null);
        assertThat(transactionAccountDTO1).isNotEqualTo(transactionAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionAccountMapper.fromId(null)).isNull();
    }
}
