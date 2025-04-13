package org.cat.fish.tripservice.exception.wrapper;

public class TripNotFoundException extends RuntimeException {

    public TripNotFoundException() {
        super();
    }

    public TripNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TripNotFoundException(String message) {
        super(message);
    }

    public TripNotFoundException(Throwable cause) {
        super(cause);
    }
}
