package org.players.exception;

public class InvalidPlayerException extends Exception {

    public InvalidPlayerException(String message) {
        super(message);
    }

    public InvalidPlayerException(String message, Throwable e) {
        super(message, e);
    }
}
