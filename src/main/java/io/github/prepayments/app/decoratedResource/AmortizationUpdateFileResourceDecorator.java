package io.github.prepayments.app.decoratedResource;

import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.web.rest.AmortizationUpdateFileResource;
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

@RestController
@RequestMapping("/api")
public class AmortizationUpdateFileResourceDecorator implements IAmortizationUpdateFileResource {


    private final AmortizationUpdateFileResource amortizationUpdateFileResource;

    public AmortizationUpdateFileResourceDecorator(final AmortizationUpdateFileResource amortizationUpdateFileResource) {
        this.amortizationUpdateFileResource = amortizationUpdateFileResource;
    }

    /**
     * {@code POST  /amortization-update-files} : Create a new amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new amortizationUpdateFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUpdateFile has
     * already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/amortization-update-files")
    public ResponseEntity<AmortizationUpdateFileDTO> createAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {

        return this.amortizationUpdateFileResource.createAmortizationUpdateFile(amortizationUpdateFileDTO);
    }

    /**
     * {@code PUT  /amortization-update-files} : Updates an existing amortizationUpdateFile.
     *
     * @param amortizationUpdateFileDTO the amortizationUpdateFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated amortizationUpdateFileDTO, or with status {@code 400 (Bad Request)} if the amortizationUpdateFileDTO is
     * not valid, or with status {@code 500 (Internal Server Error)} if the amortizationUpdateFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/amortization-update-files")
    public ResponseEntity<AmortizationUpdateFileDTO> updateAmortizationUpdateFile(@Valid @RequestBody AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {
        return this.amortizationUpdateFileResource.updateAmortizationUpdateFile(amortizationUpdateFileDTO);
    }

    /**
     * {@code GET  /amortization-update-files} : get all the amortizationUpdateFiles.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of amortizationUpdateFiles in body.
     */
    @GetMapping("/amortization-update-files")
    public ResponseEntity<List<AmortizationUpdateFileDTO>> getAllAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria, Pageable pageable,
                                                                                         @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        return this.amortizationUpdateFileResource.getAllAmortizationUpdateFiles(criteria, pageable, queryParams, uriBuilder);
    }

    /**
     * {@code GET  /amortization-update-files/count} : count all the amortizationUpdateFiles.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/amortization-update-files/count")
    public ResponseEntity<Long> countAmortizationUpdateFiles(AmortizationUpdateFileCriteria criteria) {
        return this.amortizationUpdateFileResource.countAmortizationUpdateFiles(criteria);
    }

    /**
     * {@code GET  /amortization-update-files/:id} : get the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the amortizationUpdateFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/amortization-update-files/{id}")
    public ResponseEntity<AmortizationUpdateFileDTO> getAmortizationUpdateFile(@PathVariable Long id) {
        return this.amortizationUpdateFileResource.getAmortizationUpdateFile(id);
    }

    /**
     * {@code DELETE  /amortization-update-files/:id} : delete the "id" amortizationUpdateFile.
     *
     * @param id the id of the amortizationUpdateFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/amortization-update-files/{id}")
    public ResponseEntity<Void> deleteAmortizationUpdateFile(@PathVariable Long id) {
        return this.amortizationUpdateFileResource.deleteAmortizationUpdateFile(id);
    }

    /**
     * {@code SEARCH  /_search/amortization-update-files?query=:query} : search for the amortizationUpdateFile corresponding to the query.
     *
     * @param query    the query of the amortizationUpdateFile search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/amortization-update-files")
    public ResponseEntity<List<AmortizationUpdateFileDTO>> searchAmortizationUpdateFiles(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams,
                                                                                  UriComponentsBuilder uriBuilder) {
        return this.amortizationUpdateFileResource.searchAmortizationUpdateFiles(query, pageable, queryParams, uriBuilder);
    }
}
