package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.GeneratedReportDTO;
import io.github.prepayments.app.services.TransactionAccountsReportService;
import io.github.prepayments.service.dto.TransactionAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.prepayments.app.services.FileInputUtil.createExcelFile;

@Slf4j
@Transactional
@Service("transactionAccountReportGeneratorService")
public class TransactionAccountReportGeneratorService implements ReportGeneratorService {

    private final TransactionAccountsReportService transactionAccountsReportService;

    public TransactionAccountReportGeneratorService(final TransactionAccountsReportService transactionAccountsReportService) {
        this.transactionAccountsReportService = transactionAccountsReportService;
    }

    @Override
    public Optional<GeneratedReportDTO> generateReport(String reportTitle, String reportType) {

        List<TransactionAccountDTO> transactionAccounts = transactionAccountsReportService.findAll();

        log.debug("Fetched {} transaction accounts entry items from the serviceOutletCatalogue", transactionAccounts.size());

        byte[] dataFile = createExcelFile(transactionAccounts, TransactionAccountDTO.class);

        GeneratedReportDTO dto = GeneratedReportDTO.builder()
                                                   .reportTitle(reportTitle)
                                                   .reportType(reportType)
                                                   .reportFile(dataFile)
                                                   .reportFileContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                                                   .build();

        return Optional.of(Objects.requireNonNull(dto));
    }
}
