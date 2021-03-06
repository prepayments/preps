package io.github.prepayments.app.messaging.services.scheduler;

import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.AmortizationDataEntryMessageService;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("amortizationDataEntryFileQueueScheduler")
public class AmortizationDataEntryFileQueueScheduler implements DataEntryFileQueueScheduler<AmortizationDataEntryFileDTO, String> {

    private final AmortizationDataEntryMessageService amortizationDataEntryMessageService;

    public AmortizationDataEntryFileQueueScheduler(final AmortizationDataEntryMessageService amortizationDataEntryMessageService) {
        this.amortizationDataEntryMessageService = amortizationDataEntryMessageService;
    }

    /**
     * This method takes in a file data object and returns a List of row indices of items succeffully read from the excel file.
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final AmortizationDataEntryFileDTO dataEntryFile, final String fileToken) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        AtomicInteger count = new AtomicInteger();

        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeAmortizationFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .peek(amortizationEntryEVM -> {
                              dataEntryFile.setEntriesCount(count.incrementAndGet());
                          })
                          .map(amortizationDataEntryMessageService::sendMessage)
                          .collect(Collectors.toList()));
        // @formatter:on

    }
}
