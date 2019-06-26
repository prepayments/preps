package io.github.prepayments.app.messaging.data_entry.listener;

import io.github.prepayments.app.messaging.data_entry.mapper.PrepaymentDataEntryFileDTOMapper;
import io.github.prepayments.app.messaging.data_entry.vm.SimplePrepaymentEntryEVM;
import io.github.prepayments.app.messaging.filing.streams.FilingPrepaymentEntryStreams;
import io.github.prepayments.service.PrepaymentEntryService;
import io.github.prepayments.service.dto.PrepaymentEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataSinkPrepaymentsStreamsListener {


    private final PrepaymentEntryService prepaymentEntryService;
    private final PrepaymentDataEntryFileDTOMapper prepaymentDataEntryFileDTOMapper;

    public DataSinkPrepaymentsStreamsListener(final PrepaymentEntryService prepaymentEntryService, final PrepaymentDataEntryFileDTOMapper prepaymentDataEntryFileDTOMapper) {
        this.prepaymentEntryService = prepaymentEntryService;
        this.prepaymentDataEntryFileDTOMapper = prepaymentDataEntryFileDTOMapper;
    }

    @StreamListener(FilingPrepaymentEntryStreams.INPUT)
    public void handlePrepaymentEntryStreamItem(@Payload SimplePrepaymentEntryEVM streamItem) {
        log.info("Received prepaymentEntry @ index #: {} standby for persistence...", streamItem.getRowIndex());

        PrepaymentEntryDTO dto = prepaymentDataEntryFileDTOMapper.toDTO(streamItem);

        PrepaymentEntryDTO result = prepaymentEntryService.save(dto);

        log.debug("Prepayment entry item saved : {}", result.getId());
    }
}
