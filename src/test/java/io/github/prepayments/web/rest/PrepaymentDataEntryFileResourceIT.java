package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.PrepaymentDataEntryFile;
import io.github.prepayments.repository.PrepaymentDataEntryFileRepository;
import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.service.mapper.PrepaymentDataEntryFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.PrepaymentDataEntryFileQueryService;

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
 * Integration tests for the {@Link PrepaymentDataEntryFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class PrepaymentDataEntryFileResourceIT {

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    @Autowired
    private PrepaymentDataEntryFileRepository prepaymentDataEntryFileRepository;

    @Autowired
    private PrepaymentDataEntryFileMapper prepaymentDataEntryFileMapper;

    @Autowired
    private PrepaymentDataEntryFileService prepaymentDataEntryFileService;

    @Autowired
    private PrepaymentDataEntryFileQueryService prepaymentDataEntryFileQueryService;

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

    private MockMvc restPrepaymentDataEntryFileMockMvc;

    private PrepaymentDataEntryFile prepaymentDataEntryFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrepaymentDataEntryFileResource prepaymentDataEntryFileResource = new PrepaymentDataEntryFileResource(prepaymentDataEntryFileService, prepaymentDataEntryFileQueryService,
                                                                                                                    prepaymentDataFileMessageService);
        this.restPrepaymentDataEntryFileMockMvc = MockMvcBuilders.standaloneSetup(prepaymentDataEntryFileResource)
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
    public static PrepaymentDataEntryFile createEntity(EntityManager em) {
        PrepaymentDataEntryFile prepaymentDataEntryFile = new PrepaymentDataEntryFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL);
        return prepaymentDataEntryFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrepaymentDataEntryFile createUpdatedEntity(EntityManager em) {
        PrepaymentDataEntryFile prepaymentDataEntryFile = new PrepaymentDataEntryFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL);
        return prepaymentDataEntryFile;
    }

    @BeforeEach
    public void initTest() {
        prepaymentDataEntryFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrepaymentDataEntryFile() throws Exception {
        int databaseSizeBeforeCreate = prepaymentDataEntryFileRepository.findAll().size();

        // Create the PrepaymentDataEntryFile
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);
        restPrepaymentDataEntryFileMockMvc.perform(post("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isCreated());

        // Validate the PrepaymentDataEntryFile in the database
        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeCreate + 1);
        PrepaymentDataEntryFile testPrepaymentDataEntryFile = prepaymentDataEntryFileList.get(prepaymentDataEntryFileList.size() - 1);
        assertThat(testPrepaymentDataEntryFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testPrepaymentDataEntryFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testPrepaymentDataEntryFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testPrepaymentDataEntryFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testPrepaymentDataEntryFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testPrepaymentDataEntryFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void createPrepaymentDataEntryFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prepaymentDataEntryFileRepository.findAll().size();

        // Create the PrepaymentDataEntryFile with an existing ID
        prepaymentDataEntryFile.setId(1L);
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrepaymentDataEntryFileMockMvc.perform(post("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrepaymentDataEntryFile in the database
        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentDataEntryFileRepository.findAll().size();
        // set the field null
        prepaymentDataEntryFile.setPeriodFrom(null);

        // Create the PrepaymentDataEntryFile, which fails.
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);

        restPrepaymentDataEntryFileMockMvc.perform(post("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = prepaymentDataEntryFileRepository.findAll().size();
        // set the field null
        prepaymentDataEntryFile.setPeriodTo(null);

        // Create the PrepaymentDataEntryFile, which fails.
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);

        restPrepaymentDataEntryFileMockMvc.perform(post("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFiles() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepaymentDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())));
    }

    @Test
    @Transactional
    public void getPrepaymentDataEntryFile() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get the prepaymentDataEntryFile
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files/{id}", prepaymentDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prepaymentDataEntryFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the prepaymentDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the prepaymentDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodFrom is not null
        defaultPrepaymentDataEntryFileShouldBeFound("periodFrom.specified=true");

        // Get all the prepaymentDataEntryFileList where periodFrom is null
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the prepaymentDataEntryFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the prepaymentDataEntryFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultPrepaymentDataEntryFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the prepaymentDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the prepaymentDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodTo is not null
        defaultPrepaymentDataEntryFileShouldBeFound("periodTo.specified=true");

        // Get all the prepaymentDataEntryFileList where periodTo is null
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the prepaymentDataEntryFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the prepaymentDataEntryFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultPrepaymentDataEntryFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultPrepaymentDataEntryFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the prepaymentDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultPrepaymentDataEntryFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the prepaymentDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadProcessed is not null
        defaultPrepaymentDataEntryFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the prepaymentDataEntryFileList where uploadProcessed is null
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultPrepaymentDataEntryFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the prepaymentDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultPrepaymentDataEntryFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the prepaymentDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllPrepaymentDataEntryFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        // Get all the prepaymentDataEntryFileList where uploadSuccessful is not null
        defaultPrepaymentDataEntryFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the prepaymentDataEntryFileList where uploadSuccessful is null
        defaultPrepaymentDataEntryFileShouldNotBeFound("uploadSuccessful.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrepaymentDataEntryFileShouldBeFound(String filter) throws Exception {
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prepaymentDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())));

        // Check, that the count call also returns 1
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrepaymentDataEntryFileShouldNotBeFound(String filter) throws Exception {
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrepaymentDataEntryFile() throws Exception {
        // Get the prepaymentDataEntryFile
        restPrepaymentDataEntryFileMockMvc.perform(get("/api/prepayment-data-entry-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrepaymentDataEntryFile() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        int databaseSizeBeforeUpdate = prepaymentDataEntryFileRepository.findAll().size();

        // Update the prepaymentDataEntryFile
        PrepaymentDataEntryFile updatedPrepaymentDataEntryFile = prepaymentDataEntryFileRepository.findById(prepaymentDataEntryFile.getId()).get();
        // Disconnect from session so that the updates on updatedPrepaymentDataEntryFile are not directly saved in db
        em.detach(updatedPrepaymentDataEntryFile);
        updatedPrepaymentDataEntryFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL);
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(updatedPrepaymentDataEntryFile);

        restPrepaymentDataEntryFileMockMvc.perform(put("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isOk());

        // Validate the PrepaymentDataEntryFile in the database
        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
        PrepaymentDataEntryFile testPrepaymentDataEntryFile = prepaymentDataEntryFileList.get(prepaymentDataEntryFileList.size() - 1);
        assertThat(testPrepaymentDataEntryFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testPrepaymentDataEntryFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testPrepaymentDataEntryFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testPrepaymentDataEntryFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testPrepaymentDataEntryFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testPrepaymentDataEntryFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void updateNonExistingPrepaymentDataEntryFile() throws Exception {
        int databaseSizeBeforeUpdate = prepaymentDataEntryFileRepository.findAll().size();

        // Create the PrepaymentDataEntryFile
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = prepaymentDataEntryFileMapper.toDto(prepaymentDataEntryFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrepaymentDataEntryFileMockMvc.perform(put("/api/prepayment-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prepaymentDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrepaymentDataEntryFile in the database
        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrepaymentDataEntryFile() throws Exception {
        // Initialize the database
        prepaymentDataEntryFileRepository.saveAndFlush(prepaymentDataEntryFile);

        int databaseSizeBeforeDelete = prepaymentDataEntryFileRepository.findAll().size();

        // Delete the prepaymentDataEntryFile
        restPrepaymentDataEntryFileMockMvc.perform(delete("/api/prepayment-data-entry-files/{id}", prepaymentDataEntryFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<PrepaymentDataEntryFile> prepaymentDataEntryFileList = prepaymentDataEntryFileRepository.findAll();
        assertThat(prepaymentDataEntryFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrepaymentDataEntryFile.class);
        PrepaymentDataEntryFile prepaymentDataEntryFile1 = new PrepaymentDataEntryFile();
        prepaymentDataEntryFile1.setId(1L);
        PrepaymentDataEntryFile prepaymentDataEntryFile2 = new PrepaymentDataEntryFile();
        prepaymentDataEntryFile2.setId(prepaymentDataEntryFile1.getId());
        assertThat(prepaymentDataEntryFile1).isEqualTo(prepaymentDataEntryFile2);
        prepaymentDataEntryFile2.setId(2L);
        assertThat(prepaymentDataEntryFile1).isNotEqualTo(prepaymentDataEntryFile2);
        prepaymentDataEntryFile1.setId(null);
        assertThat(prepaymentDataEntryFile1).isNotEqualTo(prepaymentDataEntryFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrepaymentDataEntryFileDTO.class);
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO1 = new PrepaymentDataEntryFileDTO();
        prepaymentDataEntryFileDTO1.setId(1L);
        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO2 = new PrepaymentDataEntryFileDTO();
        assertThat(prepaymentDataEntryFileDTO1).isNotEqualTo(prepaymentDataEntryFileDTO2);
        prepaymentDataEntryFileDTO2.setId(prepaymentDataEntryFileDTO1.getId());
        assertThat(prepaymentDataEntryFileDTO1).isEqualTo(prepaymentDataEntryFileDTO2);
        prepaymentDataEntryFileDTO2.setId(2L);
        assertThat(prepaymentDataEntryFileDTO1).isNotEqualTo(prepaymentDataEntryFileDTO2);
        prepaymentDataEntryFileDTO1.setId(null);
        assertThat(prepaymentDataEntryFileDTO1).isNotEqualTo(prepaymentDataEntryFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prepaymentDataEntryFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prepaymentDataEntryFileMapper.fromId(null)).isNull();
    }
}
