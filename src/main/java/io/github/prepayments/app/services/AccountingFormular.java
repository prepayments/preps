package io.github.prepayments.app.services;

/**
 * This is a generalization of the concept by which an account is recognised
 * by an app, which enables an amortization schedule to have a custom way
 * of reporting amortization based on account number stored in the system
 * @param <T> Type of data for account numbers
 */
public interface AccountingFormular<T> {

    /**
     * This method takes account arguments and generates an account number of the same
     * time according to the implementation desired
     *
     * @param accountArgs
     * @return
     */
    T formulateAccount(T... accountArgs);

    /**
     * This method returns constituent parts used in the formula for coming up with the
     * account number
     *
     * @param accountForm
     * @return
     */
    T[] formulateAccountComponents(T accountForm);
}
