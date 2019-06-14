package io.github.prepayments.repository.search;

import io.github.prepayments.domain.ScannedDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ScannedDocument} entity.
 */
public interface ScannedDocumentSearchRepository extends ElasticsearchRepository<ScannedDocument, Long> {
}
