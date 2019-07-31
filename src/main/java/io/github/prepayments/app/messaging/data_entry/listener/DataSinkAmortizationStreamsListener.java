package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationEntryStreams;
import io.github.prepayments.app.util.AmortizationEntryEVMDTOMapper;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;

/**
 * Temporary setup to see if messaging works. We are going to listen to streams of data and upload it to the server
 */
@Component
@Slf4j
public class DataSinkAmortizationStreamsListener {

    private final AmortizationEntryService amortizationEntryService;
    private final AmortizationEntryEVMDTOMapper amortizationEntryEVMDTOMapper;

    public DataSinkAmortizationStreamsListener(final AmortizationEntryService amortizationEntryService, final AmortizationEntryEVMDTOMapper amortizationEntryEVMDTOMapper) {
        this.amortizationEntryService = amortizationEntryService;
        this.amortizationEntryEVMDTOMapper = amortizationEntryEVMDTOMapper;
    }

    @StreamListener(FilingAmortizationEntryStreams.INPUT)
    public void handleAmortizationEntryStreams(@Payload SimpleAmortizationEntryEVM simpleAmortizationEntryEVM) {
        log.info("Received amortizationEntry dated : {} for prepayment id #: {} standby for persistence...", simpleAmortizationEntryEVM.getAmortizationDate(),
                 simpleAmortizationEntryEVM.getPrepaymentEntryId());

        AmortizationEntryDTO dto = amortizationEntryEVMDTOMapper.toDto(simpleAmortizationEntryEVM, DateTimeFormatter.ofPattern(DATETIME_FORMAT));

        AmortizationEntryDTO result = amortizationEntryService.save(dto);

        log.debug("Amortization entry item saved : #{}", result.getId());
    }
}
