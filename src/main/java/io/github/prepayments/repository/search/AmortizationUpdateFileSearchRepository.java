package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationUpdateFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationUpdateFile} entity.
 */
public interface AmortizationUpdateFileSearchRepository extends ElasticsearchRepository<AmortizationUpdateFile, Long> {
}
