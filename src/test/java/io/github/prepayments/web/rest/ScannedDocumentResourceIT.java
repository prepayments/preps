package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.ScannedDocument;
import io.github.prepayments.repository.ScannedDocumentRepository;
import io.github.prepayments.repository.search.ScannedDocumentSearchRepository;
import io.github.prepayments.service.ScannedDocumentService;
import io.github.prepayments.service.dto.ScannedDocumentDTO;
import io.github.prepayments.service.mapper.ScannedDocumentMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.ScannedDocumentCriteria;
import io.github.prepayments.service.ScannedDocumentQueryService;

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
 * Integration tests for the {@Link ScannedDocumentResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class ScannedDocumentResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_INVOICE_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INVOICE_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_REQUISITION_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REQUISITION_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_APPROVAL_MEMO_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_APPROVAL_MEMO_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_OTHER_SCANNED_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTHER_SCANNED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private ScannedDocumentRepository scannedDocumentRepository;

    @Autowired
    private ScannedDocumentMapper scannedDocumentMapper;

    @Autowired
    private ScannedDocumentService scannedDocumentService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.ScannedDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScannedDocumentSearchRepository mockScannedDocumentSearchRepository;

    @Autowired
    private ScannedDocumentQueryService scannedDocumentQueryService;

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

    private MockMvc restScannedDocumentMockMvc;

    private ScannedDocument scannedDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScannedDocumentResource scannedDocumentResource = new ScannedDocumentResource(scannedDocumentService, scannedDocumentQueryService);
        this.restScannedDocumentMockMvc = MockMvcBuilders.standaloneSetup(scannedDocumentResource)
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
    public static ScannedDocument createEntity(EntityManager em) {
        ScannedDocument scannedDocument = new ScannedDocument()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .invoiceDocument(DEFAULT_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(DEFAULT_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .approvalMemoDocument(DEFAULT_APPROVAL_MEMO_DOCUMENT)
            .approvalMemoDocumentContentType(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)
            .otherScannedDocument(DEFAULT_OTHER_SCANNED_DOCUMENT)
            .otherScannedDocumentContentType(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE);
        return scannedDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScannedDocument createUpdatedEntity(EntityManager em) {
        ScannedDocument scannedDocument = new ScannedDocument()
            .documentName(UPDATED_DOCUMENT_NAME)
            .description(UPDATED_DESCRIPTION)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .invoiceDocument(UPDATED_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(UPDATED_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .approvalMemoDocument(UPDATED_APPROVAL_MEMO_DOCUMENT)
            .approvalMemoDocumentContentType(UPDATED_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)
            .otherScannedDocument(UPDATED_OTHER_SCANNED_DOCUMENT)
            .otherScannedDocumentContentType(UPDATED_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE);
        return scannedDocument;
    }

    @BeforeEach
    public void initTest() {
        scannedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createScannedDocument() throws Exception {
        int databaseSizeBeforeCreate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);
        restScannedDocumentMockMvc.perform(post("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ScannedDocument testScannedDocument = scannedDocumentList.get(scannedDocumentList.size() - 1);
        assertThat(testScannedDocument.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testScannedDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScannedDocument.getInvoiceNumber()).isEqualTo(DEFAULT_INVOICE_NUMBER);
        assertThat(testScannedDocument.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testScannedDocument.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testScannedDocument.getInvoiceDocument()).isEqualTo(DEFAULT_INVOICE_DOCUMENT);
        assertThat(testScannedDocument.getInvoiceDocumentContentType()).isEqualTo(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getRequisitionDocument()).isEqualTo(DEFAULT_REQUISITION_DOCUMENT);
        assertThat(testScannedDocument.getRequisitionDocumentContentType()).isEqualTo(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getApprovalMemoDocument()).isEqualTo(DEFAULT_APPROVAL_MEMO_DOCUMENT);
        assertThat(testScannedDocument.getApprovalMemoDocumentContentType()).isEqualTo(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getOtherScannedDocument()).isEqualTo(DEFAULT_OTHER_SCANNED_DOCUMENT);
        assertThat(testScannedDocument.getOtherScannedDocumentContentType()).isEqualTo(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).save(testScannedDocument);
    }

    @Test
    @Transactional
    public void createScannedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument with an existing ID
        scannedDocument.setId(1L);
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScannedDocumentMockMvc.perform(post("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(0)).save(scannedDocument);
    }


    @Test
    @Transactional
    public void checkDocumentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = scannedDocumentRepository.findAll().size();
        // set the field null
        scannedDocument.setDocumentName(null);

        // Create the ScannedDocument, which fails.
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);

        restScannedDocumentMockMvc.perform(post("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isBadRequest());

        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllScannedDocuments() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].approvalMemoDocumentContentType").value(hasItem(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalMemoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_MEMO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherScannedDocumentContentType").value(hasItem(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherScannedDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_SCANNED_DOCUMENT))));
    }
    
    @Test
    @Transactional
    public void getScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/{id}", scannedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scannedDocument.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.invoiceDocumentContentType").value(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.invoiceDocument").value(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT)))
            .andExpect(jsonPath("$.requisitionDocumentContentType").value(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.requisitionDocument").value(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT)))
            .andExpect(jsonPath("$.approvalMemoDocumentContentType").value(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.approvalMemoDocument").value(Base64Utils.encodeToString(DEFAULT_APPROVAL_MEMO_DOCUMENT)))
            .andExpect(jsonPath("$.otherScannedDocumentContentType").value(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.otherScannedDocument").value(Base64Utils.encodeToString(DEFAULT_OTHER_SCANNED_DOCUMENT)));
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultScannedDocumentShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the scannedDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultScannedDocumentShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultScannedDocumentShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the scannedDocumentList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultScannedDocumentShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where documentName is not null
        defaultScannedDocumentShouldBeFound("documentName.specified=true");

        // Get all the scannedDocumentList where documentName is null
        defaultScannedDocumentShouldNotBeFound("documentName.specified=false");
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description equals to DEFAULT_DESCRIPTION
        defaultScannedDocumentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the scannedDocumentList where description equals to UPDATED_DESCRIPTION
        defaultScannedDocumentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultScannedDocumentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the scannedDocumentList where description equals to UPDATED_DESCRIPTION
        defaultScannedDocumentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description is not null
        defaultScannedDocumentShouldBeFound("description.specified=true");

        // Get all the scannedDocumentList where description is null
        defaultScannedDocumentShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByInvoiceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where invoiceNumber equals to DEFAULT_INVOICE_NUMBER
        defaultScannedDocumentShouldBeFound("invoiceNumber.equals=" + DEFAULT_INVOICE_NUMBER);

        // Get all the scannedDocumentList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultScannedDocumentShouldNotBeFound("invoiceNumber.equals=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByInvoiceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where invoiceNumber in DEFAULT_INVOICE_NUMBER or UPDATED_INVOICE_NUMBER
        defaultScannedDocumentShouldBeFound("invoiceNumber.in=" + DEFAULT_INVOICE_NUMBER + "," + UPDATED_INVOICE_NUMBER);

        // Get all the scannedDocumentList where invoiceNumber equals to UPDATED_INVOICE_NUMBER
        defaultScannedDocumentShouldNotBeFound("invoiceNumber.in=" + UPDATED_INVOICE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByInvoiceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where invoiceNumber is not null
        defaultScannedDocumentShouldBeFound("invoiceNumber.specified=true");

        // Get all the scannedDocumentList where invoiceNumber is null
        defaultScannedDocumentShouldNotBeFound("invoiceNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultScannedDocumentShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the scannedDocumentList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultScannedDocumentShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultScannedDocumentShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the scannedDocumentList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultScannedDocumentShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionId is not null
        defaultScannedDocumentShouldBeFound("transactionId.specified=true");

        // Get all the scannedDocumentList where transactionId is null
        defaultScannedDocumentShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultScannedDocumentShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the scannedDocumentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultScannedDocumentShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultScannedDocumentShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the scannedDocumentList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultScannedDocumentShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionDate is not null
        defaultScannedDocumentShouldBeFound("transactionDate.specified=true");

        // Get all the scannedDocumentList where transactionDate is null
        defaultScannedDocumentShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionDate greater than or equals to DEFAULT_TRANSACTION_DATE
        defaultScannedDocumentShouldBeFound("transactionDate.greaterOrEqualThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the scannedDocumentList where transactionDate greater than or equals to UPDATED_TRANSACTION_DATE
        defaultScannedDocumentShouldNotBeFound("transactionDate.greaterOrEqualThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where transactionDate less than or equals to DEFAULT_TRANSACTION_DATE
        defaultScannedDocumentShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the scannedDocumentList where transactionDate less than or equals to UPDATED_TRANSACTION_DATE
        defaultScannedDocumentShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScannedDocumentShouldBeFound(String filter) throws Exception {
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].approvalMemoDocumentContentType").value(hasItem(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalMemoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_MEMO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherScannedDocumentContentType").value(hasItem(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherScannedDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_SCANNED_DOCUMENT))));

        // Check, that the count call also returns 1
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScannedDocumentShouldNotBeFound(String filter) throws Exception {
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScannedDocument() throws Exception {
        // Get the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        int databaseSizeBeforeUpdate = scannedDocumentRepository.findAll().size();

        // Update the scannedDocument
        ScannedDocument updatedScannedDocument = scannedDocumentRepository.findById(scannedDocument.getId()).get();
        // Disconnect from session so that the updates on updatedScannedDocument are not directly saved in db
        em.detach(updatedScannedDocument);
        updatedScannedDocument
            .documentName(UPDATED_DOCUMENT_NAME)
            .description(UPDATED_DESCRIPTION)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .invoiceDocument(UPDATED_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(UPDATED_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .approvalMemoDocument(UPDATED_APPROVAL_MEMO_DOCUMENT)
            .approvalMemoDocumentContentType(UPDATED_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)
            .otherScannedDocument(UPDATED_OTHER_SCANNED_DOCUMENT)
            .otherScannedDocumentContentType(UPDATED_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE);
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(updatedScannedDocument);

        restScannedDocumentMockMvc.perform(put("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeUpdate);
        ScannedDocument testScannedDocument = scannedDocumentList.get(scannedDocumentList.size() - 1);
        assertThat(testScannedDocument.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testScannedDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScannedDocument.getInvoiceNumber()).isEqualTo(UPDATED_INVOICE_NUMBER);
        assertThat(testScannedDocument.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testScannedDocument.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testScannedDocument.getInvoiceDocument()).isEqualTo(UPDATED_INVOICE_DOCUMENT);
        assertThat(testScannedDocument.getInvoiceDocumentContentType()).isEqualTo(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getRequisitionDocument()).isEqualTo(UPDATED_REQUISITION_DOCUMENT);
        assertThat(testScannedDocument.getRequisitionDocumentContentType()).isEqualTo(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getApprovalMemoDocument()).isEqualTo(UPDATED_APPROVAL_MEMO_DOCUMENT);
        assertThat(testScannedDocument.getApprovalMemoDocumentContentType()).isEqualTo(UPDATED_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getOtherScannedDocument()).isEqualTo(UPDATED_OTHER_SCANNED_DOCUMENT);
        assertThat(testScannedDocument.getOtherScannedDocumentContentType()).isEqualTo(UPDATED_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).save(testScannedDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingScannedDocument() throws Exception {
        int databaseSizeBeforeUpdate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScannedDocumentMockMvc.perform(put("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(0)).save(scannedDocument);
    }

    @Test
    @Transactional
    public void deleteScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        int databaseSizeBeforeDelete = scannedDocumentRepository.findAll().size();

        // Delete the scannedDocument
        restScannedDocumentMockMvc.perform(delete("/api/scanned-documents/{id}", scannedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).deleteById(scannedDocument.getId());
    }

    @Test
    @Transactional
    public void searchScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);
        when(mockScannedDocumentSearchRepository.search(queryStringQuery("id:" + scannedDocument.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(scannedDocument), PageRequest.of(0, 1), 1));
        // Search the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/_search/scanned-documents?query=id:" + scannedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].approvalMemoDocumentContentType").value(hasItem(DEFAULT_APPROVAL_MEMO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalMemoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_MEMO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherScannedDocumentContentType").value(hasItem(DEFAULT_OTHER_SCANNED_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherScannedDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_SCANNED_DOCUMENT))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScannedDocument.class);
        ScannedDocument scannedDocument1 = new ScannedDocument();
        scannedDocument1.setId(1L);
        ScannedDocument scannedDocument2 = new ScannedDocument();
        scannedDocument2.setId(scannedDocument1.getId());
        assertThat(scannedDocument1).isEqualTo(scannedDocument2);
        scannedDocument2.setId(2L);
        assertThat(scannedDocument1).isNotEqualTo(scannedDocument2);
        scannedDocument1.setId(null);
        assertThat(scannedDocument1).isNotEqualTo(scannedDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScannedDocumentDTO.class);
        ScannedDocumentDTO scannedDocumentDTO1 = new ScannedDocumentDTO();
        scannedDocumentDTO1.setId(1L);
        ScannedDocumentDTO scannedDocumentDTO2 = new ScannedDocumentDTO();
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO2.setId(scannedDocumentDTO1.getId());
        assertThat(scannedDocumentDTO1).isEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO2.setId(2L);
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO1.setId(null);
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scannedDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scannedDocumentMapper.fromId(null)).isNull();
    }
}
