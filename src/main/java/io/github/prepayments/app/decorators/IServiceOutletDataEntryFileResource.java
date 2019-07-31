package io.github.prepayments.app.decorators;

import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
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

public interface IServiceOutletDataEntryFileResource {
    /**
     * {@code POST  /service-outlet-data-entry-files} : Create a new serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the serviceOutletDataEntryFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOutletDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * serviceOutletDataEntryFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<ServiceOutletDataEntryFileDTO> createServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code PUT  /service-outlet-data-entry-files} : Updates an existing serviceOutletDataEntryFile.
     *
     * @param serviceOutletDataEntryFileDTO the serviceOutletDataEntryFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOutletDataEntryFileDTO, or with status {@code 400 (Bad Request)} if the
     * serviceOutletDataEntryFileDTO is not valid, or with status {@code 500 (Internal Server Error)} if the serviceOutletDataEntryFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    ResponseEntity<ServiceOutletDataEntryFileDTO> updateServiceOutletDataEntryFile(@Valid @RequestBody ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException;

    /**
     * {@code GET  /service-outlet-data-entry-files} : get all the serviceOutletDataEntryFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOutletDataEntryFiles in body.
     */
    ResponseEntity<List<ServiceOutletDataEntryFileDTO>> getAllServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria, Pageable pageable,
                                                                                          @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder);

    /**
     * {@code GET  /service-outlet-data-entry-files/count} : count all the serviceOutletDataEntryFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    ResponseEntity<Long> countServiceOutletDataEntryFiles(ServiceOutletDataEntryFileCriteria criteria);

    /**
     * {@code GET  /service-outlet-data-entry-files/:id} : get the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOutletDataEntryFileDTO, or with status {@code 404 (Not Found)}.
     */
    ResponseEntity<ServiceOutletDataEntryFileDTO> getServiceOutletDataEntryFile(@PathVariable Long id);

    /**
     * {@code DELETE  /service-outlet-data-entry-files/:id} : delete the "id" serviceOutletDataEntryFile.
     *
     * @param id the id of the serviceOutletDataEntryFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    ResponseEntity<Void> deleteServiceOutletDataEntryFile(@PathVariable Long id);

    /**
     * {@code SEARCH  /_search/service-outlet-data-entry-files?query=:query} : search for the serviceOutletDataEntryFile corresponding to the query.
     *
     * @param query    the query of the serviceOutletDataEntryFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    ResponseEntity<List<ServiceOutletDataEntryFileDTO>> searchServiceOutletDataEntryFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                          UriComponentsBuilder uriBuilder);
}
