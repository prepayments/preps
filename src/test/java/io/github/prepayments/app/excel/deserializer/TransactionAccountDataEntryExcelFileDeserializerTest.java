package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionAccountDataEntryExcelFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<TransactionAccountEVM> entries = transactionAccountDeserializer.deserialize(toBytes(readFile("transactionAccountDataEntryFile.xlsx")));


        assertEquals(1000, entries.size());
        assertEquals("Bridget Checchi", entries.get(0).getAccountName());
        assertEquals("3538716992297800", entries.get(0).getAccountNumber());
        assertEquals("PREPAYMENT", entries.get(0).getAccountType());
        assertEquals("2019/03/22", entries.get(0).getOpeningDate());
    }
}
