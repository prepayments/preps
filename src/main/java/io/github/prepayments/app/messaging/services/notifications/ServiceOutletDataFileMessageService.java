package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.ServiceOutletFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import io.github.prepayments.app.messaging.notifications.dto.ServiceOutletFileUploadNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("serviceOutletDataFileMessageService")
@Transactional
public class ServiceOutletDataFileMessageService extends FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {


    public ServiceOutletDataFileMessageService(final ServiceOutletFileUploadStreams serviceOutletFileUploadStreams) {
        super(serviceOutletFileUploadStreams.outboundSolFileUploads());
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final ServiceOutletFileUploadNotification fileUploadNotification) {

        log.info("Sending notification for file uploaded id : {} with timestamp : {}", fileUploadNotification.getId(), fileUploadNotification.getTimeStamp());

        return super.sendMessage(fileUploadNotification);
    }
}
