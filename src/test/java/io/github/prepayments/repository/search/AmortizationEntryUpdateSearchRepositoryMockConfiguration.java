package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AmortizationEntryUpdateSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AmortizationEntryUpdateSearchRepositoryMockConfiguration {

    @MockBean
    private AmortizationEntryUpdateSearchRepository mockAmortizationEntryUpdateSearchRepository;

}
