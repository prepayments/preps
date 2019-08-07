package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.*;

class RegisteredSupplierDataEntryFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<RegisteredSupplierEVM> entries = registeredSupplierDeserializer.deserialize(toBytes(readFile("supplierDataEntryFile.xlsx")));

        assertEquals(1000, entries.size());
    }
}
