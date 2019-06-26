package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.AmortizationUploadFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUploadFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.AmortizationUploadFileQueueScheduler;
import io.github.prepayments.service.AmortizationUploadFileService;
import io.github.prepayments.service.dto.AmortizationUploadFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Slf4j
@Transactional
@Service("amortizationUploadFileUploadNotificationListener")
public class AmortizationUploadFileUploadNotificationListener implements UploadNotificationsStreamListener<AmortizationUploadFileUploadNotification>  {

    private final AmortizationUploadFileQueueScheduler amortizationUploadFileQueueScheduler;
    private final AmortizationUploadFileService amortizationUploadFileService;

    public AmortizationUploadFileUploadNotificationListener(final AmortizationUploadFileQueueScheduler amortizationUploadFileQueueScheduler,
                                                            final AmortizationUploadFileService amortizationUploadFileService) {
        this.amortizationUploadFileQueueScheduler = amortizationUploadFileQueueScheduler;
        this.amortizationUploadFileService = amortizationUploadFileService;
    }

    @StreamListener(AmortizationUploadFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload AmortizationUploadFileUploadNotification dataStreamItem) {
        log.debug("Amortization upload file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        AmortizationUploadFileDTO result =
            amortizationUploadFileService.findOne(dataStreamItem.getId()).orElseThrow(() -> new IllegalArgumentException("Amortization upload file id : " + dataStreamItem.getId() + " was not " +
                                                                                                                             "found!"));

        result.setUploadSuccessful(true);

        // @formatter:off
        amortizationUploadFileQueueScheduler
            .deserializeAndEnqueue(result)
            .doOnCompleted(() -> result.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io())
            .subscribe();
        // @formatter:on

        amortizationUploadFileService.save(result);
    }
}
