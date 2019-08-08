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
 * This is an excel view model for the amortization upload data model. This view model assumes the uploaded file contains the prepayment Entry prepaymentId and prepaymentDate
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class AmortizationUploadEVM implements Serializable, ExcelViewModel {

    private static final long serialVersionUID = 4812288900845715487L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String accountName;

    @ExcelCell(1)
    private String particulars;

    @ExcelCell(2)
    private String amortizationServiceOutletCode;

    @ExcelCell(3)
    private String prepaymentServiceOutletCode;

    @ExcelCell(4)
    private String prepaymentAccountNumber;

    @ExcelCell(5)
    private String expenseAccountNumber;

    @ExcelCell(6)
    private String prepaymentTransactionId;

    @ExcelCell(7)
    private String prepaymentTransactionDate;

    @ExcelCell(8)
    private String prepaymentTransactionAmount;

    @ExcelCell(9)
    private String amortizationAmount;

    @ExcelCell(10)
    private String numberOfAmortizations;

    @ExcelCell(11)
    private String firstAmortizationDate;

    private String originatingFileToken;

    private Boolean uploadSuccessful;

    private Boolean uploadOrphaned;

}
