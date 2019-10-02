package io.github.prepayments.app.messaging.data_entry.updates;

/**
 * This is an interface for data objects which can be considered updates to existing system entries
 * @param <I> Data type for entry id, which could be either integer or long
 */
public interface InstallableUpdate<I, E> {

    /**
     *
     * @return The id of the update to be installed
     */
    I getEntryId();

    /**
     * Returns the update which is to be installed
     *
     * @return the update which is to be installed
     */
    E getEntity();
}
