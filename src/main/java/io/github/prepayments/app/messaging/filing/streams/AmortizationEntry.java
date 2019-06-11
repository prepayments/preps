package io.github.prepayments.app.messaging.filing.streams;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.Value;

@Builder
@Data
@Value
@AllArgsConstructor
@JsonDeserialize(builder = AmortizationEntry.AmortizationEntryBuilder.class)
@ToString
public class AmortizationEntry {

    private long timestamp;
    private int rowIndex;
    private String amortizationDate;
    private String amortizationAmount;
    private String particulars;
    private String serviceOutlet;
    private String accountNumber;
    private String accountName;
    private String prepaymentEntryId;
    private String prepaymentEntryDate;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AmortizationEntryBuilder {
    }
}
