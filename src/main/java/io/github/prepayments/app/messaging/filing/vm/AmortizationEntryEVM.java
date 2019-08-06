package io.github.prepayments.app.messaging.filing.vm;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
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
public class AmortizationEntryEVM implements Serializable, ExcelViewModel {

    private static final long serialVersionUID = 4812288900845715487L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String amortizationDate;

    @ExcelCell(1)
    private String amortizationAmount;

    @ExcelCell(2)
    private String particulars;

    @ExcelCell(3)
    private String prepaymentServiceOutlet;

    @ExcelCell(4)
    private String prepaymentAccountNumber;

    @ExcelCell(5)
    private String amortizationServiceOutlet;

    @ExcelCell(6)
    private String amortizationAccountNumber;

    @ExcelCell(7)
    private String prepaymentEntryId;

    @ExcelCell(8)
    private String prepaymentEntryDate;


    private String originatingFileToken;

}
