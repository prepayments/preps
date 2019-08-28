package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.TransactionAccountFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.TransactionAccountFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.TransactionAccountDataEntryFileQueueScheduler;
import io.github.prepayments.service.TransactionAccountDataEntryFileService;
import io.github.prepayments.service.dto.TransactionAccountDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service
@Transactional
@Slf4j
public class TransactionAccountFileUploadNotificationListener implements UploadNotificationsStreamListener<TransactionAccountFileUploadNotification> {

    private final TransactionAccountDataEntryFileService transactionAccountDataEntryFileService;
    private final TransactionAccountDataEntryFileQueueScheduler transactionAccountDataEntryFileQueueScheduler;


    public TransactionAccountFileUploadNotificationListener(final TransactionAccountDataEntryFileService transactionAccountDataEntryFileService,
                                                            final TransactionAccountDataEntryFileQueueScheduler transactionAccountDataEntryFileQueueScheduler) {
        this.transactionAccountDataEntryFileService = transactionAccountDataEntryFileService;
        this.transactionAccountDataEntryFileQueueScheduler = transactionAccountDataEntryFileQueueScheduler;
    }

    @StreamListener(TransactionAccountFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload TransactionAccountFileUploadNotification dataStreamItem) {
        log.debug("Transaction Account file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        // @formatter:off
        TransactionAccountDataEntryFileDTO result =
                              transactionAccountDataEntryFileService
                                   .findOne(dataStreamItem.getId())
                                     .orElseThrow(() -> new IllegalArgumentException("TransactionAccount data entry file id : " + dataStreamItem.getId() + " was not found!"));
        // @formatter: on

        result.setUploadSuccessful(true);

        // @formatter:off
        transactionAccountDataEntryFileQueueScheduler
                 .deserializeAndEnqueue(result, dataStreamItem.getFileToken())
                 .doOnCompleted(() -> result.setUploadSuccessful(true))
                 .subscribeOn(Schedulers.io()).subscribe();
        // @formatter:on

        transactionAccountDataEntryFileService.save(result);
    }
}
