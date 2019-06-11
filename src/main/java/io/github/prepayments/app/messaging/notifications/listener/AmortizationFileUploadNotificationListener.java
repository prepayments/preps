package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.AmortizationFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.AmortizationDataEntryFileQueueScheduler;
import io.github.prepayments.service.AmortizationDataEntryFileService;
import io.github.prepayments.service.dto.AmortizationDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service
@Transactional
@Slf4j
public class AmortizationFileUploadNotificationListener implements UploadNotificationsStreamListener<AmortizationFileUploadNotification> {

    private final AmortizationDataEntryFileQueueScheduler amortizationDataEntryFileQueueScheduler;
    private final AmortizationDataEntryFileService amortizationDataEntryFileService;

    public AmortizationFileUploadNotificationListener(final AmortizationDataEntryFileQueueScheduler amortizationDataEntryFileQueueScheduler,
                                                      final AmortizationDataEntryFileService amortizationDataEntryFileService) {
        this.amortizationDataEntryFileQueueScheduler = amortizationDataEntryFileQueueScheduler;
        this.amortizationDataEntryFileService = amortizationDataEntryFileService;
    }

    @StreamListener(AmortizationFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload AmortizationFileUploadNotification dataStreamItem) {
        log.debug("Amortization file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        AmortizationDataEntryFileDTO result =
            amortizationDataEntryFileService.findOne(dataStreamItem.getId()).orElseThrow(() -> new IllegalArgumentException("Amortization file id : " + dataStreamItem.getId() + " was not found!"));

        result.setUploadSuccessful(true);

        // @formatter:off
        amortizationDataEntryFileQueueScheduler
            .deserializeAndEnqueue(result)
            .doOnCompleted(() -> result.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io())
            .subscribe();
        // @formatter:on

        amortizationDataEntryFileService.save(result);
    }

}
