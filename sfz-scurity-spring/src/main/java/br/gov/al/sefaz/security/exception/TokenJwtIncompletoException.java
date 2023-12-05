package br.gov.al.sefaz.security.exception;

public class TokenJwtIncompletoException extends RuntimeException {

    public TokenJwtIncompletoException(String message) {
        super(message);
    }

}
