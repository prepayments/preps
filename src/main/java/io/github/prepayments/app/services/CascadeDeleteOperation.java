package io.github.prepayments.app.services;

public interface CascadeDeleteOperation<T> extends Cascader<T> {

    @Override
    void cascade(CascadedOperation cascadedOperation, T cascadable);
}
