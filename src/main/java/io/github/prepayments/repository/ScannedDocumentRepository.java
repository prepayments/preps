package io.github.prepayments.repository;

import io.github.prepayments.domain.ScannedDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScannedDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScannedDocumentRepository extends JpaRepository<ScannedDocument, Long>, JpaSpecificationExecutor<ScannedDocument> {

}
