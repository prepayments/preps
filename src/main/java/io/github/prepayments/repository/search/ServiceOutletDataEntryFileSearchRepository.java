package io.github.prepayments.repository.search;

import io.github.prepayments.domain.ServiceOutletDataEntryFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceOutletDataEntryFile} entity.
 */
public interface ServiceOutletDataEntryFileSearchRepository extends ElasticsearchRepository<ServiceOutletDataEntryFile, Long> {
}
