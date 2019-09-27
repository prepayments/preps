package io.github.prepayments.app.messaging.data_entry.service;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.PrepsApp;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static io.github.prepayments.app.AppConstants.DATETIME_FORMATTER;
import static org.junit.Assert.assertEquals;

@Transactional
@SpringBootTest(classes = PrepsApp.class)
public class PrepaymentEntryIdServiceIT {


    private static final String PREPAYMENT_1_ACCOUNT_NUMBER = "ACCOUNT_NO_1";
    private static final String PREPAYMENT_2_ACCOUNT_NUMBER = "ACCOUNT_NO_2";
    private static final String PREPAYMENT_3_ACCOUNT_NUMBER = "ACCOUNT_NO_3";

    private static final String PREPAYMENT_1_ACCOUNT_NAME = "ACCOUNT_1";
    private static final String PREPAYMENT_2_ACCOUNT_NAME = "ACCOUNT_2";
    private static final String PREPAYMENT_3_ACCOUNT_NAME = "ACCOUNT_3";

    private static final String PREPAYMENT_1_PREPAYMENT_ID = "PREP_1";
    private static final String PREPAYMENT_2_PREPAYMENT_ID = "PREP_1";
    private static final String PREPAYMENT_3_PREPAYMENT_ID = "PREP_1";

    private static final LocalDate PREPAYMENT_1_PREPAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate PREPAYMENT_2_PREPAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate PREPAYMENT_3_PREPAYMENT_DATE = LocalDate.ofEpochDay(0L);

    private static final String PREPAYMENT_1_PARTICULARS = "PREPAYMENT_1";
    private static final String PREPAYMENT_2_PARTICULARS = "PREPAYMENT_2";
    private static final String PREPAYMENT_3_PARTICULARS = "PREPAYMENT_3";

    private static final String PREPAYMENT_1_SERVICE_OUTLET = "SOL_1";
    private static final String PREPAYMENT_2_SERVICE_OUTLET = "SOL_2";
    private static final String PREPAYMENT_3_SERVICE_OUTLET = "SOL_3";

    private static final BigDecimal PREPAYMENT_1_PREPAYMENT_AMOUNT = new BigDecimal(100);
    private static final BigDecimal PREPAYMENT_2_PREPAYMENT_AMOUNT = new BigDecimal(200);
    private static final BigDecimal PREPAYMENT_3_PREPAYMENT_AMOUNT = new BigDecimal(300);

    private static final Integer PREPAYMENT_1_MONTHS = 3;
    private static final Integer PREPAYMENT_2_MONTHS = 3;
    private static final Integer PREPAYMENT_3_MONTHS = 3;

    private static final String PREPAYMENT_1_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_SUPPLIER_NAME = "BBBBBBBBBB";
    private static final String PREPAYMENT_3_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String PREPAYMENT_1_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_INVOICE_NUMBER = "BBBBBBBBBB";
    private static final String PREPAYMENT_3_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Long PREPAYMENT_1_SCANNED_DOCUMENT_ID = 1L;
    private static final Long PREPAYMENT_2_SCANNED_DOCUMENT_ID = 2L;
    private static final Long PREPAYMENT_3_SCANNED_DOCUMENT_ID = 2L;

    private static final String PREPAYMENT_1_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";
    private static final String PREPAYMENT_3_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";


    private static final LocalDate AMORTIZATION_ENTRY_1_DATE = LocalDate.ofEpochDay(30L);
    private static final BigDecimal AMORTIZATION_ENTRY_1_AMOUNT = BigDecimal.ONE;
    private static final String AMORTIZATION_ENTRY_1_PARTICULARS = "AMORT-1";
    private static final String AMORTIZATION_ENTRY_1_PREP_SOL = "SOL_1";
    private static final String AMORTIZATION_ENTRY_1_PREP_AC_NO = "ACCOUNT_NO_1";
    private static final String AMORTIZATION_ENTRY_1_AMORT_SOL = "BBB";
    private static final String AMORTIZATION_ENTRY_1_AMORT_AC_NO = "BBBBB";
    private static final String AMORTIZATION_ENTRY_1_F_TOKEN = "FILE_TOKEN_1";
    private static final String AMORTIZATION_ENTRY_1_AMORT_TAG = "FILE_TAG_1";
    private static final boolean AMORTIZATION_ENTRY_1_ORPHANED = false;
    private static final long AMORTIZATION_ENTRY_1_PREP_ID = 1L;

