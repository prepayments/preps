package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.service.AmortizationEntriesPropagatorService;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUploadStreams;
import io.github.prepayments.app.util.SimpleAmortizationUploadEVMDTOMapper;
import io.github.prepayments.service.AmortizationUploadService;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;
import static io.github.prepayments.app.AppConstants.MONTHLY_AMORTIZATION_DATE;

@Slf4j
@Component
public class DataSinkAmortizationUploadsStreamsListener {

    private final AmortizationUploadService amortizationUploadService;
    private final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService;
    private final SimpleAmortizationUploadEVMDTOMapper amortizationUploadEVMDTOMapper;

    public DataSinkAmortizationUploadsStreamsListener(final AmortizationUploadService amortizationUploadService, final AmortizationEntriesPropagatorService amortizationEntriesPropagatorService,
                                                      final SimpleAmortizationUploadEVMDTOMapper amortizationUploadEVMDTOMapper) {
        this.amortizationUploadService = amortizationUploadService;
        this.amortizationEntriesPropagatorService = amortizationEntriesPropagatorService;
        this.amortizationUploadEVMDTOMapper = amortizationUploadEVMDTOMapper;
    }

    @StreamListener(FilingAmortizationUploadStreams.INPUT)
    public void handleAmortizationUploadStreams(@Payload SimpleAmortizationUploadEVM simpleAmortizationUploadEVM) {
        log.info("Received amortization upload #: {} standby for persistence...", simpleAmortizationUploadEVM.getRowIndex());

        AmortizationUploadDTO result = amortizationUploadService.save(amortizationUploadEVMDTOMapper.toDto(simpleAmortizationUploadEVM, DATETIME_FORMATTER));

        if (simpleAmortizationUploadEVM.getMonthlyAmortizationDate() == null) {
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DATETIME_FORMATTER, result, MONTHLY_AMORTIZATION_DATE);
        }
        if (simpleAmortizationUploadEVM.getMonthlyAmortizationDate() != null) {
            amortizationEntriesPropagatorService.propagateAmortizationEntries(DATETIME_FORMATTER, result, Integer.parseInt(simpleAmortizationUploadEVM.getMonthlyAmortizationDate()));
        }
        log.debug("Amortization upload item saved : #{}", result.getId());
    }
}
