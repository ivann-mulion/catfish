package org.cat.fish.routeservice.exception.wrapper;

public class RouteNotFoundException extends RuntimeException {

    public RouteNotFoundException() {
        super();
    }

    public RouteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RouteNotFoundException(String message) {
        super(message);
    }

    public RouteNotFoundException(Throwable cause) {
        super(cause);
    }
}
