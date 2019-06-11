package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.TransactionAccountFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.TransactionAccountFileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("transactionAccountDataFileMessageService")
@Transactional
public class TransactionAccountDataFileMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {


    public TransactionAccountDataFileMessageService(final TransactionAccountFileUploadStreams transactionAccountFileUploadStreams) {
        super(transactionAccountFileUploadStreams.outboundTransactionAccountFileUploads());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final TransactionAccountFileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
