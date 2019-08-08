package io.github.prepayments.app.decorators;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.PrepaymentFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.PrepaymentDataFileMessageService;
import io.github.prepayments.app.token.FileTokenProvider;
import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.web.rest.PrepaymentDataEntryFileResource;
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
public class PrepaymentDataEntryFileResourceDecorator implements IPrepaymentDataEntryFileResource {

    private static final String ENTITY_NAME = "dataEntryPrepaymentDataEntryFile";
    private final PrepaymentDataFileMessageService prepaymentDataFileMessageService;
    private final PrepaymentDataEntryFileService prepaymentDataEntryFileService;
    private final PrepaymentDataEntryFileResource prepaymentDataEntryFileResourceDelegate;
    private final FileTokenProvider excelFileTokenProvider;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public PrepaymentDataEntryFileResourceDecorator(final PrepaymentDataFileMessageService prepaymentDataFileMessageService, final PrepaymentDataEntryFileService prepaymentDataEntryFileService,
                                                    final @Qualifier("prepaymentDataEntryFileResourceDelegate") PrepaymentDataEntryFileResource prepaymentDataEntryFileResourceDelegate,
                                                    final FileTokenProvider excelFileTokenProvider) {
        this.prepaymentDataFileMessageService = prepaymentDataFileMessageService;
        this.prepaymentDataEntryFileService = prepaymentDataEntryFileService;
        this.prepaymentDataEntryFileResourceDelegate = prepaymentDataEntryFileResourceDelegate;
        this.excelFileTokenProvider = excelFileTokenProvider;
    }

    /**
     * {@code POST  /prepayment-data-entry-files} : Create a new prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFile
     * has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> createPrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save PrepaymentDataEntryFile : {}", prepaymentDataEntryFileDTO);
        if (prepaymentDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new prepaymentDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        prepaymentDataEntryFileDTO.setFileToken(excelFileTokenProvider.getFileToken(prepaymentDataEntryFileDTO));
        PrepaymentDataEntryFileDTO result = prepaymentDataEntryFileService.save(prepaymentDataEntryFileDTO);

        // @formatter:off
        prepaymentDataFileMessageService.sendMessage(PrepaymentFileUploadNotification.builder()
                                                     .id(result.getId())
                                                     .timeStamp(System.currentTimeMillis())
                                                     .fileUpload(result.getDataEntryFile())
                                                     .fileToken(prepaymentDataEntryFileDTO.getFileToken())
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFileDTO
     * is not valid, or with status {@code 500 (Internal Server Error)} if the prepaymentDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/prepayment-data-entry-files")
    public ResponseEntity<PrepaymentDataEntryFileDTO> updatePrepaymentDataEntryFile(@Valid @RequestBody PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        return prepaymentDataEntryFileResourceDelegate.updatePrepaymentDataEntryFile(prepaymentDataEntryFileDTO);
    }

    /**
     * {@code GET  /prepayment-data-entry-files} : get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepaymentDataEntryFiles in body.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files")
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> getAllPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria, Pageable pageable,
                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return prepaymentDataEntryFileResourceDelegate.getAllPrepaymentDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /prepayment-data-entry-files/count} : count all the prepaymentDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files/count")
    public ResponseEntity<Long> countPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria) {
        return prepaymentDataEntryFileResourceDelegate.countPrepaymentDataEntryFiles(criteria);
    }

    /**
     * {@code GET  /prepayment-data-entry-files/:id} : get the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepaymentDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<PrepaymentDataEntryFileDTO> getPrepaymentDataEntryFile(@PathVariable Long id) {
        return prepaymentDataEntryFileResourceDelegate.getPrepaymentDataEntryFile(id);
    }

    /**
     * {@code DELETE  /prepayment-data-entry-files/:id} : delete the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/prepayment-data-entry-files/{id}")
    public ResponseEntity<Void> deletePrepaymentDataEntryFile(@PathVariable Long id) {
        return prepaymentDataEntryFileResourceDelegate.deletePrepaymentDataEntryFile(id);
    }

    /**
     * {@code SEARCH  /_search/prepayment-data-entry-files?query=:query} : search for the prepaymentDataEntryFile corresponding to the query.
     *
     * @param query    the query of the prepaymentDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/prepayment-data-entry-files")
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> searchPrepaymentDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                           UriComponentsBuilder uriBuilder) {
        return prepaymentDataEntryFileResourceDelegate.searchPrepaymentDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
