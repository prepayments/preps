package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("amortizationDataEntryMessageService")
public class AmortizationDataEntryMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public AmortizationDataEntryMessageService(final FilingAmortizationEntryStreams filingAmortizationEntryStreams) {
        super(filingAmortizationEntryStreams.outboundAmortizationEntries());
    }

    /**
     * This method sends a services of type T into a queue destination and returns true if successful
     */
    public long sendMessage(final AmortizationEntryEVM dataEntryItem) {

        log.trace("Sending amortizationEntry {}", dataEntryItem.getRowIndex());

        return super.sendMessage(dataEntryItem);

    }
}
