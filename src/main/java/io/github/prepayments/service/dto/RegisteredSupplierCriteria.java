package io.github.prepayments.service.dto;

import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the {@link io.github.prepayments.domain.RegisteredSupplier} entity. This class is used in {@link io.github.prepayments.web.rest.RegisteredSupplierResource} to receive all the
 * possible filtering options from the Http GET request parameters. For example the following could be a valid request: {@code /registered-suppliers?id
 * .greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use fix type specific filters.
 */
public class RegisteredSupplierCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter supplierName;

    private StringFilter supplierAddress;

    private StringFilter phoneNumber;

    private StringFilter supplierEmail;

    private StringFilter bankAccountName;

    private StringFilter bankAccountNumber;

    private StringFilter supplierBankName;

    private StringFilter supplierBankBranch;

    private StringFilter bankSwiftCode;

    private StringFilter bankPhysicalAddress;

    private BooleanFilter locallyDomiciled;

    private StringFilter taxAuthorityPIN;

    public RegisteredSupplierCriteria() {
    }

    public RegisteredSupplierCriteria(RegisteredSupplierCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.supplierName = other.supplierName == null ? null : other.supplierName.copy();
        this.supplierAddress = other.supplierAddress == null ? null : other.supplierAddress.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.supplierEmail = other.supplierEmail == null ? null : other.supplierEmail.copy();
        this.bankAccountName = other.bankAccountName == null ? null : other.bankAccountName.copy();
        this.bankAccountNumber = other.bankAccountNumber == null ? null : other.bankAccountNumber.copy();
        this.supplierBankName = other.supplierBankName == null ? null : other.supplierBankName.copy();
        this.supplierBankBranch = other.supplierBankBranch == null ? null : other.supplierBankBranch.copy();
        this.bankSwiftCode = other.bankSwiftCode == null ? null : other.bankSwiftCode.copy();
        this.bankPhysicalAddress = other.bankPhysicalAddress == null ? null : other.bankPhysicalAddress.copy();
        this.locallyDomiciled = other.locallyDomiciled == null ? null : other.locallyDomiciled.copy();
        this.taxAuthorityPIN = other.taxAuthorityPIN == null ? null : other.taxAuthorityPIN.copy();
    }

    @Override
    public RegisteredSupplierCriteria copy() {
        return new RegisteredSupplierCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(StringFilter supplierName) {
        this.supplierName = supplierName;
    }

    public StringFilter getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(StringFilter supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(StringFilter supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public StringFilter getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(StringFilter bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public StringFilter getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(StringFilter bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public StringFilter getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(StringFilter supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public StringFilter getSupplierBankBranch() {
        return supplierBankBranch;
    }

    public void setSupplierBankBranch(StringFilter supplierBankBranch) {
        this.supplierBankBranch = supplierBankBranch;
    }

    public StringFilter getBankSwiftCode() {
        return bankSwiftCode;
    }

    public void setBankSwiftCode(StringFilter bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public StringFilter getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public void setBankPhysicalAddress(StringFilter bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public BooleanFilter getLocallyDomiciled() {
        return locallyDomiciled;
    }

    public void setLocallyDomiciled(BooleanFilter locallyDomiciled) {
        this.locallyDomiciled = locallyDomiciled;
    }

    public StringFilter getTaxAuthorityPIN() {
        return taxAuthorityPIN;
    }

    public void setTaxAuthorityPIN(StringFilter taxAuthorityPIN) {
        this.taxAuthorityPIN = taxAuthorityPIN;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RegisteredSupplierCriteria that = (RegisteredSupplierCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(supplierName, that.supplierName) && Objects.equals(supplierAddress, that.supplierAddress) &&
            Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(supplierEmail, that.supplierEmail) && Objects.equals(bankAccountName, that.bankAccountName) &&
            Objects.equals(bankAccountNumber, that.bankAccountNumber) && Objects.equals(supplierBankName, that.supplierBankName) && Objects.equals(supplierBankBranch, that.supplierBankBranch) &&
            Objects.equals(bankSwiftCode, that.bankSwiftCode) && Objects.equals(bankPhysicalAddress, that.bankPhysicalAddress) && Objects.equals(locallyDomiciled, that.locallyDomiciled) &&
            Objects.equals(taxAuthorityPIN, that.taxAuthorityPIN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplierName, supplierAddress, phoneNumber, supplierEmail, bankAccountName, bankAccountNumber, supplierBankName, supplierBankBranch, bankSwiftCode, bankPhysicalAddress,
                            locallyDomiciled, taxAuthorityPIN);
    }

    @Override
    public String toString() {
        return "RegisteredSupplierCriteria{" + (id != null ? "id=" + id + ", " : "") + (supplierName != null ? "supplierName=" + supplierName + ", " : "") +
            (supplierAddress != null ? "supplierAddress=" + supplierAddress + ", " : "") + (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (supplierEmail != null ? "supplierEmail=" + supplierEmail + ", " : "") + (bankAccountName != null ? "bankAccountName=" + bankAccountName + ", " : "") +
            (bankAccountNumber != null ? "bankAccountNumber=" + bankAccountNumber + ", " : "") + (supplierBankName != null ? "supplierBankName=" + supplierBankName + ", " : "") +
            (supplierBankBranch != null ? "supplierBankBranch=" + supplierBankBranch + ", " : "") + (bankSwiftCode != null ? "bankSwiftCode=" + bankSwiftCode + ", " : "") +
            (bankPhysicalAddress != null ? "bankPhysicalAddress=" + bankPhysicalAddress + ", " : "") + (locallyDomiciled != null ? "locallyDomiciled=" + locallyDomiciled + ", " : "") +
            (taxAuthorityPIN != null ? "taxAuthorityPIN=" + taxAuthorityPIN + ", " : "") + "}";
    }

}
