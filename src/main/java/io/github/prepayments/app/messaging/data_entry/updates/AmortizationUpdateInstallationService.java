package io.github.prepayments.app.messaging.data_entry.updates;

import io.github.prepayments.service.AmortizationEntryService;
import io.github.prepayments.service.UpdatedAmortizationEntryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("amortizationUpdateInstallationService")
public class AmortizationUpdateInstallationService
    implements UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> {

    private EntryService<Long,AmortizationEntryDTO> amortizationEntryService;
    private EntryService<Long, UpdatedAmortizationEntryDTO> updatedAmortizationEntryService;
    private UpdatedAmortizationEntryDTOMapper updatedAmortizationEntryDTOMapper;

    public AmortizationUpdateInstallationService(final EntryService<Long,AmortizationEntryDTO> amortizationEntryService, final EntryService<Long, UpdatedAmortizationEntryDTO> updatedAmortizationEntryService) {
        this.amortizationEntryService = amortizationEntryService;
        this.updatedAmortizationEntryService = updatedAmortizationEntryService;
    }

    @Override
    public AmortizationEntryDTO install(final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {

        // TODO Get amortization-entry DTO
        AmortizationEntryDTO updatedEntry =
            amortizationEntryService.findOne(installableUpdate.getEntryId())
                                    .orElseThrow(() -> new RuntimeException("Could not update entry id" + installableUpdate.getEntryId()));

        // TODO PERSIST backup
        UpdatedAmortizationEntryDTO persistedUpdatedEntry = updatedAmortizationEntryService.save(updatedAmortizationEntryDTOMapper.toUpdateBackupDTO(updatedEntry));
        log.debug("Updated entry has been backed up into the system with id  : {}", persistedUpdatedEntry.getId());

        // TODO update entry
        return amortizationEntryService.save(updatedAmortizationEntryDTOMapper.toUpdateDTO(updatedEntry, installableUpdate));
    }

}
