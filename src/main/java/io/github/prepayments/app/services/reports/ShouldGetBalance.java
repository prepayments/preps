package io.github.prepayments.app.services.reports;

import java.time.LocalDate;
import java.util.List;

public interface ShouldGetBalance<T> {

    List<T> getBalance(LocalDate balanceDate);
}
