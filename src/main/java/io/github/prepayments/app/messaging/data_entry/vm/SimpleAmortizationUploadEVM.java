package io.github.prepayments.app.messaging.data_entry.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * This is an excel view model for the amortizationUpload data model. This view model assumes the uploaded file contains the prepayment Entry prepaymentId and prepaymentDate
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class SimpleAmortizationUploadEVM implements Serializable {

    private static final long serialVersionUID = 1588025101540758601L;
    private long rowIndex;
    private String accountName;
    private String particulars;
    private String amortizationServiceOutletCode;
    private String prepaymentServiceOutletCode;
    private String prepaymentAccountNumber;
    private String expenseAccountNumber;
    private String prepaymentTransactionId;
    private String prepaymentTransactionDate;
    private String prepaymentTransactionAmount;
    private String amortizationAmount;
    private String numberOfAmortizations;
    private String firstAmortizationDate;
    private String monthlyAmortizationDate;
    private Boolean uploadSuccessful;
    private Boolean uploadOrphaned;
    private String originatingFileToken;
    private String amortizationTag;
}
