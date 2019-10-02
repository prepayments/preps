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

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@Accessors(chain = true)
public class SimpleAmortizationEntryUpdateEVM implements Serializable, SimpleExcelViewModel, TagCapable<String>, TagCapableAmortizationModel, GetsOrphaned {

    private long rowIndex;

    private String amortizationEntryId;

    private String amortizationDate;

    private String amortizationAmount;

    private String particulars;

    private String prepaymentServiceOutlet;

    private String prepaymentAccountNumber;

    private String amortizationServiceOutlet;

    private String amortizationAccountNumber;

    private String prepaymentEntryId;

    private Boolean orphaned = false;

    private String originatingFileToken;

    private String amortizationTag;

    @Override
    public boolean orphaned() {
        return orphaned;
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
