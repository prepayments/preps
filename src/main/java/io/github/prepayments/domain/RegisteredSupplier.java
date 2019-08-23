package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A RegisteredSupplier.
 */
@Entity
@Table(name = "registered_supplier")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "registeredsupplier")
public class RegisteredSupplier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "supplier_name", nullable = false, unique = true)
    private String supplierName;

    @Column(name = "supplier_address")
    private String supplierAddress;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "supplier_email")
    private String supplierEmail;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "supplier_bank_name")
    private String supplierBankName;

    @Column(name = "supplier_bank_branch")
    private String supplierBankBranch;

    @Column(name = "bank_swift_code")
    private String bankSwiftCode;

    @Column(name = "bank_physical_address")
    private String bankPhysicalAddress;

    @Column(name = "domicile")
    private String domicile;

    @Column(name = "tax_authority_pin")
    private String taxAuthorityPIN;

    @Column(name = "originating_file_token")
    private String OriginatingFileToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public RegisteredSupplier supplierName(String supplierName) {
        this.supplierName = supplierName;
        return this;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public RegisteredSupplier supplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public RegisteredSupplier phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    public RegisteredSupplier supplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
        return this;
    }

    public String getBankAccountName() {
        return bankAccountName;
    }

    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    public RegisteredSupplier bankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
        return this;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public RegisteredSupplier bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public RegisteredSupplier supplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
        return this;
    }

    public String getSupplierBankBranch() {
        return supplierBankBranch;
    }

    public void setSupplierBankBranch(String supplierBankBranch) {
        this.supplierBankBranch = supplierBankBranch;
    }

    public RegisteredSupplier supplierBankBranch(String supplierBankBranch) {
        this.supplierBankBranch = supplierBankBranch;
        return this;
    }

    public String getBankSwiftCode() {
        return bankSwiftCode;
    }

    public void setBankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public RegisteredSupplier bankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
        return this;
    }

    public String getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public void setBankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public RegisteredSupplier bankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
        return this;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public RegisteredSupplier domicile(String domicile) {
        this.domicile = domicile;
        return this;
    }

    public String getTaxAuthorityPIN() {
        return taxAuthorityPIN;
    }

    public void setTaxAuthorityPIN(String taxAuthorityPIN) {
        this.taxAuthorityPIN = taxAuthorityPIN;
    }

    public RegisteredSupplier taxAuthorityPIN(String taxAuthorityPIN) {
        this.taxAuthorityPIN = taxAuthorityPIN;
        return this;
    }

    public String getOriginatingFileToken() {
        return OriginatingFileToken;
    }

    public void setOriginatingFileToken(String OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
    }

    public RegisteredSupplier OriginatingFileToken(String OriginatingFileToken) {
        this.OriginatingFileToken = OriginatingFileToken;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegisteredSupplier)) {
            return false;
        }
        return id != null && id.equals(((RegisteredSupplier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RegisteredSupplier{" + "id=" + getId() + ", supplierName='" + getSupplierName() + "'" + ", supplierAddress='" + getSupplierAddress() + "'" + ", phoneNumber='" + getPhoneNumber() +
            "'" + ", supplierEmail='" + getSupplierEmail() + "'" + ", bankAccountName='" + getBankAccountName() + "'" + ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", supplierBankName='" + getSupplierBankName() + "'" + ", supplierBankBranch='" + getSupplierBankBranch() + "'" + ", bankSwiftCode='" + getBankSwiftCode() + "'" +
            ", bankPhysicalAddress='" + getBankPhysicalAddress() + "'" + ", domicile='" + getDomicile() + "'" + ", taxAuthorityPIN='" + getTaxAuthorityPIN() + "'" + ", OriginatingFileToken='" +
            getOriginatingFileToken() + "'" + "}";
    }
}
