package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.SupplierDataFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.SupplierDataFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.SupplierDataEntryFileQueueScheduler;
import io.github.prepayments.service.SupplierDataEntryFileService;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service
@Transactional
@Slf4j
public class SupplierUploadNotificationsFileUploadNotification implements UploadNotificationsStreamListener<SupplierDataFileUploadNotification> {

    private final SupplierDataEntryFileService supplierDataEntryFileService;
    private final SupplierDataEntryFileQueueScheduler supplierDataEntryFileQueueScheduler;

    public SupplierUploadNotificationsFileUploadNotification(final SupplierDataEntryFileService supplierDataEntryFileService,
                                                             final SupplierDataEntryFileQueueScheduler supplierDataEntryFileQueueScheduler) {
        this.supplierDataEntryFileService = supplierDataEntryFileService;
        this.supplierDataEntryFileQueueScheduler = supplierDataEntryFileQueueScheduler;
    }

    @Override
    @StreamListener(SupplierDataFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload SupplierDataFileUploadNotification dataStreamItem) {

        log.debug("ServiceOutlet file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        // @formatter:off
        SupplierDataEntryFileDTO result =
            supplierDataEntryFileService
                .findOne(dataStreamItem.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Service Outlet data entry file id : " + dataStreamItem.getId() + " was not found!"));
        // @formatter: on

        result.setUploadSuccessful(true);

        // @formatter:off
        supplierDataEntryFileQueueScheduler
            .deserializeAndEnqueue(result)
            .doOnCompleted(() -> result.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io()).subscribe();
        // @formatter:on

        supplierDataEntryFileService.save(result);
    }
}
