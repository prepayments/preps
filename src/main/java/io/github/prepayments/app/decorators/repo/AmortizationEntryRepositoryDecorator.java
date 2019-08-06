package io.github.prepayments.app.decorators.repo;

import io.github.prepayments.domain.AmortizationEntry;
import io.github.prepayments.domain.PrepaymentEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("amortizationEntryRepositoryDecorator")
public interface AmortizationEntryRepositoryDecorator extends JpaRepository<AmortizationEntry, Long>, JpaSpecificationExecutor<AmortizationEntry> {

    List<AmortizationEntry> findAllByPrepaymentEntryIs(PrepaymentEntry prepaymentEntry);
}
