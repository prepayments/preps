package io.github.prepayments.app.messaging.data_entry.updates;

import io.github.jhipster.service.filter.StringFilter;
import io.github.prepayments.service.AmortizationUpdateFileQueryService;
import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.dto.AmortizationUpdateFileCriteria;
import io.github.prepayments.service.dto.AmortizationUpdateFileDTO;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component("amortizationUpdateInstallationService")
public class AmortizationUpdateInstallationService
    implements UpdateInstallationService<Long, AmortizationEntryUpdateDTO, AmortizationEntryDTO> {

    private final AmortizationUpdateFileQueryService amortizationUpdateFileQueryService;

    private final EntryService<Long,AmortizationEntryDTO> amortizationEntryService;
    private final EntryService<Long, UpdatedAmortizationEntryDTO> updatedAmortizationEntryService;
    private final UpdatedEntryDTOMapper<AmortizationEntryDTO, UpdatedAmortizationEntryDTO, Long, AmortizationEntryUpdateDTO> updatedAmortizationEntryDTOMapper;

    public AmortizationUpdateInstallationService(final AmortizationUpdateFileQueryService amortizationUpdateFileQueryService, final EntryService<Long, AmortizationEntryDTO> amortizationEntryService,
                                                 final EntryService<Long, UpdatedAmortizationEntryDTO> updatedAmortizationEntryService,
                                                 final UpdatedEntryDTOMapper<AmortizationEntryDTO, UpdatedAmortizationEntryDTO, Long, AmortizationEntryUpdateDTO> updatedAmortizationEntryDTOMapper) {
        this.amortizationUpdateFileQueryService = amortizationUpdateFileQueryService;
        this.amortizationEntryService = amortizationEntryService;
        this.updatedAmortizationEntryService = updatedAmortizationEntryService;
        this.updatedAmortizationEntryDTOMapper = updatedAmortizationEntryDTOMapper;
    }

    @Override
    public AmortizationEntryDTO install(final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {

        // TODO Make sure you update the right amortization-entry

        // TODO Get amortization-entry DTO
        AmortizationEntryDTO updatedEntry =
            amortizationEntryService.findOne(installableUpdate.getEntryId())
                                    .orElseThrow(() -> new RuntimeException("Could not update entry id" + installableUpdate.getEntryId()));

        // TODO Be sure to fetch this from appropriate file with appropriate file-token
        AmortizationUpdateFileDTO amortizationUpdateFile = getAmortizationUpdateFileDTO(installableUpdate);

        // TODO PERSIST backup
        UpdatedAmortizationEntryDTO persistedUpdatedEntry =
            updatedAmortizationEntryService.save(updatedAmortizationEntryDTOMapper.toUpdateBackupDTO(updatedEntry, amortizationUpdateFile::getReasonForUpdate));
        log.debug("Updated entry has been backed up into the system with id  : {}", persistedUpdatedEntry.getId());

        // TODO update entry
        return amortizationEntryService.save(updatedAmortizationEntryDTOMapper.toUpdateDTO(updatedEntry, installableUpdate));
    }

    private AmortizationUpdateFileDTO getAmortizationUpdateFileDTO(final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {
        return amortizationUpdateFileQueryService.findByCriteria(getUpdateFileCriteria(installableUpdate)).get(0);
    }

    private AmortizationUpdateFileCriteria getUpdateFileCriteria(final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {
        AmortizationUpdateFileCriteria amortizationUpdateFileCriteria = new AmortizationUpdateFileCriteria();

        StringFilter fileTokenFilter = new StringFilter();
        fileTokenFilter.setEquals(installableUpdate.getEntity().getOriginatingFileToken());

        amortizationUpdateFileCriteria.setFileToken(fileTokenFilter);

        return amortizationUpdateFileCriteria;
    }

}
