package io.github.prepayments.app.messaging.services;

import rx.Observable;

/**
 * This interface takes a dataEntryFile creates a list of data items from that file and sends the data to the queue
 */
public interface DataEntryFileQueueScheduler<T, F> {

    /**
     * Enqueues a file into the MQ and returns a list of rowIndices as they appear in the ExcelViewModel, that have successfully been enqueued
     */
    Observable<Long> deserializeAndEnqueue(T dataEntryFile, F fileToken);
}