    private static final LocalDate AMORTIZATION_ENTRY_2_DATE = LocalDate.ofEpochDay(30L);
    private static final BigDecimal AMORTIZATION_ENTRY_2_AMOUNT = BigDecimal.ONE;
    private static final String AMORTIZATION_ENTRY_2_PARTICULARS = "AMORT-2";
    private static final String AMORTIZATION_ENTRY_2_PREP_SOL = "SOL_2";
    private static final String AMORTIZATION_ENTRY_2_PREP_AC_NO = "ACCOUNT_NO_2";
    private static final String AMORTIZATION_ENTRY_2_AMORT_SOL = "BBB";
    private static final String AMORTIZATION_ENTRY_2_AMORT_AC_NO = "BBBBB";
    private static final String AMORTIZATION_ENTRY_2_F_TOKEN = "FILE_TOKEN_2";
    private static final String AMORTIZATION_ENTRY_2_AMORT_TAG = "FILE_TAG_2";
    private static final boolean AMORTIZATION_ENTRY_2_ORPHANED = false;
    private static final long AMORTIZATION_ENTRY_2_PREP_ID = 2L;

    private static final LocalDate AMORTIZATION_ENTRY_3_DATE = LocalDate.ofEpochDay(30L);
    private static final BigDecimal AMORTIZATION_ENTRY_3_AMOUNT = BigDecimal.ONE;
    private static final String AMORTIZATION_ENTRY_3_PARTICULARS = "AMORT-3";
    private static final String AMORTIZATION_ENTRY_3_PREP_SOL = "SOL_3";
    private static final String AMORTIZATION_ENTRY_3_PREP_AC_NO = "ACCOUNT_NO_3";
    private static final String AMORTIZATION_ENTRY_3_AMORT_SOL = "BBB";
    private static final String AMORTIZATION_ENTRY_3_AMORT_AC_NO = "BBBBB";
    private static final String AMORTIZATION_ENTRY_3_F_TOKEN = "FILE_TOKEN_3";
    private static final String AMORTIZATION_ENTRY_3_AMORT_TAG = "FILE_TAG_3";
    private static final boolean AMORTIZATION_ENTRY_3_ORPHANED = false;
    private static final long AMORTIZATION_ENTRY_3_PREP_ID = 3L;

    private static final LocalDate AMORTIZATION_ENTRY_4_DATE = LocalDate.ofEpochDay(30L);
    private static final BigDecimal AMORTIZATION_ENTRY_4_AMOUNT = BigDecimal.ONE;
    private static final String AMORTIZATION_ENTRY_4_PARTICULARS = "AMORT-3";
    private static final String AMORTIZATION_ENTRY_4_PREP_SOL = "SOL_1";
    private static final String AMORTIZATION_ENTRY_4_PREP_AC_NO = "ACCOUNT_NO_2";
    private static final String AMORTIZATION_ENTRY_4_AMORT_SOL = "BBB";
    private static final String AMORTIZATION_ENTRY_4_AMORT_AC_NO = "BBBBB";
    private static final String AMORTIZATION_ENTRY_4_F_TOKEN = "FILE_TOKEN_3";
    private static final String AMORTIZATION_ENTRY_4_AMORT_TAG = "FILE_TAG_3";
    private static final boolean AMORTIZATION_ENTRY_4_ORPHANED = false;
    private static final long AMORTIZATION_ENTRY_4_PREP_ID = 3L;

    private static final LocalDate AMORTIZATION_ENTRY_5_DATE = LocalDate.ofEpochDay(30L);
    private static final BigDecimal AMORTIZATION_ENTRY_5_AMOUNT = BigDecimal.ONE;
    private static final String AMORTIZATION_ENTRY_5_PARTICULARS = "AMORT-3";
    private static final String AMORTIZATION_ENTRY_5_PREP_SOL = "SOL_1";
    private static final String AMORTIZATION_ENTRY_5_PREP_AC_NO = "ACCOUNT_NO_2";
    private static final String AMORTIZATION_ENTRY_5_AMORT_SOL = "BBB";
    private static final String AMORTIZATION_ENTRY_5_AMORT_AC_NO = "BBBBB";
    private static final String AMORTIZATION_ENTRY_5_F_TOKEN = "FILE_TOKEN_3";
    private static final String AMORTIZATION_ENTRY_5_AMORT_TAG = "FILE_TAG_3";
    private static final boolean AMORTIZATION_ENTRY_5_ORPHANED = false;
    private static final long AMORTIZATION_ENTRY_5_PREP_ID = 3L;

