package io.github.prepayments.app.services;

/**
 * This interface carries out operations on the child similar to the operation carried out on the parent
 */
public interface Cascader<T> {

    void cascade(CascadedOperation cascadedOperation,T cascadable);
}
