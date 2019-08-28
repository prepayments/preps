package io.github.prepayments.app.messaging.services.scheduler;

import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.app.messaging.services.PrepaymentDataEntryMessageService;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class PrepaymentDataEntryFileQueueScheduler implements DataEntryFileQueueScheduler<PrepaymentDataEntryFileDTO, String> {


    private final PrepaymentDataEntryMessageService prepaymentDataEntryMessageService;

    public PrepaymentDataEntryFileQueueScheduler(final PrepaymentDataEntryMessageService prepaymentDataEntryMessageService) {
        this.prepaymentDataEntryMessageService = prepaymentDataEntryMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final PrepaymentDataEntryFileDTO dataEntryFile, final String fileToken) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializePrepaymentsFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .map(prepaymentDataEntryMessageService::sendMessage)
                          .collect(Collectors.toList()));
        // @formatter:on
    }
}
