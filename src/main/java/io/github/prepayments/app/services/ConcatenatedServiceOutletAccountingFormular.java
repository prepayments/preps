package io.github.prepayments.app.services;

import org.springframework.stereotype.Component;

/**
 * This formular of account formation rules that an account contains 2 concatenated parts those being the
 * service outlet and the account number. The service outlet is always the first and the account number
 *  the second. The following is an example in usage :
 * {@code
 *
 *  public AmortizationEntryDTO toTypeT1(final AmortizationScheduleDTO amortizationScheduleDTO) {
 *         String prepaymentServiceOutlet =
 *             concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getPrepaymentAccount())[0];
 *         String  prepaymentAccountNumber =
 *             concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getPrepaymentAccount())[1];
 *         String amortizationServiceOutlet =
 *             concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getAmortizationAccount())[0];
 *         String amortizationAccountNumber =
 *             concatenatedServiceOutletAccountingFormular.formulateAccountComponents(amortizationScheduleDTO.getAmortizationAccount())[1];
 *         // @formatter:off
 *         return AmortizationEntryDTO.builder()
 *                                    .prepaymentEntryId(amortizationScheduleDTO.getPrepaymentEntryId())
 *                                    .prepaymentServiceOutlet(prepaymentServiceOutlet)
 *                                    .prepaymentAccountNumber(prepaymentAccountNumber)
 *                                    .amortizationServiceOutlet(amortizationServiceOutlet)
 *                                    .amortizationAccountNumber(amortizationAccountNumber)
 *                                    .particulars(amortizationScheduleDTO.getParticulars())
 *                                    .amortizationAmount(amortizationScheduleDTO.getAmount())
 *                                    .build();
 *         // @formatter:on
 *     }
 * }
 *
 * Notice how the index is used to obtain the first value as service outlet and the second as account from that array.
 *
 * While the formulating the account the following might be a typical approach :
 *
 * {@code
 *   public AmortizationScheduleDTO toTypeT2(final AmortizationEntryDTO amortizationEntryDTO) {
 *         String amortizationAccount =
 *             concatenatedServiceOutletAccountingFormular.formulateAccount(
 *                 amortizationEntryDTO.getAmortizationServiceOutlet(), amortizationEntryDTO.getAmortizationAccountNumber());
 *         String prepaymentAccount =
 *             concatenatedServiceOutletAccountingFormular.formulateAccount(
 *                 amortizationEntryDTO.getPrepaymentServiceOutlet(), amortizationEntryDTO.getPrepaymentAccountNumber());
 *         // @formatter:off
 *         return AmortizationScheduleDTO.builder()
 *                                       .prepaymentEntryId(amortizationEntryDTO.getPrepaymentEntryId())
 *                                       .amortizationEntryId(amortizationEntryDTO.getId())
 *                                       .prepaymentAccount(prepaymentAccount)
 *                                       .amortizationAccount(amortizationAccount)
 *                                       .particulars(amortizationEntryDTO.getParticulars())
 *                                       .amount(amortizationEntryDTO.getAmortizationAmount())
 *                                       .build();
 *         // @formatter:on
 *     }
 * }
 *
 * So the same convention is to be maintained. The service outlet first and the account number second. This implementation
 * assumes the service outlet a 3 digit code, so if a custom implementation is required please implement the AccountingFormular
 * interface
 *
 */
@Component("concatenatedServiceOutletAccountingFormular")
public class ConcatenatedServiceOutletAccountingFormular implements AccountingFormular<String> {

    /**
     * This method takes account arguments and generates an account number of the same time according to the implementation desired
     */
    @Override
    public String formulateAccount(final String... accountArgs) {
        return accountArgs[0].concat(accountArgs[1]);
    }

    /**
     * This method returns constituent parts used in the formula for coming up with the account number
     */
    @Override
    public String[] formulateAccountComponents(final String accountForm) {
        String[] components = new String[2];
        components[0] = accountForm.substring(0,2);
        components[1] = accountForm.substring(2);
        return components;
    }
}
