package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingPrepaymentEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("prepaymentDataEntryMessageService")
@Transactional
public class PrepaymentDataEntryMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public PrepaymentDataEntryMessageService(final FilingPrepaymentEntryStreams filingPrepaymentEntryStreams) {
        super(filingPrepaymentEntryStreams.outboundPrepaymentEntries());
    }

    public long sendMessage(final PrepaymentEntryEVM dataEntryItem) {

        log.trace("Sending prepaymentEntry {}", dataEntryItem);

        return super.sendMessage(dataEntryItem);
    }
}
