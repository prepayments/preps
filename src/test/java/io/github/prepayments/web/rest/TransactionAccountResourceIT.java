package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.TransactionAccount;
import io.github.prepayments.repository.TransactionAccountRepository;
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
 * Integration tests for the {@Link TransactionAccountResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class TransactionAccountResourceIT {

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "0835839974";
    private static final String UPDATED_ACCOUNT_NUMBER = "9770114069195299";

    private static final BigDecimal DEFAULT_ACCOUNT_BALANCE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNT_BALANCE = new BigDecimal(2);

    private static final LocalDate DEFAULT_OPENING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_OPENING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ACCOUNT_OPENING_DATE_BALANCE = new BigDecimal(0);
    private static final BigDecimal UPDATED_ACCOUNT_OPENING_DATE_BALANCE = new BigDecimal(1);

    @Autowired
    private TransactionAccountRepository transactionAccountRepository;

    @Autowired
    private TransactionAccountMapper transactionAccountMapper;

    @Autowired
    private TransactionAccountService transactionAccountService;

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
            .accountBalance(DEFAULT_ACCOUNT_BALANCE)
            .openingDate(DEFAULT_OPENING_DATE)
            .accountOpeningDateBalance(DEFAULT_ACCOUNT_OPENING_DATE_BALANCE);
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
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .openingDate(UPDATED_OPENING_DATE)
            .accountOpeningDateBalance(UPDATED_ACCOUNT_OPENING_DATE_BALANCE);
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
        assertThat(testTransactionAccount.getAccountBalance()).isEqualTo(DEFAULT_ACCOUNT_BALANCE);
        assertThat(testTransactionAccount.getOpeningDate()).isEqualTo(DEFAULT_OPENING_DATE);
        assertThat(testTransactionAccount.getAccountOpeningDateBalance()).isEqualTo(DEFAULT_ACCOUNT_OPENING_DATE_BALANCE);
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
    public void checkOpeningDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountRepository.findAll().size();
        // set the field null
        transactionAccount.setOpeningDate(null);

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
    public void checkAccountOpeningDateBalanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountRepository.findAll().size();
        // set the field null
        transactionAccount.setAccountOpeningDateBalance(null);

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
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountOpeningDateBalance").value(hasItem(DEFAULT_ACCOUNT_OPENING_DATE_BALANCE.intValue())));
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
            .andExpect(jsonPath("$.accountBalance").value(DEFAULT_ACCOUNT_BALANCE.intValue()))
            .andExpect(jsonPath("$.openingDate").value(DEFAULT_OPENING_DATE.toString()))
            .andExpect(jsonPath("$.accountOpeningDateBalance").value(DEFAULT_ACCOUNT_OPENING_DATE_BALANCE.intValue()));
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
    public void getAllTransactionAccountsByAccountBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountBalance equals to DEFAULT_ACCOUNT_BALANCE
        defaultTransactionAccountShouldBeFound("accountBalance.equals=" + DEFAULT_ACCOUNT_BALANCE);

        // Get all the transactionAccountList where accountBalance equals to UPDATED_ACCOUNT_BALANCE
        defaultTransactionAccountShouldNotBeFound("accountBalance.equals=" + UPDATED_ACCOUNT_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountBalance in DEFAULT_ACCOUNT_BALANCE or UPDATED_ACCOUNT_BALANCE
        defaultTransactionAccountShouldBeFound("accountBalance.in=" + DEFAULT_ACCOUNT_BALANCE + "," + UPDATED_ACCOUNT_BALANCE);

        // Get all the transactionAccountList where accountBalance equals to UPDATED_ACCOUNT_BALANCE
        defaultTransactionAccountShouldNotBeFound("accountBalance.in=" + UPDATED_ACCOUNT_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountBalance is not null
        defaultTransactionAccountShouldBeFound("accountBalance.specified=true");

        // Get all the transactionAccountList where accountBalance is null
        defaultTransactionAccountShouldNotBeFound("accountBalance.specified=false");
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
    public void getAllTransactionAccountsByAccountOpeningDateBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountOpeningDateBalance equals to DEFAULT_ACCOUNT_OPENING_DATE_BALANCE
        defaultTransactionAccountShouldBeFound("accountOpeningDateBalance.equals=" + DEFAULT_ACCOUNT_OPENING_DATE_BALANCE);

        // Get all the transactionAccountList where accountOpeningDateBalance equals to UPDATED_ACCOUNT_OPENING_DATE_BALANCE
        defaultTransactionAccountShouldNotBeFound("accountOpeningDateBalance.equals=" + UPDATED_ACCOUNT_OPENING_DATE_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountOpeningDateBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountOpeningDateBalance in DEFAULT_ACCOUNT_OPENING_DATE_BALANCE or UPDATED_ACCOUNT_OPENING_DATE_BALANCE
        defaultTransactionAccountShouldBeFound("accountOpeningDateBalance.in=" + DEFAULT_ACCOUNT_OPENING_DATE_BALANCE + "," + UPDATED_ACCOUNT_OPENING_DATE_BALANCE);

        // Get all the transactionAccountList where accountOpeningDateBalance equals to UPDATED_ACCOUNT_OPENING_DATE_BALANCE
        defaultTransactionAccountShouldNotBeFound("accountOpeningDateBalance.in=" + UPDATED_ACCOUNT_OPENING_DATE_BALANCE);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountsByAccountOpeningDateBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountRepository.saveAndFlush(transactionAccount);

        // Get all the transactionAccountList where accountOpeningDateBalance is not null
        defaultTransactionAccountShouldBeFound("accountOpeningDateBalance.specified=true");

        // Get all the transactionAccountList where accountOpeningDateBalance is null
        defaultTransactionAccountShouldNotBeFound("accountOpeningDateBalance.specified=false");
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
            .andExpect(jsonPath("$.[*].accountBalance").value(hasItem(DEFAULT_ACCOUNT_BALANCE.intValue())))
            .andExpect(jsonPath("$.[*].openingDate").value(hasItem(DEFAULT_OPENING_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountOpeningDateBalance").value(hasItem(DEFAULT_ACCOUNT_OPENING_DATE_BALANCE.intValue())));

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
            .accountBalance(UPDATED_ACCOUNT_BALANCE)
            .openingDate(UPDATED_OPENING_DATE)
            .accountOpeningDateBalance(UPDATED_ACCOUNT_OPENING_DATE_BALANCE);
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
        assertThat(testTransactionAccount.getAccountBalance()).isEqualTo(UPDATED_ACCOUNT_BALANCE);
        assertThat(testTransactionAccount.getOpeningDate()).isEqualTo(UPDATED_OPENING_DATE);
        assertThat(testTransactionAccount.getAccountOpeningDateBalance()).isEqualTo(UPDATED_ACCOUNT_OPENING_DATE_BALANCE);
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