    static final AmortizationEntryDTO AMORTIZATION_ENTRY_1 = AmortizationEntryDTO.builder()
                                                                                 .amortizationDate(AMORTIZATION_ENTRY_1_DATE)
                                                                                 .amortizationAmount(AMORTIZATION_ENTRY_1_AMOUNT)
                                                                                 .particulars(AMORTIZATION_ENTRY_1_PARTICULARS)
                                                                                 .prepaymentServiceOutlet(AMORTIZATION_ENTRY_1_PREP_SOL)
                                                                                 .prepaymentAccountNumber(AMORTIZATION_ENTRY_1_PREP_AC_NO)
                                                                                 .amortizationServiceOutlet(AMORTIZATION_ENTRY_1_AMORT_SOL)
                                                                                 .amortizationAccountNumber(AMORTIZATION_ENTRY_1_AMORT_AC_NO)
                                                                                 .originatingFileToken(AMORTIZATION_ENTRY_1_F_TOKEN)
                                                                                 .amortizationTag(AMORTIZATION_ENTRY_1_AMORT_TAG)
                                                                                 .orphaned(AMORTIZATION_ENTRY_1_ORPHANED)
                                                                                 .prepaymentEntryId(AMORTIZATION_ENTRY_1_PREP_ID)
                                                                                 .build();
    static final AmortizationEntryDTO AMORTIZATION_ENTRY_2 = AmortizationEntryDTO.builder()
                                                                                 .amortizationDate(AMORTIZATION_ENTRY_2_DATE)
                                                                                 .amortizationAmount(AMORTIZATION_ENTRY_2_AMOUNT)
                                                                                 .particulars(AMORTIZATION_ENTRY_2_PARTICULARS)
                                                                                 .prepaymentServiceOutlet(AMORTIZATION_ENTRY_2_PREP_SOL)
                                                                                 .prepaymentAccountNumber(AMORTIZATION_ENTRY_2_PREP_AC_NO)
                                                                                 .amortizationServiceOutlet(AMORTIZATION_ENTRY_2_AMORT_SOL)
                                                                                 .amortizationAccountNumber(AMORTIZATION_ENTRY_2_AMORT_AC_NO)
                                                                                 .originatingFileToken(AMORTIZATION_ENTRY_2_F_TOKEN)
                                                                                 .amortizationTag(AMORTIZATION_ENTRY_2_AMORT_TAG)
                                                                                 .orphaned(AMORTIZATION_ENTRY_2_ORPHANED)
                                                                                 .prepaymentEntryId(AMORTIZATION_ENTRY_2_PREP_ID)
                                                                                 .build();
    static final AmortizationEntryDTO AMORTIZATION_ENTRY_3 = AmortizationEntryDTO.builder()
                                                                                 .amortizationDate(AMORTIZATION_ENTRY_3_DATE)
                                                                                 .amortizationAmount(AMORTIZATION_ENTRY_3_AMOUNT)
                                                                                 .particulars(AMORTIZATION_ENTRY_3_PARTICULARS)
                                                                                 .prepaymentServiceOutlet(AMORTIZATION_ENTRY_3_PREP_SOL)
                                                                                 .prepaymentAccountNumber(AMORTIZATION_ENTRY_3_PREP_AC_NO)
                                                                                 .amortizationServiceOutlet(AMORTIZATION_ENTRY_3_AMORT_SOL)
                                                                                 .amortizationAccountNumber(AMORTIZATION_ENTRY_3_AMORT_AC_NO)
                                                                                 .originatingFileToken(AMORTIZATION_ENTRY_3_F_TOKEN)
                                                                                 .amortizationTag(AMORTIZATION_ENTRY_3_AMORT_TAG)
                                                                                 .orphaned(AMORTIZATION_ENTRY_3_ORPHANED)
                                                                                 .prepaymentEntryId(AMORTIZATION_ENTRY_3_PREP_ID)
                                                                                 .build();
    static final AmortizationEntryDTO AMORTIZATION_ENTRY_4 = AmortizationEntryDTO.builder()
                                                                                 .amortizationDate(AMORTIZATION_ENTRY_4_DATE)
                                                                                 .amortizationAmount(AMORTIZATION_ENTRY_4_AMOUNT)
                                                                                 .particulars(AMORTIZATION_ENTRY_4_PARTICULARS)
                                                                                 .prepaymentServiceOutlet(AMORTIZATION_ENTRY_4_PREP_SOL)
                                                                                 .prepaymentAccountNumber(AMORTIZATION_ENTRY_4_PREP_AC_NO)
                                                                                 .amortizationServiceOutlet(AMORTIZATION_ENTRY_4_AMORT_SOL)
                                                                                 .amortizationAccountNumber(AMORTIZATION_ENTRY_4_AMORT_AC_NO)
                                                                                 .originatingFileToken(AMORTIZATION_ENTRY_4_F_TOKEN)
                                                                                 .amortizationTag(AMORTIZATION_ENTRY_4_AMORT_TAG)
                                                                                 .orphaned(AMORTIZATION_ENTRY_4_ORPHANED)
                                                                                 .prepaymentEntryId(AMORTIZATION_ENTRY_4_PREP_ID)
                                                                                 .build();
    static final AmortizationEntryDTO AMORTIZATION_ENTRY_5 = AmortizationEntryDTO.builder()
                                                                                 .amortizationDate(AMORTIZATION_ENTRY_5_DATE)
                                                                                 .amortizationAmount(AMORTIZATION_ENTRY_5_AMOUNT)
                                                                                 .particulars(AMORTIZATION_ENTRY_5_PARTICULARS)
                                                                                 .prepaymentServiceOutlet(AMORTIZATION_ENTRY_5_PREP_SOL)
                                                                                 .prepaymentAccountNumber(AMORTIZATION_ENTRY_5_PREP_AC_NO)
                                                                                 .amortizationServiceOutlet(AMORTIZATION_ENTRY_5_AMORT_SOL)
                                                                                 .amortizationAccountNumber(AMORTIZATION_ENTRY_5_AMORT_AC_NO)
                                                                                 .originatingFileToken(AMORTIZATION_ENTRY_5_F_TOKEN)
                                                                                 .amortizationTag(AMORTIZATION_ENTRY_5_AMORT_TAG)
                                                                                 .orphaned(AMORTIZATION_ENTRY_5_ORPHANED)
                                                                                 .prepaymentEntryId(AMORTIZATION_ENTRY_5_PREP_ID)
                                                                                 .build();

