package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.ISupplierDataEntryFileResource;
import io.github.prepayments.service.dto.SupplierDataEntryFileCriteria;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import io.github.prepayments.web.rest.SupplierDataEntryFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("supplierDataEntryFileResourceDecorator")
public class SupplierDataEntryFileResourceDecorator implements ISupplierDataEntryFileResource {

    private final SupplierDataEntryFileResource supplierDataEntryFileResource;

    public SupplierDataEntryFileResourceDecorator(final SupplierDataEntryFileResource supplierDataEntryFileResource) {
        this.supplierDataEntryFileResource = supplierDataEntryFileResource;
    }

    @Override
    public ResponseEntity<SupplierDataEntryFileDTO> createSupplierDataEntryFile(@Valid final SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        return supplierDataEntryFileResource.createSupplierDataEntryFile(supplierDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<SupplierDataEntryFileDTO> updateSupplierDataEntryFile(@Valid final SupplierDataEntryFileDTO supplierDataEntryFileDTO) throws URISyntaxException {
        return supplierDataEntryFileResource.updateSupplierDataEntryFile(supplierDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<List<SupplierDataEntryFileDTO>> getAllSupplierDataEntryFiles(final SupplierDataEntryFileCriteria criteria, final Pageable pageable,
                                                                                       final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return supplierDataEntryFileResource.getAllSupplierDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countSupplierDataEntryFiles(final SupplierDataEntryFileCriteria criteria) {
        return supplierDataEntryFileResource.countSupplierDataEntryFiles(criteria);
    }

    @Override
    public ResponseEntity<SupplierDataEntryFileDTO> getSupplierDataEntryFile(final Long id) {
        return supplierDataEntryFileResource.getSupplierDataEntryFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteSupplierDataEntryFile(final Long id) {
        return supplierDataEntryFileResource.deleteSupplierDataEntryFile(id);
    }

    @Override
    public ResponseEntity<List<SupplierDataEntryFileDTO>> searchSupplierDataEntryFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                       final UriComponentsBuilder uriBuilder) {
        return supplierDataEntryFileResource.searchSupplierDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
