package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
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

public interface IAmortizationUpdateFileResource {
    /**
     * {@code POST  /amortization-update-files} : Create a new amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUpdateFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUpdateFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-update-files")
    ResponseEntity<AmortizationUpdateFileDTO> createAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException;

    /**
     * {@code PUT  /amortization-update-files} : Updates an existing amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUpdateFileDTO,
     * or with status {@code 400 (Bad Request)} if the amortizationUpdateFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the amortizationUpdateFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-update-files")
    ResponseEntity<AmortizationUpdateFileDTO> updateAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException;

    /**
     * {@code GET  /amortization-update-files} : get all the amortizationUpdateFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUpdateFiles in body.
     */
    @GetMapping("/amortization-update-files")
    ResponseEntity<List<AmortizationUpdateFileDTO>> getAllAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                  UriComponentsBuilder uriBuilder);

    /**
    * {@code GET  /amortization-update-files/count} : count all the amortizationUpdateFiles.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/amortization-update-files/count")
    ResponseEntity<Long> countAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria);

    /**
     * {@code GET  /amortization-update-files/:id} : get the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUpdateFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-update-files/{id}")
    ResponseEntity<AmortizationUpdateFileDTO> getAmortizationUpdateFile(@PathVariable Long id);

    /**
     * {@code DELETE  /amortization-update-files/:id} : delete the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-update-files/{id}")
    ResponseEntity<Void> deleteAmortizationUpdateFile(@PathVariable Long id);

    /**
     * {@code SEARCH  /_search/amortization-update-files?query=:query} : search for the amortizationUpdateFile corresponding
     * to the query.
     *
     * @param query the query of the amortizationUpdateFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-update-files")
    ResponseEntity<List<AmortizationUpdateFileDTO>> searchAmortizationUpdateFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                  UriComponentsBuilder uriBuilder);
}
