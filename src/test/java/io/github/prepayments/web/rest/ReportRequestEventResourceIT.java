package io.github.prepayments.web.rest;

import io.github.prepayments.PrepsApp;
import io.github.prepayments.domain.ReportRequestEvent;
import io.github.prepayments.domain.ReportType;
import io.github.prepayments.repository.ReportRequestEventRepository;
import io.github.prepayments.repository.search.ReportRequestEventSearchRepository;
import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.service.dto.ReportRequestEventDTO;
import io.github.prepayments.service.mapper.ReportRequestEventMapper;
import io.github.prepayments.web.rest.errors.ExceptionTranslator;
import io.github.prepayments.service.dto.ReportRequestEventCriteria;
import io.github.prepayments.service.ReportRequestEventQueryService;

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
 * Integration tests for the {@Link ReportRequestEventResource} REST controller.
 */
@SpringBootTest(classes = PrepsApp.class)
public class ReportRequestEventResourceIT {

    private static final LocalDate DEFAULT_REPORT_REQUEST_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_REPORT_REQUEST_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REQUESTED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REQUESTED_BY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_REPORT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REPORT_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REPORT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REPORT_FILE_CONTENT_TYPE = "image/png";

    @Autowired
    private ReportRequestEventRepository reportRequestEventRepository;

    @Autowired
    private ReportRequestEventMapper reportRequestEventMapper;

    @Autowired
    private ReportRequestEventService reportRequestEventService;

    /**
     * This repository is mocked in the io.github.prepayments.repository.search test package.
     *
     * @see io.github.prepayments.repository.search.ReportRequestEventSearchRepositoryMockConfiguration
     */
    @Autowired
    private ReportRequestEventSearchRepository mockReportRequestEventSearchRepository;

    @Autowired
    private ReportRequestEventQueryService reportRequestEventQueryService;

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

    private MockMvc restReportRequestEventMockMvc;

