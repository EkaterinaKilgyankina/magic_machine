package com.coffee.magic_machine.domain.exception;

import lombok.Getter;

@Getter
public class NotAvailableException extends RuntimeException {
    public NotAvailableException(String message) {
        super(message);
    }
}
