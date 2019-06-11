package io.github.prepayments.app.messaging.data_entry.vm;

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
    private long rowIndex;
    private String supplierName;
    private String supplierAddress;
    private String phoneNumber;
    private String supplierEmail;
    private String bankAccountName;
    private String bankAccountNumber;
    private String supplierBankName;
    private String supplierBankBranch;
    private String bankSwiftCode;
    private String bankPhysicalAddress;
    private Boolean locallyDomiciled;
    private String taxAuthorityPIN;
}