    static final PrepaymentEntryDTO PREPAYMENT_1 = PrepaymentEntryDTO.builder()
                                                                     .accountNumber(PREPAYMENT_1_ACCOUNT_NUMBER)
                                                                     .accountName(PREPAYMENT_1_ACCOUNT_NAME)
                                                                     .prepaymentId(PREPAYMENT_1_PREPAYMENT_ID)
                                                                     .prepaymentDate(PREPAYMENT_1_PREPAYMENT_DATE)
                                                                     .particulars(PREPAYMENT_1_PARTICULARS)
                                                                     .serviceOutlet(PREPAYMENT_1_SERVICE_OUTLET)
                                                                     .prepaymentAmount(PREPAYMENT_1_PREPAYMENT_AMOUNT)
                                                                     .months(PREPAYMENT_1_MONTHS)
                                                                     .supplierName(PREPAYMENT_1_SUPPLIER_NAME)
                                                                     .invoiceNumber(PREPAYMENT_1_INVOICE_NUMBER)
                                                                     .scannedDocumentId(PREPAYMENT_1_SCANNED_DOCUMENT_ID)
                                                                     .originatingFileToken(PREPAYMENT_1_ORIGINATING_FILE_TOKEN)
                                                                     .build();

    static final PrepaymentEntryDTO PREPAYMENT_2 = PrepaymentEntryDTO.builder()
                                                                     .accountNumber(PREPAYMENT_2_ACCOUNT_NUMBER)
                                                                     .accountName(PREPAYMENT_2_ACCOUNT_NAME)
                                                                     .prepaymentId(PREPAYMENT_2_PREPAYMENT_ID)
                                                                     .prepaymentDate(PREPAYMENT_2_PREPAYMENT_DATE)
                                                                     .particulars(PREPAYMENT_2_PARTICULARS)
                                                                     .serviceOutlet(PREPAYMENT_2_SERVICE_OUTLET)
                                                                     .prepaymentAmount(PREPAYMENT_2_PREPAYMENT_AMOUNT)
                                                                     .months(PREPAYMENT_2_MONTHS)
                                                                     .supplierName(PREPAYMENT_2_SUPPLIER_NAME)
                                                                     .invoiceNumber(PREPAYMENT_2_INVOICE_NUMBER)
                                                                     .scannedDocumentId(PREPAYMENT_2_SCANNED_DOCUMENT_ID)
                                                                     .originatingFileToken(PREPAYMENT_2_ORIGINATING_FILE_TOKEN)
                                                                     .build();

