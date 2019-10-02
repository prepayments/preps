package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.DtoMapper;
import io.github.prepayments.app.messaging.data_entry.updates.UpdateInstallationService;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleAmortizationEntryUpdateEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUpdateStreams;
import io.github.prepayments.app.token.Tag;
import io.github.prepayments.app.token.TagProvider;
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

    private final TagProvider<String> tagProvider;
    private final AmortizationEntryUpdateService amortizationEntryUpdateService;
    private final UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> amortizationUpdateInstallationService;
    private final DtoMapper<SimpleAmortizationEntryUpdateEVM, AmortizationEntryUpdateDTO> amortizationEntryUpdateDtoMapper;


    public DataSinkAmortizationUpdateStreamsListener(final TagProvider<String> tagProvider, final AmortizationEntryUpdateService amortizationEntryUpdateService,
                                                     final UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> amortizationUpdateInstallationService,
                                                     final DtoMapper<SimpleAmortizationEntryUpdateEVM, AmortizationEntryUpdateDTO> amortizationEntryUpdateDtoMapper) {
        this.tagProvider = tagProvider;
        this.amortizationEntryUpdateService = amortizationEntryUpdateService;
        this.amortizationUpdateInstallationService = amortizationUpdateInstallationService;
        this.amortizationEntryUpdateDtoMapper = amortizationEntryUpdateDtoMapper;
    }

    @StreamListener(FilingAmortizationUpdateStreams.INPUT)
    public void handleAmortizationEntryStreams(@Payload SimpleAmortizationEntryUpdateEVM updateEVM) {
        log.trace("Received amortizationEntry update for amortizaztion id : {} dated : {} for prepayment id #: {} standby for persistence...", updateEVM.getAmortizationEntryId(),
                  updateEVM.getAmortizationDate(), updateEVM.getPrepaymentEntryId());

        // TODO Add tag sequence to check conformity
        Tag<String> amortTag = tagProvider.tag(updateEVM);
        updateEVM.setOrphaned(false);

        // TODO save the amortization update
        AmortizationEntryUpdateDTO result = amortizationEntryUpdateService.save(amortizationEntryUpdateDtoMapper.toDTO(updateEVM));
        log.trace("Amortization entry update id : {} persisted and ready for installation...", result.getId());

        // TODO create backup of existing amortization entries and update
        try {
            amortizationUpdateInstallationService.install(result);
        } catch (Throwable throwable) {
            throw new RuntimeException("Could not install update for amortization id : " + updateEVM.getAmortizationEntryId(), throwable);
        }

    }
}
