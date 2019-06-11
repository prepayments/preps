package io.github.prepayments.app.messaging;

/**
 * This is an abstraction for sending a services into a queue
 *
 * @param <T> Type of services
 */
public interface MessageService<T> {

    /**
     * This method sends a services of type T into a queue destination and returns true if successful.
     *
     * @param message This is the item being sent
     * @return This is the ID of the item that has just been sent. Useful for polling success rate
     */
    long sendMessage(final T message);
}
