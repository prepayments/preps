package io.github.prepayments.app.services;

import io.github.prepayments.service.dto.AmortizationUploadDTO;

/**
 * This interface carries out operations on the child similar to the operation carried out on the parent.
 *
 * For this instance, once an amortization-upload is deleted or updated, all amortization-entries
 * will be deleted or updated accordingly
 *
 */
public interface AmortizationUploadCascader extends Cascader<AmortizationUploadDTO> {

    @Override
    void cascade(CascadedOperation cascadedOperation, AmortizationUploadDTO cascadable);
}