    private ReportRequestEvent reportRequestEvent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ReportRequestEventResource reportRequestEventResource = new ReportRequestEventResource(reportRequestEventService, reportRequestEventQueryService);
        this.restReportRequestEventMockMvc = MockMvcBuilders.standaloneSetup(reportRequestEventResource)
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
    public static ReportRequestEvent createEntity(EntityManager em) {
        ReportRequestEvent reportRequestEvent = new ReportRequestEvent()
            .reportRequestDate(DEFAULT_REPORT_REQUEST_DATE)
            .requestedBy(DEFAULT_REQUESTED_BY)
            .reportFile(DEFAULT_REPORT_FILE)
            .reportFileContentType(DEFAULT_REPORT_FILE_CONTENT_TYPE);
        return reportRequestEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReportRequestEvent createUpdatedEntity(EntityManager em) {
        ReportRequestEvent reportRequestEvent = new ReportRequestEvent()
            .reportRequestDate(UPDATED_REPORT_REQUEST_DATE)
            .requestedBy(UPDATED_REQUESTED_BY)
            .reportFile(UPDATED_REPORT_FILE)
            .reportFileContentType(UPDATED_REPORT_FILE_CONTENT_TYPE);
        return reportRequestEvent;
    }

    @BeforeEach
    public void initTest() {
        reportRequestEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createReportRequestEvent() throws Exception {
        int databaseSizeBeforeCreate = reportRequestEventRepository.findAll().size();

        // Create the ReportRequestEvent
        ReportRequestEventDTO reportRequestEventDTO = reportRequestEventMapper.toDto(reportRequestEvent);
        restReportRequestEventMockMvc.perform(post("/api/report-request-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportRequestEventDTO)))
            .andExpect(status().isCreated());

        // Validate the ReportRequestEvent in the database
        List<ReportRequestEvent> reportRequestEventList = reportRequestEventRepository.findAll();
        assertThat(reportRequestEventList).hasSize(databaseSizeBeforeCreate + 1);
        ReportRequestEvent testReportRequestEvent = reportRequestEventList.get(reportRequestEventList.size() - 1);
        assertThat(testReportRequestEvent.getReportRequestDate()).isEqualTo(DEFAULT_REPORT_REQUEST_DATE);
        assertThat(testReportRequestEvent.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testReportRequestEvent.getReportFile()).isEqualTo(DEFAULT_REPORT_FILE);
        assertThat(testReportRequestEvent.getReportFileContentType()).isEqualTo(DEFAULT_REPORT_FILE_CONTENT_TYPE);

        // Validate the ReportRequestEvent in Elasticsearch
        verify(mockReportRequestEventSearchRepository, times(1)).save(testReportRequestEvent);
    }

    @Test
    @Transactional
    public void createReportRequestEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reportRequestEventRepository.findAll().size();

        // Create the ReportRequestEvent with an existing ID
        reportRequestEvent.setId(1L);
        ReportRequestEventDTO reportRequestEventDTO = reportRequestEventMapper.toDto(reportRequestEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReportRequestEventMockMvc.perform(post("/api/report-request-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportRequestEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportRequestEvent in the database
        List<ReportRequestEvent> reportRequestEventList = reportRequestEventRepository.findAll();
        assertThat(reportRequestEventList).hasSize(databaseSizeBeforeCreate);

        // Validate the ReportRequestEvent in Elasticsearch
        verify(mockReportRequestEventSearchRepository, times(0)).save(reportRequestEvent);
    }


    @Test
    @Transactional
    public void getAllReportRequestEvents() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList
        restReportRequestEventMockMvc.perform(get("/api/report-request-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportRequestEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportRequestDate").value(hasItem(DEFAULT_REPORT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY.toString())))
            .andExpect(jsonPath("$.[*].reportFileContentType").value(hasItem(DEFAULT_REPORT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reportFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT_FILE))));
    }

    @Test
    @Transactional
    public void getReportRequestEvent() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get the reportRequestEvent
        restReportRequestEventMockMvc.perform(get("/api/report-request-events/{id}", reportRequestEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(reportRequestEvent.getId().intValue()))
            .andExpect(jsonPath("$.reportRequestDate").value(DEFAULT_REPORT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.requestedBy").value(DEFAULT_REQUESTED_BY.toString()))
            .andExpect(jsonPath("$.reportFileContentType").value(DEFAULT_REPORT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.reportFile").value(Base64Utils.encodeToString(DEFAULT_REPORT_FILE)));
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportRequestDateIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where reportRequestDate equals to DEFAULT_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldBeFound("reportRequestDate.equals=" + DEFAULT_REPORT_REQUEST_DATE);

        // Get all the reportRequestEventList where reportRequestDate equals to UPDATED_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldNotBeFound("reportRequestDate.equals=" + UPDATED_REPORT_REQUEST_DATE);
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportRequestDateIsInShouldWork() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where reportRequestDate in DEFAULT_REPORT_REQUEST_DATE or UPDATED_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldBeFound("reportRequestDate.in=" + DEFAULT_REPORT_REQUEST_DATE + "," + UPDATED_REPORT_REQUEST_DATE);

        // Get all the reportRequestEventList where reportRequestDate equals to UPDATED_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldNotBeFound("reportRequestDate.in=" + UPDATED_REPORT_REQUEST_DATE);
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportRequestDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where reportRequestDate is not null
        defaultReportRequestEventShouldBeFound("reportRequestDate.specified=true");

        // Get all the reportRequestEventList where reportRequestDate is null
        defaultReportRequestEventShouldNotBeFound("reportRequestDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportRequestDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where reportRequestDate greater than or equals to DEFAULT_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldBeFound("reportRequestDate.greaterOrEqualThan=" + DEFAULT_REPORT_REQUEST_DATE);

        // Get all the reportRequestEventList where reportRequestDate greater than or equals to UPDATED_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldNotBeFound("reportRequestDate.greaterOrEqualThan=" + UPDATED_REPORT_REQUEST_DATE);
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportRequestDateIsLessThanSomething() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where reportRequestDate less than or equals to DEFAULT_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldNotBeFound("reportRequestDate.lessThan=" + DEFAULT_REPORT_REQUEST_DATE);

        // Get all the reportRequestEventList where reportRequestDate less than or equals to UPDATED_REPORT_REQUEST_DATE
        defaultReportRequestEventShouldBeFound("reportRequestDate.lessThan=" + UPDATED_REPORT_REQUEST_DATE);
    }


    @Test
    @Transactional
    public void getAllReportRequestEventsByRequestedByIsEqualToSomething() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where requestedBy equals to DEFAULT_REQUESTED_BY
        defaultReportRequestEventShouldBeFound("requestedBy.equals=" + DEFAULT_REQUESTED_BY);

        // Get all the reportRequestEventList where requestedBy equals to UPDATED_REQUESTED_BY
        defaultReportRequestEventShouldNotBeFound("requestedBy.equals=" + UPDATED_REQUESTED_BY);
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByRequestedByIsInShouldWork() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where requestedBy in DEFAULT_REQUESTED_BY or UPDATED_REQUESTED_BY
        defaultReportRequestEventShouldBeFound("requestedBy.in=" + DEFAULT_REQUESTED_BY + "," + UPDATED_REQUESTED_BY);

        // Get all the reportRequestEventList where requestedBy equals to UPDATED_REQUESTED_BY
        defaultReportRequestEventShouldNotBeFound("requestedBy.in=" + UPDATED_REQUESTED_BY);
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByRequestedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        // Get all the reportRequestEventList where requestedBy is not null
        defaultReportRequestEventShouldBeFound("requestedBy.specified=true");

        // Get all the reportRequestEventList where requestedBy is null
        defaultReportRequestEventShouldNotBeFound("requestedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllReportRequestEventsByReportTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ReportType reportType = ReportTypeResourceIT.createEntity(em);
        em.persist(reportType);
        em.flush();
        reportRequestEvent.setReportType(reportType);
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);
        Long reportTypeId = reportType.getId();

        // Get all the reportRequestEventList where reportType equals to reportTypeId
        defaultReportRequestEventShouldBeFound("reportTypeId.equals=" + reportTypeId);

        // Get all the reportRequestEventList where reportType equals to reportTypeId + 1
        defaultReportRequestEventShouldNotBeFound("reportTypeId.equals=" + (reportTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultReportRequestEventShouldBeFound(String filter) throws Exception {
        restReportRequestEventMockMvc.perform(get("/api/report-request-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportRequestEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportRequestDate").value(hasItem(DEFAULT_REPORT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY)))
            .andExpect(jsonPath("$.[*].reportFileContentType").value(hasItem(DEFAULT_REPORT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reportFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT_FILE))));

        // Check, that the count call also returns 1
        restReportRequestEventMockMvc.perform(get("/api/report-request-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultReportRequestEventShouldNotBeFound(String filter) throws Exception {
        restReportRequestEventMockMvc.perform(get("/api/report-request-events?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restReportRequestEventMockMvc.perform(get("/api/report-request-events/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingReportRequestEvent() throws Exception {
        // Get the reportRequestEvent
        restReportRequestEventMockMvc.perform(get("/api/report-request-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReportRequestEvent() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        int databaseSizeBeforeUpdate = reportRequestEventRepository.findAll().size();

        // Update the reportRequestEvent
        ReportRequestEvent updatedReportRequestEvent = reportRequestEventRepository.findById(reportRequestEvent.getId()).get();
        // Disconnect from session so that the updates on updatedReportRequestEvent are not directly saved in db
        em.detach(updatedReportRequestEvent);
        updatedReportRequestEvent
            .reportRequestDate(UPDATED_REPORT_REQUEST_DATE)
            .requestedBy(UPDATED_REQUESTED_BY)
            .reportFile(UPDATED_REPORT_FILE)
            .reportFileContentType(UPDATED_REPORT_FILE_CONTENT_TYPE);
        ReportRequestEventDTO reportRequestEventDTO = reportRequestEventMapper.toDto(updatedReportRequestEvent);

        restReportRequestEventMockMvc.perform(put("/api/report-request-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportRequestEventDTO)))
            .andExpect(status().isOk());

        // Validate the ReportRequestEvent in the database
        List<ReportRequestEvent> reportRequestEventList = reportRequestEventRepository.findAll();
        assertThat(reportRequestEventList).hasSize(databaseSizeBeforeUpdate);
        ReportRequestEvent testReportRequestEvent = reportRequestEventList.get(reportRequestEventList.size() - 1);
        assertThat(testReportRequestEvent.getReportRequestDate()).isEqualTo(UPDATED_REPORT_REQUEST_DATE);
        assertThat(testReportRequestEvent.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testReportRequestEvent.getReportFile()).isEqualTo(UPDATED_REPORT_FILE);
        assertThat(testReportRequestEvent.getReportFileContentType()).isEqualTo(UPDATED_REPORT_FILE_CONTENT_TYPE);

        // Validate the ReportRequestEvent in Elasticsearch
        verify(mockReportRequestEventSearchRepository, times(1)).save(testReportRequestEvent);
    }

    @Test
    @Transactional
    public void updateNonExistingReportRequestEvent() throws Exception {
        int databaseSizeBeforeUpdate = reportRequestEventRepository.findAll().size();

        // Create the ReportRequestEvent
        ReportRequestEventDTO reportRequestEventDTO = reportRequestEventMapper.toDto(reportRequestEvent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReportRequestEventMockMvc.perform(put("/api/report-request-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(reportRequestEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ReportRequestEvent in the database
        List<ReportRequestEvent> reportRequestEventList = reportRequestEventRepository.findAll();
        assertThat(reportRequestEventList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ReportRequestEvent in Elasticsearch
        verify(mockReportRequestEventSearchRepository, times(0)).save(reportRequestEvent);
    }

    @Test
    @Transactional
    public void deleteReportRequestEvent() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);

        int databaseSizeBeforeDelete = reportRequestEventRepository.findAll().size();

        // Delete the reportRequestEvent
        restReportRequestEventMockMvc.perform(delete("/api/report-request-events/{id}", reportRequestEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ReportRequestEvent> reportRequestEventList = reportRequestEventRepository.findAll();
        assertThat(reportRequestEventList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ReportRequestEvent in Elasticsearch
        verify(mockReportRequestEventSearchRepository, times(1)).deleteById(reportRequestEvent.getId());
    }

    @Test
    @Transactional
    public void searchReportRequestEvent() throws Exception {
        // Initialize the database
        reportRequestEventRepository.saveAndFlush(reportRequestEvent);
        when(mockReportRequestEventSearchRepository.search(queryStringQuery("id:" + reportRequestEvent.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(reportRequestEvent), PageRequest.of(0, 1), 1));
        // Search the reportRequestEvent
        restReportRequestEventMockMvc.perform(get("/api/_search/report-request-events?query=id:" + reportRequestEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reportRequestEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportRequestDate").value(hasItem(DEFAULT_REPORT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY)))
            .andExpect(jsonPath("$.[*].reportFileContentType").value(hasItem(DEFAULT_REPORT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].reportFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_REPORT_FILE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportRequestEvent.class);
        ReportRequestEvent reportRequestEvent1 = new ReportRequestEvent();
        reportRequestEvent1.setId(1L);
        ReportRequestEvent reportRequestEvent2 = new ReportRequestEvent();
        reportRequestEvent2.setId(reportRequestEvent1.getId());
        assertThat(reportRequestEvent1).isEqualTo(reportRequestEvent2);
        reportRequestEvent2.setId(2L);
        assertThat(reportRequestEvent1).isNotEqualTo(reportRequestEvent2);
        reportRequestEvent1.setId(null);
        assertThat(reportRequestEvent1).isNotEqualTo(reportRequestEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ReportRequestEventDTO.class);
        ReportRequestEventDTO reportRequestEventDTO1 = new ReportRequestEventDTO();
        reportRequestEventDTO1.setId(1L);
        ReportRequestEventDTO reportRequestEventDTO2 = new ReportRequestEventDTO();
        assertThat(reportRequestEventDTO1).isNotEqualTo(reportRequestEventDTO2);
        reportRequestEventDTO2.setId(reportRequestEventDTO1.getId());
        assertThat(reportRequestEventDTO1).isEqualTo(reportRequestEventDTO2);
        reportRequestEventDTO2.setId(2L);
        assertThat(reportRequestEventDTO1).isNotEqualTo(reportRequestEventDTO2);
        reportRequestEventDTO1.setId(null);
        assertThat(reportRequestEventDTO1).isNotEqualTo(reportRequestEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(reportRequestEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(reportRequestEventMapper.fromId(null)).isNull();
    }
}
