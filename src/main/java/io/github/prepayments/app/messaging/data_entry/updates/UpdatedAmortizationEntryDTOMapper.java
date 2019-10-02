package io.github.prepayments.app.messaging.data_entry.updates;

import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import org.springframework.stereotype.Component;

@Component
public class UpdatedAmortizationEntryDTOMapper {


    public UpdatedAmortizationEntryDTO toUpdateBackupDTO(final AmortizationEntryDTO updatedEntry) {
        return null;
    }

    public AmortizationEntryDTO toUpdateDTO(final AmortizationEntryDTO updatedEntry, final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {
        return null;
    }
}
