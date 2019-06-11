package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.SupplierDataEntryFile;
import io.github.prepayments.repository.SupplierDataEntryFileRepository;
import io.github.prepayments.service.SupplierDataEntryFileService;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.service.mapper.SupplierDataEntryFileMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.SupplierDataEntryFileQueryService;

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
 * Integration tests for the {@Link SupplierDataEntryFileResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class SupplierDataEntryFileResourceIT {

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
    private SupplierDataEntryFileRepository supplierDataEntryFileRepository;

    @Autowired
    private SupplierDataEntryFileMapper supplierDataEntryFileMapper;

    @Autowired
    private SupplierDataEntryFileService supplierDataEntryFileService;

    @Autowired
    private SupplierDataEntryFileQueryService supplierDataEntryFileQueryService;

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

    private MockMvc restSupplierDataEntryFileMockMvc;

    private SupplierDataEntryFile supplierDataEntryFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SupplierDataEntryFileResource supplierDataEntryFileResource = new SupplierDataEntryFileResource(supplierDataEntryFileService, supplierDataEntryFileQueryService);
        this.restSupplierDataEntryFileMockMvc = MockMvcBuilders.standaloneSetup(supplierDataEntryFileResource)
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
    public static SupplierDataEntryFile createEntity(EntityManager em) {
        SupplierDataEntryFile supplierDataEntryFile = new SupplierDataEntryFile()
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .dataEntryFile(DEFAULT_DATA_ENTRY_FILE)
            .dataEntryFileContentType(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED);
        return supplierDataEntryFile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierDataEntryFile createUpdatedEntity(EntityManager em) {
        SupplierDataEntryFile supplierDataEntryFile = new SupplierDataEntryFile()
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        return supplierDataEntryFile;
    }

    @BeforeEach
    public void initTest() {
        supplierDataEntryFile = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierDataEntryFile() throws Exception {
        int databaseSizeBeforeCreate = supplierDataEntryFileRepository.findAll().size();

        // Create the SupplierDataEntryFile
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);
        restSupplierDataEntryFileMockMvc.perform(post("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isCreated());

        // Validate the SupplierDataEntryFile in the database
        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierDataEntryFile testSupplierDataEntryFile = supplierDataEntryFileList.get(supplierDataEntryFileList.size() - 1);
        assertThat(testSupplierDataEntryFile.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testSupplierDataEntryFile.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testSupplierDataEntryFile.getDataEntryFile()).isEqualTo(DEFAULT_DATA_ENTRY_FILE);
        assertThat(testSupplierDataEntryFile.getDataEntryFileContentType()).isEqualTo(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testSupplierDataEntryFile.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testSupplierDataEntryFile.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void createSupplierDataEntryFileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierDataEntryFileRepository.findAll().size();

        // Create the SupplierDataEntryFile with an existing ID
        supplierDataEntryFile.setId(1L);
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierDataEntryFileMockMvc.perform(post("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierDataEntryFile in the database
        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPeriodFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierDataEntryFileRepository.findAll().size();
        // set the field null
        supplierDataEntryFile.setPeriodFrom(null);

        // Create the SupplierDataEntryFile, which fails.
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);

        restSupplierDataEntryFileMockMvc.perform(post("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPeriodToIsRequired() throws Exception {
        int databaseSizeBeforeTest = supplierDataEntryFileRepository.findAll().size();
        // set the field null
        supplierDataEntryFile.setPeriodTo(null);

        // Create the SupplierDataEntryFile, which fails.
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);

        restSupplierDataEntryFileMockMvc.perform(post("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFiles() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSupplierDataEntryFile() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get the supplierDataEntryFile
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files/{id}", supplierDataEntryFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(supplierDataEntryFile.getId().intValue()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.dataEntryFileContentType").value(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataEntryFile").value(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultSupplierDataEntryFileShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the supplierDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultSupplierDataEntryFileShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultSupplierDataEntryFileShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the supplierDataEntryFileList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultSupplierDataEntryFileShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodFrom is not null
        defaultSupplierDataEntryFileShouldBeFound("periodFrom.specified=true");

        // Get all the supplierDataEntryFileList where periodFrom is null
        defaultSupplierDataEntryFileShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultSupplierDataEntryFileShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the supplierDataEntryFileList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultSupplierDataEntryFileShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultSupplierDataEntryFileShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the supplierDataEntryFileList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultSupplierDataEntryFileShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodTo equals to DEFAULT_PERIOD_TO
        defaultSupplierDataEntryFileShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the supplierDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultSupplierDataEntryFileShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultSupplierDataEntryFileShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the supplierDataEntryFileList where periodTo equals to UPDATED_PERIOD_TO
        defaultSupplierDataEntryFileShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodTo is not null
        defaultSupplierDataEntryFileShouldBeFound("periodTo.specified=true");

        // Get all the supplierDataEntryFileList where periodTo is null
        defaultSupplierDataEntryFileShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultSupplierDataEntryFileShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the supplierDataEntryFileList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultSupplierDataEntryFileShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultSupplierDataEntryFileShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the supplierDataEntryFileList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultSupplierDataEntryFileShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultSupplierDataEntryFileShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the supplierDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultSupplierDataEntryFileShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultSupplierDataEntryFileShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the supplierDataEntryFileList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultSupplierDataEntryFileShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadSuccessful is not null
        defaultSupplierDataEntryFileShouldBeFound("uploadSuccessful.specified=true");

        // Get all the supplierDataEntryFileList where uploadSuccessful is null
        defaultSupplierDataEntryFileShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultSupplierDataEntryFileShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the supplierDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultSupplierDataEntryFileShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultSupplierDataEntryFileShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the supplierDataEntryFileList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultSupplierDataEntryFileShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllSupplierDataEntryFilesByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        // Get all the supplierDataEntryFileList where uploadProcessed is not null
        defaultSupplierDataEntryFileShouldBeFound("uploadProcessed.specified=true");

        // Get all the supplierDataEntryFileList where uploadProcessed is null
        defaultSupplierDataEntryFileShouldNotBeFound("uploadProcessed.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierDataEntryFileShouldBeFound(String filter) throws Exception {
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierDataEntryFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].dataEntryFileContentType").value(hasItem(DEFAULT_DATA_ENTRY_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataEntryFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_ENTRY_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())));

        // Check, that the count call also returns 1
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierDataEntryFileShouldNotBeFound(String filter) throws Exception {
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingSupplierDataEntryFile() throws Exception {
        // Get the supplierDataEntryFile
        restSupplierDataEntryFileMockMvc.perform(get("/api/supplier-data-entry-files/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierDataEntryFile() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        int databaseSizeBeforeUpdate = supplierDataEntryFileRepository.findAll().size();

        // Update the supplierDataEntryFile
        SupplierDataEntryFile updatedSupplierDataEntryFile = supplierDataEntryFileRepository.findById(supplierDataEntryFile.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierDataEntryFile are not directly saved in db
        em.detach(updatedSupplierDataEntryFile);
        updatedSupplierDataEntryFile
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .dataEntryFile(UPDATED_DATA_ENTRY_FILE)
            .dataEntryFileContentType(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED);
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(updatedSupplierDataEntryFile);

        restSupplierDataEntryFileMockMvc.perform(put("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isOk());

        // Validate the SupplierDataEntryFile in the database
        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
        SupplierDataEntryFile testSupplierDataEntryFile = supplierDataEntryFileList.get(supplierDataEntryFileList.size() - 1);
        assertThat(testSupplierDataEntryFile.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testSupplierDataEntryFile.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testSupplierDataEntryFile.getDataEntryFile()).isEqualTo(UPDATED_DATA_ENTRY_FILE);
        assertThat(testSupplierDataEntryFile.getDataEntryFileContentType()).isEqualTo(UPDATED_DATA_ENTRY_FILE_CONTENT_TYPE);
        assertThat(testSupplierDataEntryFile.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testSupplierDataEntryFile.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierDataEntryFile() throws Exception {
        int databaseSizeBeforeUpdate = supplierDataEntryFileRepository.findAll().size();

        // Create the SupplierDataEntryFile
        SupplierDataEntryFileDTO supplierDataEntryFileDTO = supplierDataEntryFileMapper.toDto(supplierDataEntryFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierDataEntryFileMockMvc.perform(put("/api/supplier-data-entry-files")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(supplierDataEntryFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierDataEntryFile in the database
        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierDataEntryFile() throws Exception {
        // Initialize the database
        supplierDataEntryFileRepository.saveAndFlush(supplierDataEntryFile);

        int databaseSizeBeforeDelete = supplierDataEntryFileRepository.findAll().size();

        // Delete the supplierDataEntryFile
        restSupplierDataEntryFileMockMvc.perform(delete("/api/supplier-data-entry-files/{id}", supplierDataEntryFile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<SupplierDataEntryFile> supplierDataEntryFileList = supplierDataEntryFileRepository.findAll();
        assertThat(supplierDataEntryFileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierDataEntryFile.class);
        SupplierDataEntryFile supplierDataEntryFile1 = new SupplierDataEntryFile();
        supplierDataEntryFile1.setId(1L);
        SupplierDataEntryFile supplierDataEntryFile2 = new SupplierDataEntryFile();
        supplierDataEntryFile2.setId(supplierDataEntryFile1.getId());
        assertThat(supplierDataEntryFile1).isEqualTo(supplierDataEntryFile2);
        supplierDataEntryFile2.setId(2L);
        assertThat(supplierDataEntryFile1).isNotEqualTo(supplierDataEntryFile2);
        supplierDataEntryFile1.setId(null);
        assertThat(supplierDataEntryFile1).isNotEqualTo(supplierDataEntryFile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierDataEntryFileDTO.class);
        SupplierDataEntryFileDTO supplierDataEntryFileDTO1 = new SupplierDataEntryFileDTO();
        supplierDataEntryFileDTO1.setId(1L);
        SupplierDataEntryFileDTO supplierDataEntryFileDTO2 = new SupplierDataEntryFileDTO();
        assertThat(supplierDataEntryFileDTO1).isNotEqualTo(supplierDataEntryFileDTO2);
        supplierDataEntryFileDTO2.setId(supplierDataEntryFileDTO1.getId());
        assertThat(supplierDataEntryFileDTO1).isEqualTo(supplierDataEntryFileDTO2);
        supplierDataEntryFileDTO2.setId(2L);
        assertThat(supplierDataEntryFileDTO1).isNotEqualTo(supplierDataEntryFileDTO2);
        supplierDataEntryFileDTO1.setId(null);
        assertThat(supplierDataEntryFileDTO1).isNotEqualTo(supplierDataEntryFileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(supplierDataEntryFileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(supplierDataEntryFileMapper.fromId(null)).isNull();
    }
}
