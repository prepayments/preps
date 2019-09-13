package io.github.prepayments.app.services.cascade;

public interface CascadeUpdateOperation<T> extends Cascader<T>  {

    @Override
    void cascade(CascadedOperation cascadedOperation, T cascadable);
}
