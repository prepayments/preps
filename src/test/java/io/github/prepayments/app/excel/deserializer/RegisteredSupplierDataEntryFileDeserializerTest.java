package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisteredSupplierDataEntryFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<RegisteredSupplierEVM> entries = registeredSupplierDeserializer.deserialize(toBytes(readFile("supplierDataEntryFile.xlsx")));

        assertEquals(1000, entries.size());
        assertEquals("Cloe Philbin", entries.get(0).getSupplierName());
        assertEquals("289 Nobel Junction", entries.get(0).getSupplierAddress());
        assertEquals("(150) 9657121", entries.get(0).getPhoneNumber());
        assertEquals("gleasor0@hexun.com", entries.get(0).getSupplierEmail());
        assertEquals("4485235893137", entries.get(0).getBankAccountNumber());
        assertEquals("Cloe Philbin", entries.get(0).getBankAccountName());
        assertEquals("Runolfsson, Nolan and Abernathy", entries.get(0).getSupplierBankName());
        assertEquals("Vernon", entries.get(0).getSupplierBankBranch());
        assertEquals("LT73 9849 9433 5729 0972", entries.get(0).getBankSwiftCode());
        assertEquals("31403 Chinook Crossing", entries.get(0).getBankPhysicalAddress());
        assertEquals("POLAND", entries.get(0).getDomicile());
        assertEquals("034577459-0", entries.get(0).getTaxAuthorityPIN());
    }
}
