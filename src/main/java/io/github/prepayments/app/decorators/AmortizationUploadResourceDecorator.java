package io.github.prepayments.app.decorators;

import io.github.prepayments.app.messaging.data_entry.service.AmortizationEntriesPropagatorService;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.web.rest.AmortizationUploadResource;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;
import static io.github.prepayments.app.AppConstants.MONTHLY_AMORTIZATION_DATE;

/**
 * REST controller for managing {@link io.github.prepayments.domain.AmortizationUpload}.
 */
@RestController
@RequestMapping("/api")
public class AmortizationUploadResourceDecorator implements IAmortizationUploadResource {

    private final AmortizationUploadResource amortizationUploadResource;
    private final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService;

    public AmortizationUploadResourceDecorator(@Qualifier("amortizationUploadResourceDelegate") AmortizationUploadResource amortizationUploadResource,
                                               AmortizationEntriesPropagatorService amortizationEntriesPropagatorService) {
        this.amortizationUploadResource = amortizationUploadResource;
        this.amortizationEntriesPropagatorService = amortizationEntriesPropagatorService;
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

        // create amortization entries from the amortization upload
        if (amortizationUploadDTO.getMonthlyAmortizationDate() == null)
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DateTimeFormatter.ofPattern(DATETIME_FORMAT), amortizationUploadDTO, MONTHLY_AMORTIZATION_DATE);
        amortizationEntriesPropagatorService.propagateAmortizationEntries(DateTimeFormatter.ofPattern(DATETIME_FORMAT), amortizationUploadDTO, amortizationUploadDTO.getMonthlyAmortizationDate());

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
