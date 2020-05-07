package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IAmortizationUpdateFileResource;
import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.web.rest.AmortizationUpdateFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("amortizationUpdateFileResourceDecorator")
public class AmortizationUpdateFileResourceDecorator implements IAmortizationUpdateFileResource {

    private final AmortizationUpdateFileResource amortizationUpdateFileResource;

    public AmortizationUpdateFileResourceDecorator(final AmortizationUpdateFileResource amortizationUpdateFileResource) {
        this.amortizationUpdateFileResource = amortizationUpdateFileResource;
    }

    @Override
    public ResponseEntity<AmortizationUpdateFileDTO> createAmortizationUpdateFile(@Valid final AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {
        return amortizationUpdateFileResource.createAmortizationUpdateFile(amortizationUpdateFileDTO);
    }

    @Override
    public ResponseEntity<AmortizationUpdateFileDTO> updateAmortizationUpdateFile(@Valid final AmortizationUpdateFileDTO amortizationUpdateFileDTO) throws URISyntaxException {
        return amortizationUpdateFileResource.updateAmortizationUpdateFile(amortizationUpdateFileDTO);
    }

    @Override
    public ResponseEntity<List<AmortizationUpdateFileDTO>> getAllAmortizationUpdateFiles(final AmortizationUpdateFileCriteria criteria, final Pageable pageable,
                                                                                         final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return amortizationUpdateFileResource.getAllAmortizationUpdateFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countAmortizationUpdateFiles(final AmortizationUpdateFileCriteria criteria) {
        return amortizationUpdateFileResource.countAmortizationUpdateFiles(criteria);
    }

    @Override
    public ResponseEntity<AmortizationUpdateFileDTO> getAmortizationUpdateFile(final Long id) {
        return amortizationUpdateFileResource.getAmortizationUpdateFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteAmortizationUpdateFile(final Long id) {
        return amortizationUpdateFileResource.deleteAmortizationUpdateFile(id);
    }

    @Override
    public ResponseEntity<List<AmortizationUpdateFileDTO>> searchAmortizationUpdateFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                         final UriComponentsBuilder uriBuilder) {
        return amortizationUpdateFileResource.searchAmortizationUpdateFiles(query, pageable, queryParams, uriBuilder);
    }
}
