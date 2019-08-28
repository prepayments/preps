package io.github.prepayments.app.token;

/**
 * Interface for all data models with originatingFileToken field
 *
 * @param <T>
 */
public interface IsTokenized<T> {

    T originatingFileToken();

    IsTokenized<T> originatingFileToken(T token);
}
