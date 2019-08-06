package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.*;

class AmortizationEntryExcelFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<AmortizationEntryEVM> entries =  amortizationEntryDeserializer.deserialize(toBytes(readFile("amortizationDataEntryFile.xlsx")));

        assertEquals(388, entries.size());
        assertEquals("2019/05/24", entries.get(0).getAmortizationDate());
        assertEquals("2019/06/11", entries.get(387).getAmortizationDate());
        assertEquals("298,990.60", entries.get(0).getAmortizationAmount());
        assertEquals("857,199.57", entries.get(387).getAmortizationAmount());
        assertEquals("Amortization of DC1004 dated: 09-10-2018", entries.get(0).getParticulars());
        assertEquals("Amortization of DC2369 dated: 22-10-2018", entries.get(387).getParticulars());
        assertEquals("250", entries.get(0).getPrepaymentServiceOutlet());
        assertEquals("651", entries.get(387).getPrepaymentServiceOutlet());
        assertEquals("6771-8903-9734-0686", entries.get(0).getPrepaymentAccountNumber());
        assertEquals("3528-4898-6759-6506", entries.get(387).getPrepaymentAccountNumber());
        assertEquals("250", entries.get(0).getAmortizationServiceOutlet());
        assertEquals("651", entries.get(387).getAmortizationServiceOutlet());
        assertEquals("6771-8903-9734-0686", entries.get(0).getAmortizationAccountNumber());
        assertEquals("3528-4898-6759-6506", entries.get(387).getAmortizationAccountNumber());
        assertEquals("DC1004", entries.get(0).getPrepaymentEntryId());
        assertEquals("DC2369", entries.get(387).getPrepaymentEntryId());
        assertEquals("2018/10/09", entries.get(0).getPrepaymentEntryDate());
        assertEquals("2018/10/22", entries.get(387).getPrepaymentEntryDate());
    }
}
