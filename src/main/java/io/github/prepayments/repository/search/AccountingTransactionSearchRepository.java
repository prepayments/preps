package io.github.prepayments.repository.search;

import io.github.prepayments.domain.AccountingTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AccountingTransaction} entity.
 */
public interface AccountingTransactionSearchRepository extends ElasticsearchRepository<AccountingTransaction, Long> {
}
