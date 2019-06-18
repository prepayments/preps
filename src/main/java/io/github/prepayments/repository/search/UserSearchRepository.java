package io.github.prepayments.repository.search;

import io.github.prepayments.domain.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Elasticsearch repository for the User entity.
 */
@Repository("userSearchRepo")
public interface UserSearchRepository extends ElasticsearchRepository<User, Long> {
}
