package io.github.prepayments.app.resources;

import io.github.jhipster.web.util.ResponseUtil;
import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.service.ReportRequestEventService;
import io.github.prepayments.service.dto.ReportRequestEventDTO;
import io.github.prepayments.service.dto.ReportTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static io.github.prepayments.security.SecurityUtils.getCurrentUserLogin;

@Slf4j
public abstract class AbstractGeneratedListingReportResource implements ReportResponseGenerator {

    private final ReportRequestEventService reportRequestEventService;

    public AbstractGeneratedListingReportResource(final ReportRequestEventService reportRequestEventService) {
        this.reportRequestEventService = reportRequestEventService;
    }

    public ResponseEntity<GeneratedReportDTO> generatedReportResponseEntity() {

        log.debug("REST request to get prepaymentEntries report : {}", getReportTitle());

        ReportTypeDTO reportType = getReportType();


        Optional<GeneratedReportDTO> generatedReportDTO = generateReport();

        LocalDate reportTime = LocalDate.now();

        final ReportRequestEventDTO requestEventDTO =
            ReportRequestEventDTO.builder()
                                 .reportFile(generatedReportDTO.get().getReportFile())
                                 .reportFileContentType(generatedReportDTO.get().getReportFileContentType())
                                 .reportRequestDate(reportTime)
                                 .reportTypeId(reportType.getId())
                                 .requestedBy(getCurrentUserLogin().get())
                                 .build();

        ReportRequestEventDTO result = reportRequestEventService.save(requestEventDTO);

        log.debug("Report requested : {}", result);

        return ResponseUtil.wrapOrNotFound(generatedReportDTO);
    }


    abstract Optional<GeneratedReportDTO> generateReport();

    abstract String getReportTitle();

    abstract ReportTypeDTO getReportType();
}