    static final PrepaymentEntryDTO PREPAYMENT_3 = PrepaymentEntryDTO.builder()
                                                                     .accountNumber(PREPAYMENT_3_ACCOUNT_NUMBER)
                                                                     .accountName(PREPAYMENT_3_ACCOUNT_NAME)
                                                                     .prepaymentId(PREPAYMENT_3_PREPAYMENT_ID)
                                                                     .prepaymentDate(PREPAYMENT_3_PREPAYMENT_DATE)
                                                                     .particulars(PREPAYMENT_3_PARTICULARS)
                                                                     .serviceOutlet(PREPAYMENT_3_SERVICE_OUTLET)
                                                                     .prepaymentAmount(PREPAYMENT_3_PREPAYMENT_AMOUNT)
                                                                     .months(PREPAYMENT_3_MONTHS)
                                                                     .supplierName(PREPAYMENT_3_SUPPLIER_NAME)
                                                                     .invoiceNumber(PREPAYMENT_3_INVOICE_NUMBER)
                                                                     .scannedDocumentId(PREPAYMENT_3_SCANNED_DOCUMENT_ID)
                                                                     .originatingFileToken(PREPAYMENT_3_ORIGINATING_FILE_TOKEN)
                                                                     .build();


    @Autowired
    private IPrepaymentEntryIdService failSafePrepaymentIdService;

    @Autowired
    private PrepaymentEntryService prepaymentEntryService;

    private List<EntryIDaAndDate> presentIds;

    private List<PrepaymentEntryDTO> persistedEntries;

    private List<Long> ids;
    private List<AmortizationEntryDTO> amortizationEntries;

    private static void logEntries(EntryIDaAndDate prep) {
        System.out.println("Entry id recorded : " + prep);
    }

    @BeforeEach
    public void setUp() {

        // Everything linked list sequence is important
        List<PrepaymentEntryDTO> prepaymentEntries = new LinkedList<>();
        amortizationEntries = new LinkedList<>();
        persistedEntries = new LinkedList<>();
        ids = new LinkedList<>();

        MockitoAnnotations.initMocks(this);

        prepaymentEntries.add(PREPAYMENT_1);
        prepaymentEntries.add(PREPAYMENT_2);
        prepaymentEntries.add(PREPAYMENT_3);

        amortizationEntries.add(AMORTIZATION_ENTRY_1);
        amortizationEntries.add(AMORTIZATION_ENTRY_2);
        amortizationEntries.add(AMORTIZATION_ENTRY_3);
        amortizationEntries.add(AMORTIZATION_ENTRY_4);
        amortizationEntries.add(AMORTIZATION_ENTRY_5);

        presentIds = prepaymentEntries.stream()
                                      .map(prepaymentEntryService::save)
                                      .peek(persistedEntries::add)
                                      .map(prep -> new EntryIDaAndDate(prep.getId(), prep.getPrepaymentId(), prep.getPrepaymentDate(), prep.getParticulars()))
                                      .peek(PrepaymentEntryIdServiceIT::logEntries)
                                      .collect(ImmutableList.toImmutableList());

        IntStream.range(0, prepaymentEntries.size())
                 .forEach(i -> ids.add(
                     failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(i), presentIds.get(i).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(i).getTransactionDate()))));
    }

    @Test
    public void findByIdAndDate() {

        presentIds.forEach(PrepaymentEntryIdServiceIT::logEntries);

        System.out.println("No of amortization-entries : " + amortizationEntries.size());

        // First amortization-entry will pick the first prepayment-id
        assertEquals(persistedEntries.get(0).getId(),
                     failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(0), presentIds.get(0).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(0).getTransactionDate())));
        // Second amortization-entry will pick the first prepayment-id
        assertEquals(persistedEntries.get(1).getId(),
                     failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(1), presentIds.get(1).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(1).getTransactionDate())));
        // Third amortization-entry will pick the first prepayment-id
        assertEquals(persistedEntries.get(2).getId(),
        failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(2), presentIds.get(2).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(2).getTransactionDate())));
        // The fourth amortization-entry will pick the second prepayment entry
        assertEquals(persistedEntries.get(1).getId(),
                     failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(3), presentIds.get(1).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(1).getTransactionDate())));
        // The fifth amortization-entry will pick the 3rd prepayment-entry
        assertEquals(persistedEntries.get(1).getId(),
                     failSafePrepaymentIdService.findByIdAndDate(amortizationEntries.get(4), presentIds.get(1).getTransactionId(), DATETIME_FORMATTER.format(presentIds.get(1).getTransactionDate())));
        assertEquals(3, presentIds.size());
    }

    @Data
    @AllArgsConstructor
    @ToString
    class EntryIDaAndDate {
        private long id;
        private String transactionId;
        private LocalDate transactionDate;
        private String particulards;
    }
}
