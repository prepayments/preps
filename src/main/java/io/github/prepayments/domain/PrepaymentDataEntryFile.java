package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
 * A PrepaymentDataEntryFile.
 */
@Entity
@Table(name = "prepayment_data_entry_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PrepaymentDataEntryFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
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

    @Column(name = "upload_processed")
    private Boolean uploadProcessed;

    @Column(name = "upload_successful")
    private Boolean uploadSuccessful;

    @Column(name = "entries_count")
    private Integer entriesCount;

    @Column(name = "file_token")
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

    public PrepaymentDataEntryFile periodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public PrepaymentDataEntryFile periodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public byte[] getDataEntryFile() {
        return dataEntryFile;
    }

    public PrepaymentDataEntryFile dataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
        return this;
    }

    public void setDataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
    }

    public String getDataEntryFileContentType() {
        return dataEntryFileContentType;
    }

    public PrepaymentDataEntryFile dataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
        return this;
    }

    public void setDataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public PrepaymentDataEntryFile uploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
        return this;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public PrepaymentDataEntryFile uploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
        return this;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Integer getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
    }

    public PrepaymentDataEntryFile entriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
        return this;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    public PrepaymentDataEntryFile fileToken(String fileToken) {
        this.fileToken = fileToken;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PrepaymentDataEntryFile)) {
            return false;
        }
        return id != null && id.equals(((PrepaymentDataEntryFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PrepaymentDataEntryFile{" + "id=" + getId() + ", periodFrom='" + getPeriodFrom() + "'" + ", periodTo='" + getPeriodTo() + "'" + ", dataEntryFile='" + getDataEntryFile() + "'" +
            ", dataEntryFileContentType='" + getDataEntryFileContentType() + "'" + ", uploadProcessed='" + isUploadProcessed() + "'" + ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", entriesCount=" + getEntriesCount() + ", fileToken='" + getFileToken() + "'" + "}";
    }
}
