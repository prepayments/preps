package io.github.prepayments.app.resources;

import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import io.github.prepayments.repository.ReportTypeRepository;
import io.github.prepayments.service.ReportTypeQueryService;
import io.github.prepayments.service.ReportTypeService;
import io.github.prepayments.service.dto.ReportTypeCriteria;
import io.github.prepayments.service.dto.ReportTypeDTO;
import io.github.prepayments.service.mapper.ReportTypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Creates or returns a type of report that is based on excel whose title serves as both the type and title of the same
 */
@Slf4j
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

        log.info("Getting from server reportType of the criteria: {} using the medium: {}",reportTypeCriteria, reportTypeMedium.toString());

        List<ReportTypeDTO> reportTypes =
            reportTypeMapper.toDto(Collections.singletonList(reportTypeRepository.findFirstByReportModelName(reportTypeCriteria.getReportModelName().getContains())));

        if (reportTypes.isEmpty()) {

            log.error("There are no report types matching the criteria: {}", reportTypeCriteria);

            // @formatter:off
            reportTypes.add(reportTypeService.save(ReportTypeDTO.builder()
                                                    .reportModelName(reportTypeCriteria.getReportModelName().getContains())
                                                    .reportMediumType(reportTypeMedium)
                                                    .reportPassword("")
                                                    .build())
            );
            // @formatter:on
        }

        log.info("Report type found : {}", reportTypes.get(0));

        return reportTypes;
    }
}
