package io.github.prepayments.app.messaging;

/**
 * This interface checks if a model which is correlated to another has failed
 * to establish relationship
 */
public interface GetsOrphaned {

    boolean orphaned();
}
