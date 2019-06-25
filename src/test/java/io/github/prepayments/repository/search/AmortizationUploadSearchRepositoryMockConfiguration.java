package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AmortizationUploadSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AmortizationUploadSearchRepositoryMockConfiguration {

    @MockBean
    private AmortizationUploadSearchRepository mockAmortizationUploadSearchRepository;

}
