package org.cat.fish.vesselsservice.exception.wrapper;

public class VesselNotFoundException extends RuntimeException {

    public VesselNotFoundException() {
        super();
    }

    public VesselNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VesselNotFoundException(String message) {
        super(message);
    }

    public VesselNotFoundException(Throwable cause) {
        super(cause);
    }
}
