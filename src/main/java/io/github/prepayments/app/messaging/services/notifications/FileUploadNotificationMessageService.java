package io.github.prepayments.app.messaging.services.notifications;

import io.github.prepayments.app.messaging.MessageService;
import io.github.prepayments.app.messaging.notifications.dto.FileUploadNotification;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

public class FileUploadNotificationMessageService implements MessageService<FileUploadNotification> {

    private final MessageChannel messageChannel;

    public FileUploadNotificationMessageService(final MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    /**
     * If successful the method returns the file timestamp, if not, it will return 0
     */
    public long sendMessage(final FileUploadNotification fileUploadNotification) {

        if (messageChannel.send(MessageBuilder.withPayload(fileUploadNotification).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON).build())) {

            return fileUploadNotification.getTimeStamp();
        }
        return 0;
    }
}
