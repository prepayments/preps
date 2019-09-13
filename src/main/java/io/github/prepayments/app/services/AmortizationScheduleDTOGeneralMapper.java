package io.github.prepayments.app.services;

import io.github.prepayments.app.GeneralMapper;
import io.github.prepayments.app.models.AmortizationScheduleDTO;
import io.github.prepayments.app.services.formular.AccountingFormular;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import org.springframework.stereotype.Component;

@Component("amortizationScheduleDTOGeneralMapper")
public class AmortizationScheduleDTOGeneralMapper implements GeneralMapper<AmortizationEntryDTO, AmortizationScheduleDTO> {

    private final AccountingFormular<String> concatenatedServiceOutletAccountingFormular;

    public AmortizationScheduleDTOGeneralMapper(final AccountingFormular<String> concatenatedServiceOutletAccountingFormular) {
        this.concatenatedServiceOutletAccountingFormular = concatenatedServiceOutletAccountingFormular;
    }

    @Override
    public AmortizationScheduleDTO toTypeT2(final AmortizationEntryDTO amortizationEntryDTO) {
        String amortizationAccount =
            concatenatedServiceOutletAccountingFormular.formulateAccount(
                amortizationEntryDTO.getAmortizationServiceOutlet(), amortizationEntryDTO.getAmortizationAccountNumber());
        String prepaymentAccount =
            concatenatedServiceOutletAccountingFormular.formulateAccount(
                amortizationEntryDTO.getPrepaymentServiceOutlet(), amortizationEntryDTO.getPrepaymentAccountNumber());
        // @formatter:off
        return AmortizationScheduleDTO.builder()
                                      .prepaymentEntryId(amortizationEntryDTO.getPrepaymentEntryId())
                                      .amortizationEntryId(amortizationEntryDTO.getId())
                                      .prepaymentAccount(prepaymentAccount)
                                      .amortizationAccount(amortizationAccount)
                                      .particulars(amortizationEntryDTO.getParticulars())
                                      // TODO update balance in client code
//                                      .amount(amortizationEntryDTO.getAmortizationAmount())
                                      .amortizationAmount(amortizationEntryDTO.getAmortizationAmount())
                                      .build();
        // @formatter:on
    }

    @Override
    public AmortizationEntryDTO toTypeT1(final AmortizationScheduleDTO amortizationScheduleDTO) {
        String prepaymentServiceOutlet =
            concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getPrepaymentAccount())[0];
        String  prepaymentAccountNumber =
            concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getPrepaymentAccount())[1];
        String amortizationServiceOutlet =
            concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getAmortizationAccount())[0];
        String amortizationAccountNumber =
            concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getAmortizationAccount())[1];
        // @formatter:off
        return AmortizationEntryDTO.builder()
                                   .prepaymentEntryId(amortizationScheduleDTO.getPrepaymentEntryId())
                                   // TODO truncate concatenated string
                                   .prepaymentServiceOutlet(prepaymentServiceOutlet)
                                   .prepaymentAccountNumber(prepaymentAccountNumber)
                                   .amortizationServiceOutlet(amortizationServiceOutlet)
                                   .amortizationAccountNumber(amortizationAccountNumber)
                                   .particulars(amortizationScheduleDTO.getParticulars())
                                   .amortizationAmount(amortizationScheduleDTO.getAmortizationAmount())
                                   .build();
        // @formatter:on
    }
}
