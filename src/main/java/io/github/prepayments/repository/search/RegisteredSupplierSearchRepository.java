package io.github.prepayments.repository.search;

import io.github.prepayments.domain.RegisteredSupplier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link RegisteredSupplier} entity.
 */
public interface RegisteredSupplierSearchRepository extends ElasticsearchRepository<RegisteredSupplier, Long> {
}
