package io.github.prepayments.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.prepayments.domain.RegisteredSupplier} entity.
 */
public class RegisteredSupplierDTO implements Serializable {

    private Long id;

    @NotNull
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

    private String OriginatingFileToken;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public String getSupplierBankBranch() {
        return supplierBankBranch;
    }

    public void setSupplierBankBranch(String supplierBankBranch) {
        this.supplierBankBranch = supplierBankBranch;
    }

    public String getBankSwiftCode() {
        return bankSwiftCode;
    }

    public void setBankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public String getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public void setBankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getTaxAuthorityPIN() {
        return taxAuthorityPIN;
    }

    public void setTaxAuthorityPIN(String taxAuthorityPIN) {
        this.taxAuthorityPIN = taxAuthorityPIN;
    }

    public String getOriginatingFileToken() {
        return OriginatingFileToken;
    }

    public void setOriginatingFileToken(String OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegisteredSupplierDTO registeredSupplierDTO = (RegisteredSupplierDTO) o;
        if (registeredSupplierDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registeredSupplierDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegisteredSupplierDTO{" +
            "id=" + getId() +
            ", supplierName='" + getSupplierName() + "'" +
            ", supplierAddress='" + getSupplierAddress() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", supplierEmail='" + getSupplierEmail() + "'" +
            ", bankAccountName='" + getBankAccountName() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", supplierBankName='" + getSupplierBankName() + "'" +
            ", supplierBankBranch='" + getSupplierBankBranch() + "'" +
            ", bankSwiftCode='" + getBankSwiftCode() + "'" +
            ", bankPhysicalAddress='" + getBankPhysicalAddress() + "'" +
            ", domicile='" + getDomicile() + "'" +
            ", taxAuthorityPIN='" + getTaxAuthorityPIN() + "'" +
            ", OriginatingFileToken='" + getOriginatingFileToken() + "'" +
            "}";
    }
}
