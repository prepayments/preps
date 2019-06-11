package io.github.prepayments.app.resources;

import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import io.github.prepayments.repository.ReportTypeRepository;
import io.github.prepayments.service.ReportTypeQueryService;
import io.github.prepayments.service.ReportTypeService;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.mapper.ReportTypeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Creates or returns a type of report that is based on excel whose title serves as both the type and title of the same
 */
@Transactional
@Service("reportTitleTypeResolver")
public class ReportTitleTypeResolver implements ReportTypeResolver {

    private final ReportTypeMapper reportTypeMapper;
    private final ReportTypeService reportTypeService;
    private final ReportTypeRepository reportTypeRepository;

    public ReportTitleTypeResolver(final ReportTypeQueryService reportTypeQueryService, final ReportTypeMapper reportTypeMapper, final ReportTypeService reportTypeService, final ReportTypeRepository reportTypeRepository) {
        this.reportTypeMapper = reportTypeMapper;
        this.reportTypeService = reportTypeService;
        this.reportTypeRepository = reportTypeRepository;
    }

    public List<ReportTypeDTO> getReportTypeDTOS(final ReportTypeCriteria reportTypeCriteria, ReportMediumTypes reportTypeMedium) {

        List<ReportTypeDTO> reportTypes =
            reportTypeMapper.toDto(Collections.singletonList(reportTypeRepository.findFirstByReportModelName(reportTypeCriteria.getReportModelName().getContains())));

        if (reportTypes.isEmpty()) {

            // @formatter:off
            reportTypes.add(reportTypeService.save(ReportTypeDTO.builder()
                                                    .reportModelName(reportTypeCriteria.getReportModelName().getContains())
                                                    .reportMediumType(reportTypeMedium)
                                                    .reportPassword("")
                                                    .build())
            );
            // @formatter:on
        }

        return reportTypes;
    }
}
