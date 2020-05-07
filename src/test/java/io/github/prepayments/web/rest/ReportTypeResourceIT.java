package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.ReportType;
import io.github.prepayments.repository.ReportTypeRepository;
import io.github.prepayments.service.ReportTypeService;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.mapper.ReportTypeMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.ReportTypeQueryService;

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
import java.util.List;

import static io.github.prepayments.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.prepayments.domain.enumeration.ReportMediumTypes;
/**
 * Integration tests for the {@Link ReportTypeResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class ReportTypeResourceIT {

    private static final String DEFAULT_REPORT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_MODEL_NAME = "BBBBBBBBBB";

    private static final ReportMediumTypes DEFAULT_REPORT_MEDIUM_TYPE = ReportMediumTypes.EXCEL;
    private static final ReportMediumTypes UPDATED_REPORT_MEDIUM_TYPE = ReportMediumTypes.PDF;

    private static final String DEFAULT_REPORT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_REPORT_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private ReportTypeRepository reportTypeRepository;

    @Autowired
    private ReportTypeMapper reportTypeMapper;

    @Autowired
    private ReportTypeService reportTypeService;

    @Autowired
    private ReportTypeQueryService reportTypeQueryService;

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

    private MockMvc restReportTypeMockMvc;

    private ReportType reportType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportTypeResource reportTypeResource = new ReportTypeResource(reportTypeService, reportTypeQueryService);
        this.restReportTypeMockMvc = MockMvcBuilders.standaloneSetup(reportTypeResource)
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
    public static ReportType createEntity(EntityManager em) {
        ReportType reportType = new ReportType()
            .reportModelName(DEFAULT_REPORT_MODEL_NAME)
            .reportMediumType(DEFAULT_REPORT_MEDIUM_TYPE)
            .reportPassword(DEFAULT_REPORT_PASSWORD);
        return reportType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportType createUpdatedEntity(EntityManager em) {
        ReportType reportType = new ReportType()
            .reportModelName(UPDATED_REPORT_MODEL_NAME)
            .reportMediumType(UPDATED_REPORT_MEDIUM_TYPE)
            .reportPassword(UPDATED_REPORT_PASSWORD);
        return reportType;
    }

    @BeforeEach
    public void initTest() {
        reportType = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportType() throws Exception {
        int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();

        // Create the ReportType
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);
        restReportTypeMockMvc.perform(post("/api/report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
        assertThat(testReportType.getReportModelName()).isEqualTo(DEFAULT_REPORT_MODEL_NAME);
        assertThat(testReportType.getReportMediumType()).isEqualTo(DEFAULT_REPORT_MEDIUM_TYPE);
        assertThat(testReportType.getReportPassword()).isEqualTo(DEFAULT_REPORT_PASSWORD);
    }

    @Test
    @Transactional
    public void createReportTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportTypeRepository.findAll().size();

        // Create the ReportType with an existing ID
        reportType.setId(1L);
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportTypeMockMvc.perform(post("/api/report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllReportTypes() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportModelName").value(hasItem(DEFAULT_REPORT_MODEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].reportMediumType").value(hasItem(DEFAULT_REPORT_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reportPassword").value(hasItem(DEFAULT_REPORT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get the reportType
        restReportTypeMockMvc.perform(get("/api/report-types/{id}", reportType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportType.getId().intValue()))
            .andExpect(jsonPath("$.reportModelName").value(DEFAULT_REPORT_MODEL_NAME.toString()))
            .andExpect(jsonPath("$.reportMediumType").value(DEFAULT_REPORT_MEDIUM_TYPE.toString()))
            .andExpect(jsonPath("$.reportPassword").value(DEFAULT_REPORT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportModelNameIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportModelName equals to DEFAULT_REPORT_MODEL_NAME
        defaultReportTypeShouldBeFound("reportModelName.equals=" + DEFAULT_REPORT_MODEL_NAME);

        // Get all the reportTypeList where reportModelName equals to UPDATED_REPORT_MODEL_NAME
        defaultReportTypeShouldNotBeFound("reportModelName.equals=" + UPDATED_REPORT_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportModelNameIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportModelName in DEFAULT_REPORT_MODEL_NAME or UPDATED_REPORT_MODEL_NAME
        defaultReportTypeShouldBeFound("reportModelName.in=" + DEFAULT_REPORT_MODEL_NAME + "," + UPDATED_REPORT_MODEL_NAME);

        // Get all the reportTypeList where reportModelName equals to UPDATED_REPORT_MODEL_NAME
        defaultReportTypeShouldNotBeFound("reportModelName.in=" + UPDATED_REPORT_MODEL_NAME);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportModelNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportModelName is not null
        defaultReportTypeShouldBeFound("reportModelName.specified=true");

        // Get all the reportTypeList where reportModelName is null
        defaultReportTypeShouldNotBeFound("reportModelName.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportMediumTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportMediumType equals to DEFAULT_REPORT_MEDIUM_TYPE
        defaultReportTypeShouldBeFound("reportMediumType.equals=" + DEFAULT_REPORT_MEDIUM_TYPE);

        // Get all the reportTypeList where reportMediumType equals to UPDATED_REPORT_MEDIUM_TYPE
        defaultReportTypeShouldNotBeFound("reportMediumType.equals=" + UPDATED_REPORT_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportMediumTypeIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportMediumType in DEFAULT_REPORT_MEDIUM_TYPE or UPDATED_REPORT_MEDIUM_TYPE
        defaultReportTypeShouldBeFound("reportMediumType.in=" + DEFAULT_REPORT_MEDIUM_TYPE + "," + UPDATED_REPORT_MEDIUM_TYPE);

        // Get all the reportTypeList where reportMediumType equals to UPDATED_REPORT_MEDIUM_TYPE
        defaultReportTypeShouldNotBeFound("reportMediumType.in=" + UPDATED_REPORT_MEDIUM_TYPE);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportMediumTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportMediumType is not null
        defaultReportTypeShouldBeFound("reportMediumType.specified=true");

        // Get all the reportTypeList where reportMediumType is null
        defaultReportTypeShouldNotBeFound("reportMediumType.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportPassword equals to DEFAULT_REPORT_PASSWORD
        defaultReportTypeShouldBeFound("reportPassword.equals=" + DEFAULT_REPORT_PASSWORD);

        // Get all the reportTypeList where reportPassword equals to UPDATED_REPORT_PASSWORD
        defaultReportTypeShouldNotBeFound("reportPassword.equals=" + UPDATED_REPORT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportPassword in DEFAULT_REPORT_PASSWORD or UPDATED_REPORT_PASSWORD
        defaultReportTypeShouldBeFound("reportPassword.in=" + DEFAULT_REPORT_PASSWORD + "," + UPDATED_REPORT_PASSWORD);

        // Get all the reportTypeList where reportPassword equals to UPDATED_REPORT_PASSWORD
        defaultReportTypeShouldNotBeFound("reportPassword.in=" + UPDATED_REPORT_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllReportTypesByReportPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        // Get all the reportTypeList where reportPassword is not null
        defaultReportTypeShouldBeFound("reportPassword.specified=true");

        // Get all the reportTypeList where reportPassword is null
        defaultReportTypeShouldNotBeFound("reportPassword.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportTypeShouldBeFound(String filter) throws Exception {
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportType.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportModelName").value(hasItem(DEFAULT_REPORT_MODEL_NAME)))
            .andExpect(jsonPath("$.[*].reportMediumType").value(hasItem(DEFAULT_REPORT_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].reportPassword").value(hasItem(DEFAULT_REPORT_PASSWORD)));

        // Check, that the count call also returns 1
        restReportTypeMockMvc.perform(get("/api/report-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportTypeShouldNotBeFound(String filter) throws Exception {
        restReportTypeMockMvc.perform(get("/api/report-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportTypeMockMvc.perform(get("/api/report-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReportType() throws Exception {
        // Get the reportType
        restReportTypeMockMvc.perform(get("/api/report-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

        // Update the reportType
        ReportType updatedReportType = reportTypeRepository.findById(reportType.getId()).get();
        // Disconnect from session so that the updates on updatedReportType are not directly saved in db
        em.detach(updatedReportType);
        updatedReportType
            .reportModelName(UPDATED_REPORT_MODEL_NAME)
            .reportMediumType(UPDATED_REPORT_MEDIUM_TYPE)
            .reportPassword(UPDATED_REPORT_PASSWORD);
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(updatedReportType);

        restReportTypeMockMvc.perform(put("/api/report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);
        ReportType testReportType = reportTypeList.get(reportTypeList.size() - 1);
        assertThat(testReportType.getReportModelName()).isEqualTo(UPDATED_REPORT_MODEL_NAME);
        assertThat(testReportType.getReportMediumType()).isEqualTo(UPDATED_REPORT_MEDIUM_TYPE);
        assertThat(testReportType.getReportPassword()).isEqualTo(UPDATED_REPORT_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingReportType() throws Exception {
        int databaseSizeBeforeUpdate = reportTypeRepository.findAll().size();

        // Create the ReportType
        ReportTypeDTO reportTypeDTO = reportTypeMapper.toDto(reportType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportTypeMockMvc.perform(put("/api/report-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportType in the database
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReportType() throws Exception {
        // Initialize the database
        reportTypeRepository.saveAndFlush(reportType);

        int databaseSizeBeforeDelete = reportTypeRepository.findAll().size();

        // Delete the reportType
        restReportTypeMockMvc.perform(delete("/api/report-types/{id}", reportType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ReportType> reportTypeList = reportTypeRepository.findAll();
        assertThat(reportTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportType.class);
        ReportType reportType1 = new ReportType();
        reportType1.setId(1L);
        ReportType reportType2 = new ReportType();
        reportType2.setId(reportType1.getId());
        assertThat(reportType1).isEqualTo(reportType2);
        reportType2.setId(2L);
        assertThat(reportType1).isNotEqualTo(reportType2);
        reportType1.setId(null);
        assertThat(reportType1).isNotEqualTo(reportType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportTypeDTO.class);
        ReportTypeDTO reportTypeDTO1 = new ReportTypeDTO();
        reportTypeDTO1.setId(1L);
        ReportTypeDTO reportTypeDTO2 = new ReportTypeDTO();
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
        reportTypeDTO2.setId(reportTypeDTO1.getId());
        assertThat(reportTypeDTO1).isEqualTo(reportTypeDTO2);
        reportTypeDTO2.setId(2L);
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
        reportTypeDTO1.setId(null);
        assertThat(reportTypeDTO1).isNotEqualTo(reportTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportTypeMapper.fromId(null)).isNull();
    }
}
