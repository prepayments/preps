package io.github.prepayments.repository.search;

import io.github.prepayments.domain.SupplierDataEntryFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link SupplierDataEntryFile} entity.
 */
public interface SupplierDataEntryFileSearchRepository extends ElasticsearchRepository<SupplierDataEntryFile, Long> {
}
