package io.github.prepayments.web.rest;

import io.github.prepayments.app.messaging.notifications.dto.AmortizationFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.AmortizationDataFileMessageService;
import io.github.prepayments.service.AmortizationDataEntryFileService;
import io.github.prepayments.web.rest.errors.BadRequestAlertException;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.service.dto.AmortizationDataEntryFileCriteria;
import io.github.prepayments.service.AmortizationDataEntryFileQueryService;

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
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationDataEntryFile}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationDataEntryFileResource {

    private final Logger log = LoggerFactory.getLogger(AmortizationDataEntryFileResource.class);

    private static final String ENTITY_NAME = "dataEntryAmortizationDataEntryFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AmortizationDataFileMessageService amortizationDataFileMessageService;

    private final AmortizationDataEntryFileService amortizationDataEntryFileService;

    private final AmortizationDataEntryFileQueryService amortizationDataEntryFileQueryService;

    public AmortizationDataEntryFileResource(AmortizationDataEntryFileService amortizationDataEntryFileService, AmortizationDataEntryFileQueryService amortizationDataEntryFileQueryService,
                                             final AmortizationDataFileMessageService amortizationDataFileMessageService) {
        this.amortizationDataEntryFileService = amortizationDataEntryFileService;
        this.amortizationDataEntryFileQueryService = amortizationDataEntryFileQueryService;
        this.amortizationDataFileMessageService = amortizationDataFileMessageService;
    }

    /**
     * {@code POST  /amortization-data-entry-files} : Create a new amortizationDataEntryFile.
     *
     * @param amortizationDataEntryFileDTO the amortizationDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the amortizationDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-data-entry-files")
    public ResponseEntity<AmortizationDataEntryFileDTO> createAmortizationDataEntryFile(@Valid @RequestBody AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationDataEntryFile : {}", amortizationDataEntryFileDTO);
        if (amortizationDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AmortizationDataEntryFileDTO result = amortizationDataEntryFileService.save(amortizationDataEntryFileDTO);

        // @formatter:off
        amortizationDataFileMessageService.sendMessage(
            AmortizationFileUploadNotification.builder()
                                                 .id(result.getId())
                                                 .timeStamp(System.currentTimeMillis())
                                                 .fileUpload(result.getDataEntryFile())
                                              .build());
        // @formatter:on


        return ResponseEntity.created(new URI("/api/amortization-data-entry-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /amortization-data-entry-files} : Updates an existing amortizationDataEntryFile.
     *
     * @param amortizationDataEntryFileDTO the amortizationDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationDataEntryFileDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationDataEntryFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-data-entry-files")
    public ResponseEntity<AmortizationDataEntryFileDTO> updateAmortizationDataEntryFile(@Valid @RequestBody AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to update AmortizationDataEntryFile : {}", amortizationDataEntryFileDTO);
        if (amortizationDataEntryFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AmortizationDataEntryFileDTO result = amortizationDataEntryFileService.save(amortizationDataEntryFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, amortizationDataEntryFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /amortization-data-entry-files} : get all the amortizationDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationDataEntryFiles in body.
     */
    @GetMapping("/amortization-data-entry-files")
    public ResponseEntity<List<AmortizationDataEntryFileDTO>> getAllAmortizationDataEntryFiles(AmortizationDataEntryFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AmortizationDataEntryFiles by criteria: {}", criteria);
        Page<AmortizationDataEntryFileDTO> page = amortizationDataEntryFileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /amortization-data-entry-files/count} : count all the amortizationDataEntryFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/amortization-data-entry-files/count")
    public ResponseEntity<Long> countAmortizationDataEntryFiles(AmortizationDataEntryFileCriteria criteria) {
        log.debug("REST request to count AmortizationDataEntryFiles by criteria: {}", criteria);
        return ResponseEntity.ok().body(amortizationDataEntryFileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /amortization-data-entry-files/:id} : get the "id" amortizationDataEntryFile.
     *
     * @param id the id of the amortizationDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-data-entry-files/{id}")
    public ResponseEntity<AmortizationDataEntryFileDTO> getAmortizationDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to get AmortizationDataEntryFile : {}", id);
        Optional<AmortizationDataEntryFileDTO> amortizationDataEntryFileDTO = amortizationDataEntryFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(amortizationDataEntryFileDTO);
    }

    /**
     * {@code DELETE  /amortization-data-entry-files/:id} : delete the "id" amortizationDataEntryFile.
     *
     * @param id the id of the amortizationDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-data-entry-files/{id}")
    public ResponseEntity<Void> deleteAmortizationDataEntryFile(@PathVariable Long id) {
        log.debug("REST request to delete AmortizationDataEntryFile : {}", id);
        amortizationDataEntryFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
