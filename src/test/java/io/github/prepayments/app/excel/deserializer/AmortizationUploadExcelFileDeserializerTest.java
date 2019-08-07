package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.*;

class AmortizationUploadExcelFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<AmortizationUploadEVM> entries = amortizationUploadDeserializer.deserialize(toBytes(readFile("amortizationDataEntryUploadFile.xlsx")));

        assertEquals(1000, entries.size());
        assertEquals("Hynda Tweedle", entries.get(0).getAccountName());
        assertEquals("Bethlehem", entries.get(0).getParticulars());
        assertEquals("269", entries.get(0).getAmortizationServiceOutletCode());
        assertEquals("269", entries.get(0).getPrepaymentServiceOutletCode());
        assertEquals("415157723400", entries.get(0).getPrepaymentAccountNumber());
        assertEquals("415157723400", entries.get(0).getExpenseAccountNumber());
        assertEquals("DC1777", entries.get(0).getPrepaymentTransactionId());
        assertEquals("2018/09/04", entries.get(0).getPrepaymentTransactionDate());
        assertEquals("43040.75", entries.get(0).getPrepaymentTransactionAmount());
        assertEquals("8608.15", entries.get(0).getAmortizationAmount());
        assertEquals("5", entries.get(0).getNumberOfAmortizations());
        assertEquals("2019/05/28", entries.get(0).getFirstAmortizationDate());

    }
}
