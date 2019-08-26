package io.github.prepayments.app.token;

/**
 * As you might have noticed, linking file to their originating source might
 * not be that easy therefore and interface like this exists to enable a client to
 * receive a unique tag that might correlate it to the source point
 */
public interface TagCapable<T> {

    TagCapable setAmortizationTag(T Tag);
}
