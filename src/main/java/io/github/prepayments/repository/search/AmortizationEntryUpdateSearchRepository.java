package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationEntryUpdate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationEntryUpdate} entity.
 */
public interface AmortizationEntryUpdateSearchRepository extends ElasticsearchRepository<AmortizationEntryUpdate, Long> {
}
