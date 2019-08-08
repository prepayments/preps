package io.github.prepayments.app.token;

import java.time.LocalDate;

/**
 * Generally represents file for which a token algorithm can be written
 */
public interface Tokenizable {

    LocalDate getPeriodFrom();

    LocalDate getPeriodTo();

    byte[] getDataEntryFile();

    String getDataEntryFileContentType();

    Integer getEntriesCount();

    void setFileToken(String fileToken);
}
