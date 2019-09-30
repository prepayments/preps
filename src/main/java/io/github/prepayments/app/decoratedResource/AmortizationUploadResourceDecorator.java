package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.app.messaging.data_entry.service.AmortizationEntriesPropagatorService;
import io.github.prepayments.app.services.cascade.AmortizationUploadCascader;
import io.github.prepayments.app.token.Tag;
import io.github.prepayments.app.token.TagProvider;
import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.web.rest.AmortizationUploadResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.net.URISyntaxException;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;
import static io.github.prepayments.app.AppConstants.MONTHLY_AMORTIZATION_DATE;
import static io.github.prepayments.app.services.cascade.CascadedOperation.DELETE;
import static io.github.prepayments.app.services.cascade.CascadedOperation.UPDATE;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpload}.
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AmortizationUploadResourceDecorator implements IAmortizationUploadResource {

    private final AmortizationUploadResource amortizationUploadResource;
    private final TagProvider<String> tagProvider;
    private final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService;
    private final AmortizationUploadCascader amortizationUploadCascader;
    private final AmortizationUploadService amortizationUploadService;

    public AmortizationUploadResourceDecorator(final @Qualifier("amortizationUploadResourceDelegate") AmortizationUploadResource amortizationUploadResource, final TagProvider<String> tagProvider,
                                               final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService, final AmortizationUploadCascader amortizationUploadCascader,
                                               final AmortizationUploadService amortizationUploadService) {
        this.amortizationUploadResource = amortizationUploadResource;
        this.tagProvider = tagProvider;
        this.amortizationEntriesPropagatorService = amortizationEntriesPropagatorService;
        this.amortizationUploadCascader = amortizationUploadCascader;
        this.amortizationUploadService = amortizationUploadService;
    }

    /**
     * {@code POST  /amortization-uploads} : Create a new amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUpload has already
     * an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-uploads")
    public ResponseEntity<AmortizationUploadDTO> createAmortizationUpload(@Valid @RequestBody AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {

        // TODO check if DTO is orphaned
        //        amortizationUploadDTO.setAmortizationTag(tagProvider.tag(amortizationUploadDTO).getTag());
        Tag<String> amortTag = tagProvider.tag(amortizationUploadDTO);
        log.info("Create amortization-entries for amortization tag # : {}", amortTag);
        // create amortization entries from the amortization upload
        if (amortizationUploadDTO.getMonthlyAmortizationDate() == null) {
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DATETIME_FORMATTER, amortizationUploadDTO, MONTHLY_AMORTIZATION_DATE);
        }
        if (amortizationUploadDTO.getMonthlyAmortizationDate() != null) {
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DATETIME_FORMATTER, amortizationUploadDTO, amortizationUploadDTO.getMonthlyAmortizationDate());
        }
        amortizationUploadDTO.setUploadSuccessful(true);

        return amortizationUploadResource.createAmortizationUpload(amortizationUploadDTO);
    }

    /**
     * {@code PUT  /amortization-uploads} : Updates an existing amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the amortizationUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-uploads")
    public ResponseEntity<AmortizationUploadDTO> updateAmortizationUpload(@Valid @RequestBody AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {

        amortizationUploadCascader.cascade(UPDATE, amortizationUploadDTO);

        return amortizationUploadResource.updateAmortizationUpload(amortizationUploadDTO);
    }

    /**
     * {@code GET  /amortization-uploads} : get all the amortizationUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUploads in body.
     */
    @GetMapping("/amortization-uploads")
    public ResponseEntity<List<AmortizationUploadDTO>> getAllAmortizationUploads(AmortizationUploadCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {

        return amortizationUploadResource.getAllAmortizationUploads(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /amortization-uploads/count} : count all the amortizationUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/amortization-uploads/count")
    public ResponseEntity<Long> countAmortizationUploads(AmortizationUploadCriteria criteria) {

        return amortizationUploadResource.countAmortizationUploads(criteria);
    }

    /**
     * {@code GET  /amortization-uploads/:id} : get the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUploadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-uploads/{id}")
    public ResponseEntity<AmortizationUploadDTO> getAmortizationUpload(@PathVariable Long id) {

        return amortizationUploadResource.getAmortizationUpload(id);
    }

    /**
     * {@code DELETE  /amortization-uploads/:id} : delete the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-uploads/{id}")
    public ResponseEntity<Void> deleteAmortizationUpload(@PathVariable Long id) {

        amortizationUploadCascader.cascade(DELETE, amortizationUploadService.findOne(id).get());

        return amortizationUploadResource.deleteAmortizationUpload(id);
    }

    /**
     * {@code SEARCH  /_search/amortization-uploads?query=:query} : search for the amortizationUpload corresponding to the query.
     *
     * @param query    the query of the amortizationUpload search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-uploads")
    public ResponseEntity<List<AmortizationUploadDTO>> searchAmortizationUploads(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                 UriComponentsBuilder uriBuilder) {

        return amortizationUploadResource.searchAmortizationUploads(query, pageable, queryParams, uriBuilder);
    }
}
