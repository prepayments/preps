package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.mapper.ServiceOutletDataEntryFileDTOMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimpleServiceOutletEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingServiceOutletDataStreams;
import io.github.prepayments.service.ServiceOutletService;
import io.github.prepayments.service.dto.ServiceOutletDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSinkServiceOutletDataStreamsListener {

    private final ServiceOutletService serviceOutletService;
    private final ServiceOutletDataEntryFileDTOMapper serviceOutletDataEntryFileDTOMapper;

    public DataSinkServiceOutletDataStreamsListener(final ServiceOutletService serviceOutletService, final ServiceOutletDataEntryFileDTOMapper serviceOutletDataEntryFileDTOMapper) {
        this.serviceOutletService = serviceOutletService;
        this.serviceOutletDataEntryFileDTOMapper = serviceOutletDataEntryFileDTOMapper;
    }

    @StreamListener(FilingServiceOutletDataStreams.INPUT)
    public void handleUploadedServiceOutletData(@Payload SimpleServiceOutletEVM simpleServiceOutletEVM) {
        log.debug("Received serviceOutletDataEntry @ index #: {} standby for persistence...", simpleServiceOutletEVM.getRowIndex());

        ServiceOutletDTO dto = serviceOutletDataEntryFileDTOMapper.toDTO(simpleServiceOutletEVM);

        log.debug("Saving service outlet # : {} to the DB", dto.getServiceOutletCode());

        ServiceOutletDTO result = serviceOutletService.save(dto);

        log.debug("Service outlet # : {} persisted with Id : {}", result.getServiceOutletCode(), result.getId());
    }
}
