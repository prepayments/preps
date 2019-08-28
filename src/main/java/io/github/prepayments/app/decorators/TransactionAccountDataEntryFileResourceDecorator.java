package io.github.prepayments.app.decorators;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.TransactionAccountFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.TransactionAccountDataFileMessageService;
import io.github.prepayments.app.token.FileTokenProvider;
import io.github.prepayments.service.TransactionAccountDataEntryFileService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import io.github.prepayments.web.rest.TransactionAccountDataEntryFileResource;
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
public class TransactionAccountDataEntryFileResourceDecorator implements ITransactionAccountDataEntryFileResource {


    private static final String ENTITY_NAME = "dataEntryTransactionAccountDataEntryFile";
    private final TransactionAccountDataEntryFileService transactionAccountDataEntryFileService;
    private final TransactionAccountDataFileMessageService transactionAccountDataFileMessageService;
    private final TransactionAccountDataEntryFileResource transactionAccountDataEntryFileResourceDelegate;
    private final FileTokenProvider excelFileTokenProvider;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public TransactionAccountDataEntryFileResourceDecorator(final TransactionAccountDataEntryFileService transactionAccountDataEntryFileService,
                                                            final TransactionAccountDataFileMessageService transactionAccountDataFileMessageService,
                                                            final @Qualifier("transactionAccountDataEntryFileResourceDelegate")
                                                                TransactionAccountDataEntryFileResource transactionAccountDataEntryFileResourceDelegate, final FileTokenProvider
                                                                excelFileTokenProvider) {
        this.transactionAccountDataEntryFileService = transactionAccountDataEntryFileService;
        this.transactionAccountDataFileMessageService = transactionAccountDataFileMessageService;
        this.transactionAccountDataEntryFileResourceDelegate = transactionAccountDataEntryFileResourceDelegate;
        this.excelFileTokenProvider = excelFileTokenProvider;
    }

    /**
     * {@code POST  /transaction-account-data-entry-files} : Create a new transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/transaction-account-data-entry-files")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> createTransactionAccountDataEntryFile(@Valid @RequestBody TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        log.debug("REST request to save TransactionAccountDataEntryFile : {}", transactionAccountDataEntryFileDTO);
        if (transactionAccountDataEntryFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionAccountDataEntryFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        transactionAccountDataEntryFileDTO.setFileToken(excelFileTokenProvider.getFileToken(transactionAccountDataEntryFileDTO));
        TransactionAccountDataEntryFileDTO result = transactionAccountDataEntryFileService.save(transactionAccountDataEntryFileDTO);

        // @formatter:off
        transactionAccountDataFileMessageService.sendMessage(TransactionAccountFileUploadNotification.builder()
                                                     .id(result.getId())
                                                     .timeStamp(System.currentTimeMillis())
                                                     .fileUpload(result.getDataEntryFile())
                                                     .fileToken(result.getFileToken())
                                                     .build());
        // @formatter:on

        return ResponseEntity.created(new URI("/api/transaction-account-data-entry-files/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /transaction-account-data-entry-files} : Updates an existing transactionAccountDataEntryFile.
     *
     * @param transactionAccountDataEntryFileDTO the transactionAccountDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionAccountDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * transactionAccountDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the transactionAccountDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/transaction-account-data-entry-files")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> updateTransactionAccountDataEntryFile(@Valid @RequestBody TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        return transactionAccountDataEntryFileResourceDelegate.updateTransactionAccountDataEntryFile(transactionAccountDataEntryFileDTO);
    }

    /**
     * {@code GET  /transaction-account-data-entry-files} : get all the transactionAccountDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionAccountDataEntryFiles in body.
     */
    @Override
    @GetMapping("/transaction-account-data-entry-files")
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> getAllTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria, Pageable pageable,
                                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return transactionAccountDataEntryFileResourceDelegate.getAllTransactionAccountDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /transaction-account-data-entry-files/count} : count all the transactionAccountDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/transaction-account-data-entry-files/count")
    public ResponseEntity<Long> countTransactionAccountDataEntryFiles(TransactionAccountDataEntryFileCriteria criteria) {
        return transactionAccountDataEntryFileResourceDelegate.countTransactionAccountDataEntryFiles(criteria);
    }

    /**
     * {@code GET  /transaction-account-data-entry-files/:id} : get the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionAccountDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/transaction-account-data-entry-files/{id}")
    public ResponseEntity<TransactionAccountDataEntryFileDTO> getTransactionAccountDataEntryFile(@PathVariable Long id) {
        return transactionAccountDataEntryFileResourceDelegate.getTransactionAccountDataEntryFile(id);
    }

    /**
     * {@code DELETE  /transaction-account-data-entry-files/:id} : delete the "id" transactionAccountDataEntryFile.
     *
     * @param id the id of the transactionAccountDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/transaction-account-data-entry-files/{id}")
    public ResponseEntity<Void> deleteTransactionAccountDataEntryFile(@PathVariable Long id) {
        return transactionAccountDataEntryFileResourceDelegate.deleteTransactionAccountDataEntryFile(id);
    }

    /**
     * {@code SEARCH  /_search/transaction-account-data-entry-files?query=:query} : search for the transactionAccountDataEntryFile corresponding to the query.
     *
     * @param query    the query of the transactionAccountDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/transaction-account-data-entry-files")
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> searchTransactionAccountDataEntryFiles(@RequestParam String query, Pageable pageable,
                                                                                                           @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return transactionAccountDataEntryFileResourceDelegate.searchTransactionAccountDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }

}
