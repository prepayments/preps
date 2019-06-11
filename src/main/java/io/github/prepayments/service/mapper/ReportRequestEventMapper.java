package io.github.prepayments.service.mapper;

import io.github.prepayments.domain.*;
import io.github.prepayments.service.dto.ReportRequestEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReportRequestEvent} and its DTO {@link ReportRequestEventDTO}.
 */
@Mapper(componentModel = "spring", uses = {ReportTypeMapper.class})
public interface ReportRequestEventMapper extends EntityMapper<ReportRequestEventDTO, ReportRequestEvent> {

    @Mapping(source = "reportType.id", target = "reportTypeId")
    ReportRequestEventDTO toDto(ReportRequestEvent reportRequestEvent);

    @Mapping(source = "reportTypeId", target = "reportType")
    ReportRequestEvent toEntity(ReportRequestEventDTO reportRequestEventDTO);

    default ReportRequestEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReportRequestEvent reportRequestEvent = new ReportRequestEvent();
        reportRequestEvent.setId(id);
        return reportRequestEvent;
    }
}
