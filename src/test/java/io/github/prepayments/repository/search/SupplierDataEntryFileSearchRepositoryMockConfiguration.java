package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link SupplierDataEntryFileSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class SupplierDataEntryFileSearchRepositoryMockConfiguration {

    @MockBean
    private SupplierDataEntryFileSearchRepository mockSupplierDataEntryFileSearchRepository;

}
