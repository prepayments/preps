package io.github.prepayments.app.util;

import io.github.jhipster.service.filter.LocalDateFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.service.AmortizationUploadQueryService;
import io.github.prepayments.service.PrepaymentEntryQueryService;
import io.github.prepayments.service.TransactionAccountQueryService;
import io.github.prepayments.service.dto.AmortizationUploadCriteria;
import io.github.prepayments.service.dto.AmortizationUploadDTO;
import io.github.prepayments.service.dto.PrepaymentEntryCriteria;
import io.github.prepayments.service.dto.TransactionAccountCriteria;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMAT;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.math.NumberUtils.toScaledBigDecimal;

@Component("uploadAnnotatedEVMMapper")
public class UploadAnnotatedEVMMapper implements IUploadAnnotatedEVMMapper {

    private final TransactionAccountQueryService transactionAccountQueryService;
    private final PrepaymentEntryQueryService prepaymentEntryQueryService;
    private final AmortizationUploadQueryService amortizationUploadQueryService;

    public UploadAnnotatedEVMMapper(final TransactionAccountQueryService transactionAccountQueryService, final PrepaymentEntryQueryService prepaymentEntryQueryService,
                                    final AmortizationUploadQueryService amortizationUploadQueryService) {
        this.transactionAccountQueryService = transactionAccountQueryService;
        this.prepaymentEntryQueryService = prepaymentEntryQueryService;
        this.amortizationUploadQueryService = amortizationUploadQueryService;
    }

