package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.AmortizationUpdateFileStreams;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUpdateFileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("amortizationUpdateFileUploadNotificationListener")
@Transactional
@Slf4j
public class AmortizationUpdateFileUploadNotificationListener implements UploadNotificationsStreamListener<AmortizationUpdateFileUploadNotification>  {

    @Override
    @StreamListener(AmortizationUpdateFileStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload AmortizationUpdateFileUploadNotification dataStreamItem) {

        log.info("Amortization update file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());
    }
}
