package io.github.prepayments.app.decorators;

import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.util.List;

public interface IAmortizationUploadResource {

    /**
     * {@code POST  /amortization-uploads} : Create a new amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUpload has already
     * an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<AmortizationUploadDTO> createAmortizationUpload(AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException;

    /**
     * {@code PUT  /amortization-uploads} : Updates an existing amortizationUpload.
     *
     * @param amortizationUploadDTO the amortizationUploadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUploadDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadDTO is not
     * valid, or with status {@code 500 (Internal Server Error)} if the amortizationUploadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<AmortizationUploadDTO> updateAmortizationUpload(AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException;

    /**
     * {@code GET  /amortization-uploads} : get all the amortizationUploads.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUploads in body.
     */
    ResponseEntity<List<AmortizationUploadDTO>> getAllAmortizationUploads(AmortizationUploadCriteria criteria, Pageable pageable, MultiValueMap<String, String> queryParams,
                                                                          UriComponentsBuilder uriBuilder);
    /**
     * {@code GET  /amortization-uploads/count} : count all the amortizationUploads.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countAmortizationUploads(AmortizationUploadCriteria criteria);

    /**
     * {@code GET  /amortization-uploads/:id} : get the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUploadDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<AmortizationUploadDTO> getAmortizationUpload(Long id);

    /**
     * {@code DELETE  /amortization-uploads/:id} : delete the "id" amortizationUpload.
     *
     * @param id the id of the amortizationUploadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteAmortizationUpload(Long id);

    /**
     * {@code SEARCH  /_search/amortization-uploads?query=:query} : search for the amortizationUpload corresponding to the query.
     *
     * @param query    the query of the amortizationUpload search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<AmortizationUploadDTO>> searchAmortizationUploads(String query, Pageable pageable,MultiValueMap<String, String> queryParams,
                                                                          UriComponentsBuilder uriBuilder);
}
