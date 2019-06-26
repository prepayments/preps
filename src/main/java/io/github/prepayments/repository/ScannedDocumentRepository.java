package io.github.prepayments.repository;

import io.github.prepayments.domain.ScannedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScannedDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScannedDocumentRepository extends JpaRepository<ScannedDocument, Long>, JpaSpecificationExecutor<ScannedDocument> {

}
