package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.service.IPrepaymentEntryIdService;
import io.github.prepayments.app.messaging.data_entry.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationEntryStreams;
import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Temporary setup to see if messagin works. We are going to listen to streams of data and upload it to the server
 */
@Component
@Slf4j
public class DataSinkAmortizationStreamsListener {

    private final AmortizationEntryService amortizationEntryService;
    private final IPrepaymentEntryIdService prepaymentEntryIdService;

    public DataSinkAmortizationStreamsListener(AmortizationEntryService amortizationEntryService, @Qualifier("prepaymentIdService") IPrepaymentEntryIdService prepaymentEntryIdService) {
        this.amortizationEntryService = amortizationEntryService;
        this.prepaymentEntryIdService = prepaymentEntryIdService;
    }

    @StreamListener(FilingAmortizationEntryStreams.INPUT)
    public void handleAmortizationEntryStreams(@Payload AmortizationEntryEVM amortizationEntryEVM) {
        log.info("Received amortizationEntry #: {} standby for persistence...", amortizationEntryEVM.getRowIndex());

//        AmortizationEntryDTO dto = amortizationDataEntryFileDTOMapper.toDTO(amortizationEntryEVM);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        // @formatter:off
        AmortizationEntryDTO dto = AmortizationEntryDTO.builder()
                                                       .amortizationDate(
                                                           LocalDate.parse(amortizationEntryEVM.getAmortizationDate(), dtf))
                                                       .amortizationAmount(BigDecimal.valueOf(Double.parseDouble(amortizationEntryEVM.getAmortizationAmount())))
                                                       .particulars(amortizationEntryEVM.getParticulars())
                                                       .posted(false)
                                                       .serviceOutlet(amortizationEntryEVM.getServiceOutlet())
                                                       .accountNumber(amortizationEntryEVM.getAccountNumber())
                                                       .accountName(amortizationEntryEVM.getAccountName())
                                                       .build();
        // @formatter:on

        // PREPAYMENT ID is assumed to be id # as stored in the db for prepayments
        dto.setPrepaymentEntryId(prepaymentEntryIdService.findByIdAndDate(amortizationEntryEVM.getPrepaymentEntryId(), amortizationEntryEVM.getPrepaymentEntryDate()));
        AmortizationEntryDTO result = amortizationEntryService.save(dto);

        log.debug("Amortization entry item saved : #{}", result.getId());


    }
}
