package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AccountingTransaction;
import io.github.prepayments.repository.AccountingTransactionRepository;
import io.github.prepayments.service.AccountingTransactionService;
import io.github.prepayments.service.dto.AccountingTransactionDTO;
import io.github.prepayments.service.mapper.AccountingTransactionMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AccountingTransactionCriteria;
import io.github.prepayments.service.AccountingTransactionQueryService;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.prepayments.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AccountingTransactionResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AccountingTransactionResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "060885505545392";
    private static final String UPDATED_ACCOUNT_NUMBER = "95822049838430";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_INCREMENT_ACCOUNT = false;
    private static final Boolean UPDATED_INCREMENT_ACCOUNT = true;

    @Autowired
    private AccountingTransactionRepository accountingTransactionRepository;

    @Autowired
    private AccountingTransactionMapper accountingTransactionMapper;

    @Autowired
    private AccountingTransactionService accountingTransactionService;

    @Autowired
    private AccountingTransactionQueryService accountingTransactionQueryService;

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

    private MockMvc restAccountingTransactionMockMvc;

    private AccountingTransaction accountingTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccountingTransactionResource accountingTransactionResource = new AccountingTransactionResource(accountingTransactionService, accountingTransactionQueryService);
        this.restAccountingTransactionMockMvc = MockMvcBuilders.standaloneSetup(accountingTransactionResource)
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
    public static AccountingTransaction createEntity(EntityManager em) {
        AccountingTransaction accountingTransaction = new AccountingTransaction()
            .accountName(DEFAULT_ACCOUNT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .incrementAccount(DEFAULT_INCREMENT_ACCOUNT);
        return accountingTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccountingTransaction createUpdatedEntity(EntityManager em) {
        AccountingTransaction accountingTransaction = new AccountingTransaction()
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .incrementAccount(UPDATED_INCREMENT_ACCOUNT);
        return accountingTransaction;
    }

    @BeforeEach
    public void initTest() {
        accountingTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccountingTransaction() throws Exception {
        int databaseSizeBeforeCreate = accountingTransactionRepository.findAll().size();

        // Create the AccountingTransaction
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);
        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the AccountingTransaction in the database
        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        AccountingTransaction testAccountingTransaction = accountingTransactionList.get(accountingTransactionList.size() - 1);
        assertThat(testAccountingTransaction.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testAccountingTransaction.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testAccountingTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testAccountingTransaction.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testAccountingTransaction.isIncrementAccount()).isEqualTo(DEFAULT_INCREMENT_ACCOUNT);
    }

    @Test
    @Transactional
    public void createAccountingTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accountingTransactionRepository.findAll().size();

        // Create the AccountingTransaction with an existing ID
        accountingTransaction.setId(1L);
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountingTransaction in the database
        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingTransactionRepository.findAll().size();
        // set the field null
        accountingTransaction.setAccountName(null);

        // Create the AccountingTransaction, which fails.
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingTransactionRepository.findAll().size();
        // set the field null
        accountingTransaction.setAccountNumber(null);

        // Create the AccountingTransaction, which fails.
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingTransactionRepository.findAll().size();
        // set the field null
        accountingTransaction.setTransactionDate(null);

        // Create the AccountingTransaction, which fails.
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingTransactionRepository.findAll().size();
        // set the field null
        accountingTransaction.setTransactionAmount(null);

        // Create the AccountingTransaction, which fails.
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIncrementAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountingTransactionRepository.findAll().size();
        // set the field null
        accountingTransaction.setIncrementAccount(null);

        // Create the AccountingTransaction, which fails.
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        restAccountingTransactionMockMvc.perform(post("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactions() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountingTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME.toString())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].incrementAccount").value(hasItem(DEFAULT_INCREMENT_ACCOUNT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAccountingTransaction() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get the accountingTransaction
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions/{id}", accountingTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accountingTransaction.getId().intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME.toString()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.incrementAccount").value(DEFAULT_INCREMENT_ACCOUNT.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountName equals to DEFAULT_ACCOUNT_NAME
        defaultAccountingTransactionShouldBeFound("accountName.equals=" + DEFAULT_ACCOUNT_NAME);

        // Get all the accountingTransactionList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAccountingTransactionShouldNotBeFound("accountName.equals=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNameIsInShouldWork() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountName in DEFAULT_ACCOUNT_NAME or UPDATED_ACCOUNT_NAME
        defaultAccountingTransactionShouldBeFound("accountName.in=" + DEFAULT_ACCOUNT_NAME + "," + UPDATED_ACCOUNT_NAME);

        // Get all the accountingTransactionList where accountName equals to UPDATED_ACCOUNT_NAME
        defaultAccountingTransactionShouldNotBeFound("accountName.in=" + UPDATED_ACCOUNT_NAME);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountName is not null
        defaultAccountingTransactionShouldBeFound("accountName.specified=true");

        // Get all the accountingTransactionList where accountName is null
        defaultAccountingTransactionShouldNotBeFound("accountName.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultAccountingTransactionShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the accountingTransactionList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultAccountingTransactionShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultAccountingTransactionShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the accountingTransactionList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultAccountingTransactionShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where accountNumber is not null
        defaultAccountingTransactionShouldBeFound("accountNumber.specified=true");

        // Get all the accountingTransactionList where accountNumber is null
        defaultAccountingTransactionShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultAccountingTransactionShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the accountingTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultAccountingTransactionShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultAccountingTransactionShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the accountingTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultAccountingTransactionShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionDate is not null
        defaultAccountingTransactionShouldBeFound("transactionDate.specified=true");

        // Get all the accountingTransactionList where transactionDate is null
        defaultAccountingTransactionShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionDate greater than or equals to DEFAULT_TRANSACTION_DATE
        defaultAccountingTransactionShouldBeFound("transactionDate.greaterOrEqualThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the accountingTransactionList where transactionDate greater than or equals to UPDATED_TRANSACTION_DATE
        defaultAccountingTransactionShouldNotBeFound("transactionDate.greaterOrEqualThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionDate less than or equals to DEFAULT_TRANSACTION_DATE
        defaultAccountingTransactionShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the accountingTransactionList where transactionDate less than or equals to UPDATED_TRANSACTION_DATE
        defaultAccountingTransactionShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultAccountingTransactionShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the accountingTransactionList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultAccountingTransactionShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultAccountingTransactionShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the accountingTransactionList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultAccountingTransactionShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where transactionAmount is not null
        defaultAccountingTransactionShouldBeFound("transactionAmount.specified=true");

        // Get all the accountingTransactionList where transactionAmount is null
        defaultAccountingTransactionShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByIncrementAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where incrementAccount equals to DEFAULT_INCREMENT_ACCOUNT
        defaultAccountingTransactionShouldBeFound("incrementAccount.equals=" + DEFAULT_INCREMENT_ACCOUNT);

        // Get all the accountingTransactionList where incrementAccount equals to UPDATED_INCREMENT_ACCOUNT
        defaultAccountingTransactionShouldNotBeFound("incrementAccount.equals=" + UPDATED_INCREMENT_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByIncrementAccountIsInShouldWork() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where incrementAccount in DEFAULT_INCREMENT_ACCOUNT or UPDATED_INCREMENT_ACCOUNT
        defaultAccountingTransactionShouldBeFound("incrementAccount.in=" + DEFAULT_INCREMENT_ACCOUNT + "," + UPDATED_INCREMENT_ACCOUNT);

        // Get all the accountingTransactionList where incrementAccount equals to UPDATED_INCREMENT_ACCOUNT
        defaultAccountingTransactionShouldNotBeFound("incrementAccount.in=" + UPDATED_INCREMENT_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllAccountingTransactionsByIncrementAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        // Get all the accountingTransactionList where incrementAccount is not null
        defaultAccountingTransactionShouldBeFound("incrementAccount.specified=true");

        // Get all the accountingTransactionList where incrementAccount is null
        defaultAccountingTransactionShouldNotBeFound("incrementAccount.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountingTransactionShouldBeFound(String filter) throws Exception {
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accountingTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].incrementAccount").value(hasItem(DEFAULT_INCREMENT_ACCOUNT.booleanValue())));

        // Check, that the count call also returns 1
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountingTransactionShouldNotBeFound(String filter) throws Exception {
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAccountingTransaction() throws Exception {
        // Get the accountingTransaction
        restAccountingTransactionMockMvc.perform(get("/api/accounting-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccountingTransaction() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        int databaseSizeBeforeUpdate = accountingTransactionRepository.findAll().size();

        // Update the accountingTransaction
        AccountingTransaction updatedAccountingTransaction = accountingTransactionRepository.findById(accountingTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedAccountingTransaction are not directly saved in db
        em.detach(updatedAccountingTransaction);
        updatedAccountingTransaction
            .accountName(UPDATED_ACCOUNT_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .incrementAccount(UPDATED_INCREMENT_ACCOUNT);
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(updatedAccountingTransaction);

        restAccountingTransactionMockMvc.perform(put("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the AccountingTransaction in the database
        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeUpdate);
        AccountingTransaction testAccountingTransaction = accountingTransactionList.get(accountingTransactionList.size() - 1);
        assertThat(testAccountingTransaction.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testAccountingTransaction.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testAccountingTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testAccountingTransaction.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testAccountingTransaction.isIncrementAccount()).isEqualTo(UPDATED_INCREMENT_ACCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingAccountingTransaction() throws Exception {
        int databaseSizeBeforeUpdate = accountingTransactionRepository.findAll().size();

        // Create the AccountingTransaction
        AccountingTransactionDTO accountingTransactionDTO = accountingTransactionMapper.toDto(accountingTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountingTransactionMockMvc.perform(put("/api/accounting-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accountingTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccountingTransaction in the database
        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccountingTransaction() throws Exception {
        // Initialize the database
        accountingTransactionRepository.saveAndFlush(accountingTransaction);

        int databaseSizeBeforeDelete = accountingTransactionRepository.findAll().size();

        // Delete the accountingTransaction
        restAccountingTransactionMockMvc.perform(delete("/api/accounting-transactions/{id}", accountingTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AccountingTransaction> accountingTransactionList = accountingTransactionRepository.findAll();
        assertThat(accountingTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountingTransaction.class);
        AccountingTransaction accountingTransaction1 = new AccountingTransaction();
        accountingTransaction1.setId(1L);
        AccountingTransaction accountingTransaction2 = new AccountingTransaction();
        accountingTransaction2.setId(accountingTransaction1.getId());
        assertThat(accountingTransaction1).isEqualTo(accountingTransaction2);
        accountingTransaction2.setId(2L);
        assertThat(accountingTransaction1).isNotEqualTo(accountingTransaction2);
        accountingTransaction1.setId(null);
        assertThat(accountingTransaction1).isNotEqualTo(accountingTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccountingTransactionDTO.class);
        AccountingTransactionDTO accountingTransactionDTO1 = new AccountingTransactionDTO();
        accountingTransactionDTO1.setId(1L);
        AccountingTransactionDTO accountingTransactionDTO2 = new AccountingTransactionDTO();
        assertThat(accountingTransactionDTO1).isNotEqualTo(accountingTransactionDTO2);
        accountingTransactionDTO2.setId(accountingTransactionDTO1.getId());
        assertThat(accountingTransactionDTO1).isEqualTo(accountingTransactionDTO2);
        accountingTransactionDTO2.setId(2L);
        assertThat(accountingTransactionDTO1).isNotEqualTo(accountingTransactionDTO2);
        accountingTransactionDTO1.setId(null);
        assertThat(accountingTransactionDTO1).isNotEqualTo(accountingTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(accountingTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(accountingTransactionMapper.fromId(null)).isNull();
    }
}
