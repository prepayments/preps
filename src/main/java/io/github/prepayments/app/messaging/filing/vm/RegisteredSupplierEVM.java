package io.github.prepayments.app.messaging.filing.vm;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
public class RegisteredSupplierEVM implements Serializable, ExcelViewModel {
    private static final long serialVersionUID = 494926810094105583L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String supplierName;

    @ExcelCell(1)
    private String supplierAddress;

    @ExcelCell(2)
    private String phoneNumber;

    @ExcelCell(3)
    private String supplierEmail;

    @ExcelCell(4)
    private String bankAccountName;

    @ExcelCell(5)
    private String bankAccountNumber;

    @ExcelCell(6)
    private String supplierBankName;

    @ExcelCell(7)
    private String supplierBankBranch;

    @ExcelCell(8)
    private String bankSwiftCode;

    @ExcelCell(9)
    private String bankPhysicalAddress;

    @ExcelCell(10)
    private Boolean locallyDomiciled;

    @ExcelCell(11)
    private String taxAuthorityPIN;
}
