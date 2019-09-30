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
public class AmortizationUpdateFileUploadNotification implements FileUploadNotification {

    private long id;
    private long timeStamp;
    private byte[] fileUpload;
    private String fileToken;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AmortizationUpdateFileUploadNotification{");
        sb.append("id=").append(id);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append(", fileToken='").append(fileToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
