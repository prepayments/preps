package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.IServiceOutletDataEntryFileResource;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileCriteria;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import io.github.prepayments.web.rest.ServiceOutletDataEntryFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@Component("serviceOutletDataEntryFileResourceDecorator")
public class ServiceOutletDataEntryFileResourceDecorator implements IServiceOutletDataEntryFileResource {

    private final ServiceOutletDataEntryFileResource serviceOutletDataEntryFileResource;

    public ServiceOutletDataEntryFileResourceDecorator(final ServiceOutletDataEntryFileResource serviceOutletDataEntryFileResource) {
        this.serviceOutletDataEntryFileResource = serviceOutletDataEntryFileResource;
    }

    @Override
    public ResponseEntity<ServiceOutletDataEntryFileDTO> createServiceOutletDataEntryFile(@Valid final ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        return serviceOutletDataEntryFileResource.createServiceOutletDataEntryFile(serviceOutletDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<ServiceOutletDataEntryFileDTO> updateServiceOutletDataEntryFile(@Valid final ServiceOutletDataEntryFileDTO serviceOutletDataEntryFileDTO) throws URISyntaxException {
        return serviceOutletDataEntryFileResource.updateServiceOutletDataEntryFile(serviceOutletDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<List<ServiceOutletDataEntryFileDTO>> getAllServiceOutletDataEntryFiles(final ServiceOutletDataEntryFileCriteria criteria, final Pageable pageable,
                                                                                                 final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return serviceOutletDataEntryFileResource.getAllServiceOutletDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countServiceOutletDataEntryFiles(final ServiceOutletDataEntryFileCriteria criteria) {
        return null;
    }

    @Override
    public ResponseEntity<ServiceOutletDataEntryFileDTO> getServiceOutletDataEntryFile(final Long id) {
        return serviceOutletDataEntryFileResource.getServiceOutletDataEntryFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteServiceOutletDataEntryFile(final Long id) {
        return serviceOutletDataEntryFileResource.deleteServiceOutletDataEntryFile(id);
    }

    @Override
    public ResponseEntity<List<ServiceOutletDataEntryFileDTO>> searchServiceOutletDataEntryFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                                 final UriComponentsBuilder uriBuilder) {
        return serviceOutletDataEntryFileResource.searchServiceOutletDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
