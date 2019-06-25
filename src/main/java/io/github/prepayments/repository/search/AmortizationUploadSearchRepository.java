package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationUpload} entity.
 */
public interface AmortizationUploadSearchRepository extends ElasticsearchRepository<AmortizationUpload, Long> {
}
