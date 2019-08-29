package io.github.prepayments.app.messaging.services.scheduler;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.AmortizationUploadFileMessageService;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("amortizationUploadFileQueueScheduler")
public class AmortizationUploadFileQueueScheduler implements DataEntryFileQueueScheduler<AmortizationUploadFileDTO, String> {

    private final AmortizationUploadFileMessageService amortizationUploadFileMessageService;

    public AmortizationUploadFileQueueScheduler(final AmortizationUploadFileMessageService amortizationUploadFileMessageService) {
        this.amortizationUploadFileMessageService = amortizationUploadFileMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final AmortizationUploadFileDTO dataEntryFile,final String fileToken) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        AtomicInteger count = new AtomicInteger();

        // TODO Check if originating-file-token is transmitted
        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeAmortizationUploadFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .peek(amortizationUploadEVM -> {
                              dataEntryFile.setEntriesCount(count.incrementAndGet());
                          })
                          .map(amortizationUploadFileMessageService::sendMessage)
                          .collect(ImmutableList.toImmutableList()));
        // @formatter:on
    }
}
