package io.github.prepayments.app.resources;


import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;

import java.util.List;

public interface ReportTypeResolver {

    List<ReportTypeDTO> getReportTypeDTOS(final ReportTypeCriteria reportTypeCriteria, ReportMediumTypes reportTypeMedium);
}
