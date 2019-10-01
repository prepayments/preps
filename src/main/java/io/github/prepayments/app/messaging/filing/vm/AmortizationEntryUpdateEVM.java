package io.github.prepayments.app.messaging.filing.vm;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
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
public class AmortizationEntryUpdateEVM implements Serializable, IsTokenized<String>, ExcelViewModel, TagCapableAmortizationModel, GetsOrphaned, TagCapable<String> {

    private static final long serialVersionUID = 6713580197920078161L;

    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String amortizationEntryId;

    // Excel field
    @ExcelCell(1)
    private String amortizationDate;

    @ExcelCell(2)
    private String amortizationAmount;

    @ExcelCell(3)
    private String particulars;

    @ExcelCell(4)
    private String prepaymentServiceOutlet;

    @ExcelCell(5)
    private String prepaymentAccountNumber;

    @ExcelCell(6)
    private String amortizationServiceOutlet;

    @ExcelCell(7)
    private String amortizationAccountNumber;

    @ExcelCell(8)
    private String prepaymentEntryId;

    private Boolean orphaned;

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
