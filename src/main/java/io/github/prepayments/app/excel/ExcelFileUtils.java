package io.github.prepayments.app.excel;

import com.google.common.collect.ImmutableList;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.prepayments.app.excel.deserializer.AmortizationEntryExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.AmortizationUploadExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.PrepaymentEntryExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.RegisteredSupplierDataEntryFileDeserializer;
import io.github.prepayments.app.excel.deserializer.ServiceOutletDataEntryFileDeserializer;
import io.github.prepayments.app.excel.deserializer.TransactionAccountDataEntryExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controls the business logic for deserializing excel file data
 */
public class ExcelFileUtils {

    public static List<AmortizationEntryEVM> deserializeAmortizationFile(byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<AmortizationEntryEVM> excelFileDeserializer = new AmortizationEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
                                    .stream()
                                    .peek(amortizationEntryEVM -> amortizationEntryEVM.setOriginatingFileToken(originatingFileToken))
                                    .collect(ImmutableList.toImmutableList());
    }

    public static List<PrepaymentEntryEVM> deserializePrepaymentsFile(byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<PrepaymentEntryEVM> excelFileDeserializer = new PrepaymentEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
            .stream()
            .peek(prepaymentEntryEVM -> prepaymentEntryEVM.setOriginatingFileToken(originatingFileToken))
            .collect(ImmutableList.toImmutableList());
    }

    public static List<RegisteredSupplierEVM> deserializeSuppliersFile(byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<RegisteredSupplierEVM> excelFileDeserializer = new RegisteredSupplierDataEntryFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
            .stream()
            .peek(registeredSupplierEVM -> registeredSupplierEVM.setOriginatingFileToken(originatingFileToken))
            .collect(ImmutableList.toImmutableList());
    }

    public static List<ServiceOutletEVM> deserializeServiceOutletFile(byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<ServiceOutletEVM> excelFileDeserializer = new ServiceOutletDataEntryFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
            .stream()
            .peek(serviceOutletEVM -> serviceOutletEVM.setOriginatingFileToken(originatingFileToken))
            .collect(ImmutableList.toImmutableList());
    }

    public static List<TransactionAccountEVM> deserializeTransactionAccountFile(byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<TransactionAccountEVM> excelFileDeserializer = new TransactionAccountDataEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
            .stream()
            .peek(transactionAccountEVM -> transactionAccountEVM.setOriginatingFileToken(originatingFileToken))
            .collect(ImmutableList.toImmutableList());
    }

    public static List<AmortizationUploadEVM> deserializeAmortizationUploadFile(final byte[] dataEntryFile, String originatingFileToken) {
        ExcelFileDeserializer<AmortizationUploadEVM> excelFileDeserializer = new AmortizationUploadExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile)
            .stream()
            .peek(amortizationUploadEVM -> amortizationUploadEVM.setOriginatingFileToken(originatingFileToken))
            .collect(ImmutableList.toImmutableList());
    }
}
