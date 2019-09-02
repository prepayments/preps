package io.github.prepayments.app.services;

import io.github.prepayments.service.dto.AmortizationUploadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static io.github.prepayments.app.services.CascadedOperation.DELETE;
import static io.github.prepayments.app.services.CascadedOperation.UPDATE;

@Slf4j
@Service("simpleAmortizationUploadCascader")
@Transactional
public class SimpleAmortizationUploadCascader implements AmortizationUploadCascader, Cascader<AmortizationUploadDTO> {

    private final CascadeDeleteOperation<AmortizationUploadDTO> cascadeDeleteOperation;
    private final CascadeUpdateOperation<AmortizationUploadDTO> cascadeUpdateOperation;

    public SimpleAmortizationUploadCascader(final CascadeDeleteOperation<AmortizationUploadDTO> cascadeDeleteOperation, final CascadeUpdateOperation<AmortizationUploadDTO> cascadeUpdateOperation) {
        this.cascadeDeleteOperation = cascadeDeleteOperation;
        this.cascadeUpdateOperation = cascadeUpdateOperation;
    }

    @Override
    public void cascade(final CascadedOperation cascadedOperation, final AmortizationUploadDTO cascadable) {

        if(cascadable.getId() == null) {
            return;
        }

        if (cascadedOperation == DELETE) {
            log.info("Cascading deletion of amortizationEntries for amortizationUpload id # : {}", cascadable.getId());
            cascadeDeleteOperation.cascade(DELETE, cascadable);
        }

        if (cascadedOperation == UPDATE) {
            log.info("Cascading update of amortizationEntries for amortizationUpload id # : {}", cascadable.getId());
            cascadeUpdateOperation.cascade(UPDATE, cascadable);
        }
    }
}
