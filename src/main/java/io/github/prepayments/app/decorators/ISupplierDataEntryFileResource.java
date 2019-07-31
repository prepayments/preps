package io.github.prepayments.app.decorators;

import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

public interface ISupplierDataEntryFileResource {
    /**
     * {@code POST  /supplier-data-entry-files} : Create a new supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the supplierDataEntryFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<SupplierDataEntryFileDTO> createSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code PUT  /supplier-data-entry-files} : Updates an existing supplierDataEntryFile.
     *
     * @param supplierDataEntryFileDTO the supplierDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the supplierDataEntryFileDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the supplierDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<SupplierDataEntryFileDTO> updateSupplierDataEntryFile(@Valid @RequestBody SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code GET  /supplier-data-entry-files} : get all the supplierDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierDataEntryFiles in body.
     */
    ResponseEntity<List<SupplierDataEntryFileDTO>> getAllSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                UriComponentsBuilder uriBuilder);

    /**
     * {@code GET  /supplier-data-entry-files/count} : count all the supplierDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countSupplierDataEntryFiles(SupplierDataEntryFileCriteria criteria);

    /**
     * {@code GET  /supplier-data-entry-files/:id} : get the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<SupplierDataEntryFileDTO> getSupplierDataEntryFile(@PathVariable Long id);

    /**
     * {@code DELETE  /supplier-data-entry-files/:id} : delete the "id" supplierDataEntryFile.
     *
     * @param id the id of the supplierDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteSupplierDataEntryFile(@PathVariable Long id);

    /**
     * {@code SEARCH  /_search/supplier-data-entry-files?query=:query} : search for the supplierDataEntryFile corresponding to the query.
     *
     * @param query    the query of the supplierDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<SupplierDataEntryFileDTO>> searchSupplierDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                UriComponentsBuilder uriBuilder);
}
