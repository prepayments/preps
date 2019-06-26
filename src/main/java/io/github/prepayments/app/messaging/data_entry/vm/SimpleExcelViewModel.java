package io.github.prepayments.app.messaging.data_entry.vm;

/**
 * With this super class we hope to add features which we forgot to add to the view model, features that can allow the user to know the stage of processing a particular object is in.
 */
public interface SimpleExcelViewModel {

    /**
     * This is the index of the row of a data item in an excel sheet
     */
    long getRowIndex();

}
