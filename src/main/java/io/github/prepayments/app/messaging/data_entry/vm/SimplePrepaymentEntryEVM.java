package io.github.prepayments.app.messaging.data_entry.vm;

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
public class SimplePrepaymentEntryEVM implements Serializable, SimpleExcelViewModel {

    private static final long serialVersionUID = 1915669204333680748L;
    private int rowIndex;
    private String accountNumber;
    private String accountName;
    private String prepaymentId;
    private String prepaymentDate;
    private String particulars;
    private String serviceOutlet;
    private String prepaymentAmount;
    private int months;
    private String supplierName;
    private String invoiceNumber;
    private String originatingFileToken;

    public long getRowIndex() {
        return (long) rowIndex;
    }
}
