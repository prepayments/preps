package io.github.prepayments.app.messaging.data_entry.vm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * This is an excel view model for the amortizationEntry data model. This view model assumes the uploaded file contains the prepayment Entry prepaymentId and prepaymentDate
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class SimpleAmortizationEntryEVM implements Serializable {

    private static final long serialVersionUID = 4812288900845715487L;
    private int rowIndex;

    private String amortizationDate;

    private String amortizationAmount;

    private String particulars;

    private String prepaymentServiceOutlet;

    private String prepaymentAccountNumber;

    private String amortizationServiceOutlet;

    private String amortizationAccountNumber;

    private String OriginatingFileToken;

    // No direct mapping...essential to find prepayment Id
    private String prepaymentEntryId;

    private String prepaymentEntryDate;
}
