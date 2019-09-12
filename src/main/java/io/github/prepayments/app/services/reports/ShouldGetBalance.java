package io.github.prepayments.app.services.reports;

import java.util.List;

public interface ShouldGetBalance<R, T, A> {

    /**
     * Returns a list of outstanding balances of the type T having received
     * a request of type R
     *
     * @param requestParameter
     * @return
     */
    List<T> getBalance(R requestParameter);

    /**
     * Returns an amount of all outstanding balances of type A having received
     * a request of type R
     *
     * @param requestParameter
     * @return
     */
    A getBalanceAmount(R requestParameter);
}
