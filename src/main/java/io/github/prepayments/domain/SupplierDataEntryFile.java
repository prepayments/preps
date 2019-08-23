package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A SupplierDataEntryFile.
 */
@Entity
@Table(name = "supplier_data_entry_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "supplierdataentryfile")
public class SupplierDataEntryFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "period_from", nullable = false)
    private LocalDate periodFrom;

    @NotNull
    @Column(name = "period_to", nullable = false)
    private LocalDate periodTo;


    @Lob
    @Column(name = "data_entry_file", nullable = false)
    private byte[] dataEntryFile;

    @Column(name = "data_entry_file_content_type", nullable = false)
    private String dataEntryFileContentType;

    @Column(name = "upload_successful")
    private Boolean uploadSuccessful;

    @Column(name = "upload_processed")
    private Boolean uploadProcessed;

    @Column(name = "entries_count")
    private Integer entriesCount;


    @Column(name = "file_token", unique = true)
    private String fileToken;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public SupplierDataEntryFile periodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public SupplierDataEntryFile periodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public byte[] getDataEntryFile() {
        return dataEntryFile;
    }

    public void setDataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
    }

    public SupplierDataEntryFile dataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
        return this;
    }

    public String getDataEntryFileContentType() {
        return dataEntryFileContentType;
    }

    public void setDataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
    }

    public SupplierDataEntryFile dataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
        return this;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public SupplierDataEntryFile uploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
        return this;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public SupplierDataEntryFile uploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
        return this;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public Integer getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
    }

    public SupplierDataEntryFile entriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
        return this;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    public SupplierDataEntryFile fileToken(String fileToken) {
        this.fileToken = fileToken;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierDataEntryFile)) {
            return false;
        }
        return id != null && id.equals(((SupplierDataEntryFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SupplierDataEntryFile{" + "id=" + getId() + ", periodFrom='" + getPeriodFrom() + "'" + ", periodTo='" + getPeriodTo() + "'" + ", dataEntryFile='" + getDataEntryFile() + "'" +
            ", dataEntryFileContentType='" + getDataEntryFileContentType() + "'" + ", uploadSuccessful='" + isUploadSuccessful() + "'" + ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", entriesCount=" + getEntriesCount() + ", fileToken='" + getFileToken() + "'" + "}";
    }
}
