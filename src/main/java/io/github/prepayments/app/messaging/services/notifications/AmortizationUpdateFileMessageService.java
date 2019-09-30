package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("amortizationUpdateFileMessageService")
public class AmortizationUpdateFileMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {

    public AmortizationUpdateFileMessageService(final AmortizationUpdateFileStreams amortizationUpdateFileStreams) {
        super(amortizationUpdateFileStreams.outboundAmortizationUpdateFileNotifications());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    @Override
    public long sendMessage(final FileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
