package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.AmortizationUploadFileUploadStream;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationUploadFileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("amortizationUploadDataFileMessageService")
@Transactional
public class AmortizationUploadFileNotificationMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {

    public AmortizationUploadFileNotificationMessageService(final AmortizationUploadFileUploadStream amortizationUploadFileUploadStream) {
        super(amortizationUploadFileUploadStream.outboundAmortizationFileUploads());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final AmortizationUploadFileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
