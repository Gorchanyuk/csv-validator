package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StorageSaveFileException extends RuntimeException {

    public StorageSaveFileException(String msg) {
        super(msg);
    }
}
