package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

public interface IPrepaymentDataEntryFileResource {
    /**
     * {@code POST  /prepayment-data-entry-files} : Create a new prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFile
     * has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<PrepaymentDataEntryFileDTO> createPrepaymentDataEntryFile(@Valid PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code PUT  /prepayment-data-entry-files} : Updates an existing prepaymentDataEntryFile.
     *
     * @param prepaymentDataEntryFileDTO the prepaymentDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prepaymentDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the prepaymentDataEntryFileDTO
     * is not valid, or with status {@code 500 (Internal Server Error)} if the prepaymentDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<PrepaymentDataEntryFileDTO> updatePrepaymentDataEntryFile(@Valid PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code GET  /prepayment-data-entry-files} : get all the prepaymentDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prepaymentDataEntryFiles in body.
     */
    ResponseEntity<List<PrepaymentDataEntryFileDTO>> getAllPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria, Pageable pageable,
                                                                                    MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder);

    /**
     * {@code GET  /prepayment-data-entry-files/count} : count all the prepaymentDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countPrepaymentDataEntryFiles(PrepaymentDataEntryFileCriteria criteria);

    /**
     * {@code GET  /prepayment-data-entry-files/:id} : get the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prepaymentDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<PrepaymentDataEntryFileDTO> getPrepaymentDataEntryFile(Long id);

    /**
     * {@code DELETE  /prepayment-data-entry-files/:id} : delete the "id" prepaymentDataEntryFile.
     *
     * @param id the id of the prepaymentDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deletePrepaymentDataEntryFile(Long id);

    /**
     * {@code SEARCH  /_search/prepayment-data-entry-files?query=:query} : search for the prepaymentDataEntryFile corresponding to the query.
     *
     * @param query    the query of the prepaymentDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<PrepaymentDataEntryFileDTO>> searchPrepaymentDataEntryFiles(String query, Pageable pageable,MultiValueMap<String, String> queryParams,
                                                                                    UriComponentsBuilder uriBuilder);
}
