package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryUpdateEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmortizationUpdateExcelFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<AmortizationEntryUpdateEVM> entries = amortizationEntryUpdateDeserializer.deserialize(toBytes(readFile("amortizationEntryUpdateFile.xlsx")));

        assertEquals(84, entries.size());
        assertEquals("2067", entries.get(0).getAmortizationEntryId());
        assertEquals("2019/01/20", entries.get(0).getAmortizationDate());
        assertEquals("2019/12/20", entries.get(83).getAmortizationDate());
        assertEquals("5583.33", entries.get(0).getAmortizationAmount());
        assertEquals("7000", entries.get(83).getAmortizationAmount());
        assertEquals("SERVICES BUSINESS PERMIT", entries.get(0).getParticulars());
        assertEquals("PARKING FEES", entries.get(83).getParticulars());
        assertEquals("000", entries.get(0).getPrepaymentServiceOutlet());
        assertEquals("099", entries.get(83).getPrepaymentServiceOutlet());
        assertEquals("9898201005", entries.get(0).getPrepaymentAccountNumber());
        assertEquals("1138098387", entries.get(83).getPrepaymentAccountNumber());
        assertEquals("223", entries.get(0).getAmortizationServiceOutlet());
        assertEquals("248", entries.get(83).getAmortizationServiceOutlet());
        assertEquals("4030081004", entries.get(0).getAmortizationAccountNumber());
        assertEquals("4330081004", entries.get(83).getAmortizationAccountNumber());
        assertEquals("1185", entries.get(0).getPrepaymentEntryId());
        assertEquals("1295", entries.get(83).getPrepaymentEntryId());
    }
}
