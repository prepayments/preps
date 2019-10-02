package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.updates.UpdateInstallationService;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryUpdateEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUpdateStreams;
import io.github.prepayments.service.AmortizationEntryUpdateService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSinkAmortizationUpdateStreamsListener {

    private final AmortizationEntryUpdateService amortizationEntryUpdateService;
    private final UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> amortizationUpdateInstallationService;
    private final DtoMapper<SimpleAmortizationEntryUpdateEVM, AmortizationEntryUpdateDTO> amortizationEntryUpdateDtoMapper;


    public DataSinkAmortizationUpdateStreamsListener(final AmortizationEntryUpdateService amortizationEntryUpdateService,
                                                     final UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> amortizationUpdateInstallationService,
                                                     final DtoMapper<SimpleAmortizationEntryUpdateEVM, AmortizationEntryUpdateDTO> amortizationEntryUpdateDtoMapper) {
        this.amortizationEntryUpdateService = amortizationEntryUpdateService;
        this.amortizationUpdateInstallationService = amortizationUpdateInstallationService;
        this.amortizationEntryUpdateDtoMapper = amortizationEntryUpdateDtoMapper;
    }

    @StreamListener(FilingAmortizationUpdateStreams.INPUT)
    public void handleAmortizationEntryStreams(@Payload SimpleAmortizationEntryUpdateEVM updateEVM) {
        log.trace("Received amortizationEntry update for amortizaztion id : {} dated : {} for prepayment id #: {} standby for persistence...", updateEVM.getAmortizationEntryId(),
                  updateEVM.getAmortizationDate(), updateEVM.getPrepaymentEntryId());

        // TODO save the amortization update
        AmortizationEntryUpdateDTO result = amortizationEntryUpdateService.save(amortizationEntryUpdateDtoMapper.toDTO(updateEVM));
        log.trace("Amortization entry update id : {} persisted and reeady for installation...", result.getId());

        // TODO create backup of existing amortization entries and update
        try {
            amortizationUpdateInstallationService.install(result);
        } catch (Throwable throwable) {
            throw new RuntimeException("Could not install update for amortization id : " + updateEVM.getAmortizationEntryId(), throwable);
        }

    }
}
