package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.app.messaging.services.notifications.AmortizationDataFileMessageService;
import io.github.prepayments.app.messaging.services.notifications.TransactionAccountDataFileMessageService;
import io.github.prepayments.domain.AmortizationDataEntryFile;
import io.github.prepayments.repository.AmortizationDataEntryFileRepository;
import io.github.prepayments.service.AmortizationDataEntryFileService;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.service.mapper.AmortizationDataEntryFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.AmortizationDataEntryFileQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
 * Integration tests for the {@Link AmortizationDataEntryFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationDataEntryFileResourceIT {

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
    private AmortizationDataEntryFileRepository amortizationDataEntryFileRepository;

    @Autowired
    private AmortizationDataEntryFileMapper amortizationDataEntryFileMapper;

    @Autowired
    private AmortizationDataEntryFileService amortizationDataEntryFileService;

    @Autowired
    private AmortizationDataEntryFileQueryService amortizationDataEntryFileQueryService;

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

    private MockMvc restAmortizationDataEntryFileMockMvc;

    private AmortizationDataEntryFile amortizationDataEntryFile;

    @Mock private AmortizationDataFileMessageService amortizationDataFileMessageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationDataEntryFileResource amortizationDataEntryFileResource = new AmortizationDataEntryFileResource(amortizationDataEntryFileService, amortizationDataEntryFileQueryService,
                                                                                                                          amortizationDataFileMessageService);
        this.restAmortizationDataEntryFileMockMvc = MockMvcBuilders.standaloneSetup(amortizationDataEntryFileResource)
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
    public static AmortizationDataEntryFile createEntity(EntityManager em) {
        AmortizationDataEntryFile amortizationDataEntryFile = new AmortizationDataEntryFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED);
        return amortizationDataEntryFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationDataEntryFile createUpdatedEntity(EntityManager em) {
        AmortizationDataEntryFile amortizationDataEntryFile = new AmortizationDataEntryFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        return amortizationDataEntryFile;
    }

    @BeforeEach
    public void initTest() {
        amortizationDataEntryFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationDataEntryFile() throws Exception {
        int databaseSizeBeforeCreate = amortizationDataEntryFileRepository.findAll().size();

        // Create the AmortizationDataEntryFile
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);
        restAmortizationDataEntryFileMockMvc.perform(post("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationDataEntryFile in the database
        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationDataEntryFile testAmortizationDataEntryFile = amortizationDataEntryFileList.get(amortizationDataEntryFileList.size() - 1);
        assertThat(testAmortizationDataEntryFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testAmortizationDataEntryFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testAmortizationDataEntryFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testAmortizationDataEntryFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationDataEntryFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationDataEntryFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void createAmortizationDataEntryFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationDataEntryFileRepository.findAll().size();

        // Create the AmortizationDataEntryFile with an existing ID
        amortizationDataEntryFile.setId(1L);
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationDataEntryFileMockMvc.perform(post("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDataEntryFile in the database
        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationDataEntryFileRepository.findAll().size();
        // set the field null
        amortizationDataEntryFile.setPeriodFrom(null);

        // Create the AmortizationDataEntryFile, which fails.
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);

        restAmortizationDataEntryFileMockMvc.perform(post("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationDataEntryFileRepository.findAll().size();
        // set the field null
        amortizationDataEntryFile.setPeriodTo(null);

        // Create the AmortizationDataEntryFile, which fails.
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);

        restAmortizationDataEntryFileMockMvc.perform(post("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFiles() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAmortizationDataEntryFile() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get the amortizationDataEntryFile
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files/{id}", amortizationDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationDataEntryFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the amortizationDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodFrom is not null
        defaultAmortizationDataEntryFileShouldBeFound("periodFrom.specified=true");

        // Get all the amortizationDataEntryFileList where periodFrom is null
        defaultAmortizationDataEntryFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationDataEntryFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationDataEntryFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultAmortizationDataEntryFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultAmortizationDataEntryFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultAmortizationDataEntryFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultAmortizationDataEntryFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the amortizationDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultAmortizationDataEntryFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodTo is not null
        defaultAmortizationDataEntryFileShouldBeFound("periodTo.specified=true");

        // Get all the amortizationDataEntryFileList where periodTo is null
        defaultAmortizationDataEntryFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultAmortizationDataEntryFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationDataEntryFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultAmortizationDataEntryFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultAmortizationDataEntryFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationDataEntryFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultAmortizationDataEntryFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultAmortizationDataEntryFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the amortizationDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationDataEntryFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the amortizationDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadSuccessful is not null
        defaultAmortizationDataEntryFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the amortizationDataEntryFileList where uploadSuccessful is null
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultAmortizationDataEntryFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the amortizationDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultAmortizationDataEntryFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the amortizationDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationDataEntryFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        // Get all the amortizationDataEntryFileList where uploadProcessed is not null
        defaultAmortizationDataEntryFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the amortizationDataEntryFileList where uploadProcessed is null
        defaultAmortizationDataEntryFileShouldNotBeFound("uploadProcessed.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationDataEntryFileShouldBeFound(String filter) throws Exception {
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationDataEntryFileShouldNotBeFound(String filter) throws Exception {
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationDataEntryFile() throws Exception {
        // Get the amortizationDataEntryFile
        restAmortizationDataEntryFileMockMvc.perform(get("/api/amortization-data-entry-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationDataEntryFile() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        int databaseSizeBeforeUpdate = amortizationDataEntryFileRepository.findAll().size();

        // Update the amortizationDataEntryFile
        AmortizationDataEntryFile updatedAmortizationDataEntryFile = amortizationDataEntryFileRepository.findById(amortizationDataEntryFile.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationDataEntryFile are not directly saved in db
        em.detach(updatedAmortizationDataEntryFile);
        updatedAmortizationDataEntryFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(updatedAmortizationDataEntryFile);

        restAmortizationDataEntryFileMockMvc.perform(put("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationDataEntryFile in the database
        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
        AmortizationDataEntryFile testAmortizationDataEntryFile = amortizationDataEntryFileList.get(amortizationDataEntryFileList.size() - 1);
        assertThat(testAmortizationDataEntryFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testAmortizationDataEntryFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testAmortizationDataEntryFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testAmortizationDataEntryFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationDataEntryFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationDataEntryFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationDataEntryFile() throws Exception {
        int databaseSizeBeforeUpdate = amortizationDataEntryFileRepository.findAll().size();

        // Create the AmortizationDataEntryFile
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO = amortizationDataEntryFileMapper.toDto(amortizationDataEntryFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationDataEntryFileMockMvc.perform(put("/api/amortization-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationDataEntryFile in the database
        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAmortizationDataEntryFile() throws Exception {
        // Initialize the database
        amortizationDataEntryFileRepository.saveAndFlush(amortizationDataEntryFile);

        int databaseSizeBeforeDelete = amortizationDataEntryFileRepository.findAll().size();

        // Delete the amortizationDataEntryFile
        restAmortizationDataEntryFileMockMvc.perform(delete("/api/amortization-data-entry-files/{id}", amortizationDataEntryFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationDataEntryFile> amortizationDataEntryFileList = amortizationDataEntryFileRepository.findAll();
        assertThat(amortizationDataEntryFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationDataEntryFile.class);
        AmortizationDataEntryFile amortizationDataEntryFile1 = new AmortizationDataEntryFile();
        amortizationDataEntryFile1.setId(1L);
        AmortizationDataEntryFile amortizationDataEntryFile2 = new AmortizationDataEntryFile();
        amortizationDataEntryFile2.setId(amortizationDataEntryFile1.getId());
        assertThat(amortizationDataEntryFile1).isEqualTo(amortizationDataEntryFile2);
        amortizationDataEntryFile2.setId(2L);
        assertThat(amortizationDataEntryFile1).isNotEqualTo(amortizationDataEntryFile2);
        amortizationDataEntryFile1.setId(null);
        assertThat(amortizationDataEntryFile1).isNotEqualTo(amortizationDataEntryFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationDataEntryFileDTO.class);
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO1 = new AmortizationDataEntryFileDTO();
        amortizationDataEntryFileDTO1.setId(1L);
        AmortizationDataEntryFileDTO amortizationDataEntryFileDTO2 = new AmortizationDataEntryFileDTO();
        assertThat(amortizationDataEntryFileDTO1).isNotEqualTo(amortizationDataEntryFileDTO2);
        amortizationDataEntryFileDTO2.setId(amortizationDataEntryFileDTO1.getId());
        assertThat(amortizationDataEntryFileDTO1).isEqualTo(amortizationDataEntryFileDTO2);
        amortizationDataEntryFileDTO2.setId(2L);
        assertThat(amortizationDataEntryFileDTO1).isNotEqualTo(amortizationDataEntryFileDTO2);
        amortizationDataEntryFileDTO1.setId(null);
        assertThat(amortizationDataEntryFileDTO1).isNotEqualTo(amortizationDataEntryFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationDataEntryFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationDataEntryFileMapper.fromId(null)).isNull();
    }
}
