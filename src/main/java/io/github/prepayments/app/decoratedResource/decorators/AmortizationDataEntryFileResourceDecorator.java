package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IAmortizationDataEntryFileResource;
import io.github.prepayments.service.dto.AmortizationDataEntryFileCriteria;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import io.github.prepayments.web.rest.AmortizationDataEntryFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("amortizationDataEntryFileResourceDecorator")
public class AmortizationDataEntryFileResourceDecorator implements IAmortizationDataEntryFileResource {

    private final AmortizationDataEntryFileResource amortizationDataEntryFileResource;

    public AmortizationDataEntryFileResourceDecorator(final AmortizationDataEntryFileResource amortizationDataEntryFileResource) {
        this.amortizationDataEntryFileResource = amortizationDataEntryFileResource;
    }

    @Override
    public ResponseEntity<AmortizationDataEntryFileDTO> createAmortizationDataEntryFile(@Valid final AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) throws URISyntaxException {
        return amortizationDataEntryFileResource.createAmortizationDataEntryFile(amortizationDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<AmortizationDataEntryFileDTO> updateAmortizationDataEntryFile(@Valid final AmortizationDataEntryFileDTO amortizationDataEntryFileDTO) throws URISyntaxException {
        return amortizationDataEntryFileResource.updateAmortizationDataEntryFile(amortizationDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<List<AmortizationDataEntryFileDTO>> getAllAmortizationDataEntryFiles(final AmortizationDataEntryFileCriteria criteria, final Pageable pageable,
                                                                                               final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return amortizationDataEntryFileResource.getAllAmortizationDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countAmortizationDataEntryFiles(final AmortizationDataEntryFileCriteria criteria) {
        return amortizationDataEntryFileResource.countAmortizationDataEntryFiles(criteria);
    }

    @Override
    public ResponseEntity<AmortizationDataEntryFileDTO> getAmortizationDataEntryFile(final Long id) {
        return amortizationDataEntryFileResource.getAmortizationDataEntryFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteAmortizationDataEntryFile(final Long id) {
        return amortizationDataEntryFileResource.deleteAmortizationDataEntryFile(id);
    }

    @Override
    public ResponseEntity<List<AmortizationDataEntryFileDTO>> searchAmortizationDataEntryFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                               final UriComponentsBuilder uriBuilder) {
        return amortizationDataEntryFileResource.searchAmortizationDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
