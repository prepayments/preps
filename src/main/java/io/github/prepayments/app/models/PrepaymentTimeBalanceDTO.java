package io.github.prepayments.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PrepaymentTimeBalanceDTO {

    private Long id;

    @NotNull
    @Pattern(regexp = "^\\d{10,16}$")
    private String accountNumber;

    @NotNull
    private String accountName;

    @NotNull
    private String prepaymentId;

    @NotNull
    private LocalDate prepaymentDate;

    private String particulars;

    @NotNull
    @Pattern(regexp = "^[0-9]{3}$")
    private String serviceOutlet;

    @NotNull
    private BigDecimal prepaymentAmount;

    private Integer months;

    private String supplierName;

    private String invoiceNumber;

    private Long scannedDocumentId;

    private BigDecimal balanceAmount;
}
