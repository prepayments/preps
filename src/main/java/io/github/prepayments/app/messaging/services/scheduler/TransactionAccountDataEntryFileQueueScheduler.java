package io.github.prepayments.app.messaging.services.scheduler;

import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.app.messaging.services.TransactionAccountDataEntryMessageService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.stream.Collectors;


@Slf4j
@Transactional
@Service("transactionAccountDataEntryFileQueueScheduler")
public class TransactionAccountDataEntryFileQueueScheduler implements DataEntryFileQueueScheduler<TransactionAccountDataEntryFileDTO> {


    private final TransactionAccountDataEntryMessageService transactionAccountDataEntryMessageService;

    public TransactionAccountDataEntryFileQueueScheduler(final TransactionAccountDataEntryMessageService transactionAccountDataEntryMessageService) {
        this.transactionAccountDataEntryMessageService = transactionAccountDataEntryMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final TransactionAccountDataEntryFileDTO dataEntryFile) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeTransactionAccountFile(
                dataEntryFile.getDataEntryFile())
                          .stream()
                          .map(transactionAccountDataEntryMessageService::sendMessage)
                          .collect(Collectors.toList()));
        // @formatter:on
    }
}
