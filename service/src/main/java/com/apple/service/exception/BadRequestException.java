package com.apple.service.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException(final String message) {
        super(message, null, true, false);
    }
}
