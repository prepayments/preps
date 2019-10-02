package io.github.prepayments.app.messaging.data_entry.updates;

public interface UpdatedEntryDTOMapper<Target, Backup, Identifier, Installation> {

    Backup toUpdateBackupDTO(Target updatedEntry, ReasonableUpdate narratableUpdate);

    Target toUpdateDTO(Target updatedEntry, InstallableUpdate<Identifier, Installation> installableUpdate);
}
