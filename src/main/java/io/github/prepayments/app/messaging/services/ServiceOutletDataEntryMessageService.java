package io.github.prepayments.app.messaging.services;

import io.github.prepayments.app.messaging.DataEntryMessageService;
import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.filing.streams.FilingServiceOutletDataStreams;
import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServiceOutletDataEntryMessageService extends DataEntryMessageService implements MessageService<ExcelViewModel> {

    public ServiceOutletDataEntryMessageService(final FilingServiceOutletDataStreams filingServiceOutletDataStreams) {
        super(filingServiceOutletDataStreams.outboundServiceOutletDataEntries());
    }

    public long sendMessage(final ServiceOutletEVM dataEntryItem) {

        log.trace("Sending serviceOutletEVM index # {}", dataEntryItem.getRowIndex());

        return super.sendMessage(dataEntryItem);
    }
}
