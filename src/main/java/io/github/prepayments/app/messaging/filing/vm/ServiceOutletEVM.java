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
public class ServiceOutletEVM implements Serializable, ExcelViewModel {

    private static final long serialVersionUID = -3771291030467604864L;
    @ExcelRow
    private long rowIndex;

    @ExcelCell(0)
    private String serviceOutletName;

    @ExcelCell(1)
    private String serviceOutletCode;

    @ExcelCell(2)
    private String serviceOutletLocation;

    @ExcelCell(3)
    private String serviceOutletManager;

    @ExcelCell(4)
    private int numberOfStaff;

    @ExcelCell(5)
    private String building;

    @ExcelCell(6)
    private int floor;

    @ExcelCell(7)
    private String postalAddress;

    @ExcelCell(8)
    private String contactPersonName;

    @ExcelCell(9)
    private String contactEmail;

    @ExcelCell(10)
    private String street;
}
