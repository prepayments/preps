package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.AmortizationFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.AmortizationFileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("amortizationDataFileMessageService")
@Transactional
public class AmortizationDataFileMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {


    public AmortizationDataFileMessageService(final AmortizationFileUploadStreams amortizationFileUploadStreams) {
        super(amortizationFileUploadStreams.outboundAmortizationFileUploads());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final AmortizationFileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
