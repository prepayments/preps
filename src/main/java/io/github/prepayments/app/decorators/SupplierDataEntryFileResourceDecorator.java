package io.github.prepayments.app.decorators;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.SupplierDataFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.SupplierDataFileMessageService;
import io.github.prepayments.app.token.FileTokenProvider;
import io.github.prepayments.service.SupplierDataEntryFileService;
import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.web.rest.SupplierDataEntryFileResource;
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
public class SupplierDataEntryFileResourceDecorator implements ISupplierDataEntryFileResource {

    private final SupplierDataEntryFileResource supplierDataEntryFileResource;

    private static final String ENTITY_NAME = "dataEntrySupplierDataEntryFile";
    private final SupplierDataEntryFileService supplierDataEntryFileService;
    private final SupplierDataFileMessageService supplierDataFileMessageService;
    private final FileTokenProvider excelFileTokenProvider;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public SupplierDataEntryFileResourceDecorator(final SupplierDataEntryFileService supplierDataEntryFileService, final SupplierDataFileMessageService supplierDataFileMessageService,
                                                  final @Qualifier("SupplierDataEntryFileResourceDelegate") SupplierDataEntryFileResource supplierDataEntryFileResource,
                                                  final FileTokenProvider excelFileTokenProvider) {
        this.supplierDataEntryFileResource = supplierDataEntryFileResource;
        this.supplierDataEntryFileService = supplierDataEntryFileService;
        this.supplierDataFileMessageService = supplierDataFileMessageService;
        this.excelFileTokenProvider = excelFileTokenProvider;
    }

    /**
     * {@code POST  /supplier-data-entry-files} : Create a new supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the supplierDataEntryFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/supplier-data-entry-files")
    public ResponseEntity<SupplierDataEntryFileDTO> createSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        log.debug("REST request to save SupplierDataEntryFile : {}", supplierDataEntryFileDTO);
        if (supplierDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplierDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        supplierDataEntryFileDTO.setFileToken(excelFileTokenProvider.getFileToken(supplierDataEntryFileDTO));
        SupplierDataEntryFileDTO result = supplierDataEntryFileService.save(supplierDataEntryFileDTO);

        // @formatter:off
        supplierDataFileMessageService.sendMessage(
            SupplierDataFileUploadNotification.builder()
                                                 .id(result.getId())
                                                 .timeStamp(System.currentTimeMillis())
                                                 .fileToken(result.getFileToken())
                                                 .fileUpload(result.getDataEntryFile())
                                              .build());
        // @formatter:on

        return ResponseEntity.created(new URI("/api/supplier-data-entry-files/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /supplier-data-entry-files} : Updates an existing supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the supplierDataEntryFileDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the supplierDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/supplier-data-entry-files")
    public ResponseEntity<SupplierDataEntryFileDTO> updateSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        return supplierDataEntryFileResource.updateSupplierDataEntryFile(supplierDataEntryFileDTO);
    }

    /**
     * {@code GET  /supplier-data-entry-files} : get all the supplierDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierDataEntryFiles in body.
     */
    @Override
    @GetMapping("/supplier-data-entry-files")
    public ResponseEntity<List<SupplierDataEntryFileDTO>> getAllSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria, Pageable pageable,
                                                                                       @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return supplierDataEntryFileResource.getAllSupplierDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /supplier-data-entry-files/count} : count all the supplierDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/supplier-data-entry-files/count")
    public ResponseEntity<Long> countSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria) {
        return supplierDataEntryFileResource.countSupplierDataEntryFiles(criteria);
    }

    /**
     * {@code GET  /supplier-data-entry-files/:id} : get the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/supplier-data-entry-files/{id}")
    public ResponseEntity<SupplierDataEntryFileDTO> getSupplierDataEntryFile(@PathVariable Long id) {
        return supplierDataEntryFileResource.getSupplierDataEntryFile(id);
    }

    /**
     * {@code DELETE  /supplier-data-entry-files/:id} : delete the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/supplier-data-entry-files/{id}")
    public ResponseEntity<Void> deleteSupplierDataEntryFile(@PathVariable Long id) {
        return supplierDataEntryFileResource.deleteSupplierDataEntryFile(id);
    }

    /**
     * {@code SEARCH  /_search/supplier-data-entry-files?query=:query} : search for the supplierDataEntryFile corresponding to the query.
     *
     * @param query    the query of the supplierDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/supplier-data-entry-files")
    public ResponseEntity<List<SupplierDataEntryFileDTO>> searchSupplierDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                       UriComponentsBuilder uriBuilder) {
        return supplierDataEntryFileResource.searchSupplierDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
