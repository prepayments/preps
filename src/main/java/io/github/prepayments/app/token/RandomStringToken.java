package io.github.prepayments.app.token;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

import static io.github.prepayments.app.AppConstants.DEFAULT_TOKEN_BYTE_LENGTH;

/**
 * Used as token generator to include randomness
 */
@Component("randomStringToken")
public class RandomStringToken implements Token<String> {

    @Override
    public String generateHexToken() {

        return this.generateHexToken(DEFAULT_TOKEN_BYTE_LENGTH);
    }

    @Override
    public String generateBase64Token() {

        return this.generateBase64Token(DEFAULT_TOKEN_BYTE_LENGTH);
    }

    //generateRandomHexToken(16) -> 2189df7475e96aa3982dbeab266497cd
    @Override
    public String generateHexToken(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return new BigInteger(1, token).toString(16); //hex encoding
    }

    //generateRandomBase64Token(16) -> EEcCCAYuUcQk7IuzdaPzrg
    @Override
    public String generateBase64Token(int byteLength) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] token = new byte[byteLength];
        secureRandom.nextBytes(token);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(token); //base64 encoding
    }

}
