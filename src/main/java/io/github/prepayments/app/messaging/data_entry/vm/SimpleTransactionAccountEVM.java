package io.github.prepayments.app.messaging.data_entry.vm;

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
public class SimpleTransactionAccountEVM implements Serializable, SimpleExcelViewModel {


    private long rowIndex;
    private String accountName;
    private String accountNumber;
    private String accountBalance;
    private String openingDate;
    private String accountOpeningDateBalance;
    private String originatingFileToken;
}
