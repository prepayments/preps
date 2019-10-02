package io.github.prepayments.app.messaging.data_entry.updates;

import io.github.prepayments.service.dto.AmortizationEntryDTO;
import io.github.prepayments.service.dto.AmortizationEntryUpdateDTO;
import io.github.prepayments.service.dto.UpdatedAmortizationEntryDTO;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.time.LocalDate;

@Component("updatedAmortizationEntryDTOMapper")
public class UpdatedAmortizationEntryDTOMapper
    implements UpdatedEntryDTOMapper<AmortizationEntryDTO, UpdatedAmortizationEntryDTO, Long, AmortizationEntryUpdateDTO> {


    @Override
    public UpdatedAmortizationEntryDTO toUpdateBackupDTO(final AmortizationEntryDTO updatedEntry, final ReasonableUpdate reasonableUpdate) {
        return UpdatedAmortizationEntryDTO.builder()
                                          .amortizationDate(updatedEntry.getAmortizationDate())
                                          .amortizationAmount(updatedEntry.getAmortizationAmount())
                                          .particulars(updatedEntry.getParticulars())
                                          .prepaymentServiceOutlet(updatedEntry.getPrepaymentServiceOutlet())
                                          .prepaymentAccountNumber(updatedEntry.getPrepaymentAccountNumber())
                                          .amortizationServiceOutlet(updatedEntry.getAmortizationServiceOutlet())
                                          .amortizationAccountNumber(updatedEntry.getAmortizationAccountNumber())
                                          .originatingFileToken(updatedEntry.getOriginatingFileToken())
                                          .amortizationTag(updatedEntry.getAmortizationTag())
                                          .orphaned(updatedEntry.getOrphaned())
                                          .dateOfUpdate(LocalDate.now())
                                          .reasonForUpdate(reasonableUpdate.getReasonForUpdate())
                                          .prepaymentEntryId(updatedEntry.getPrepaymentEntryId())
                                          .build();
    }

    @Override
    public AmortizationEntryDTO toUpdateDTO(AmortizationEntryDTO updatedEntry, final InstallableUpdate<Long, AmortizationEntryUpdateDTO> installableUpdate) {

        AmortizationEntryUpdateDTO installation = installableUpdate.getEntity();

        if(updatedEntry.getId() == null){
            throw new RuntimeException("The amortization-entry to be updated does not exist");
        }

        if (!updatedEntry.getId().equals(installation.getAmortizationEntryId())) {
            String message = MessageFormat.format("Attempt to update the wrong entry id  : {1}, update installation id : {2} failed",updatedEntry.getId(), installation.getAmortizationEntryId());
            throw new RuntimeException(message);
        }

        updatedEntry.setAmortizationDate(installation.getAmortizationDate());
        updatedEntry.setAmortizationAmount(installation.getAmortizationAmount());
        updatedEntry.setParticulars(installation.getParticulars());
        updatedEntry.setPrepaymentServiceOutlet(installation.getPrepaymentServiceOutlet());
        updatedEntry.setPrepaymentAccountNumber(installation.getPrepaymentAccountNumber());
        updatedEntry.setAmortizationServiceOutlet(installation.getAmortizationServiceOutlet());
        updatedEntry.setAmortizationAccountNumber(installation.getAmortizationAccountNumber());
        updatedEntry.setOriginatingFileToken(installation.getOriginatingFileToken());
        updatedEntry.setAmortizationTag(installation.getAmortizationTag());
        updatedEntry.setOrphaned(installation.getOrphaned());
        updatedEntry.setPrepaymentEntryId(installation.getPrepaymentEntryId());

        return updatedEntry;
    }
}
