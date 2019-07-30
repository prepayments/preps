package io.github.prepayments.repository.search;

import io.github.prepayments.domain.PrepaymentDataEntryFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrepaymentDataEntryFile} entity.
 */
public interface PrepaymentDataEntryFileSearchRepository extends ElasticsearchRepository<PrepaymentDataEntryFile, Long> {
}
