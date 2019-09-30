package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.AmortizationUpdateFile;
import io.github.prepayments.repository.AmortizationUpdateFileRepository;
import io.github.prepayments.repository.search.AmortizationUpdateFileSearchRepository;
import io.github.prepayments.service.AmortizationUpdateFileService;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.mapper.AmortizationUpdateFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.AmortizationUpdateFileQueryService;

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
 * Integration tests for the {@Link AmortizationUpdateFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class AmortizationUpdateFileResourceIT {

    private static final String DEFAULT_NARRATION = "AAAAAAAAAA";
    private static final String UPDATED_NARRATION = "BBBBBBBBBB";

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

    private static final String DEFAULT_REASON_FOR_UPDATE = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_UPDATE = "BBBBBBBBBB";

    @Autowired
    private AmortizationUpdateFileRepository amortizationUpdateFileRepository;

    @Autowired
    private AmortizationUpdateFileMapper amortizationUpdateFileMapper;

    @Autowired
    private AmortizationUpdateFileService amortizationUpdateFileService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.AmortizationUpdateFileSearchRepositoryMockConfiguration
     */
    @Autowired
    private AmortizationUpdateFileSearchRepository mockAmortizationUpdateFileSearchRepository;

    @Autowired
    private AmortizationUpdateFileQueryService amortizationUpdateFileQueryService;

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

    private MockMvc restAmortizationUpdateFileMockMvc;

    private AmortizationUpdateFile amortizationUpdateFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AmortizationUpdateFileResource amortizationUpdateFileResource = new AmortizationUpdateFileResource(amortizationUpdateFileService, amortizationUpdateFileQueryService);
        this.restAmortizationUpdateFileMockMvc = MockMvcBuilders.standaloneSetup(amortizationUpdateFileResource)
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
    public static AmortizationUpdateFile createEntity(EntityManager em) {
        AmortizationUpdateFile amortizationUpdateFile = new AmortizationUpdateFile()
            .narration(DEFAULT_NARRATION)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .entriesCount(DEFAULT_ENTRIES_COUNT)
            .fileToken(DEFAULT_FILE_TOKEN)
            .reasonForUpdate(DEFAULT_REASON_FOR_UPDATE);
        return amortizationUpdateFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AmortizationUpdateFile createUpdatedEntity(EntityManager em) {
        AmortizationUpdateFile amortizationUpdateFile = new AmortizationUpdateFile()
            .narration(UPDATED_NARRATION)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN)
            .reasonForUpdate(UPDATED_REASON_FOR_UPDATE);
        return amortizationUpdateFile;
    }

    @BeforeEach
    public void initTest() {
        amortizationUpdateFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createAmortizationUpdateFile() throws Exception {
        int databaseSizeBeforeCreate = amortizationUpdateFileRepository.findAll().size();

        // Create the AmortizationUpdateFile
        AmortizationUpdateFileDTO amortizationUpdateFileDTO = amortizationUpdateFileMapper.toDto(amortizationUpdateFile);
        restAmortizationUpdateFileMockMvc.perform(post("/api/amortization-update-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUpdateFileDTO)))
            .andExpect(status().isCreated());

        // Validate the AmortizationUpdateFile in the database
        List<AmortizationUpdateFile> amortizationUpdateFileList = amortizationUpdateFileRepository.findAll();
        assertThat(amortizationUpdateFileList).hasSize(databaseSizeBeforeCreate + 1);
        AmortizationUpdateFile testAmortizationUpdateFile = amortizationUpdateFileList.get(amortizationUpdateFileList.size() - 1);
        assertThat(testAmortizationUpdateFile.getNarration()).isEqualTo(DEFAULT_NARRATION);
        assertThat(testAmortizationUpdateFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testAmortizationUpdateFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationUpdateFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUpdateFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testAmortizationUpdateFile.getEntriesCount()).isEqualTo(DEFAULT_ENTRIES_COUNT);
        assertThat(testAmortizationUpdateFile.getFileToken()).isEqualTo(DEFAULT_FILE_TOKEN);
        assertThat(testAmortizationUpdateFile.getReasonForUpdate()).isEqualTo(DEFAULT_REASON_FOR_UPDATE);

        // Validate the AmortizationUpdateFile in Elasticsearch
        verify(mockAmortizationUpdateFileSearchRepository, times(1)).save(testAmortizationUpdateFile);
    }

    @Test
    @Transactional
    public void createAmortizationUpdateFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = amortizationUpdateFileRepository.findAll().size();

        // Create the AmortizationUpdateFile with an existing ID
        amortizationUpdateFile.setId(1L);
        AmortizationUpdateFileDTO amortizationUpdateFileDTO = amortizationUpdateFileMapper.toDto(amortizationUpdateFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAmortizationUpdateFileMockMvc.perform(post("/api/amortization-update-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUpdateFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUpdateFile in the database
        List<AmortizationUpdateFile> amortizationUpdateFileList = amortizationUpdateFileRepository.findAll();
        assertThat(amortizationUpdateFileList).hasSize(databaseSizeBeforeCreate);

        // Validate the AmortizationUpdateFile in Elasticsearch
        verify(mockAmortizationUpdateFileSearchRepository, times(0)).save(amortizationUpdateFile);
    }


    @Test
    @Transactional
    public void getAllAmortizationUpdateFiles() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpdateFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAmortizationUpdateFile() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get the amortizationUpdateFile
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files/{id}", amortizationUpdateFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(amortizationUpdateFile.getId().intValue()))
            .andExpect(jsonPath("$.narration").value(DEFAULT_NARRATION.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.entriesCount").value(DEFAULT_ENTRIES_COUNT))
            .andExpect(jsonPath("$.fileToken").value(DEFAULT_FILE_TOKEN.toString()))
            .andExpect(jsonPath("$.reasonForUpdate").value(DEFAULT_REASON_FOR_UPDATE.toString()));
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByNarrationIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where narration equals to DEFAULT_NARRATION
        defaultAmortizationUpdateFileShouldBeFound("narration.equals=" + DEFAULT_NARRATION);

        // Get all the amortizationUpdateFileList where narration equals to UPDATED_NARRATION
        defaultAmortizationUpdateFileShouldNotBeFound("narration.equals=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByNarrationIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where narration in DEFAULT_NARRATION or UPDATED_NARRATION
        defaultAmortizationUpdateFileShouldBeFound("narration.in=" + DEFAULT_NARRATION + "," + UPDATED_NARRATION);

        // Get all the amortizationUpdateFileList where narration equals to UPDATED_NARRATION
        defaultAmortizationUpdateFileShouldNotBeFound("narration.in=" + UPDATED_NARRATION);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByNarrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where narration is not null
        defaultAmortizationUpdateFileShouldBeFound("narration.specified=true");

        // Get all the amortizationUpdateFileList where narration is null
        defaultAmortizationUpdateFileShouldNotBeFound("narration.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultAmortizationUpdateFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUpdateFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUpdateFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUpdateFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the amortizationUpdateFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultAmortizationUpdateFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadSuccessful is not null
        defaultAmortizationUpdateFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the amortizationUpdateFileList where uploadSuccessful is null
        defaultAmortizationUpdateFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultAmortizationUpdateFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the amortizationUpdateFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUpdateFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUpdateFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the amortizationUpdateFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultAmortizationUpdateFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where uploadProcessed is not null
        defaultAmortizationUpdateFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the amortizationUpdateFileList where uploadProcessed is null
        defaultAmortizationUpdateFileShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByEntriesCountIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where entriesCount equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldBeFound("entriesCount.equals=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUpdateFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldNotBeFound("entriesCount.equals=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByEntriesCountIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where entriesCount in DEFAULT_ENTRIES_COUNT or UPDATED_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldBeFound("entriesCount.in=" + DEFAULT_ENTRIES_COUNT + "," + UPDATED_ENTRIES_COUNT);

        // Get all the amortizationUpdateFileList where entriesCount equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldNotBeFound("entriesCount.in=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByEntriesCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where entriesCount is not null
        defaultAmortizationUpdateFileShouldBeFound("entriesCount.specified=true");

        // Get all the amortizationUpdateFileList where entriesCount is null
        defaultAmortizationUpdateFileShouldNotBeFound("entriesCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByEntriesCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where entriesCount greater than or equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldBeFound("entriesCount.greaterOrEqualThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUpdateFileList where entriesCount greater than or equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldNotBeFound("entriesCount.greaterOrEqualThan=" + UPDATED_ENTRIES_COUNT);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByEntriesCountIsLessThanSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where entriesCount less than or equals to DEFAULT_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldNotBeFound("entriesCount.lessThan=" + DEFAULT_ENTRIES_COUNT);

        // Get all the amortizationUpdateFileList where entriesCount less than or equals to UPDATED_ENTRIES_COUNT
        defaultAmortizationUpdateFileShouldBeFound("entriesCount.lessThan=" + UPDATED_ENTRIES_COUNT);
    }


    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByFileTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where fileToken equals to DEFAULT_FILE_TOKEN
        defaultAmortizationUpdateFileShouldBeFound("fileToken.equals=" + DEFAULT_FILE_TOKEN);

        // Get all the amortizationUpdateFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultAmortizationUpdateFileShouldNotBeFound("fileToken.equals=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByFileTokenIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where fileToken in DEFAULT_FILE_TOKEN or UPDATED_FILE_TOKEN
        defaultAmortizationUpdateFileShouldBeFound("fileToken.in=" + DEFAULT_FILE_TOKEN + "," + UPDATED_FILE_TOKEN);

        // Get all the amortizationUpdateFileList where fileToken equals to UPDATED_FILE_TOKEN
        defaultAmortizationUpdateFileShouldNotBeFound("fileToken.in=" + UPDATED_FILE_TOKEN);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByFileTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where fileToken is not null
        defaultAmortizationUpdateFileShouldBeFound("fileToken.specified=true");

        // Get all the amortizationUpdateFileList where fileToken is null
        defaultAmortizationUpdateFileShouldNotBeFound("fileToken.specified=false");
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByReasonForUpdateIsEqualToSomething() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where reasonForUpdate equals to DEFAULT_REASON_FOR_UPDATE
        defaultAmortizationUpdateFileShouldBeFound("reasonForUpdate.equals=" + DEFAULT_REASON_FOR_UPDATE);

        // Get all the amortizationUpdateFileList where reasonForUpdate equals to UPDATED_REASON_FOR_UPDATE
        defaultAmortizationUpdateFileShouldNotBeFound("reasonForUpdate.equals=" + UPDATED_REASON_FOR_UPDATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByReasonForUpdateIsInShouldWork() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where reasonForUpdate in DEFAULT_REASON_FOR_UPDATE or UPDATED_REASON_FOR_UPDATE
        defaultAmortizationUpdateFileShouldBeFound("reasonForUpdate.in=" + DEFAULT_REASON_FOR_UPDATE + "," + UPDATED_REASON_FOR_UPDATE);

        // Get all the amortizationUpdateFileList where reasonForUpdate equals to UPDATED_REASON_FOR_UPDATE
        defaultAmortizationUpdateFileShouldNotBeFound("reasonForUpdate.in=" + UPDATED_REASON_FOR_UPDATE);
    }

    @Test
    @Transactional
    public void getAllAmortizationUpdateFilesByReasonForUpdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        // Get all the amortizationUpdateFileList where reasonForUpdate is not null
        defaultAmortizationUpdateFileShouldBeFound("reasonForUpdate.specified=true");

        // Get all the amortizationUpdateFileList where reasonForUpdate is null
        defaultAmortizationUpdateFileShouldNotBeFound("reasonForUpdate.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAmortizationUpdateFileShouldBeFound(String filter) throws Exception {
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpdateFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE)));

        // Check, that the count call also returns 1
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAmortizationUpdateFileShouldNotBeFound(String filter) throws Exception {
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAmortizationUpdateFile() throws Exception {
        // Get the amortizationUpdateFile
        restAmortizationUpdateFileMockMvc.perform(get("/api/amortization-update-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAmortizationUpdateFile() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        int databaseSizeBeforeUpdate = amortizationUpdateFileRepository.findAll().size();

        // Update the amortizationUpdateFile
        AmortizationUpdateFile updatedAmortizationUpdateFile = amortizationUpdateFileRepository.findById(amortizationUpdateFile.getId()).get();
        // Disconnect from session so that the updates on updatedAmortizationUpdateFile are not directly saved in db
        em.detach(updatedAmortizationUpdateFile);
        updatedAmortizationUpdateFile
            .narration(UPDATED_NARRATION)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .entriesCount(UPDATED_ENTRIES_COUNT)
            .fileToken(UPDATED_FILE_TOKEN)
            .reasonForUpdate(UPDATED_REASON_FOR_UPDATE);
        AmortizationUpdateFileDTO amortizationUpdateFileDTO = amortizationUpdateFileMapper.toDto(updatedAmortizationUpdateFile);

        restAmortizationUpdateFileMockMvc.perform(put("/api/amortization-update-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUpdateFileDTO)))
            .andExpect(status().isOk());

        // Validate the AmortizationUpdateFile in the database
        List<AmortizationUpdateFile> amortizationUpdateFileList = amortizationUpdateFileRepository.findAll();
        assertThat(amortizationUpdateFileList).hasSize(databaseSizeBeforeUpdate);
        AmortizationUpdateFile testAmortizationUpdateFile = amortizationUpdateFileList.get(amortizationUpdateFileList.size() - 1);
        assertThat(testAmortizationUpdateFile.getNarration()).isEqualTo(UPDATED_NARRATION);
        assertThat(testAmortizationUpdateFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testAmortizationUpdateFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testAmortizationUpdateFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testAmortizationUpdateFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testAmortizationUpdateFile.getEntriesCount()).isEqualTo(UPDATED_ENTRIES_COUNT);
        assertThat(testAmortizationUpdateFile.getFileToken()).isEqualTo(UPDATED_FILE_TOKEN);
        assertThat(testAmortizationUpdateFile.getReasonForUpdate()).isEqualTo(UPDATED_REASON_FOR_UPDATE);

        // Validate the AmortizationUpdateFile in Elasticsearch
        verify(mockAmortizationUpdateFileSearchRepository, times(1)).save(testAmortizationUpdateFile);
    }

    @Test
    @Transactional
    public void updateNonExistingAmortizationUpdateFile() throws Exception {
        int databaseSizeBeforeUpdate = amortizationUpdateFileRepository.findAll().size();

        // Create the AmortizationUpdateFile
        AmortizationUpdateFileDTO amortizationUpdateFileDTO = amortizationUpdateFileMapper.toDto(amortizationUpdateFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAmortizationUpdateFileMockMvc.perform(put("/api/amortization-update-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(amortizationUpdateFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AmortizationUpdateFile in the database
        List<AmortizationUpdateFile> amortizationUpdateFileList = amortizationUpdateFileRepository.findAll();
        assertThat(amortizationUpdateFileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AmortizationUpdateFile in Elasticsearch
        verify(mockAmortizationUpdateFileSearchRepository, times(0)).save(amortizationUpdateFile);
    }

    @Test
    @Transactional
    public void deleteAmortizationUpdateFile() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);

        int databaseSizeBeforeDelete = amortizationUpdateFileRepository.findAll().size();

        // Delete the amortizationUpdateFile
        restAmortizationUpdateFileMockMvc.perform(delete("/api/amortization-update-files/{id}", amortizationUpdateFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AmortizationUpdateFile> amortizationUpdateFileList = amortizationUpdateFileRepository.findAll();
        assertThat(amortizationUpdateFileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AmortizationUpdateFile in Elasticsearch
        verify(mockAmortizationUpdateFileSearchRepository, times(1)).deleteById(amortizationUpdateFile.getId());
    }

    @Test
    @Transactional
    public void searchAmortizationUpdateFile() throws Exception {
        // Initialize the database
        amortizationUpdateFileRepository.saveAndFlush(amortizationUpdateFile);
        when(mockAmortizationUpdateFileSearchRepository.search(queryStringQuery("id:" + amortizationUpdateFile.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(amortizationUpdateFile), PageRequest.of(0, 1), 1));
        // Search the amortizationUpdateFile
        restAmortizationUpdateFileMockMvc.perform(get("/api/_search/amortization-update-files?query=id:" + amortizationUpdateFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(amortizationUpdateFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].narration").value(hasItem(DEFAULT_NARRATION)))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].entriesCount").value(hasItem(DEFAULT_ENTRIES_COUNT)))
            .andExpect(jsonPath("$.[*].fileToken").value(hasItem(DEFAULT_FILE_TOKEN)))
            .andExpect(jsonPath("$.[*].reasonForUpdate").value(hasItem(DEFAULT_REASON_FOR_UPDATE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUpdateFile.class);
        AmortizationUpdateFile amortizationUpdateFile1 = new AmortizationUpdateFile();
        amortizationUpdateFile1.setId(1L);
        AmortizationUpdateFile amortizationUpdateFile2 = new AmortizationUpdateFile();
        amortizationUpdateFile2.setId(amortizationUpdateFile1.getId());
        assertThat(amortizationUpdateFile1).isEqualTo(amortizationUpdateFile2);
        amortizationUpdateFile2.setId(2L);
        assertThat(amortizationUpdateFile1).isNotEqualTo(amortizationUpdateFile2);
        amortizationUpdateFile1.setId(null);
        assertThat(amortizationUpdateFile1).isNotEqualTo(amortizationUpdateFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AmortizationUpdateFileDTO.class);
        AmortizationUpdateFileDTO amortizationUpdateFileDTO1 = new AmortizationUpdateFileDTO();
        amortizationUpdateFileDTO1.setId(1L);
        AmortizationUpdateFileDTO amortizationUpdateFileDTO2 = new AmortizationUpdateFileDTO();
        assertThat(amortizationUpdateFileDTO1).isNotEqualTo(amortizationUpdateFileDTO2);
        amortizationUpdateFileDTO2.setId(amortizationUpdateFileDTO1.getId());
        assertThat(amortizationUpdateFileDTO1).isEqualTo(amortizationUpdateFileDTO2);
        amortizationUpdateFileDTO2.setId(2L);
        assertThat(amortizationUpdateFileDTO1).isNotEqualTo(amortizationUpdateFileDTO2);
        amortizationUpdateFileDTO1.setId(null);
        assertThat(amortizationUpdateFileDTO1).isNotEqualTo(amortizationUpdateFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(amortizationUpdateFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(amortizationUpdateFileMapper.fromId(null)).isNull();
    }
}
