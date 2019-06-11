package io.github.prepayments.web.rest;

import io.github.prepayments.app.messaging.notifications.dto.PrepaymentFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.PrepaymentDataFileMessageService;
import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.PrepaymentDataEntryFileQueryService;

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
 * REST controller for managing {@link io.github.prepayments.domain.PrepaymentDataEntryFile}.
 */
@RestController
@RequestMapping("/api")
public class PrepaymentDataEntryFileResource {

    private final Logger log = LoggerFactory.getLogger(PrepaymentDataEntryFileResource.class);

    private static final String ENTITY_NAME = "dataEntryPrepaymentDataEntryFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrepaymentDataFileMessageService prepaymentDataFileMessageService;
    private final PrepaymentDataEntryFileService prepaymentDataEntryFileService;

    private final PrepaymentDataEntryFileQueryService prepaymentDataEntryFileQueryService;

    public PrepaymentDataEntryFileResource(PrepaymentDataEntryFileService prepaymentDataEntryFileService, PrepaymentDataEntryFileQueryService prepaymentDataEntryFileQueryService,
                                           final PrepaymentDataFileMessageService prepaymentDataFileMessageService) {
        this.prepaymentDataEntryFileService = prepaymentDataEntryFileService;
        this.prepaymentDataEntryFileQueryService = prepaymentDataEntryFileQueryService;
        this.prepaymentDataFileMessageService = prepaymentDataFileMessageService;
    }

    /**
     * {@code POST  /prepayment-data-entry-files} : Create a new prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> createPrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        if (prepaymentDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new prepaymentDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileService.save(prepaymentDataEntryFileDTO);

        // @formatter:off
        prepaymentDataFileMessageService.sendMessage(PrepaymentFileUploadNotification.builder()
                                                     .id(result.getId())
                                                     .timeStamp(System.currentTimeMillis())
                                                     .fileUpload(result.getDataEntryFile())
                                                     .build()
        );
        // @formatter:on


        return ResponseEntity.created(new URI("/api/prepayment-data-entry-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prepayment-data-entry-files} : Updates an existing prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepaymentDataEntryFileDTO,
     * or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the prepaymentDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> updatePrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to update PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        if (prepaymentDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileService.save(prepaymentDataEntryFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, prepaymentDataEntryFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prepayment-data-entry-files} : get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepaymentDataEntryFiles in body.
     */
    @GetMapping("/prepayment-data-entry-files")
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> getAllPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get PrepaymentDataEntryFiles by criteria: {}", criteria);
        Page<PrepaymentDataEntryFileDTO> page = prepaymentDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /prepayment-data-entry-files/count} : count all the prepaymentDataEntryFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/prepayment-data-entry-files/count")
    public ResponseEntity<Long> countPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria) {
        log.debug("REST request to count PrepaymentDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(prepaymentDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /prepayment-data-entry-files/:id} : get the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepaymentDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<PrepaymentDataEntryFileDTO> getPrepaymentDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get PrepaymentDataEntryFile : {}", id);
        Optional<PrepaymentDataEntryFileDTO> prepaymentDataEntryFileDTO = prepaymentDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prepaymentDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /prepayment-data-entry-files/:id} : delete the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<Void> deletePrepaymentDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete PrepaymentDataEntryFile : {}", id);
        prepaymentDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
