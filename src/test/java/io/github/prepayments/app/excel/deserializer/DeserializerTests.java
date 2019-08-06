package io.github.prepayments.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;

import java.time.format.DateTimeFormatter;

import static io.github.prepayments.app.ExcelTestUtil.getDefaultPoijiOptions;

public class DeserializerTests {


    ExcelFileDeserializer<AmortizationEntryEVM> amortizationEntryDeserializer = new AmortizationEntryExcelFileDeserializer(getDefaultPoijiOptions());
}
