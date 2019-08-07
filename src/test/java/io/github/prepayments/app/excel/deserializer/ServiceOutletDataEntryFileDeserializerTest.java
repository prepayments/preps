package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static io.github.prepayments.app.ExcelTestUtil.readFile;
import static io.github.prepayments.app.ExcelTestUtil.toBytes;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServiceOutletDataEntryFileDeserializerTest extends DeserializerTests {

    @Test
    void deserialize() throws IOException {

        List<ServiceOutletEVM> entries = serviceOutletDeserializer.deserialize(toBytes(readFile("solDataEntryFile.xlsx")));

        assertEquals(289, entries.size());
        assertEquals(289, entries.size());
    }
}
