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
public class SimpleServiceOutletEVM implements Serializable, SimpleExcelViewModel {


    private static final long serialVersionUID = -4705775023373128718L;
    private long rowIndex;
    private String serviceOutletName;
    private String serviceOutletCode;
    private String serviceOutletLocation;
    private String serviceOutletManager;
    private int numberOfStaff;
    private String building;
    private int floor;
    private String postalAddress;
    private String contactPersonName;
    private String contactEmail;
    private String street;
    private String originatingFileToken;
}
