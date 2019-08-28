package io.github.prepayments.app.messaging.filing.vm;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import io.github.prepayments.app.token.IsTokenized;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * This is an excel view model for the prepaymentEntry data model. This view model assumes the uploaded file for amortization contains the prepayment Entry prepaymentId and prepaymentDate. The list of
 * amortizationEntries for this prepayment has to be patched manually in the service
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class PrepaymentEntryEVM implements Serializable,IsTokenized<String>, ExcelViewModel {

    private static final long serialVersionUID = 1915669204333680748L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String accountNumber;

    @ExcelCell(1)
    private String accountName;

    @ExcelCell(2)
    private String prepaymentId;

    @ExcelCell(3)
    private String prepaymentDate;

    @ExcelCell(4)
    private String particulars;

    @ExcelCell(5)
    private String serviceOutlet;

    @ExcelCell(6)
    private String prepaymentAmount;

    @ExcelCell(7)
    private int months;

    @ExcelCell(8)
    private String supplierName;

    @ExcelCell(9)
    private String invoiceNumber;

    private String originatingFileToken;

    @Override
    public String originatingFileToken() {
        return this.originatingFileToken;
    }

    @Override
    public IsTokenized<String> originatingFileToken(final String token) {
        this.originatingFileToken = token;
        return this;
    }
}
