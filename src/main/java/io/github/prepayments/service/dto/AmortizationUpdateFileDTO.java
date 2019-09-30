package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.Tokenizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationUpdateFile} entity.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmortizationUpdateFileDTO implements Serializable, Tokenizable {

    private Long id;

    private String narration;


    @Lob
    private byte[] dataEntryFile;

    private String dataEntryFileContentType;
    private Boolean uploadSuccessful;

    private Boolean uploadProcessed;

    private Integer entriesCount;


    private String fileToken;

    private String reasonForUpdate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AmortizationUpdateFileDTO{");
        sb.append("id=").append(id);
        sb.append(", Narration='").append(narration).append('\'');
        sb.append(", uploadSuccessful=").append(uploadSuccessful);
        sb.append(", uploadProcessed=").append(uploadProcessed);
        sb.append(", entriesCount=").append(entriesCount);
        sb.append(", fileToken='").append(fileToken).append('\'');
        sb.append(", reasonForUpdate='").append(reasonForUpdate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
