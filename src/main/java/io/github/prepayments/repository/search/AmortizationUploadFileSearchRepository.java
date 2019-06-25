package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AmortizationUploadFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AmortizationUploadFile} entity.
 */
public interface AmortizationUploadFileSearchRepository extends ElasticsearchRepository<AmortizationUploadFile, Long> {
}
