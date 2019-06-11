package io.github.prepayments.repository.search;

import io.github.prepayments.domain.ReportRequestEvent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ReportRequestEvent} entity.
 */
public interface ReportRequestEventSearchRepository extends ElasticsearchRepository<ReportRequestEvent, Long> {
}
