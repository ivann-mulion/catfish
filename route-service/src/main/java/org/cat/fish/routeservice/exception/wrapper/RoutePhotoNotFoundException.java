package org.cat.fish.routeservice.exception.wrapper;

public class RoutePhotoNotFoundException extends RuntimeException {
    public RoutePhotoNotFoundException() {
        super();
    }

    public RoutePhotoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoutePhotoNotFoundException(String message) {
        super(message);
    }

    public RoutePhotoNotFoundException(Throwable cause) {
        super(cause);
    }
}
