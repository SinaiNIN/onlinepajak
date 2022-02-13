package com.onlinepajak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileTypeException extends ResponseStatusException {
    public FileTypeException() {
        super(HttpStatus.BAD_REQUEST, "reason: file accepted only type: csv");
    }
}
