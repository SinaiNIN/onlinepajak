package com.onlinepajak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileNullException extends ResponseStatusException {
    public FileNullException() {
        super(HttpStatus.BAD_REQUEST,
                String.format("reason: file cannot be null"));
    }
}
