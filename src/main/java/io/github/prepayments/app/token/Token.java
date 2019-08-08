package io.github.prepayments.app.token;

public interface Token<T> {

    T generateHexToken();

    T generateBase64Token();

    T generateHexToken(int byteLength);

    T generateBase64Token(int byteLength);
}
