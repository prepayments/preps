package io.github.prepayments.app.messaging.services.scheduler;

import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.app.messaging.services.ServiceOutletDataEntryMessageService;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("serviceOutletDataEntryFileQueueScheduler")
public class ServiceOutletDataEntryFileQueueScheduler implements DataEntryFileQueueScheduler<ServiceOutletDataEntryFileDTO, String> {

    private final ServiceOutletDataEntryMessageService serviceOutletDataEntryMessageService;

    public ServiceOutletDataEntryFileQueueScheduler(final ServiceOutletDataEntryMessageService serviceOutletDataEntryMessageService) {
        this.serviceOutletDataEntryMessageService = serviceOutletDataEntryMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final ServiceOutletDataEntryFileDTO dataEntryFile, final String fileToken) {

        log.info("Enqueueing data read from the file id : {} ...", dataEntryFile.getId());

        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeServiceOutletFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .map(serviceOutletDataEntryMessageService::sendMessage)
                          .collect(Collectors.toList()));
        // @formatter:on
    }
}
