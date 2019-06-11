package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingSupplierDataEntryStreams;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SupplierDataEntryMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {


    public SupplierDataEntryMessageService(final FilingSupplierDataEntryStreams filingSupplierDataEntryStreams) {
        super(filingSupplierDataEntryStreams.outboundSupplierDataStream());
    }

    public long sendMessage(final RegisteredSupplierEVM dataEntryItem) {

        log.trace("Sending registeredSupplierEVM {}", dataEntryItem.getRowIndex());

        return super.sendMessage(dataEntryItem);
    }
}
