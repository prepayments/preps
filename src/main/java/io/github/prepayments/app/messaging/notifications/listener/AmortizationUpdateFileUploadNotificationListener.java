package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.AmortizationUpdateFileStreams;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUpdateFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.AmortizationUpdateFileQueueScheduler;
import io.github.prepayments.service.AmortizationUpdateFileService;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.impl.AmortizationUpdateFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service("amortizationUpdateFileUploadNotificationListener")
@Transactional
@Slf4j
public class AmortizationUpdateFileUploadNotificationListener implements UploadNotificationsStreamListener<AmortizationUpdateFileUploadNotification>  {


    private final AmortizationUpdateFileService amortizationUpdateFileService;
    private final AmortizationUpdateFileQueueScheduler amortizationUpdateFileQueueScheduler;

    public AmortizationUpdateFileUploadNotificationListener(final AmortizationUpdateFileService amortizationUpdateFileService,
                                                            final AmortizationUpdateFileQueueScheduler amortizationUpdateFileQueueScheduler) {
        this.amortizationUpdateFileService = amortizationUpdateFileService;
        this.amortizationUpdateFileQueueScheduler = amortizationUpdateFileQueueScheduler;
    }

    @Override
    @StreamListener(AmortizationUpdateFileStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload AmortizationUpdateFileUploadNotification dataStreamItem) {

        log.info("Amortization update file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        AmortizationUpdateFileDTO amortizationUpdateFile =
            amortizationUpdateFileService.findOne(dataStreamItem.getId()).orElseThrow(() -> new IllegalArgumentException("Amortization file id : " + dataStreamItem.getId() + " was not found!"));

        amortizationUpdateFile.setUploadSuccessful(true);

        // Deserialize file
        // @formatter:off
        amortizationUpdateFileQueueScheduler
            .deserializeAndEnqueue(amortizationUpdateFile, dataStreamItem.getFileToken())
            .doOnCompleted(() -> amortizationUpdateFile.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io())
            .subscribe();
        // @formatter:on

        // save file
        amortizationUpdateFileService.save(amortizationUpdateFile);
    }
}
