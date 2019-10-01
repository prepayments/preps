package io.github.prepayments.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryUpdateEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class AmortizationUpdateExcelFileDeserializer extends BinaryExcelFileDeserializer<AmortizationEntryUpdateEVM> implements ExcelFileDeserializer<AmortizationEntryUpdateEVM> {

    public AmortizationUpdateExcelFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<AmortizationEntryUpdateEVM> deserialize(final byte[] excelFile) {
        InputStream amortizationStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<AmortizationEntryUpdateEVM> amortizationEntries = Poiji.fromExcel(amortizationStream, PoijiExcelType.XLSX, AmortizationEntryUpdateEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} amortization entries deserialized from file: {} in {} millis", amortizationEntries.size(), excelFile.toString(), readTime);

        return amortizationEntries;
    }
}
