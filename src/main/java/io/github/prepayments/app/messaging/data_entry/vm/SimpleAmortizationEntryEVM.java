package io.github.prepayments.app.messaging.data_entry.vm;

import io.github.prepayments.app.messaging.GetsOrphaned;
import io.github.prepayments.app.token.IsTokenized;
import io.github.prepayments.app.token.TagCapable;
import io.github.prepayments.app.token.TagCapableAmortizationModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * This is an excel view model for the amortizationEntry data model. This view model assumes the uploaded file contains the prepayment Entry prepaymentId and prepaymentDate
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Accessors(chain = true)
public class SimpleAmortizationEntryEVM implements Serializable,SimpleExcelViewModel, TagCapable<String>, TagCapableAmortizationModel, GetsOrphaned {

    private static final long serialVersionUID = 4812288900845715487L;
    private int rowIndex;

    private String amortizationDate;

    private String amortizationAmount;

    private String particulars;

    private String prepaymentServiceOutlet;

    private String prepaymentAccountNumber;

    private String amortizationServiceOutlet;

    private String amortizationAccountNumber;

    private String originatingFileToken;

    private Boolean orphaned;

    private String amortizationTag;

    // No direct mapping...essential to find prepayment Id
    private String prepaymentEntryId;
    private String prepaymentEntryDate;

    @Override
    public boolean orphaned() {
        return orphaned;
    }

    public long getRowIndex() {

        return (long) rowIndex;
    }

    @Override
    public String originatingFileToken() {
        return originatingFileToken;
    }

    @Override
    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
