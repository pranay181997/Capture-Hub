package org.example.exceptions;

public class CameraSystemException extends RuntimeException {
    public CameraSystemException(String message) {
        super(message);
    }

    public CameraSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
