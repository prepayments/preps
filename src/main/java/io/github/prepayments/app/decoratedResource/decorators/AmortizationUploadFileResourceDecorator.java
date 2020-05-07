package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IAmortizationUploadFileResource;
import io.github.prepayments.service.dto.AmortizationUploadFileCriteria;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import io.github.prepayments.web.rest.AmortizationUploadFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("amortizationUploadFileResourceDecorator")
public class AmortizationUploadFileResourceDecorator implements IAmortizationUploadFileResource {

    private final AmortizationUploadFileResource amortizationUploadFileResource;

    public AmortizationUploadFileResourceDecorator(final AmortizationUploadFileResource amortizationUploadFileResource) {
        this.amortizationUploadFileResource = amortizationUploadFileResource;
    }

    @Override
    public ResponseEntity<AmortizationUploadFileDTO> createAmortizationUploadFile(@Valid final AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException {
        return amortizationUploadFileResource.createAmortizationUploadFile(amortizationUploadFileDTO);
    }

    @Override
    public ResponseEntity<AmortizationUploadFileDTO> updateAmortizationUploadFile(@Valid final AmortizationUploadFileDTO amortizationUploadFileDTO) throws URISyntaxException {
        return amortizationUploadFileResource.updateAmortizationUploadFile(amortizationUploadFileDTO);
    }

    @Override
    public ResponseEntity<List<AmortizationUploadFileDTO>> getAllAmortizationUploadFiles(final AmortizationUploadFileCriteria criteria, final Pageable pageable,
                                                                                         final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return amortizationUploadFileResource.getAllAmortizationUploadFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countAmortizationUploadFiles(final AmortizationUploadFileCriteria criteria) {
        return amortizationUploadFileResource.countAmortizationUploadFiles(criteria);
    }

    @Override
    public ResponseEntity<AmortizationUploadFileDTO> getAmortizationUploadFile(final Long id) {
        return amortizationUploadFileResource.getAmortizationUploadFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteAmortizationUploadFile(final Long id) {
        return amortizationUploadFileResource.deleteAmortizationUploadFile(id);
    }

    @Override
    public ResponseEntity<List<AmortizationUploadFileDTO>> searchAmortizationUploadFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                         final UriComponentsBuilder uriBuilder) {
        return amortizationUploadFileResource.searchAmortizationUploadFiles(query, pageable, queryParams, uriBuilder);
    }
}
