package io.github.prepayments.app.token;

import org.springframework.stereotype.Component;

/**
 * This provider creates token for uploads which are in the form of excel files
 */
@Component("excelFileTokenProvider")
public class ExcelFileTokenProvider implements FileTokenProvider {

    private final Token<String> randomStringToken;

    public ExcelFileTokenProvider(final Token<String> randomStringToken) {
        this.randomStringToken = randomStringToken;
    }

    @Override
    public String getFileToken(final Tokenizable tokenizable) {

        // TODO Include features from tokenizable
        return randomStringToken.generateHexToken();
    }
}
