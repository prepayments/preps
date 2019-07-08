package io.github.prepayments.app.services.reports;

import io.github.prepayments.app.models.PrepaymentTimeBalanceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Transactional
@Service("prepaymentTimeBalanceService")
public class PrepaymentTimeBalanceService implements ShouldGetBalance<PrepaymentTimeBalanceDTO> {

    @Override
    public List<PrepaymentTimeBalanceDTO> getBalance(final LocalDate balanceDate) {
        return null;
    }
}
