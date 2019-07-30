package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationDataEntryFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationDataEntryFile} entity.
 */
public interface AmortizationDataEntryFileSearchRepository extends ElasticsearchRepository<AmortizationDataEntryFile, Long> {
}
