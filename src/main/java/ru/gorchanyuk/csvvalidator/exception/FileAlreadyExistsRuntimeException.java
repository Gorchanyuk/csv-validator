package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileAlreadyExistsRuntimeException extends RuntimeException {
    public FileAlreadyExistsRuntimeException(String msg) {
        super(msg);
    }
}
