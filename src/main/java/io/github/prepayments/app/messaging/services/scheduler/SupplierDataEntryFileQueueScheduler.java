package io.github.prepayments.app.messaging.services.scheduler;

import io.github.prepayments.app.excel.ExcelFileUtils;
import io.github.prepayments.app.messaging.services.DataEntryFileQueueScheduler;
import io.github.prepayments.app.messaging.services.SupplierDataEntryMessageService;
import io.github.prepayments.service.dto.SupplierDataEntryFileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rx.Observable;

import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service("supplierDataEntryFileQueueScheduler")
public class SupplierDataEntryFileQueueScheduler implements DataEntryFileQueueScheduler<SupplierDataEntryFileDTO, String> {

    private final SupplierDataEntryMessageService supplierDataEntryMessageService;

    public SupplierDataEntryFileQueueScheduler(final SupplierDataEntryMessageService supplierDataEntryMessageService) {
        this.supplierDataEntryMessageService = supplierDataEntryMessageService;
    }

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    @Override
    public Observable<Long> deserializeAndEnqueue(final SupplierDataEntryFileDTO dataEntryFile, final String fileToken) {

        log.info("Enqueueing supplier data read from the file id : {} ...", dataEntryFile.getId());

        // @formatter:off
        return Observable.from(
            ExcelFileUtils.deserializeSuppliersFile(
                dataEntryFile.getDataEntryFile(), fileToken)
                          .stream()
                          .map(supplierDataEntryMessageService::sendMessage)
                          .collect(Collectors.toList()));
        // @formatter:on
    }
}
