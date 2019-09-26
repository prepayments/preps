package io.github.prepayments.app.messaging.data_entry.service;

import com.google.common.collect.ImmutableList;
import io.github.prepayments.PrepsApp;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@Slf4j
@Transactional
@SpringBootTest(classes = PrepsApp.class)
public class PrepaymentEntryIdServiceIT {


    private static final String PREPAYMENT_1_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String PREPAYMENT_1_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final String PREPAYMENT_1_PREPAYMENT_ID = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_PREPAYMENT_ID = "BBBBBBBBBB";

    private static final LocalDate PREPAYMENT_1_PREPAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate PREPAYMENT_2_PREPAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String PREPAYMENT_1_PARTICULARS = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_PARTICULARS = "BBBBBBBBBB";

    private static final String PREPAYMENT_1_SERVICE_OUTLET = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_SERVICE_OUTLET = "BBBBBBBBBB";

    private static final BigDecimal PREPAYMENT_1_PREPAYMENT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal PREPAYMENT_2_PREPAYMENT_AMOUNT = new BigDecimal(2);

    private static final Integer PREPAYMENT_1_MONTHS = 1;
    private static final Integer PREPAYMENT_2_MONTHS = 2;

    private static final String PREPAYMENT_1_SUPPLIER_NAME = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_SUPPLIER_NAME = "BBBBBBBBBB";

    private static final String PREPAYMENT_1_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Long PREPAYMENT_1_SCANNED_DOCUMENT_ID = 1L;
    private static final Long PREPAYMENT_2_SCANNED_DOCUMENT_ID = 2L;

    private static final String PREPAYMENT_1_ORIGINATING_FILE_TOKEN = "AAAAAAAAAA";
    private static final String PREPAYMENT_2_ORIGINATING_FILE_TOKEN = "BBBBBBBBBB";

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


    @Autowired
    private IPrepaymentEntryIdService failSafePrepaymentIdService;

    @Autowired
    private PrepaymentEntryService prepaymentEntryService;

    private List<EntryIDaAndDate> presentIds;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        List<PrepaymentEntryDTO> prepaymentEntries = new ArrayList<>();
        prepaymentEntries.add(PREPAYMENT_1);
        prepaymentEntries.add(PREPAYMENT_2);

        presentIds = prepaymentEntries.stream()
                                      .map(prepaymentEntryService::save)
                                      .map(prep -> new EntryIDaAndDate(prep.getId(), prep.getPrepaymentId(), prep.getPrepaymentDate()))
                                      .peek(prep -> {log.debug("Entry id recorded : {}", prep);})
                                      .collect(ImmutableList.toImmutableList());


    }

    @Test
    public void findByIdAndDate() {

        assertEquals(2, presentIds.size());
    }

    @Data
    @AllArgsConstructor
    @ToString
    class EntryIDaAndDate {
        private long id;
        private String transactionId;
        private LocalDate transactionDate;
    }
}
