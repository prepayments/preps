package io.github.prepayments.app.token;

import org.springframework.stereotype.Component;

import static io.github.prepayments.app.AppConstants.DEFAULT_TOKEN_BYTE_LENGTH;

/**
 * This creates a Tag that relates an amortization-entry to its
 * originating amortization-upload, using a random string token to set
 * the tag in the tag-capable object
 *
 */
@Component("amortizationTokenTagProvider")
public class AmortizationTokenTagProvider implements TagProvider<String> {

    private final Token<String> randomStringToken;

    public AmortizationTokenTagProvider(final Token<String> randomStringToken) {
        this.randomStringToken = randomStringToken;
    }

    /**
     * Creates a Tag for a given tag-capable object
     */
    @Override
    public Tag<String> tag(final TagCapable<String> tagCapable) {

        Tag<String> stringTag = () -> randomStringToken.generateHexToken(DEFAULT_TOKEN_BYTE_LENGTH);

        tagCapable.setAmortizationTag(stringTag.getTag());

        return stringTag;
    }
}
