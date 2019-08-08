package io.github.prepayments.service.dto;
import io.github.prepayments.app.token.Tokenizable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.prepayments.domain.ServiceOutletDataEntryFile} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOutletDataEntryFileDTO implements Serializable, Tokenizable {

    private Long id;

    @NotNull
    private LocalDate periodFrom;

    @NotNull
    private LocalDate periodTo;

    private Boolean uploadSuccessful;

    private Boolean uploadProcessed;


    @Lob
    private byte[] dataEntryFile;

    private String dataEntryFileContentType;
    private Integer entriesCount;

    private String fileToken;
}
