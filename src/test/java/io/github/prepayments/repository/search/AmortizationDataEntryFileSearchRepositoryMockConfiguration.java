package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AmortizationDataEntryFileSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AmortizationDataEntryFileSearchRepositoryMockConfiguration {

    @MockBean
    private AmortizationDataEntryFileSearchRepository mockAmortizationDataEntryFileSearchRepository;

}
