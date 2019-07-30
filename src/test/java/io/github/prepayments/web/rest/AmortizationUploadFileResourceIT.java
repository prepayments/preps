package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AmortizationUploadFile;
import io.github.prepayments.repository.AmortizationUploadFileRepository;
import io.github.prepayments.repository.search.AmortizationUploadFileSearchRepository;
import io.github.prepayments.service.AmortizationUploadFileService;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import io.github.prepayments.service.mapper.AmortizationUploadFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AmortizationUploadFileCriteria;
import io.github.prepayments.service.AmortizationUploadFileQueryService;

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
import org.springframework.util.Base64Utils;
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

/**
 * Integration tests for the {@Link AmortizationUploadFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationUploadFileResourceIT {

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

    private static final Integer DEFAULT_ENTRIES_COUNT = 1;
    private static final Integer UPDATED_ENTRIES_COUNT = 2;

    private static final String DEFAULT_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private AmortizationUploadFileRepository amortizationUploadFileRepository;

    @Autowired
    private AmortizationUploadFileMapper amortizationUploadFileMapper;

    @Autowired
    private AmortizationUploadFileService amortizationUploadFileService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.AmortizationUploadFileSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationUploadFileSearchRepository mockAmortizationUploadFileSearchRepository;

    @Autowired
    private AmortizationUploadFileQueryService amortizationUploadFileQueryService;

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

    private MockMvc restAmortizationUploadFileMockMvc;

    private AmortizationUploadFile amortizationUploadFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationUploadFileResource amortizationUploadFileResource = new AmortizationUploadFileResource(amortizationUploadFileService, amortizationUploadFileQueryService);
        this.restAmortizationUploadFileMockMvc = MockMvcBuilders.standaloneSetup(amortizationUploadFileResource)
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
    public static AmortizationUploadFile createEntity(EntityManager em) {
        AmortizationUploadFile amortizationUploadFile = new AmortizationUploadFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .entriesCount(DEFAULT_ENTRIES_COUNT)
            .fileToken(DEFAULT_FILE_TOKEN);
        return amortizationUploadFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationUploadFile createUpdatedEntity(EntityManager em) {
        AmortizationUploadFile amortizationUploadFile = new AmortizationUploadFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN);
        return amortizationUploadFile;
    }

    @BeforeEach
    public void initTest() {
        amortizationUploadFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationUploadFile() throws Exception {
        int databaseSizeBeforeCreate = amortizationUploadFileRepository.findAll().size();

        // Create the AmortizationUploadFile
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(amortizationUploadFile);
        restAmortizationUploadFileMockMvc.perform(post("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationUploadFile in the database
        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationUploadFile testAmortizationUploadFile = amortizationUploadFileList.get(amortizationUploadFileList.size() - 1);
        assertThat(testAmortizationUploadFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testAmortizationUploadFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testAmortizationUploadFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testAmortizationUploadFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationUploadFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUploadFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testAmortizationUploadFile.getEntriesCount()).isEqualTo(DEFAULT_ENTRIES_COUNT);
        assertThat(testAmortizationUploadFile.getFileToken()).isEqualTo(DEFAULT_FILE_TOKEN);

        // Validate the AmortizationUploadFile in Elasticsearch
        verify(mockAmortizationUploadFileSearchRepository, times(1)).save(testAmortizationUploadFile);
    }

    @Test
    @Transactional
    public void createAmortizationUploadFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationUploadFileRepository.findAll().size();

        // Create the AmortizationUploadFile with an existing ID
        amortizationUploadFile.setId(1L);
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(amortizationUploadFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationUploadFileMockMvc.perform(post("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUploadFile in the database
        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmortizationUploadFile in Elasticsearch
        verify(mockAmortizationUploadFileSearchRepository, times(0)).save(amortizationUploadFile);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadFileRepository.findAll().size();
        // set the field null
        amortizationUploadFile.setPeriodFrom(null);

        // Create the AmortizationUploadFile, which fails.
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(amortizationUploadFile);

        restAmortizationUploadFileMockMvc.perform(post("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = amortizationUploadFileRepository.findAll().size();
        // set the field null
        amortizationUploadFile.setPeriodTo(null);

        // Create the AmortizationUploadFile, which fails.
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(amortizationUploadFile);

        restAmortizationUploadFileMockMvc.perform(post("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isBadRequest());

        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFiles() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortizationUploadFile() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get the amortizationUploadFile
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files/{id}", amortizationUploadFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationUploadFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.entriesCount").value(DEFAULT_ENTRIES_COUNT))
            .andExpect(jsonPath("$.fileToken").value(DEFAULT_FILE_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultAmortizationUploadFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationUploadFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultAmortizationUploadFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultAmortizationUploadFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the amortizationUploadFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultAmortizationUploadFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodFrom is not null
        defaultAmortizationUploadFileShouldBeFound("periodFrom.specified=true");

        // Get all the amortizationUploadFileList where periodFrom is null
        defaultAmortizationUploadFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultAmortizationUploadFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationUploadFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultAmortizationUploadFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultAmortizationUploadFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the amortizationUploadFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultAmortizationUploadFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultAmortizationUploadFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationUploadFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultAmortizationUploadFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultAmortizationUploadFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the amortizationUploadFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultAmortizationUploadFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodTo is not null
        defaultAmortizationUploadFileShouldBeFound("periodTo.specified=true");

        // Get all the amortizationUploadFileList where periodTo is null
        defaultAmortizationUploadFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultAmortizationUploadFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationUploadFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultAmortizationUploadFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultAmortizationUploadFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the amortizationUploadFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultAmortizationUploadFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUploadFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUploadFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUploadFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadSuccessful is not null
        defaultAmortizationUploadFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the amortizationUploadFileList where uploadSuccessful is null
        defaultAmortizationUploadFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultAmortizationUploadFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the amortizationUploadFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUploadFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUploadFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the amortizationUploadFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUploadFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where uploadProcessed is not null
        defaultAmortizationUploadFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the amortizationUploadFileList where uploadProcessed is null
        defaultAmortizationUploadFileShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByEntriesCountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where entriesCount equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldBeFound("entriesCount.equals=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUploadFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldNotBeFound("entriesCount.equals=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByEntriesCountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where entriesCount in DEFAULT_ENTRIES_COUNT or UPDATED_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldBeFound("entriesCount.in=" + DEFAULT_ENTRIES_COUNT + "," + UPDATED_ENTRIES_COUNT);

        // Get all the amortizationUploadFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldNotBeFound("entriesCount.in=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByEntriesCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where entriesCount is not null
        defaultAmortizationUploadFileShouldBeFound("entriesCount.specified=true");

        // Get all the amortizationUploadFileList where entriesCount is null
        defaultAmortizationUploadFileShouldNotBeFound("entriesCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByEntriesCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where entriesCount greater than or equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldBeFound("entriesCount.greaterOrEqualThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUploadFileList where entriesCount greater than or equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldNotBeFound("entriesCount.greaterOrEqualThan=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByEntriesCountIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where entriesCount less than or equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldNotBeFound("entriesCount.lessThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUploadFileList where entriesCount less than or equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUploadFileShouldBeFound("entriesCount.lessThan=" + UPDATED_ENTRIES_COUNT);
    }


    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where fileToken equals to DEFAULT_FILE_TOKEN
        defaultAmortizationUploadFileShouldBeFound("fileToken.equals=" + DEFAULT_FILE_TOKEN);

        // Get all the amortizationUploadFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultAmortizationUploadFileShouldNotBeFound("fileToken.equals=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where fileToken in DEFAULT_FILE_TOKEN or UPDATED_FILE_TOKEN
        defaultAmortizationUploadFileShouldBeFound("fileToken.in=" + DEFAULT_FILE_TOKEN + "," + UPDATED_FILE_TOKEN);

        // Get all the amortizationUploadFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultAmortizationUploadFileShouldNotBeFound("fileToken.in=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUploadFilesByFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        // Get all the amortizationUploadFileList where fileToken is not null
        defaultAmortizationUploadFileShouldBeFound("fileToken.specified=true");

        // Get all the amortizationUploadFileList where fileToken is null
        defaultAmortizationUploadFileShouldNotBeFound("fileToken.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationUploadFileShouldBeFound(String filter) throws Exception {
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)));

        // Check, that the count call also returns 1
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationUploadFileShouldNotBeFound(String filter) throws Exception {
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationUploadFile() throws Exception {
        // Get the amortizationUploadFile
        restAmortizationUploadFileMockMvc.perform(get("/api/amortization-upload-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationUploadFile() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        int databaseSizeBeforeUpdate = amortizationUploadFileRepository.findAll().size();

        // Update the amortizationUploadFile
        AmortizationUploadFile updatedAmortizationUploadFile = amortizationUploadFileRepository.findById(amortizationUploadFile.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationUploadFile are not directly saved in db
        em.detach(updatedAmortizationUploadFile);
        updatedAmortizationUploadFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN);
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(updatedAmortizationUploadFile);

        restAmortizationUploadFileMockMvc.perform(put("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationUploadFile in the database
        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeUpdate);
        AmortizationUploadFile testAmortizationUploadFile = amortizationUploadFileList.get(amortizationUploadFileList.size() - 1);
        assertThat(testAmortizationUploadFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testAmortizationUploadFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testAmortizationUploadFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testAmortizationUploadFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationUploadFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUploadFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testAmortizationUploadFile.getEntriesCount()).isEqualTo(UPDATED_ENTRIES_COUNT);
        assertThat(testAmortizationUploadFile.getFileToken()).isEqualTo(UPDATED_FILE_TOKEN);

        // Validate the AmortizationUploadFile in Elasticsearch
        verify(mockAmortizationUploadFileSearchRepository, times(1)).save(testAmortizationUploadFile);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationUploadFile() throws Exception {
        int databaseSizeBeforeUpdate = amortizationUploadFileRepository.findAll().size();

        // Create the AmortizationUploadFile
        AmortizationUploadFileDTO amortizationUploadFileDTO = amortizationUploadFileMapper.toDto(amortizationUploadFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationUploadFileMockMvc.perform(put("/api/amortization-upload-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUploadFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUploadFile in the database
        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmortizationUploadFile in Elasticsearch
        verify(mockAmortizationUploadFileSearchRepository, times(0)).save(amortizationUploadFile);
    }

    @Test
    @Transactional
    public void deleteAmortizationUploadFile() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);

        int databaseSizeBeforeDelete = amortizationUploadFileRepository.findAll().size();

        // Delete the amortizationUploadFile
        restAmortizationUploadFileMockMvc.perform(delete("/api/amortization-upload-files/{id}", amortizationUploadFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationUploadFile> amortizationUploadFileList = amortizationUploadFileRepository.findAll();
        assertThat(amortizationUploadFileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmortizationUploadFile in Elasticsearch
        verify(mockAmortizationUploadFileSearchRepository, times(1)).deleteById(amortizationUploadFile.getId());
    }

    @Test
    @Transactional
    public void searchAmortizationUploadFile() throws Exception {
        // Initialize the database
        amortizationUploadFileRepository.saveAndFlush(amortizationUploadFile);
        when(mockAmortizationUploadFileSearchRepository.search(queryStringQuery("id:" + amortizationUploadFile.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizationUploadFile), PageRequest.of(0, 1), 1));
        // Search the amortizationUploadFile
        restAmortizationUploadFileMockMvc.perform(get("/api/_search/amortization-upload-files?query=id:" + amortizationUploadFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUploadFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUploadFile.class);
        AmortizationUploadFile amortizationUploadFile1 = new AmortizationUploadFile();
        amortizationUploadFile1.setId(1L);
        AmortizationUploadFile amortizationUploadFile2 = new AmortizationUploadFile();
        amortizationUploadFile2.setId(amortizationUploadFile1.getId());
        assertThat(amortizationUploadFile1).isEqualTo(amortizationUploadFile2);
        amortizationUploadFile2.setId(2L);
        assertThat(amortizationUploadFile1).isNotEqualTo(amortizationUploadFile2);
        amortizationUploadFile1.setId(null);
        assertThat(amortizationUploadFile1).isNotEqualTo(amortizationUploadFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUploadFileDTO.class);
        AmortizationUploadFileDTO amortizationUploadFileDTO1 = new AmortizationUploadFileDTO();
        amortizationUploadFileDTO1.setId(1L);
        AmortizationUploadFileDTO amortizationUploadFileDTO2 = new AmortizationUploadFileDTO();
        assertThat(amortizationUploadFileDTO1).isNotEqualTo(amortizationUploadFileDTO2);
        amortizationUploadFileDTO2.setId(amortizationUploadFileDTO1.getId());
        assertThat(amortizationUploadFileDTO1).isEqualTo(amortizationUploadFileDTO2);
        amortizationUploadFileDTO2.setId(2L);
        assertThat(amortizationUploadFileDTO1).isNotEqualTo(amortizationUploadFileDTO2);
        amortizationUploadFileDTO1.setId(null);
        assertThat(amortizationUploadFileDTO1).isNotEqualTo(amortizationUploadFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationUploadFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationUploadFileMapper.fromId(null)).isNull();
    }
}
