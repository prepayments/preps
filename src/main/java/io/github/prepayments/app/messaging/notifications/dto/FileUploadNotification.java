package io.github.prepayments.app.messaging.notifications.dto;

public interface FileUploadNotification {

    long getId();

    long getTimeStamp();

    byte[] getFileUpload();
}
