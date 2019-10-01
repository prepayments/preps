package io.github.prepayments.app.messaging.services.scheduler;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.AmortizationUpdateMessageService;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.app.messaging.services.notifications.AmortizationUpdateFileMessageService;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Transactional
@Service("amortizationUpdateFileQueueScheduler")
public class AmortizationUpdateFileQueueScheduler implements DataEntryFileQueueScheduler<AmortizationUpdateFileDTO, String> {

    private final AmortizationUpdateMessageService amortizationUpdateMessageService;

    public AmortizationUpdateFileQueueScheduler(final AmortizationUpdateMessageService amortizationUpdateMessageService) {
        this.amortizationUpdateMessageService = amortizationUpdateMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final AmortizationUpdateFileDTO dataEntryFile, final String fileToken) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        AtomicInteger count = new AtomicInteger();

        // TODO Check if originating-file-token is transmitted
        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeAmortizationUpdateFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .peek(amortizationUploadEVM -> {
                              dataEntryFile.setEntriesCount(count.incrementAndGet());
                          })
                          .map(amortizationUpdateMessageService::sendMessage)
                          .collect(ImmutableList.toImmutableList()));
        // @formatter:on
    }
}
