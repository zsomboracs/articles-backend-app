package com.apple.service.web;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({IllegalArgumentException.class, MissingServletRequestParameterException.class})
    protected ErrorMessage handleBadRequests(final Exception ex, final WebRequest request) {
        LOGGER.warn("operation=handleBadRequests, error={}", ex.getMessage());

        return new ErrorMessage("Bad Request: " + ex.getMessage());
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @org.springframework.web.bind.annotation.ExceptionHandler({ClientAbortException.class})
    protected ErrorMessage handleClientExceptions(final Exception ex, final WebRequest request) {
        LOGGER.warn("operation=handleClientExceptions, error={}", ex.getMessage());

        return new ErrorMessage("Remote client aborted: " + ex.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorMessage handleOthers(final Exception ex, final WebRequest request) {
        LOGGER.error("operation=handleOthers", ex);

        return new ErrorMessage("Internal server error");
    }

    public static class ErrorMessage {
        private final String message;

        ErrorMessage(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
