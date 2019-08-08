package io.github.prepayments.app.token;

public interface FileTokenProvider {

    String getFileToken(Tokenizable tokenizable);
}
