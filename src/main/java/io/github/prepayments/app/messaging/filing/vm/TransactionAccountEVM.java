package io.github.prepayments.app.messaging.filing.vm;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
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
public class TransactionAccountEVM implements Serializable, ExcelViewModel {

    private static final long serialVersionUID = 4977055757445848840L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String accountName;

    @ExcelCell(1)
    private String accountNumber;

    @ExcelCell(2)
    private String accountType;

    @ExcelCell(3)
    private String openingDate;

    private String originatingFileToken;
}
