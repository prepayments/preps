package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IAmortizationUploadResource;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.web.rest.AmortizationUploadResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.util.List;

@Component("amortizationUploadResourceDecorator")
public class AmortizationUploadResourceDecorator implements IAmortizationUploadResource {

    private final AmortizationUploadResource amortizationUploadResource;

    public AmortizationUploadResourceDecorator(final AmortizationUploadResource amortizationUploadResource) {
        this.amortizationUploadResource = amortizationUploadResource;
    }

    @Override
    public ResponseEntity<AmortizationUploadDTO> createAmortizationUpload(final AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {
        return amortizationUploadResource.createAmortizationUpload(amortizationUploadDTO);
    }

    @Override
    public ResponseEntity<AmortizationUploadDTO> updateAmortizationUpload(final AmortizationUploadDTO amortizationUploadDTO) throws URISyntaxException {
        return amortizationUploadResource.updateAmortizationUpload(amortizationUploadDTO);
    }

    @Override
    public ResponseEntity<List<AmortizationUploadDTO>> getAllAmortizationUploads(final AmortizationUploadCriteria criteria, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                 final UriComponentsBuilder uriBuilder) {
        return amortizationUploadResource.getAllAmortizationUploads(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countAmortizationUploads(final AmortizationUploadCriteria criteria) {
        return amortizationUploadResource.countAmortizationUploads(criteria);
    }

    @Override
    public ResponseEntity<AmortizationUploadDTO> getAmortizationUpload(final Long id) {
        return amortizationUploadResource.getAmortizationUpload(id);
    }

    @Override
    public ResponseEntity<Void> deleteAmortizationUpload(final Long id) {
        return amortizationUploadResource.deleteAmortizationUpload(id);
    }

    @Override
    public ResponseEntity<List<AmortizationUploadDTO>> searchAmortizationUploads(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                 final UriComponentsBuilder uriBuilder) {
        return amortizationUploadResource.searchAmortizationUploads(query, pageable, queryParams, uriBuilder);
    }
}
