package io.github.prepayments.app.token;

public interface TagCapableAmortizationModel extends TagCapable<String> {

    @Override
    default TagCapable<String> setTag(String Tag) {
        return setAmortizationTag(Tag);
    }

    TagCapable<String> setAmortizationTag(String Tag);
}
