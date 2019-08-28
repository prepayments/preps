package io.github.prepayments.app.messaging.notifications.listener;

import io.github.prepayments.app.messaging.notifications.ServiceOutletFileUploadStreams;
import io.github.prepayments.app.messaging.notifications.dto.ServiceOutletFileUploadNotification;
import io.github.prepayments.app.messaging.services.scheduler.ServiceOutletDataEntryFileQueueScheduler;
import io.github.prepayments.service.ServiceOutletDataEntryFileService;
import io.github.prepayments.service.dto.ServiceOutletDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.schedulers.Schedulers;

@Service
@Transactional
@Slf4j
public class ServiceOutletFileUploadNotificationListener implements UploadNotificationsStreamListener<ServiceOutletFileUploadNotification> {


    private final ServiceOutletDataEntryFileQueueScheduler serviceOutletDataEntryFileQueueScheduler;
    private final ServiceOutletDataEntryFileService serviceOutletDataEntryFileService;

    public ServiceOutletFileUploadNotificationListener(final ServiceOutletDataEntryFileQueueScheduler serviceOutletDataEntryFileQueueScheduler,
                                                       final ServiceOutletDataEntryFileService serviceOutletDataEntryFileService) {
        this.serviceOutletDataEntryFileQueueScheduler = serviceOutletDataEntryFileQueueScheduler;
        this.serviceOutletDataEntryFileService = serviceOutletDataEntryFileService;
    }

    @Override
    @StreamListener(ServiceOutletFileUploadStreams.FILE_INPUT)
    public void handleDataStreamItem(@Payload ServiceOutletFileUploadNotification dataStreamItem) {

        log.debug("ServiceOutlet file id #: {} has been uploaded to the prepayments system", dataStreamItem.getId());

        // @formatter:off
        ServiceOutletDataEntryFileDTO result =
            serviceOutletDataEntryFileService
                .findOne(dataStreamItem.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Service Outlet data entry file id : " + dataStreamItem.getId() + " was not found!"));
        // @formatter: on

        result.setUploadSuccessful(true);

        // @formatter:off
        serviceOutletDataEntryFileQueueScheduler
            .deserializeAndEnqueue(result, dataStreamItem.getFileToken())
            .doOnCompleted(() -> result.setUploadSuccessful(true))
            .subscribeOn(Schedulers.io()).subscribe();
        // @formatter:on

        serviceOutletDataEntryFileService.save(result);
    }
}
