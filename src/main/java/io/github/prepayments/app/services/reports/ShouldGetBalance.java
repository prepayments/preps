package io.github.prepayments.app.services.reports;

import java.time.LocalDate;
import java.util.List;

public interface ShouldGetBalance<R, T> {

        List<T> getBalance(R requestParameter);
}
