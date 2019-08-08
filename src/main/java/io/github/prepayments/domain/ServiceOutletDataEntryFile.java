package io.github.prepayments.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ServiceOutletDataEntryFile.
 */
@Entity
@Table(name = "service_outlet_data_entry_file")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "serviceoutletdataentryfile")
public class ServiceOutletDataEntryFile implements Serializable {

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

    @Column(name = "upload_successful")
    private Boolean uploadSuccessful;

    @Column(name = "upload_processed")
    private Boolean uploadProcessed;

    
    @Lob
    @Column(name = "data_entry_file", nullable = false)
    private byte[] dataEntryFile;

    @Column(name = "data_entry_file_content_type", nullable = false)
    private String dataEntryFileContentType;

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

    public ServiceOutletDataEntryFile periodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
        return this;
    }

    public void setPeriodFrom(LocalDate periodFrom) {
        this.periodFrom = periodFrom;
    }

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public ServiceOutletDataEntryFile periodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
        return this;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public ServiceOutletDataEntryFile uploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
        return this;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public ServiceOutletDataEntryFile uploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
        return this;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public byte[] getDataEntryFile() {
        return dataEntryFile;
    }

    public ServiceOutletDataEntryFile dataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
        return this;
    }

    public void setDataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
    }

    public String getDataEntryFileContentType() {
        return dataEntryFileContentType;
    }

    public ServiceOutletDataEntryFile dataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
        return this;
    }

    public void setDataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
    }

    public Integer getEntriesCount() {
        return entriesCount;
    }

    public ServiceOutletDataEntryFile entriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
        return this;
    }

    public void setEntriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
    }

    public String getFileToken() {
        return fileToken;
    }

    public ServiceOutletDataEntryFile fileToken(String fileToken) {
        this.fileToken = fileToken;
        return this;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOutletDataEntryFile)) {
            return false;
        }
        return id != null && id.equals(((ServiceOutletDataEntryFile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceOutletDataEntryFile{" +
            "id=" + getId() +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", dataEntryFile='" + getDataEntryFile() + "'" +
            ", dataEntryFileContentType='" + getDataEntryFileContentType() + "'" +
            ", entriesCount=" + getEntriesCount() +
            ", fileToken='" + getFileToken() + "'" +
            "}";
    }
}
