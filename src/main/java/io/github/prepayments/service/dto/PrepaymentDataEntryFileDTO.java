package io.github.prepayments.service.dto;

import io.github.prepayments.app.token.Tokenizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.PrepaymentDataEntryFile} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrepaymentDataEntryFileDTO implements Serializable, Tokenizable {

    private Long id;

    @NotNull
    private LocalDate periodFrom;

    @NotNull
    private LocalDate periodTo;


    @Lob
    private byte[] dataEntryFile;

    private String dataEntryFileContentType;
    private Boolean uploadProcessed;

    private Boolean uploadSuccessful;

    private Integer entriesCount;

    private String fileToken;
}
