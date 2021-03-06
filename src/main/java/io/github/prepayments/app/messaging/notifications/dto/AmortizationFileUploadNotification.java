package io.github.prepayments.app.messaging.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a notification object about amortization files uploaded. We tries asynch calls but that wasn't good enough. So when a file is uploaded, this object will be sent as notification to notify a
 * listener which can then deal with the file.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AmortizationFileUploadNotification implements FileUploadNotification {

    private long id;
    private long timeStamp;
    private byte[] fileUpload;
    private String fileToken;
}
