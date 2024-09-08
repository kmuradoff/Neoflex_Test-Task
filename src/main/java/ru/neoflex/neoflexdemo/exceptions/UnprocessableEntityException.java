package ru.neoflex.neoflexdemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends RestException {
    public UnprocessableEntityException(String code, String message) {
        super(message, code, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}