package io.github.prepayments.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link io.github.prepayments.domain.PrepaymentEntry} entity.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PrepaymentEntryDTO implements Serializable {

    private Long id;

    @NotNull
    private String accountNumber;

    @NotNull
    private String accountName;

    @NotNull
    private String prepaymentId;

    @NotNull
    private LocalDate prepaymentDate;

    private String particulars;

    @NotNull
    private String serviceOutlet;

    @NotNull
    private BigDecimal prepaymentAmount;

    private Integer months;

    private String supplierName;

    private String invoiceNumber;

    private Long scannedDocumentId;

    private String OriginatingFileToken;
}
