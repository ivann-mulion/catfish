package org.cat.fish.capitanservice.exception.wrapper;

public class CaptainNotFoundException extends RuntimeException {
    public CaptainNotFoundException() {
        super();
    }

    public CaptainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CaptainNotFoundException(String message) {
        super(message);
    }

    public CaptainNotFoundException(Throwable cause) {
        super(cause);
    }
}
