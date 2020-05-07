package io.github.prepayments.app.decoratedResource;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUploadFileUploadNotification;
import io.github.prepayments.app.messaging.services.notifications.AmortizationUploadFileNotificationMessageService;
import io.github.prepayments.app.token.FileTokenProvider;
import io.github.prepayments.service.AmortizationUploadFileService;
import io.github.prepayments.service.dto.AmortizationUploadFileCriteria;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
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
@RequestMapping("/api/app")
public class AppAmortizationUploadFileResource implements IAmortizationUploadFileResource {


    private static final String ENTITY_NAME = "dataEntryAmortizationUploadFile";
    private final AmortizationUploadFileNotificationMessageService amortizationUploadFileNotificationMessageService;
    private final AmortizationUploadFileService amortizationUploadFileService;
    private final IAmortizationUploadFileResource amortizationUploadFileResourceDecorator;
    private final FileTokenProvider excelFileTokenProvider;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public AppAmortizationUploadFileResource(final AmortizationUploadFileNotificationMessageService amortizationUploadFileNotificationMessageService,
                                             final AmortizationUploadFileService amortizationUploadFileService,
                                             final @Qualifier("amortizationUploadFileResourceDecorator") IAmortizationUploadFileResource amortizationUploadFileResourceDecorator,
                                             final FileTokenProvider excelFileTokenProvider) {
        this.amortizationUploadFileNotificationMessageService = amortizationUploadFileNotificationMessageService;
        this.amortizationUploadFileService = amortizationUploadFileService;
        this.amortizationUploadFileResourceDecorator = amortizationUploadFileResourceDecorator;
        this.excelFileTokenProvider = excelFileTokenProvider;
    }

    /**
     * {@code POST  /amortization-upload-files} : Create a new amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the amortizationUploadFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUploadFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PostMapping("/amortization-upload-files")
    public ResponseEntity<AmortizationUploadFileDTO> createAmortizationUploadFile(@Valid @RequestBody AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException {
        log.debug("REST request to save AmortizationUploadFile : {}", amortizationUploadFileDTO);
        if (amortizationUploadFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new amortizationUploadFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        amortizationUploadFileDTO.setFileToken(excelFileTokenProvider.getFileToken(amortizationUploadFileDTO));
        AmortizationUploadFileDTO result = amortizationUploadFileService.save(amortizationUploadFileDTO);

        // @formatter:off
        amortizationUploadFileNotificationMessageService.sendMessage(
            AmortizationUploadFileUploadNotification.builder()
                                                 .id(result.getId())
                                                 .timeStamp(System.currentTimeMillis())
                                                 .fileToken(result.getFileToken())
                                                 .fileUpload(result.getDataEntryFile())
                                              .build());
        // @formatter:on

        return ResponseEntity.created(new URI("/api/amortization-upload-files/" + result.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                             .body(result);
    }

    /**
     * {@code PUT  /amortization-upload-files} : Updates an existing amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the amortizationUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUploadFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadFileDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the amortizationUploadFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @Override
    @PutMapping("/amortization-upload-files")
    public ResponseEntity<AmortizationUploadFileDTO> updateAmortizationUploadFile(@Valid @RequestBody AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException {

        return amortizationUploadFileResourceDecorator.updateAmortizationUploadFile(amortizationUploadFileDTO);
    }

    /**
     * {@code GET  /amortization-upload-files} : get all the amortizationUploadFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUploadFiles in body.
     */
    @Override
    @GetMapping("/amortization-upload-files")
    public ResponseEntity<List<AmortizationUploadFileDTO>> getAllAmortizationUploadFiles(AmortizationUploadFileCriteria criteria, Pageable pageable,
                                                                                         @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {

        return amortizationUploadFileResourceDecorator.getAllAmortizationUploadFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /amortization-upload-files/count} : count all the amortizationUploadFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @Override
    @GetMapping("/amortization-upload-files/count")
    public ResponseEntity<Long> countAmortizationUploadFiles(AmortizationUploadFileCriteria criteria) {

        return amortizationUploadFileResourceDecorator.countAmortizationUploadFiles(criteria);
    }

    /**
     * {@code GET  /amortization-upload-files/:id} : get the "id" amortizationUploadFile.
     *
     * @param id the id of the amortizationUploadFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUploadFileDTO, or with status {@code 404 (Not Found)}.
     */
    @Override
    @GetMapping("/amortization-upload-files/{id}")
    public ResponseEntity<AmortizationUploadFileDTO> getAmortizationUploadFile(@PathVariable Long id) {

        return amortizationUploadFileResourceDecorator.getAmortizationUploadFile(id);
    }

    /**
     * {@code DELETE  /amortization-upload-files/:id} : delete the "id" amortizationUploadFile.
     *
     * @param id the id of the amortizationUploadFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @Override
    @DeleteMapping("/amortization-upload-files/{id}")
    public ResponseEntity<Void> deleteAmortizationUploadFile(@PathVariable Long id) {

        return amortizationUploadFileResourceDecorator.deleteAmortizationUploadFile(id);
    }

    /**
     * {@code SEARCH  /_search/amortization-upload-files?query=:query} : search for the amortizationUploadFile corresponding to the query.
     *
     * @param query    the query of the amortizationUploadFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @Override
    @GetMapping("/_search/amortization-upload-files")
    public ResponseEntity<List<AmortizationUploadFileDTO>> searchAmortizationUploadFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                         UriComponentsBuilder uriBuilder) {

        return amortizationUploadFileResourceDecorator.searchAmortizationUploadFiles(query, pageable, queryParams, uriBuilder);
    }
}
