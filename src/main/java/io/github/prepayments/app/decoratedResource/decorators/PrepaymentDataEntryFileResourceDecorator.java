package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IPrepaymentDataEntryFileResource;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileCriteria;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import io.github.prepayments.web.rest.PrepaymentDataEntryFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("prepaymentDataEntryFileResourceDecorator")
public class PrepaymentDataEntryFileResourceDecorator implements IPrepaymentDataEntryFileResource {

    private final PrepaymentDataEntryFileResource prepaymentDataEntryFileResource;

    public PrepaymentDataEntryFileResourceDecorator(final PrepaymentDataEntryFileResource prepaymentDataEntryFileResource) {
        this.prepaymentDataEntryFileResource = prepaymentDataEntryFileResource;
    }

    @Override
    public ResponseEntity<PrepaymentDataEntryFileDTO> createPrepaymentDataEntryFile(@Valid final PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        return prepaymentDataEntryFileResource.createPrepaymentDataEntryFile(prepaymentDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<PrepaymentDataEntryFileDTO> updatePrepaymentDataEntryFile(@Valid final PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO) throws URISyntaxException {
        return prepaymentDataEntryFileResource.updatePrepaymentDataEntryFile(prepaymentDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> getAllPrepaymentDataEntryFiles(final PrepaymentDataEntryFileCriteria criteria, final Pageable pageable,
                                                                                           final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return prepaymentDataEntryFileResource.getAllPrepaymentDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countPrepaymentDataEntryFiles(final PrepaymentDataEntryFileCriteria criteria) {
        return prepaymentDataEntryFileResource.countPrepaymentDataEntryFiles(criteria);
    }

    @Override
    public ResponseEntity<PrepaymentDataEntryFileDTO> getPrepaymentDataEntryFile(final Long id) {
        return prepaymentDataEntryFileResource.getPrepaymentDataEntryFile(id);
    }

    @Override
    public ResponseEntity<Void> deletePrepaymentDataEntryFile(final Long id) {
        return prepaymentDataEntryFileResource.deletePrepaymentDataEntryFile(id);
    }

    @Override
    public ResponseEntity<List<PrepaymentDataEntryFileDTO>> searchPrepaymentDataEntryFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                           final UriComponentsBuilder uriBuilder) {
        return prepaymentDataEntryFileResource.searchPrepaymentDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
