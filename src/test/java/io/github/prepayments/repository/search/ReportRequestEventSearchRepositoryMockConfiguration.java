package io.github.prepayments.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ReportRequestEventSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ReportRequestEventSearchRepositoryMockConfiguration {

    @MockBean
    private ReportRequestEventSearchRepository mockReportRequestEventSearchRepository;

}
