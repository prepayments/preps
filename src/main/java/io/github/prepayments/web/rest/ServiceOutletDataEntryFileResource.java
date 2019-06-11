package io.github.prepayments.web.rest;

import io.github.prepayments.app.messaging.notifications.dto.ServiceOutletFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.ServiceOutletDataFileMessageService;
import io.github.prepayments.service.ServiceOutletDataEntryFileService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.ServiceOutletDataEntryFileQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.prepayments.domain.ServiceOutletDataEntryFile}.
 */
@RestController
@RequestMapping("/api")
public class ServiceOutletDataEntryFileResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletDataEntryFileResource.class);

    private static final String ENTITY_NAME = "dataEntryServiceOutletDataEntryFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOutletDataEntryFileService serviceOutletDataEntryFileService;
    private final ServiceOutletDataFileMessageService serviceOutletDataFileMessageService;
    private final ServiceOutletDataEntryFileQueryService serviceOutletDataEntryFileQueryService;

    public ServiceOutletDataEntryFileResource(ServiceOutletDataEntryFileService serviceOutletDataEntryFileService, ServiceOutletDataEntryFileQueryService serviceOutletDataEntryFileQueryService,
                                              final ServiceOutletDataFileMessageService serviceOutletDataFileMessageService) {
        this.serviceOutletDataEntryFileService = serviceOutletDataEntryFileService;
        this.serviceOutletDataEntryFileQueryService = serviceOutletDataEntryFileQueryService;
        this.serviceOutletDataFileMessageService = serviceOutletDataFileMessageService;
    }

    /**
     * {@code POST  /service-outlet-data-entry-files} : Create a new serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the serviceOutletDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOutletDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the serviceOutletDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-outlet-data-entry-files")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> createServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOutletDataEntryFile : {}", serviceOutletDataEntryFileDTO);
        if (serviceOutletDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOutletDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOutletDataEntryFileDTO result = serviceOutletDataEntryFileService.save(serviceOutletDataEntryFileDTO);

        // @formatter:off
        serviceOutletDataFileMessageService.sendMessage(
            ServiceOutletFileUploadNotification.builder()
                                                 .id(result.getId())
                                                 .timeStamp(System.currentTimeMillis())
                                                 .fileUpload(result.getDataEntryFile())
                                              .build());
        // @formatter:on

        return ResponseEntity.created(new URI("/api/service-outlet-data-entry-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-outlet-data-entry-files} : Updates an existing serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the serviceOutletDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOutletDataEntryFileDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOutletDataEntryFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOutletDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-outlet-data-entry-files")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> updateServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceOutletDataEntryFile : {}", serviceOutletDataEntryFileDTO);
        if (serviceOutletDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOutletDataEntryFileDTO result = serviceOutletDataEntryFileService.save(serviceOutletDataEntryFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceOutletDataEntryFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-outlet-data-entry-files} : get all the serviceOutletDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOutletDataEntryFiles in body.
     */
    @GetMapping("/service-outlet-data-entry-files")
    public ResponseEntity<List<ServiceOutletDataEntryFileDTO>> getAllServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceOutletDataEntryFiles by criteria: {}", criteria);
        Page<ServiceOutletDataEntryFileDTO> page = serviceOutletDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-outlet-data-entry-files/count} : count all the serviceOutletDataEntryFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-outlet-data-entry-files/count")
    public ResponseEntity<Long> countServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria) {
        log.debug("REST request to count ServiceOutletDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOutletDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-outlet-data-entry-files/:id} : get the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOutletDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-outlet-data-entry-files/{id}")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> getServiceOutletDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get ServiceOutletDataEntryFile : {}", id);
        Optional<ServiceOutletDataEntryFileDTO> serviceOutletDataEntryFileDTO = serviceOutletDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOutletDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /service-outlet-data-entry-files/:id} : delete the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-outlet-data-entry-files/{id}")
    public ResponseEntity<Void> deleteServiceOutletDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOutletDataEntryFile : {}", id);
        serviceOutletDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
