package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link TransactionAccountSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class TransactionAccountSearchRepositoryMockConfiguration {

    @MockBean
    private TransactionAccountSearchRepository mockTransactionAccountSearchRepository;

}
