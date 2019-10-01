package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUpdateStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryUpdateEVM;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("amortizationUpdateMessageService")
public class AmortizationUpdateMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public AmortizationUpdateMessageService(final FilingAmortizationUpdateStreams filingAmortizationUpdateStreams) {
        super(filingAmortizationUpdateStreams.outboundAmortizationUpdates());
    }

    public long sendMessage(final AmortizationEntryUpdateEVM dataEntryItem) {
        log.trace("Sending amortizationEntry {}", dataEntryItem.getRowIndex());
        return super.sendMessage(dataEntryItem);
    }
}
