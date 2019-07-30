package io.github.prepayments.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.prepayments.domain.PrepaymentDataEntryFile} entity.
 */
public class PrepaymentDataEntryFileDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate periodFrom;

    @NotNull
    private LocalDate periodTo;

    
    @Lob
    private byte[] dataEntryFile;

    private String dataEntryFileContentType;
    private Boolean uploadProcessed;

    private Boolean uploadSuccessful;

    private Integer entriesCount;

    private String fileToken;


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

    public LocalDate getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(LocalDate periodTo) {
        this.periodTo = periodTo;
    }

    public byte[] getDataEntryFile() {
        return dataEntryFile;
    }

    public void setDataEntryFile(byte[] dataEntryFile) {
        this.dataEntryFile = dataEntryFile;
    }

    public String getDataEntryFileContentType() {
        return dataEntryFileContentType;
    }

    public void setDataEntryFileContentType(String dataEntryFileContentType) {
        this.dataEntryFileContentType = dataEntryFileContentType;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
    }

    public void setUploadProcessed(Boolean uploadProcessed) {
        this.uploadProcessed = uploadProcessed;
    }

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
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

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PrepaymentDataEntryFileDTO prepaymentDataEntryFileDTO = (PrepaymentDataEntryFileDTO) o;
        if (prepaymentDataEntryFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prepaymentDataEntryFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PrepaymentDataEntryFileDTO{" +
            "id=" + getId() +
            ", periodFrom='" + getPeriodFrom() + "'" +
            ", periodTo='" + getPeriodTo() + "'" +
            ", dataEntryFile='" + getDataEntryFile() + "'" +
            ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", entriesCount=" + getEntriesCount() +
            ", fileToken='" + getFileToken() + "'" +
            "}";
    }
}
