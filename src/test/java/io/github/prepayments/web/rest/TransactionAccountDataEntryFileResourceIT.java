package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.TransactionAccountDataEntryFile;
import io.github.prepayments.repository.TransactionAccountDataEntryFileRepository;
import io.github.prepayments.service.TransactionAccountDataEntryFileService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import io.github.prepayments.service.mapper.TransactionAccountDataEntryFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.TransactionAccountDataEntryFileQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.prepayments.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TransactionAccountDataEntryFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class TransactionAccountDataEntryFileResourceIT {

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    @Autowired
    private TransactionAccountDataEntryFileRepository transactionAccountDataEntryFileRepository;

    @Autowired
    private TransactionAccountDataEntryFileMapper transactionAccountDataEntryFileMapper;

    @Autowired
    private TransactionAccountDataEntryFileService transactionAccountDataEntryFileService;

    @Autowired
    private TransactionAccountDataEntryFileQueryService transactionAccountDataEntryFileQueryService;

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

    private MockMvc restTransactionAccountDataEntryFileMockMvc;

    private TransactionAccountDataEntryFile transactionAccountDataEntryFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionAccountDataEntryFileResource transactionAccountDataEntryFileResource = new TransactionAccountDataEntryFileResource(transactionAccountDataEntryFileService, transactionAccountDataEntryFileQueryService);
        this.restTransactionAccountDataEntryFileMockMvc = MockMvcBuilders.standaloneSetup(transactionAccountDataEntryFileResource)
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
    public static TransactionAccountDataEntryFile createEntity(EntityManager em) {
        TransactionAccountDataEntryFile transactionAccountDataEntryFile = new TransactionAccountDataEntryFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED);
        return transactionAccountDataEntryFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionAccountDataEntryFile createUpdatedEntity(EntityManager em) {
        TransactionAccountDataEntryFile transactionAccountDataEntryFile = new TransactionAccountDataEntryFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        return transactionAccountDataEntryFile;
    }

    @BeforeEach
    public void initTest() {
        transactionAccountDataEntryFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionAccountDataEntryFile() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountDataEntryFileRepository.findAll().size();

        // Create the TransactionAccountDataEntryFile
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);
        restTransactionAccountDataEntryFileMockMvc.perform(post("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionAccountDataEntryFile in the database
        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionAccountDataEntryFile testTransactionAccountDataEntryFile = transactionAccountDataEntryFileList.get(transactionAccountDataEntryFileList.size() - 1);
        assertThat(testTransactionAccountDataEntryFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testTransactionAccountDataEntryFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testTransactionAccountDataEntryFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testTransactionAccountDataEntryFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testTransactionAccountDataEntryFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testTransactionAccountDataEntryFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void createTransactionAccountDataEntryFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionAccountDataEntryFileRepository.findAll().size();

        // Create the TransactionAccountDataEntryFile with an existing ID
        transactionAccountDataEntryFile.setId(1L);
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionAccountDataEntryFileMockMvc.perform(post("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAccountDataEntryFile in the database
        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountDataEntryFileRepository.findAll().size();
        // set the field null
        transactionAccountDataEntryFile.setPeriodFrom(null);

        // Create the TransactionAccountDataEntryFile, which fails.
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);

        restTransactionAccountDataEntryFileMockMvc.perform(post("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAccountDataEntryFileRepository.findAll().size();
        // set the field null
        transactionAccountDataEntryFile.setPeriodTo(null);

        // Create the TransactionAccountDataEntryFile, which fails.
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);

        restTransactionAccountDataEntryFileMockMvc.perform(post("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFiles() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccountDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTransactionAccountDataEntryFile() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get the transactionAccountDataEntryFile
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files/{id}", transactionAccountDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionAccountDataEntryFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the transactionAccountDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the transactionAccountDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodFrom is not null
        defaultTransactionAccountDataEntryFileShouldBeFound("periodFrom.specified=true");

        // Get all the transactionAccountDataEntryFileList where periodFrom is null
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the transactionAccountDataEntryFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the transactionAccountDataEntryFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultTransactionAccountDataEntryFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the transactionAccountDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the transactionAccountDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodTo is not null
        defaultTransactionAccountDataEntryFileShouldBeFound("periodTo.specified=true");

        // Get all the transactionAccountDataEntryFileList where periodTo is null
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the transactionAccountDataEntryFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the transactionAccountDataEntryFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultTransactionAccountDataEntryFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful is not null
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the transactionAccountDataEntryFileList where uploadSuccessful is null
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the transactionAccountDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the transactionAccountDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllTransactionAccountDataEntryFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        // Get all the transactionAccountDataEntryFileList where uploadProcessed is not null
        defaultTransactionAccountDataEntryFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the transactionAccountDataEntryFileList where uploadProcessed is null
        defaultTransactionAccountDataEntryFileShouldNotBeFound("uploadProcessed.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionAccountDataEntryFileShouldBeFound(String filter) throws Exception {
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAccountDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionAccountDataEntryFileShouldNotBeFound(String filter) throws Exception {
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionAccountDataEntryFile() throws Exception {
        // Get the transactionAccountDataEntryFile
        restTransactionAccountDataEntryFileMockMvc.perform(get("/api/transaction-account-data-entry-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionAccountDataEntryFile() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        int databaseSizeBeforeUpdate = transactionAccountDataEntryFileRepository.findAll().size();

        // Update the transactionAccountDataEntryFile
        TransactionAccountDataEntryFile updatedTransactionAccountDataEntryFile = transactionAccountDataEntryFileRepository.findById(transactionAccountDataEntryFile.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionAccountDataEntryFile are not directly saved in db
        em.detach(updatedTransactionAccountDataEntryFile);
        updatedTransactionAccountDataEntryFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(updatedTransactionAccountDataEntryFile);

        restTransactionAccountDataEntryFileMockMvc.perform(put("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionAccountDataEntryFile in the database
        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
        TransactionAccountDataEntryFile testTransactionAccountDataEntryFile = transactionAccountDataEntryFileList.get(transactionAccountDataEntryFileList.size() - 1);
        assertThat(testTransactionAccountDataEntryFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testTransactionAccountDataEntryFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testTransactionAccountDataEntryFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testTransactionAccountDataEntryFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testTransactionAccountDataEntryFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testTransactionAccountDataEntryFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionAccountDataEntryFile() throws Exception {
        int databaseSizeBeforeUpdate = transactionAccountDataEntryFileRepository.findAll().size();

        // Create the TransactionAccountDataEntryFile
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO = transactionAccountDataEntryFileMapper.toDto(transactionAccountDataEntryFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionAccountDataEntryFileMockMvc.perform(put("/api/transaction-account-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionAccountDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionAccountDataEntryFile in the database
        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTransactionAccountDataEntryFile() throws Exception {
        // Initialize the database
        transactionAccountDataEntryFileRepository.saveAndFlush(transactionAccountDataEntryFile);

        int databaseSizeBeforeDelete = transactionAccountDataEntryFileRepository.findAll().size();

        // Delete the transactionAccountDataEntryFile
        restTransactionAccountDataEntryFileMockMvc.perform(delete("/api/transaction-account-data-entry-files/{id}", transactionAccountDataEntryFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TransactionAccountDataEntryFile> transactionAccountDataEntryFileList = transactionAccountDataEntryFileRepository.findAll();
        assertThat(transactionAccountDataEntryFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccountDataEntryFile.class);
        TransactionAccountDataEntryFile transactionAccountDataEntryFile1 = new TransactionAccountDataEntryFile();
        transactionAccountDataEntryFile1.setId(1L);
        TransactionAccountDataEntryFile transactionAccountDataEntryFile2 = new TransactionAccountDataEntryFile();
        transactionAccountDataEntryFile2.setId(transactionAccountDataEntryFile1.getId());
        assertThat(transactionAccountDataEntryFile1).isEqualTo(transactionAccountDataEntryFile2);
        transactionAccountDataEntryFile2.setId(2L);
        assertThat(transactionAccountDataEntryFile1).isNotEqualTo(transactionAccountDataEntryFile2);
        transactionAccountDataEntryFile1.setId(null);
        assertThat(transactionAccountDataEntryFile1).isNotEqualTo(transactionAccountDataEntryFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionAccountDataEntryFileDTO.class);
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO1 = new TransactionAccountDataEntryFileDTO();
        transactionAccountDataEntryFileDTO1.setId(1L);
        TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO2 = new TransactionAccountDataEntryFileDTO();
        assertThat(transactionAccountDataEntryFileDTO1).isNotEqualTo(transactionAccountDataEntryFileDTO2);
        transactionAccountDataEntryFileDTO2.setId(transactionAccountDataEntryFileDTO1.getId());
        assertThat(transactionAccountDataEntryFileDTO1).isEqualTo(transactionAccountDataEntryFileDTO2);
        transactionAccountDataEntryFileDTO2.setId(2L);
        assertThat(transactionAccountDataEntryFileDTO1).isNotEqualTo(transactionAccountDataEntryFileDTO2);
        transactionAccountDataEntryFileDTO1.setId(null);
        assertThat(transactionAccountDataEntryFileDTO1).isNotEqualTo(transactionAccountDataEntryFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionAccountDataEntryFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionAccountDataEntryFileMapper.fromId(null)).isNull();
    }
}
