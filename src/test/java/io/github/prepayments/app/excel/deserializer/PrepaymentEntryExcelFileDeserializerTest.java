package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.*;

class PrepaymentEntryExcelFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<PrepaymentEntryEVM> entries = prepaymentEntryDeserializer.deserialize(toBytes(readFile("prepaymentDataEntryFile.xlsx")));

        assertEquals(388, entries.size());
        assertEquals("6771-8903-9734-0686", entries.get(0).getAccountNumber());
        assertEquals("Account #: 1", entries.get(0).getAccountName());
        assertEquals("DC1004", entries.get(0).getPrepaymentId());
        assertEquals("2018/10/09", entries.get(0).getPrepaymentDate());
        assertEquals("PrepaymentDC1004 dated: 09-10-2018", entries.get(0).getParticulars());
        assertEquals("250", entries.get(0).getServiceOutlet());
        assertEquals("298,990.60", entries.get(0).getPrepaymentAmount());
        assertEquals(33, entries.get(0).getMonths());
        assertEquals("Supplier 3", entries.get(0).getSupplierName());
        assertEquals("Inv 89/2019", entries.get(0).getInvoiceNumber());

    }
}
