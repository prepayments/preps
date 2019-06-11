package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationEntry} entity.
 */
public interface AmortizationEntrySearchRepository extends ElasticsearchRepository<AmortizationEntry, Long> {
}
