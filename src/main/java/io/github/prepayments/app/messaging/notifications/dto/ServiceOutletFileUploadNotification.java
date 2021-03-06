package io.github.prepayments.app.messaging.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ServiceOutletFileUploadNotification implements FileUploadNotification {

    private long id;
    private long timeStamp;
    private byte[] fileUpload;
    private String fileToken;
}
