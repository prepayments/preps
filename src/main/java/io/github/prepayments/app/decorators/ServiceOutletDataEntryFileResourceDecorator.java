package io.github.prepayments.app.decorators;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.ServiceOutletFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.ServiceOutletDataFileMessageService;
import io.github.prepayments.app.token.FileTokenProvider;
import io.github.prepayments.service.ServiceOutletDataEntryFileService;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.web.rest.ServiceOutletDataEntryFileResource;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link io.github.prepayments.domain.ServiceOutletDataEntryFile}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ServiceOutletDataEntryFileResourceDecorator implements io.github.prepayments.app.decorators.IServiceOutletDataEntryFileResource {

    private final ServiceOutletDataEntryFileResource serviceOutletDataEntryFileResource;
    private final ServiceOutletDataFileMessageService serviceOutletDataFileMessageService;

    private static final String ENTITY_NAME = "dataEntryServiceOutletDataEntryFile";
    private final ServiceOutletDataEntryFileService serviceOutletDataEntryFileService;
    private final FileTokenProvider excelFileProvider;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public ServiceOutletDataEntryFileResourceDecorator(final ServiceOutletDataEntryFileService serviceOutletDataEntryFileService,
                                                       final ServiceOutletDataFileMessageService serviceOutletDataFileMessageService,
                                                       final @Qualifier("serviceOutletDataEntryFileResourceDelegate") ServiceOutletDataEntryFileResource serviceOutletDataEntryFileResource,
                                                       final FileTokenProvider excelFileProvider) {
        this.serviceOutletDataEntryFileResource = serviceOutletDataEntryFileResource;
        this.serviceOutletDataEntryFileService = serviceOutletDataEntryFileService;
        this.serviceOutletDataFileMessageService = serviceOutletDataFileMessageService;
        this.excelFileProvider = excelFileProvider;
    }

    /**
     * {@code POST  /service-outlet-data-entry-files} : Create a new serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the serviceOutletDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOutletDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * serviceOutletDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/service-outlet-data-entry-files")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> createServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOutletDataEntryFile : {}", serviceOutletDataEntryFileDTO);
        if (serviceOutletDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOutletDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        serviceOutletDataEntryFileDTO.setFileToken(excelFileProvider.getFileToken(serviceOutletDataEntryFileDTO));
        ServiceOutletDataEntryFileDTO result = serviceOutletDataEntryFileService.save(serviceOutletDataEntryFileDTO);

        // @formatter:off
        serviceOutletDataFileMessageService.sendMessage(
            ServiceOutletFileUploadNotification.builder()
                                                 .id(result.getId())
                                                 .timeStamp(System.currentTimeMillis())
                                                 .fileToken(serviceOutletDataEntryFileDTO.getFileToken())
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOutletDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * serviceOutletDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the serviceOutletDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/service-outlet-data-entry-files")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> updateServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        return serviceOutletDataEntryFileResource.updateServiceOutletDataEntryFile(serviceOutletDataEntryFileDTO);
    }

    /**
     * {@code GET  /service-outlet-data-entry-files} : get all the serviceOutletDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOutletDataEntryFiles in body.
     */
    @Override
    @GetMapping("/service-outlet-data-entry-files")
    public ResponseEntity<List<ServiceOutletDataEntryFileDTO>> getAllServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria, Pageable pageable,
                                                                                                 @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return serviceOutletDataEntryFileResource.getAllServiceOutletDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /service-outlet-data-entry-files/count} : count all the serviceOutletDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/service-outlet-data-entry-files/count")
    public ResponseEntity<Long> countServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria) {
        return serviceOutletDataEntryFileResource.countServiceOutletDataEntryFiles(criteria);
    }

    /**
     * {@code GET  /service-outlet-data-entry-files/:id} : get the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOutletDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/service-outlet-data-entry-files/{id}")
    public ResponseEntity<ServiceOutletDataEntryFileDTO> getServiceOutletDataEntryFile(@PathVariable Long id) {
        return serviceOutletDataEntryFileResource.getServiceOutletDataEntryFile(id);
    }

    /**
     * {@code DELETE  /service-outlet-data-entry-files/:id} : delete the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/service-outlet-data-entry-files/{id}")
    public ResponseEntity<Void> deleteServiceOutletDataEntryFile(@PathVariable Long id) {
        return serviceOutletDataEntryFileResource.deleteServiceOutletDataEntryFile(id);
    }

    /**
     * {@code SEARCH  /_search/service-outlet-data-entry-files?query=:query} : search for the serviceOutletDataEntryFile corresponding to the query.
     *
     * @param query    the query of the serviceOutletDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/service-outlet-data-entry-files")
    public ResponseEntity<List<ServiceOutletDataEntryFileDTO>> searchServiceOutletDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                                 UriComponentsBuilder uriBuilder) {
        return serviceOutletDataEntryFileResource.searchServiceOutletDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
