package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PrepaymentDataEntryFileSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PrepaymentDataEntryFileSearchRepositoryMockConfiguration {

    @MockBean
    private PrepaymentDataEntryFileSearchRepository mockPrepaymentDataEntryFileSearchRepository;

}
