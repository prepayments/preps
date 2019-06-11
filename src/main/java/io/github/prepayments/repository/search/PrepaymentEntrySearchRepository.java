package io.github.prepayments.repository.search;

import io.github.prepayments.domain.PrepaymentEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PrepaymentEntry} entity.
 */
public interface PrepaymentEntrySearchRepository extends ElasticsearchRepository<PrepaymentEntry, Long> {
}
