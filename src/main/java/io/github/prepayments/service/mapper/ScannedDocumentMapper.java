package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.ScannedDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScannedDocument} and its DTO {@link ScannedDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScannedDocumentMapper extends EntityMapper<ScannedDocumentDTO, ScannedDocument> {



    default ScannedDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScannedDocument scannedDocument = new ScannedDocument();
        scannedDocument.setId(id);
        return scannedDocument;
    }
}
