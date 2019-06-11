package io.github.prepayments.service.dto;
import java.io.Serializable;
import java.util.Objects;
import io.github.prepayments.domain.enumeration.ReportMediumTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A DTO for the {@link io.github.prepayments.domain.ReportType} entity.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class ReportTypeDTO implements Serializable {

    private Long id;

    private String reportModelName;

    private ReportMediumTypes reportMediumType;

    private String reportPassword;
}
