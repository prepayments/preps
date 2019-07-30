package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.ServiceOutletDataEntryFile;
import io.github.prepayments.repository.ServiceOutletDataEntryFileRepository;
import io.github.prepayments.repository.search.ServiceOutletDataEntryFileSearchRepository;
import io.github.prepayments.service.ServiceOutletDataEntryFileService;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.service.mapper.ServiceOutletDataEntryFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.ServiceOutletDataEntryFileQueryService;

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
 * Integration tests for the {@Link ServiceOutletDataEntryFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class ServiceOutletDataEntryFileResourceIT {

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    private static final byte[] DEFAULT_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_ENTRY_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_ENTRIES_COUNT = 1;
    private static final Integer UPDATED_ENTRIES_COUNT = 2;

    private static final String DEFAULT_FILE_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TOKEN = "BBBBBBBBBB";

    @Autowired
    private ServiceOutletDataEntryFileRepository serviceOutletDataEntryFileRepository;

    @Autowired
    private ServiceOutletDataEntryFileMapper serviceOutletDataEntryFileMapper;

    @Autowired
    private ServiceOutletDataEntryFileService serviceOutletDataEntryFileService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.ServiceOutletDataEntryFileSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceOutletDataEntryFileSearchRepository mockServiceOutletDataEntryFileSearchRepository;

    @Autowired
    private ServiceOutletDataEntryFileQueryService serviceOutletDataEntryFileQueryService;

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

    private MockMvc restServiceOutletDataEntryFileMockMvc;

    private ServiceOutletDataEntryFile serviceOutletDataEntryFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOutletDataEntryFileResource serviceOutletDataEntryFileResource = new ServiceOutletDataEntryFileResource(serviceOutletDataEntryFileService, serviceOutletDataEntryFileQueryService);
        this.restServiceOutletDataEntryFileMockMvc = MockMvcBuilders.standaloneSetup(serviceOutletDataEntryFileResource)
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
    public static ServiceOutletDataEntryFile createEntity(EntityManager em) {
        ServiceOutletDataEntryFile serviceOutletDataEntryFile = new ServiceOutletDataEntryFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .entriesCount(DEFAULT_ENTRIES_COUNT)
            .fileToken(DEFAULT_FILE_TOKEN);
        return serviceOutletDataEntryFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOutletDataEntryFile createUpdatedEntity(EntityManager em) {
        ServiceOutletDataEntryFile serviceOutletDataEntryFile = new ServiceOutletDataEntryFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN);
        return serviceOutletDataEntryFile;
    }

    @BeforeEach
    public void initTest() {
        serviceOutletDataEntryFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOutletDataEntryFile() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletDataEntryFileRepository.findAll().size();

        // Create the ServiceOutletDataEntryFile
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);
        restServiceOutletDataEntryFileMockMvc.perform(post("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOutletDataEntryFile in the database
        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOutletDataEntryFile testServiceOutletDataEntryFile = serviceOutletDataEntryFileList.get(serviceOutletDataEntryFileList.size() - 1);
        assertThat(testServiceOutletDataEntryFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testServiceOutletDataEntryFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testServiceOutletDataEntryFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testServiceOutletDataEntryFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testServiceOutletDataEntryFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testServiceOutletDataEntryFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testServiceOutletDataEntryFile.getEntriesCount()).isEqualTo(DEFAULT_ENTRIES_COUNT);
        assertThat(testServiceOutletDataEntryFile.getFileToken()).isEqualTo(DEFAULT_FILE_TOKEN);

        // Validate the ServiceOutletDataEntryFile in Elasticsearch
        verify(mockServiceOutletDataEntryFileSearchRepository, times(1)).save(testServiceOutletDataEntryFile);
    }

    @Test
    @Transactional
    public void createServiceOutletDataEntryFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletDataEntryFileRepository.findAll().size();

        // Create the ServiceOutletDataEntryFile with an existing ID
        serviceOutletDataEntryFile.setId(1L);
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOutletDataEntryFileMockMvc.perform(post("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutletDataEntryFile in the database
        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceOutletDataEntryFile in Elasticsearch
        verify(mockServiceOutletDataEntryFileSearchRepository, times(0)).save(serviceOutletDataEntryFile);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletDataEntryFileRepository.findAll().size();
        // set the field null
        serviceOutletDataEntryFile.setPeriodFrom(null);

        // Create the ServiceOutletDataEntryFile, which fails.
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);

        restServiceOutletDataEntryFileMockMvc.perform(post("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletDataEntryFileRepository.findAll().size();
        // set the field null
        serviceOutletDataEntryFile.setPeriodTo(null);

        // Create the ServiceOutletDataEntryFile, which fails.
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);

        restServiceOutletDataEntryFileMockMvc.perform(post("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFiles() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutletDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceOutletDataEntryFile() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get the serviceOutletDataEntryFile
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files/{id}", serviceOutletDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOutletDataEntryFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.entriesCount").value(DEFAULT_ENTRIES_COUNT))
            .andExpect(jsonPath("$.fileToken").value(DEFAULT_FILE_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the serviceOutletDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the serviceOutletDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodFrom is not null
        defaultServiceOutletDataEntryFileShouldBeFound("periodFrom.specified=true");

        // Get all the serviceOutletDataEntryFileList where periodFrom is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the serviceOutletDataEntryFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the serviceOutletDataEntryFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultServiceOutletDataEntryFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the serviceOutletDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the serviceOutletDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodTo is not null
        defaultServiceOutletDataEntryFileShouldBeFound("periodTo.specified=true");

        // Get all the serviceOutletDataEntryFileList where periodTo is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the serviceOutletDataEntryFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the serviceOutletDataEntryFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultServiceOutletDataEntryFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultServiceOutletDataEntryFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultServiceOutletDataEntryFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful is not null
        defaultServiceOutletDataEntryFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the serviceOutletDataEntryFileList where uploadSuccessful is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultServiceOutletDataEntryFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the serviceOutletDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultServiceOutletDataEntryFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the serviceOutletDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where uploadProcessed is not null
        defaultServiceOutletDataEntryFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the serviceOutletDataEntryFileList where uploadProcessed is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByEntriesCountIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where entriesCount equals to DEFAULT_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldBeFound("entriesCount.equals=" + DEFAULT_ENTRIES_COUNT);

        // Get all the serviceOutletDataEntryFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldNotBeFound("entriesCount.equals=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByEntriesCountIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where entriesCount in DEFAULT_ENTRIES_COUNT or UPDATED_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldBeFound("entriesCount.in=" + DEFAULT_ENTRIES_COUNT + "," + UPDATED_ENTRIES_COUNT);

        // Get all the serviceOutletDataEntryFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldNotBeFound("entriesCount.in=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByEntriesCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where entriesCount is not null
        defaultServiceOutletDataEntryFileShouldBeFound("entriesCount.specified=true");

        // Get all the serviceOutletDataEntryFileList where entriesCount is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("entriesCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByEntriesCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where entriesCount greater than or equals to DEFAULT_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldBeFound("entriesCount.greaterOrEqualThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the serviceOutletDataEntryFileList where entriesCount greater than or equals to UPDATED_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldNotBeFound("entriesCount.greaterOrEqualThan=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByEntriesCountIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where entriesCount less than or equals to DEFAULT_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldNotBeFound("entriesCount.lessThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the serviceOutletDataEntryFileList where entriesCount less than or equals to UPDATED_ENTRIES_COUNT
        defaultServiceOutletDataEntryFileShouldBeFound("entriesCount.lessThan=" + UPDATED_ENTRIES_COUNT);
    }


    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where fileToken equals to DEFAULT_FILE_TOKEN
        defaultServiceOutletDataEntryFileShouldBeFound("fileToken.equals=" + DEFAULT_FILE_TOKEN);

        // Get all the serviceOutletDataEntryFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultServiceOutletDataEntryFileShouldNotBeFound("fileToken.equals=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where fileToken in DEFAULT_FILE_TOKEN or UPDATED_FILE_TOKEN
        defaultServiceOutletDataEntryFileShouldBeFound("fileToken.in=" + DEFAULT_FILE_TOKEN + "," + UPDATED_FILE_TOKEN);

        // Get all the serviceOutletDataEntryFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultServiceOutletDataEntryFileShouldNotBeFound("fileToken.in=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllServiceOutletDataEntryFilesByFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        // Get all the serviceOutletDataEntryFileList where fileToken is not null
        defaultServiceOutletDataEntryFileShouldBeFound("fileToken.specified=true");

        // Get all the serviceOutletDataEntryFileList where fileToken is null
        defaultServiceOutletDataEntryFileShouldNotBeFound("fileToken.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOutletDataEntryFileShouldBeFound(String filter) throws Exception {
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutletDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)));

        // Check, that the count call also returns 1
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOutletDataEntryFileShouldNotBeFound(String filter) throws Exception {
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceOutletDataEntryFile() throws Exception {
        // Get the serviceOutletDataEntryFile
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/service-outlet-data-entry-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOutletDataEntryFile() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        int databaseSizeBeforeUpdate = serviceOutletDataEntryFileRepository.findAll().size();

        // Update the serviceOutletDataEntryFile
        ServiceOutletDataEntryFile updatedServiceOutletDataEntryFile = serviceOutletDataEntryFileRepository.findById(serviceOutletDataEntryFile.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOutletDataEntryFile are not directly saved in db
        em.detach(updatedServiceOutletDataEntryFile);
        updatedServiceOutletDataEntryFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN);
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(updatedServiceOutletDataEntryFile);

        restServiceOutletDataEntryFileMockMvc.perform(put("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOutletDataEntryFile in the database
        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
        ServiceOutletDataEntryFile testServiceOutletDataEntryFile = serviceOutletDataEntryFileList.get(serviceOutletDataEntryFileList.size() - 1);
        assertThat(testServiceOutletDataEntryFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testServiceOutletDataEntryFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testServiceOutletDataEntryFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testServiceOutletDataEntryFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testServiceOutletDataEntryFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testServiceOutletDataEntryFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testServiceOutletDataEntryFile.getEntriesCount()).isEqualTo(UPDATED_ENTRIES_COUNT);
        assertThat(testServiceOutletDataEntryFile.getFileToken()).isEqualTo(UPDATED_FILE_TOKEN);

        // Validate the ServiceOutletDataEntryFile in Elasticsearch
        verify(mockServiceOutletDataEntryFileSearchRepository, times(1)).save(testServiceOutletDataEntryFile);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOutletDataEntryFile() throws Exception {
        int databaseSizeBeforeUpdate = serviceOutletDataEntryFileRepository.findAll().size();

        // Create the ServiceOutletDataEntryFile
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileMapper.toDto(serviceOutletDataEntryFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOutletDataEntryFileMockMvc.perform(put("/api/service-outlet-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutletDataEntryFile in the database
        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceOutletDataEntryFile in Elasticsearch
        verify(mockServiceOutletDataEntryFileSearchRepository, times(0)).save(serviceOutletDataEntryFile);
    }

    @Test
    @Transactional
    public void deleteServiceOutletDataEntryFile() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);

        int databaseSizeBeforeDelete = serviceOutletDataEntryFileRepository.findAll().size();

        // Delete the serviceOutletDataEntryFile
        restServiceOutletDataEntryFileMockMvc.perform(delete("/api/service-outlet-data-entry-files/{id}", serviceOutletDataEntryFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ServiceOutletDataEntryFile> serviceOutletDataEntryFileList = serviceOutletDataEntryFileRepository.findAll();
        assertThat(serviceOutletDataEntryFileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceOutletDataEntryFile in Elasticsearch
        verify(mockServiceOutletDataEntryFileSearchRepository, times(1)).deleteById(serviceOutletDataEntryFile.getId());
    }

    @Test
    @Transactional
    public void searchServiceOutletDataEntryFile() throws Exception {
        // Initialize the database
        serviceOutletDataEntryFileRepository.saveAndFlush(serviceOutletDataEntryFile);
        when(mockServiceOutletDataEntryFileSearchRepository.search(queryStringQuery("id:" + serviceOutletDataEntryFile.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceOutletDataEntryFile), PageRequest.of(0, 1), 1));
        // Search the serviceOutletDataEntryFile
        restServiceOutletDataEntryFileMockMvc.perform(get("/api/_search/service-outlet-data-entry-files?query=id:" + serviceOutletDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutletDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutletDataEntryFile.class);
        ServiceOutletDataEntryFile serviceOutletDataEntryFile1 = new ServiceOutletDataEntryFile();
        serviceOutletDataEntryFile1.setId(1L);
        ServiceOutletDataEntryFile serviceOutletDataEntryFile2 = new ServiceOutletDataEntryFile();
        serviceOutletDataEntryFile2.setId(serviceOutletDataEntryFile1.getId());
        assertThat(serviceOutletDataEntryFile1).isEqualTo(serviceOutletDataEntryFile2);
        serviceOutletDataEntryFile2.setId(2L);
        assertThat(serviceOutletDataEntryFile1).isNotEqualTo(serviceOutletDataEntryFile2);
        serviceOutletDataEntryFile1.setId(null);
        assertThat(serviceOutletDataEntryFile1).isNotEqualTo(serviceOutletDataEntryFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutletDataEntryFileDTO.class);
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO1 = new ServiceOutletDataEntryFileDTO();
        serviceOutletDataEntryFileDTO1.setId(1L);
        ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO2 = new ServiceOutletDataEntryFileDTO();
        assertThat(serviceOutletDataEntryFileDTO1).isNotEqualTo(serviceOutletDataEntryFileDTO2);
        serviceOutletDataEntryFileDTO2.setId(serviceOutletDataEntryFileDTO1.getId());
        assertThat(serviceOutletDataEntryFileDTO1).isEqualTo(serviceOutletDataEntryFileDTO2);
        serviceOutletDataEntryFileDTO2.setId(2L);
        assertThat(serviceOutletDataEntryFileDTO1).isNotEqualTo(serviceOutletDataEntryFileDTO2);
        serviceOutletDataEntryFileDTO1.setId(null);
        assertThat(serviceOutletDataEntryFileDTO1).isNotEqualTo(serviceOutletDataEntryFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceOutletDataEntryFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceOutletDataEntryFileMapper.fromId(null)).isNull();
    }
}
