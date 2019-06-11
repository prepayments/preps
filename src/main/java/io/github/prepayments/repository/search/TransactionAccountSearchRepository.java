package io.github.prepayments.repository.search;

import io.github.prepayments.domain.TransactionAccount;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionAccount} entity.
 */
public interface TransactionAccountSearchRepository extends ElasticsearchRepository<TransactionAccount, Long> {
}
