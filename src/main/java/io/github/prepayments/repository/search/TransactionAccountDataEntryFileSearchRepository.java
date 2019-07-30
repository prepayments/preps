package io.github.prepayments.repository.search;

import io.github.prepayments.domain.TransactionAccountDataEntryFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionAccountDataEntryFile} entity.
 */
public interface TransactionAccountDataEntryFileSearchRepository extends ElasticsearchRepository<TransactionAccountDataEntryFile, Long> {
}
