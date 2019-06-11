package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.PrepaymentFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.PrepaymentFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.PrepaymentDataEntryFileQueueScheduler;
import io.github.prepayments.service.PrepaymentDataEntryFileService;
import io.github.prepayments.service.dto.PrepaymentDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service
@Transactional
@Slf4j
public class PrepaymentFileUploadNotificationListener implements UploadNotificationsStreamListener<PrepaymentFileUploadNotification> {


    private final PrepaymentDataEntryFileQueueScheduler prepaymentDataEntryFileQueueScheduler;
    private final PrepaymentDataEntryFileService prepaymentDataEntryService;

    public PrepaymentFileUploadNotificationListener(final PrepaymentDataEntryFileQueueScheduler prepaymentDataEntryFileQueueScheduler,
                                                    final PrepaymentDataEntryFileService prepaymentDataEntryService) {
        this.prepaymentDataEntryFileQueueScheduler = prepaymentDataEntryFileQueueScheduler;
        this.prepaymentDataEntryService = prepaymentDataEntryService;
    }

    @StreamListener(PrepaymentFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload PrepaymentFileUploadNotification dataStreamItem) {
        log.debug("Prepayment file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        // @formatter:off
        PrepaymentDataEntryFileDTO result =
            prepaymentDataEntryService
                .findOne(dataStreamItem.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Prepayment data entry file id : " + dataStreamItem.getId() + " was not found!"));
        // @formatter: on

        result.setUploadSuccessful(true);

        // @formatter:off
        prepaymentDataEntryFileQueueScheduler
            .deserializeAndEnqueue(result)
            .doOnCompleted(() -> result.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io()).subscribe();
        // @formatter:on

        prepaymentDataEntryService.save(result);
    }

}
