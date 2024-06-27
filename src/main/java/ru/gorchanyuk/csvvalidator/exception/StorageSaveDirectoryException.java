package ru.gorchanyuk.csvvalidator.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StorageSaveDirectoryException extends RuntimeException {

    public StorageSaveDirectoryException(String msg) {
        super(msg);
    }
}
