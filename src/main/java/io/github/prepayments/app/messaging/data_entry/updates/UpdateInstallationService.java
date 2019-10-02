package io.github.prepayments.app.messaging.data_entry.updates;

/**
 *
 * @param <I> Data type for Entity identifier
 * @param <E> Entity containing the update
 * @param <U> Entity that has been updated
 */
public interface UpdateInstallationService<I, E, U> {

    U install(InstallableUpdate<I, E> installableUpdate) throws Throwable;
}
