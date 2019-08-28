package io.github.prepayments.app.token;

public interface TagCapableAmortizationModel extends TagCapable<String> {

    @Override
    TagCapable<String> setAmortizationTag(String Tag);
}
