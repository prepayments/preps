package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.service.dto.AmortizationUploadFileCriteria;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

public interface IAmortizationUploadFileResource {
    /**
     * {@code POST  /amortization-upload-files} : Create a new amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the amortizationUploadFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUploadFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<AmortizationUploadFileDTO> createAmortizationUploadFile(@Valid AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException;

    /**
     * {@code PUT  /amortization-upload-files} : Updates an existing amortizationUploadFile.
     *
     * @param amortizationUploadFileDTO the amortizationUploadFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUploadFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUploadFileDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the amortizationUploadFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<AmortizationUploadFileDTO> updateAmortizationUploadFile(@Valid AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException;

    /**
     * {@code GET  /amortization-upload-files} : get all the amortizationUploadFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUploadFiles in body.
     */
    ResponseEntity<List<AmortizationUploadFileDTO>> getAllAmortizationUploadFiles(AmortizationUploadFileCriteria criteria, Pageable pageable, MultiValueMap<String, String> queryParams,
                                                                                  UriComponentsBuilder uriBuilder);

    /**
     * {@code GET  /amortization-upload-files/count} : count all the amortizationUploadFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countAmortizationUploadFiles(AmortizationUploadFileCriteria criteria);

    /**
     * {@code GET  /amortization-upload-files/:id} : get the "id" amortizationUploadFile.
     *
     * @param id the id of the amortizationUploadFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUploadFileDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<AmortizationUploadFileDTO> getAmortizationUploadFile(Long id);

    /**
     * {@code DELETE  /amortization-upload-files/:id} : delete the "id" amortizationUploadFile.
     *
     * @param id the id of the amortizationUploadFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteAmortizationUploadFile(Long id);

    /**
     * {@code SEARCH  /_search/amortization-upload-files?query=:query} : search for the amortizationUploadFile corresponding to the query.
     *
     * @param query    the query of the amortizationUploadFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<AmortizationUploadFileDTO>> searchAmortizationUploadFiles( String query, Pageable pageable, MultiValueMap<String, String> queryParams,
                                                                                  UriComponentsBuilder uriBuilder);
}
