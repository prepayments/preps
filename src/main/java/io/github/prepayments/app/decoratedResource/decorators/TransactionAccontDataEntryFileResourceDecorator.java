package io.github.prepayments.app.decoratedResource.decorators;

import io.github.prepayments.app.decoratedResource.ITransactionAccountDataEntryFileResource;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileCriteria;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import io.github.prepayments.web.rest.TransactionAccountDataEntryFileResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This is created as a decorator which in fact uses protocols from the resource being decorated
 * to implement the interface
 */
@Component("transactionAccontDataEntryFileResourceDecorator")
public class TransactionAccontDataEntryFileResourceDecorator implements ITransactionAccountDataEntryFileResource {


    private final TransactionAccountDataEntryFileResource transactionAccountDataEntryFileResource;

    public TransactionAccontDataEntryFileResourceDecorator(final TransactionAccountDataEntryFileResource transactionAccountDataEntryFileResource) {
        this.transactionAccountDataEntryFileResource = transactionAccountDataEntryFileResource;
    }

    @Override
    public ResponseEntity<TransactionAccountDataEntryFileDTO> createTransactionAccountDataEntryFile(@Valid final TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        return transactionAccountDataEntryFileResource.createTransactionAccountDataEntryFile(transactionAccountDataEntryFileDTO);
    }

    @Override
    public ResponseEntity<TransactionAccountDataEntryFileDTO> updateTransactionAccountDataEntryFile(@Valid final TransactionAccountDataEntryFileDTO transactionAccountDataEntryFileDTO)
        throws URISyntaxException {
        return transactionAccountDataEntryFileResource.updateTransactionAccountDataEntryFile(transactionAccountDataEntryFileDTO);
    }

    /**
     * Please note the last 2 parameters of this method's implementation are not needed in the newer api
     * though it is better that the interface remains the same for practicality and flexibility
     *
     * @param criteria the criteria which the requested entities should match.
     * @param pageable the pagination information.
     * @param queryParams
     * @param uriBuilder
     * @return
     */
    @Override
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> getAllTransactionAccountDataEntryFiles(final TransactionAccountDataEntryFileCriteria criteria, final Pageable pageable,
                                                                                                           final MultiValueMap<String, String> queryParams, final UriComponentsBuilder uriBuilder) {
        return transactionAccountDataEntryFileResource.getAllTransactionAccountDataEntryFiles(criteria, pageable, queryParams, uriBuilder);
    }

    @Override
    public ResponseEntity<Long> countTransactionAccountDataEntryFiles(final TransactionAccountDataEntryFileCriteria criteria) {
        return transactionAccountDataEntryFileResource.countTransactionAccountDataEntryFiles(criteria);
    }

    @Override
    public ResponseEntity<TransactionAccountDataEntryFileDTO> getTransactionAccountDataEntryFile(final Long id) {
        return transactionAccountDataEntryFileResource.getTransactionAccountDataEntryFile(id);
    }

    @Override
    public ResponseEntity<Void> deleteTransactionAccountDataEntryFile(final Long id) {
        return transactionAccountDataEntryFileResource.deleteTransactionAccountDataEntryFile(id);
    }

    @Override
    public ResponseEntity<List<TransactionAccountDataEntryFileDTO>> searchTransactionAccountDataEntryFiles(final String query, final Pageable pageable, final MultiValueMap<String, String> queryParams,
                                                                                                           final UriComponentsBuilder uriBuilder) {
        return transactionAccountDataEntryFileResource.searchTransactionAccountDataEntryFiles(query, pageable, queryParams, uriBuilder);
    }
}
