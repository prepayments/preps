package io.github.prepayments.app.messaging.data_entry.vm;

import io.github.prepayments.app.token.IsTokenized;
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
public class SimpleRegisteredSupplierEVM implements Serializable, SimpleExcelViewModel {
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
    private String domicile;
    private String taxAuthorityPIN;
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
