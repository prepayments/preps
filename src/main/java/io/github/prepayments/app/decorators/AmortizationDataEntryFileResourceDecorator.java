package io.github.prepayments.app.decorators;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.AmortizationDataFileMessageService;
import io.github.prepayments.service.AmortizationDataEntryFileService;
import io.github.prepayments.service.dto.AmortizationDataEntryFileCriteria;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.web.rest.AmortizationDataEntryFileResource;
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

@Slf4j
@RestController
@RequestMapping("/api")
public class AmortizationDataEntryFileResourceDecorator implements IAmortizationDataEntryFileResource {


    private final AmortizationDataEntryFileResource amortizationDataEntryFileResourceDelegate;
    private static final String ENTITY_NAME = "dataEntryAmortizationDataEntryFile";
    private final AmortizationDataFileMessageService amortizationDataFileMessageService;
    private final AmortizationDataEntryFileService amortizationDataEntryFileService;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AmortizationDataEntryFileResourceDecorator(final @Qualifier("amortizationDataEntryFileResourceDelegate") AmortizationDataEntryFileResource amortizationDataEntryFileResourceDelegate,
                                                      final AmortizationDataFileMessageService amortizationDataFileMessageService,
                                                      final AmortizationDataEntryFileService amortizationDataEntryFileService) {
        this.amortizationDataEntryFileResourceDelegate = amortizationDataEntryFileResourceDelegate;
        this.amortizationDataFileMessageService = amortizationDataFileMessageService;
        this.amortizationDataEntryFileService = amortizationDataEntryFileService;
    }

    /**
     * {@code POST  /amortization-data-entry-files} : Create a new amortizationDataEntryFile.
     *
     * @param amortizationDataEntryFileDTO the amortizationDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * amortizationDataEntryFile has already an ID.
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * amortizationDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the amortizationDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-data-entry-files")
    public ResponseEntity<AmortizationDataEntryFileDTO> updateAmortizationDataEntryFile(@Valid @RequestBody AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) throws URISyntaxException {
        return amortizationDataEntryFileResourceDelegate.updateAmortizationDataEntryFile(amortizationDataEntryFileDTO);
    }

    /**
     * {@code GET  /amortization-data-entry-files} : get all the amortizationDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationDataEntryFiles in body.
     */
    @GetMapping("/amortization-data-entry-files")
    public ResponseEntity<List<AmortizationDataEntryFileDTO>> getAllAmortizationDataEntryFiles(AmortizationDataEntryFileCriteria criteria, Pageable pageable,
                                                                                               @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {

        return amortizationDataEntryFileResourceDelegate.getAllAmortizationDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /amortization-data-entry-files/count} : count all the amortizationDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/amortization-data-entry-files/count")
    public ResponseEntity<Long> countAmortizationDataEntryFiles(AmortizationDataEntryFileCriteria criteria) {
        return amortizationDataEntryFileResourceDelegate.countAmortizationDataEntryFiles(criteria);
    }

    /**
     * {@code GET  /amortization-data-entry-files/:id} : get the "id" amortizationDataEntryFile.
     *
     * @param id the id of the amortizationDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-data-entry-files/{id}")
    public ResponseEntity<AmortizationDataEntryFileDTO> getAmortizationDataEntryFile(@PathVariable Long id) {
        return amortizationDataEntryFileResourceDelegate.getAmortizationDataEntryFile(id);
    }

    /**
     * {@code DELETE  /amortization-data-entry-files/:id} : delete the "id" amortizationDataEntryFile.
     *
     * @param id the id of the amortizationDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-data-entry-files/{id}")
    public ResponseEntity<Void> deleteAmortizationDataEntryFile(@PathVariable Long id) {
        return amortizationDataEntryFileResourceDelegate.deleteAmortizationDataEntryFile(id);
    }

    /**
     * {@code SEARCH  /_search/amortization-data-entry-files?query=:query} : search for the amortizationDataEntryFile corresponding to the query.
     *
     * @param query    the query of the amortizationDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-data-entry-files")
    public ResponseEntity<List<AmortizationDataEntryFileDTO>> searchAmortizationDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                               UriComponentsBuilder uriBuilder) {
        return amortizationDataEntryFileResourceDelegate.searchAmortizationDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
