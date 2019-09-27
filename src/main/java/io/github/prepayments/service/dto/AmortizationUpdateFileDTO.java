package io.github.prepayments.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.prepayments.domain.AmortizationUpdateFile} entity.
 */
public class AmortizationUpdateFileDTO implements Serializable {

    private Long id;

    @NotNull
    private String Narration;

    
    @Lob
    private byte[] dataEntryFile;

    private String dataEntryFileContentType;
    private Boolean uploadSuccessful;

    private Boolean uploadProcessed;

    private Integer entriesCount;

    
    private String fileToken;

    @NotNull
    private String reasonForUpdate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNarration() {
        return Narration;
    }

    public void setNarration(String Narration) {
        this.Narration = Narration;
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

    public Boolean isUploadSuccessful() {
        return uploadSuccessful;
    }

    public void setUploadSuccessful(Boolean uploadSuccessful) {
        this.uploadSuccessful = uploadSuccessful;
    }

    public Boolean isUploadProcessed() {
        return uploadProcessed;
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

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    public String getReasonForUpdate() {
        return reasonForUpdate;
    }

    public void setReasonForUpdate(String reasonForUpdate) {
        this.reasonForUpdate = reasonForUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AmortizationUpdateFileDTO amortizationUpdateFileDTO = (AmortizationUpdateFileDTO) o;
        if (amortizationUpdateFileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), amortizationUpdateFileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AmortizationUpdateFileDTO{" +
            "id=" + getId() +
            ", Narration='" + getNarration() + "'" +
            ", dataEntryFile='" + getDataEntryFile() + "'" +
            ", uploadSuccessful='" + isUploadSuccessful() + "'" +
            ", uploadProcessed='" + isUploadProcessed() + "'" +
            ", entriesCount=" + getEntriesCount() +
            ", fileToken='" + getFileToken() + "'" +
            ", reasonForUpdate='" + getReasonForUpdate() + "'" +
            "}";
    }
}
