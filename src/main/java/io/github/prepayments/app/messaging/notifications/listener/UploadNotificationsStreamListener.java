package io.github.prepayments.app.messaging.notifications.listener;

/**
 * Listens to data streams and deserializes the data stream received sending it back to the queue bit by bit
 *
 * @param <T> Data type
 */
public interface UploadNotificationsStreamListener<T> {

    void handleDataStreamItem(T dataStreamItem);
}
