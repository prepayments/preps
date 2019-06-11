package io.github.prepayments.app.messaging.filing.listener;

/**
 * This objects collects deserialized data from the queue and persists it in the database
 */
public interface PersistenceDataStreamListener<T> {

    void handleDataStreamItem(T dataStreamItem);
}
