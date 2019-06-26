package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingAmortizationUploadStreams;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service("amortizationUploadFileMessageService")
public class AmortizationUploadFileMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public AmortizationUploadFileMessageService(final FilingAmortizationUploadStreams filingAmortizationUploadStreams) {
        super(filingAmortizationUploadStreams.outboundAmortizationEntries());
    }

    public long sendMessage(final AmortizationUploadEVM dataEntryItem) {

        log.trace("Sending amortization upload {}", dataEntryItem.getRowIndex());

        return super.sendMessage(dataEntryItem);
    }
}
