package io.github.prepayments.app.token;

import org.springframework.stereotype.Component;

/**
 * This provider creates token for uploads which are in the form of
 * excel files
 *
 */
@Component("excelFileTokenProvider")
public class ExcelFileTokenProvider implements FileTokenProvider {


    @Override
    public String getFileToken(final Tokenizable tokenizable) {

        // TODO Actually do this!!!
        return "0001";
    }
}
