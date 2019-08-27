package io.github.prepayments.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AppConstants {

    // Regex for acceptable logins
    public static final String DATETIME_FORMAT = "yyyy/MM/dd";
    public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final int MONTHLY_AMORTIZATION_DATE = 20;
    public static final int DEFAULT_TOKEN_BYTE_LENGTH = 20;

    // Arguments for prepayment reconciliation account
    public static final String PREPAYMENT_RECONCILIATION_ACCOUNT_NUMBER = "111";
    public static final String PREPAYMENT_RECONCILIATION_ACCOUNT_NAME = "PREPAYMENT RECONCILIATION ACCOUNT";
    public static final String PREPAYMENT_RECONCILIATION_ID = "REC01";
    public static final String PREPAYMENT_RECONCILIATION_DATE = "1990/01/01";
    public static final LocalDate PREPAYMENT_RECONCILIATION_LOCAL_DATE = LocalDate.parse("1990/01/01", DATETIME_FORMATTER);
    public static final String PREPAYMENT_RECONCILIATION_PARTICULARS = "ORPHANED AMORTIZATION ENTRIES RECONCILIATION ACCOUNT";
    public static final String PREPAYMENT_RECONCILIATION_SERVICE_OUTLET = "HQ";
    public static final BigDecimal PREPAYMENT_RECONCILIATION_AMOUNT = BigDecimal.ZERO;


    private AppConstants() {
    }
}
