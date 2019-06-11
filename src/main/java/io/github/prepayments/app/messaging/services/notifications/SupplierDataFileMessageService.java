package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.SupplierDataFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.SupplierDataFileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("supplierDataFileMessageService")
@Transactional
public class SupplierDataFileMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {

    public SupplierDataFileMessageService(final SupplierDataFileUploadStreams supplierDataFileUploadStreams) {
        super(supplierDataFileUploadStreams.outboundSupplierFileUploads());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final SupplierDataFileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
