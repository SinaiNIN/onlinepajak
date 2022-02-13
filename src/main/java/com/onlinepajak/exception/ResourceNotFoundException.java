package com.onlinepajak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceNotFoundException extends ResponseStatusException {
    public ResourceNotFoundException(final String inputString, final long id) {
        super(HttpStatus.NOT_FOUND,
                String.format("reason: cannot find resource by: %s with identifier: %s", inputString, id));
    }
}