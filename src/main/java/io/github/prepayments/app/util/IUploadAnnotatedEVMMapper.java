package io.github.prepayments.app.util;

import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.service.dto.AmortizationUploadDTO;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public interface IUploadAnnotatedEVMMapper extends DatedExcelViewMapper<String, AmortizationEntryEVM, AmortizationUploadDTO> {


    /**
     * Takes an existing UploadDTO and converts it to an EVM
     *
     * @param amortizationUploadDTO
     * @param dateInstance
     * @param dtf
     * @return
     */
    @Override
    AmortizationEntryEVM toExcelView(AmortizationUploadDTO amortizationUploadDTO, String dateInstance, DateTimeFormatter dtf);

    /**
     * Given the how many search services the major implementation for this method requires, calling it in a high speed
     * data stream will lead to less than savory waiting times, especially if your are not sure whether the EVM can be
     * mapped to an actual UploadDTO
     *
     * @param amortizationEntryEVM The entity with which a correspoding UploadDTO is sought
     * @param dtf The DateTimeFormat for the query. Variance from the system DTF will lead to less than savory results
     * @return AmortizationUploadDTO, corresponding to the AmortizationEntryEVM
     */
    @Override
    AmortizationUploadDTO toDto(AmortizationEntryEVM amortizationEntryEVM, DateTimeFormatter dtf);

    @Override
    default List<AmortizationEntryEVM> toExcelView(final List<AmortizationUploadDTO> amortizationUploadDTOS, final String dateInstance, final DateTimeFormatter dtf) {
        return Collections.unmodifiableList(amortizationUploadDTOS.stream()
                                                                  .map(uploadDTO -> toExcelView(uploadDTO, dateInstance, dtf)).collect(Collectors.toList()));
    }

    @Override
    default List<AmortizationUploadDTO> toDto(final List<AmortizationEntryEVM> amortizationEntryEVMS, final DateTimeFormatter dtf) {
        return Collections.unmodifiableList(amortizationEntryEVMS.stream()
                                                                 .map(amortizationEntryEVM -> toDto(amortizationEntryEVM, dtf)).collect(Collectors.toList()));
    }


}
