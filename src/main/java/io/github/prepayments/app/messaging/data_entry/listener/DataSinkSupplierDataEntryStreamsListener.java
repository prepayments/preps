package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.mapper.SupplierDataEntryFileDTOMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleRegisteredSupplierEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingSupplierDataEntryStreams;
import io.github.prepayments.service.RegisteredSupplierService;
import io.github.prepayments.service.dto.RegisteredSupplierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSinkSupplierDataEntryStreamsListener {


    private final SupplierDataEntryFileDTOMapper supplierDataEntryFileDTOMapper;
    private final RegisteredSupplierService registeredSupplierService;

    public DataSinkSupplierDataEntryStreamsListener(final SupplierDataEntryFileDTOMapper supplierDataEntryFileDTOMapper, final RegisteredSupplierService registeredSupplierService) {
        this.supplierDataEntryFileDTOMapper = supplierDataEntryFileDTOMapper;
        this.registeredSupplierService = registeredSupplierService;
    }

    @StreamListener(FilingSupplierDataEntryStreams.INPUT)
    public void handleUploadedSupplierData(@Payload SimpleRegisteredSupplierEVM supplierEVM) {
        log.info("Received registeredSupplierEVM at index #: {} standby for persistence...", supplierEVM.getRowIndex());

        RegisteredSupplierDTO dto = supplierDataEntryFileDTOMapper.toDTO(supplierEVM);

        log.debug("Persisting supplier id : {} into DB....", dto.getSupplierName());

        RegisteredSupplierDTO result = registeredSupplierService.save(dto);

        log.info("Supplier id : {} persisted as id : {}", result.getSupplierName(), result.getId());

    }
}
