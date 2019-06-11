package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingTransactionAccountDataEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionAccountDataEntryMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public TransactionAccountDataEntryMessageService(final FilingTransactionAccountDataEntryStreams filingTransactionAccountDataEntryStreams) {
        super(filingTransactionAccountDataEntryStreams.outboundTransactionAccountDataStream());
    }

    public long sendMessage(final TransactionAccountEVM dataEntryItem) {

        log.trace("Sending transactionAccount index # {}", dataEntryItem.getRowIndex());

        return super.sendMessage(dataEntryItem);
    }
}
