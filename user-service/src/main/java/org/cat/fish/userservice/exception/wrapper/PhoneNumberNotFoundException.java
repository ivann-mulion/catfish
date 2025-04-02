package org.cat.fish.userservice.exception.wrapper;

public class PhoneNumberNotFoundException extends RuntimeException {

    public PhoneNumberNotFoundException() {
        super();
    }

    public PhoneNumberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneNumberNotFoundException(String message) {
        super(message);
    }
}
