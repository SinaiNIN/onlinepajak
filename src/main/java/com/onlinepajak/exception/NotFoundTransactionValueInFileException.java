package com.onlinepajak.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundTransactionValueInFileException extends ResponseStatusException {
    public NotFoundTransactionValueInFileException(String fileName) {
        super(HttpStatus.NOT_FOUND, String.format("reason: cannot find resource in File: %s", fileName));

    }
}
