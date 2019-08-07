package io.github.prepayments.app.excel.deserializer;

import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;

import static io.github.prepayments.app.ExcelTestUtil.getDefaultPoijiOptions;

public class DeserializerTests {


    ExcelFileDeserializer<AmortizationEntryEVM> amortizationEntryDeserializer = new AmortizationEntryExcelFileDeserializer(getDefaultPoijiOptions());

    ExcelFileDeserializer<AmortizationUploadEVM> amortizationUploadDeserializer = new AmortizationUploadExcelFileDeserializer(getDefaultPoijiOptions());

    ExcelFileDeserializer<PrepaymentEntryEVM> prepaymentEntryDeserializer = new PrepaymentEntryExcelFileDeserializer(getDefaultPoijiOptions());

    ExcelFileDeserializer<RegisteredSupplierEVM> registeredSupplierDeserializer = new RegisteredSupplierDataEntryFileDeserializer(getDefaultPoijiOptions());

    ExcelFileDeserializer<ServiceOutletEVM> serviceOutletDeserializer = new ServiceOutletDataEntryFileDeserializer(getDefaultPoijiOptions());
}
