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
        assertEquals("Accounting", entries.get(0).getServiceOutletName());
        assertEquals("377", entries.get(0).getServiceOutletCode());
        assertEquals("WAUCHULA", entries.get(0).getServiceOutletLocation());
        assertEquals("Durand Blow", entries.get(0).getServiceOutletManager());
        assertEquals(150, entries.get(0).getNumberOfStaff());
        assertEquals("Vahlen Towers", entries.get(0).getBuilding());
        assertEquals(319, entries.get(0).getFloor());
        assertEquals("3 Tony Street", entries.get(0).getPostalAddress());
        assertEquals("Rana Petrolli", entries.get(0).getContactPersonName());
        assertEquals("rpetrolli3p@home.pl", entries.get(0).getContactEmail());
        assertEquals("Vahlen Street", entries.get(0).getStreet());
    }
}
