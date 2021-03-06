package io.github.prepayments.app.messaging.data_entry.vm;

import io.github.prepayments.app.messaging.filing.vm.ExcelViewModel;
import io.github.prepayments.app.token.IsTokenized;

/**
 * With this super class we hope to add features which we forgot to add to the view model, features that can allow the user to know the stage of processing a particular object is in.
 */
interface SimpleExcelViewModel extends ExcelViewModel, IsTokenized<String> {

}
