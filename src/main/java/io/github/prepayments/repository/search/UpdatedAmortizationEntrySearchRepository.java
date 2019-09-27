package io.github.prepayments.repository.search;

import io.github.prepayments.domain.UpdatedAmortizationEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UpdatedAmortizationEntry} entity.
 */
public interface UpdatedAmortizationEntrySearchRepository extends ElasticsearchRepository<UpdatedAmortizationEntry, Long> {
}
