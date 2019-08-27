package io.github.prepayments.app;

import java.time.format.DateTimeFormatter;

public class AppConstants {

    // Regex for acceptable logins
    public static final String DATETIME_FORMAT = "yyyy/MM/dd";
    public static DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
    public static final int MONTHLY_AMORTIZATION_DATE = 20;
    public static final int DEFAULT_TOKEN_BYTE_LENGTH = 20;

    public static final String PREPAYMENT_RECONCILIATION_ID = "REC01";
    public static final String PREPAYMENT_RECONCILIATION_DATE = "1990/01/01";


    private AppConstants() {
    }
}