    @Override
    public AmortizationEntryEVM toExcelView(final AmortizationUploadDTO amortizationUploadDTO, String amortizationDateInstance, final DateTimeFormatter dtf) {
        return toAmortizationEntry(amortizationUploadDTO, amortizationDateInstance, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }

    @Override
    public AmortizationUploadDTO toDto(final AmortizationEntryEVM amortizationEntryEVM, final DateTimeFormatter dtf) {

        // Criteria to find account name
        TransactionAccountCriteria amortizationAccountCriteria = getTransactionAccountCriteria(amortizationEntryEVM);

        // Criteria to find prepayment
        PrepaymentEntryCriteria prepaymentEntryCriteria = getPrepaymentEntryCriteria(amortizationEntryEVM, dtf);

        // Criteria to find amortization upload
        AmortizationUploadCriteria amortizationUploadCriteria = getAmortizationUploadCriteria(amortizationEntryEVM);

        return toAmortizationUploadDto(amortizationEntryEVM, dtf, amortizationAccountCriteria, prepaymentEntryCriteria, amortizationUploadCriteria);
    }

    private AmortizationUploadDTO toAmortizationUploadDto(final AmortizationEntryEVM amortizationEntryEVM, final DateTimeFormatter dtf, final TransactionAccountCriteria amortizationAccountCriteria,
                                                          final PrepaymentEntryCriteria prepaymentEntryCriteria, final AmortizationUploadCriteria amortizationUploadCriteria) {
        BigDecimal amortizationAmount = toScaledBigDecimal(amortizationEntryEVM.getAmortizationAmount(), 2, HALF_EVEN);
        BigDecimal prepaymentTransactionAmount = prepaymentEntryQueryService.findByCriteria(prepaymentEntryCriteria).get(0).getPrepaymentAmount();
        return AmortizationUploadDTO.builder()
                                    .accountName(transactionAccountQueryService.findByCriteria(amortizationAccountCriteria).get(0).getAccountName())
                                    .amortizationAmount(amortizationAmount)
                                    .particulars(amortizationEntryEVM.getParticulars())
                                    .prepaymentTransactionId(amortizationEntryEVM.getPrepaymentEntryId())
                                    .prepaymentTransactionDate(LocalDate.parse(amortizationEntryEVM.getPrepaymentEntryDate(), dtf))
                                    .prepaymentServiceOutletCode(amortizationEntryEVM.getPrepaymentServiceOutlet())
                                    .prepaymentAccountNumber(amortizationEntryEVM.getPrepaymentAccountNumber())
                                    .amortizationServiceOutletCode(amortizationEntryEVM.getAmortizationServiceOutlet())
                                    .expenseAccountNumber(amortizationEntryEVM.getAmortizationAccountNumber())
                                    .prepaymentTransactionAmount(prepaymentTransactionAmount)
                                    .numberOfAmortizations(prepaymentTransactionAmount.divide(amortizationAmount, HALF_UP).intValue())
                                    .firstAmortizationDate(amortizationUploadQueryService.findByCriteria(amortizationUploadCriteria).get(0).getFirstAmortizationDate())
                                    .monthlyAmortizationDate(amortizationUploadQueryService.findByCriteria(amortizationUploadCriteria).get(0).getMonthlyAmortizationDate())
                                    .uploadSuccessful(amortizationUploadQueryService.findByCriteria(amortizationUploadCriteria).get(0).getUploadSuccessful())
                                    .uploadOrphaned(amortizationUploadQueryService.findByCriteria(amortizationUploadCriteria).get(0).getUploadOrphaned())
                                    .originatingFileToken(amortizationEntryEVM.getOriginatingFileToken())
                                    .amortizationTag(amortizationEntryEVM.getAmortizationTag())
                                    .build();
    }


    private AmortizationUploadCriteria getAmortizationUploadCriteria(final AmortizationEntryEVM amortizationEntryEVM) {
        StringFilter amortizationTagFilter = new StringFilter();
        amortizationTagFilter.setEquals(amortizationEntryEVM.getAmortizationTag());
        AmortizationUploadCriteria amortizationUploadCriteria = new AmortizationUploadCriteria();
        amortizationUploadCriteria.setAmortizationTag(amortizationTagFilter);
        return amortizationUploadCriteria;
    }

    private PrepaymentEntryCriteria getPrepaymentEntryCriteria(final AmortizationEntryEVM amortizationEntryEVM, final DateTimeFormatter dtf) {
        PrepaymentEntryCriteria prepaymentEntryCriteria = new PrepaymentEntryCriteria();
        StringFilter prepaymentIdFilter = new StringFilter();
        prepaymentIdFilter.setEquals(amortizationEntryEVM.getPrepaymentEntryId());
        LocalDateFilter prepaymentDateFilter = new LocalDateFilter();
        prepaymentDateFilter.setEquals(LocalDate.parse(amortizationEntryEVM.getPrepaymentEntryDate(), dtf));
        prepaymentEntryCriteria.setPrepaymentId(prepaymentIdFilter);
        prepaymentEntryCriteria.setPrepaymentDate(prepaymentDateFilter);
        return prepaymentEntryCriteria;
    }

    private TransactionAccountCriteria getTransactionAccountCriteria(final AmortizationEntryEVM amortizationEntryEVM) {
        TransactionAccountCriteria amortizationAccountCriteria = new TransactionAccountCriteria();
        StringFilter amortizationAccountFilter = new StringFilter();
        amortizationAccountFilter.setEquals(amortizationEntryEVM.getAmortizationAccountNumber());
        amortizationAccountCriteria.setAccountNumber(amortizationAccountFilter);
        return amortizationAccountCriteria;
    }
    //
    //    private AmortizationEntryEVM toAmortizationEntry(final AmortizationUploadDTO amortizationUploadDTO, final String amortizationDateInstance) {
    //        return toAmortizationEntry(amortizationUploadDTO, amortizationDateInstance, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    //    }

    private AmortizationEntryEVM toAmortizationEntry(final AmortizationUploadDTO amortizationUploadDTO, final String amortizationDateInstance, final DateTimeFormatter dtf) {

        // @formatter:off
        return  AmortizationEntryEVM.builder()
                                       .amortizationDate(amortizationDateInstance)
                                       .amortizationAmount(toScaledBigDecimal(amortizationUploadDTO.getAmortizationAmount().toPlainString(), 2, HALF_EVEN).toPlainString())
                                       .particulars(amortizationUploadDTO.getParticulars())
                                       .prepaymentEntryId(amortizationUploadDTO.getPrepaymentTransactionId())
                                       .prepaymentEntryDate(amortizationUploadDTO.getPrepaymentTransactionDate().format(dtf))
                                       .prepaymentServiceOutlet(amortizationUploadDTO.getPrepaymentServiceOutletCode())
                                       .prepaymentAccountNumber(amortizationUploadDTO.getPrepaymentAccountNumber())
                                       .amortizationServiceOutlet(amortizationUploadDTO.getAmortizationServiceOutletCode())
                                       .amortizationAccountNumber(amortizationUploadDTO.getExpenseAccountNumber())
                                       .originatingFileToken(amortizationUploadDTO.getOriginatingFileToken())
                                       .amortizationTag(amortizationUploadDTO.getAmortizationTag())
                                       // TODO track orphan and update status
                                       .build();
        // @formatter:on
    }
}
